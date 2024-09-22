package cn.bizspring.cloud.common.data.cache;

import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.lang.Nullable;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * {@link RedisCacheWriter} implementation capable of reading/writing binary data from/to Redis in {@literal standalone}
 * and {@literal cluster} environments. Works upon a given {@link RedisConnectionFactory} to obtain the actual
 * {@link RedisConnection}. <br />
 * {@link DefaultRedisCacheWriter} can be used in
 * {@link RedisCacheWriter#lockingRedisCacheWriter(RedisConnectionFactory) locking} or
 * {@link RedisCacheWriter#nonLockingRedisCacheWriter(RedisConnectionFactory) non-locking} mode. While
 * {@literal non-locking} aims for maximum performance it may result in overlapping, non atomic, command execution for
 * operations spanning multiple Redis interactions like {@code putIfAbsent}. The {@literal locking} counterpart prevents
 * command overlap by setting an explicit lock key and checking against presence of this key which leads to additional
 * requests and potential command wait times.
 *
 * @author Christoph Strobl
 * @author Mark Paluch
 * @since 2.0
 */
class DefaultRedisCacheWriter implements RedisCacheWriter {

	private final RedisConnectionFactory connectionFactory;
	private final Duration sleepTime;

	/**
	 * @param connectionFactory must not be {@literal null}.
	 */
	DefaultRedisCacheWriter(RedisConnectionFactory connectionFactory) {
		this(connectionFactory, Duration.ZERO);
	}

	/**
	 * @param connectionFactory must not be {@literal null}.
	 * @param sleepTime         sleep time between lock request attempts. Must not be {@literal null}. Use {@link Duration#ZERO}
	 *                          to disable locking.
	 */
	private DefaultRedisCacheWriter(RedisConnectionFactory connectionFactory, Duration sleepTime) {
		this.connectionFactory = connectionFactory;
		this.sleepTime = sleepTime;
	}

	@Override
	public void put(String name, byte[] key, byte[] value, @Nullable Duration ttl) {
		execute(name, connection -> {

			if (shouldExpireWithin(ttl)) {
				connection.set(key, value, Expiration.from(ttl.toMillis(), TimeUnit.MILLISECONDS), SetOption.upsert());
			} else {
				connection.set(key, value);
			}

			return "OK";
		});
	}


	@Override
	public byte[] get(String name, byte[] key) {
		return execute(name, connection -> connection.get(key));
	}


	@Override
	public byte[] putIfAbsent(String name, byte[] key, byte[] value, @Nullable Duration ttl) {
		return execute(name, connection -> {

			if (isLockingCacheWriter()) {
				doLock(name, connection);
			}

			try {
				if (connection.setNX(key, value)) {

					if (shouldExpireWithin(ttl)) {
						connection.pExpire(key, ttl.toMillis());
					}
					return null;
				}

				return connection.get(key);
			} finally {

				if (isLockingCacheWriter()) {
					doUnlock(name, connection);
				}
			}
		});
	}


	@Override
	public void remove(String name, byte[] key) {
		execute(name, connection -> connection.del(key));
	}


	@Override
	public void clean(String name, byte[] pattern) {
		execute(name, connection -> {

			boolean wasLocked = false;

			try {

				if (isLockingCacheWriter()) {
					doLock(name, connection);
					wasLocked = true;
				}

				byte[][] keys = Optional.ofNullable(connection.keys(pattern)).orElse(Collections.emptySet())
					.toArray(new byte[0][]);

				if (keys.length > 0) {
					connection.del(keys);
				}
			} finally {

				if (wasLocked && isLockingCacheWriter()) {
					doUnlock(name, connection);
				}
			}

			return "OK";
		});
	}

	/**
	 * Explicitly set a write lock on a cache.
	 *
	 * @param name the name of the cache to lock.
	 */
	void lock(String name) {
		execute(name, connection -> doLock(name, connection));
	}

	/**
	 * Explicitly remove a write lock from a cache.
	 *
	 * @param name the name of the cache to unlock.
	 */
	void unlock(String name) {
		executeLockFree(connection -> doUnlock(name, connection));
	}

	private Boolean doLock(String name, RedisConnection connection) {
		return connection.setNX(createCacheLockKey(name), new byte[0]);
	}

	private Long doUnlock(String name, RedisConnection connection) {
		return connection.del(createCacheLockKey(name));
	}

	boolean doCheckLock(String name, RedisConnection connection) {
		return connection.exists(createCacheLockKey(name));
	}

	/**
	 * @return {@literal true} if {@link RedisCacheWriter} uses locks.
	 */
	private boolean isLockingCacheWriter() {
		return !sleepTime.isZero() && !sleepTime.isNegative();
	}

	private <T> T execute(String name, Function<RedisConnection, T> callback) {

		RedisConnection connection = connectionFactory.getConnection();
		try {

			checkAndPotentiallyWaitUntilUnlocked(name, connection);
			return callback.apply(connection);
		} finally {
			connection.close();
		}
	}

	private void executeLockFree(Consumer<RedisConnection> callback) {

		RedisConnection connection = connectionFactory.getConnection();

		try {
			callback.accept(connection);
		} finally {
			connection.close();
		}
	}

	private void checkAndPotentiallyWaitUntilUnlocked(String name, RedisConnection connection) {

		if (!isLockingCacheWriter()) {
			return;
		}

		try {

			while (doCheckLock(name, connection)) {
				Thread.sleep(sleepTime.toMillis());
			}
		} catch (InterruptedException ex) {

			// Re-interrupt current thread, to allow other participants to react.
			Thread.currentThread().interrupt();

			throw new PessimisticLockingFailureException(String.format("Interrupted while waiting to unlock cache %s", name),
				ex);
		}
	}

	private static boolean shouldExpireWithin(@Nullable Duration ttl) {
		return ttl != null && !ttl.isZero() && !ttl.isNegative();
	}

	private static byte[] createCacheLockKey(String name) {
		return (name + "~lock").getBytes(StandardCharsets.UTF_8);
	}
}

