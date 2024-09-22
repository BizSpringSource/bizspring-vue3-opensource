package cn.bizspring.cloud.common.data.cache;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.lang.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 扩展redis-cache支持注解cacheName添加超时时间
 *
 * @author
 */
@Configuration
@AutoConfigureAfter({RedisAutoConfiguration.class})
@ConditionalOnBean({RedisConnectionFactory.class})
@ConditionalOnMissingBean({CacheManager.class})
@EnableConfigurationProperties(CacheProperties.class)
public class RedisCacheAutoConfiguration {
	private final CacheProperties cacheProperties;
	private final CacheManagerCustomizers customizerInvoker;
	@Nullable
	private final RedisCacheConfiguration redisCacheConfiguration;

	RedisCacheAutoConfiguration(CacheProperties cacheProperties,
								CacheManagerCustomizers customizerInvoker,
								ObjectProvider<RedisCacheConfiguration> redisCacheConfiguration) {
		this.cacheProperties = cacheProperties;
		this.customizerInvoker = customizerInvoker;
		this.redisCacheConfiguration = redisCacheConfiguration.getIfAvailable();
	}

	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory, ResourceLoader resourceLoader) {
		DefaultRedisCacheWriter redisCacheWriter = new DefaultRedisCacheWriter(connectionFactory);
		RedisCacheConfiguration cacheConfiguration = this.determineConfiguration(resourceLoader.getClassLoader());
		List<String> cacheNames = this.cacheProperties.getCacheNames();
		Map<String, RedisCacheConfiguration> initialCaches = new LinkedHashMap<>();
		if (!cacheNames.isEmpty()) {
			Map<String, RedisCacheConfiguration> cacheConfigMap = new LinkedHashMap<>(cacheNames.size());
			cacheNames.forEach(it -> cacheConfigMap.put(it, cacheConfiguration));
			initialCaches.putAll(cacheConfigMap);
		}
		RedisAutoCacheManager cacheManager = new RedisAutoCacheManager(redisCacheWriter, cacheConfiguration,
			initialCaches, true);
		cacheManager.setTransactionAware(false);
		return this.customizerInvoker.customize(cacheManager);
	}

	private RedisCacheConfiguration determineConfiguration(ClassLoader classLoader) {
		if (this.redisCacheConfiguration != null) {
			return this.redisCacheConfiguration;
		} else {
			CacheProperties.Redis redisProperties = this.cacheProperties.getRedis();
			RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
			config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer(classLoader)));
			if (redisProperties.getTimeToLive() != null) {
				config = config.entryTtl(redisProperties.getTimeToLive());
			}

			if (redisProperties.getKeyPrefix() != null) {
				config = config.prefixKeysWith(redisProperties.getKeyPrefix());
			}

			if (!redisProperties.isCacheNullValues()) {
				config = config.disableCachingNullValues();
			}

			if (!redisProperties.isUseKeyPrefix()) {
				config = config.disableKeyPrefix();
			}

			return config;
		}
	}
}
