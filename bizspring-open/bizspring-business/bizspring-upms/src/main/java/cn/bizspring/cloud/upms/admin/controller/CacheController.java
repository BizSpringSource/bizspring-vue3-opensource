package cn.bizspring.cloud.upms.admin.controller;

import cn.bizspring.cloud.common.core.util.R;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.*;

/**
 * 缓存监控
 *
 * @author bizspring
 */
@RestController
@AllArgsConstructor
@RequestMapping("/cache")
public class CacheController {
	private final RedisTemplate<String, String> redisTemplate;

	@Value("${redisCacheUrl}")
	private String[] redisCacheUrl;


	@PreAuthorize("@ato.hasAuthority('sys:cache:index')")
	@GetMapping
	public ResponseEntity getInfo() {
		Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info());
		Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
		Object dbSize = redisTemplate.execute((RedisCallback<Object>) connection -> connection.dbSize());

		Map<String, Object> result = new HashMap<>(3);
		Map<String, String> dat = new HashMap<>(2);
		info.stringPropertyNames().forEach(key -> {
			String property = info.getProperty(key);
			dat.put(StringUtils.removeStart(key, redisCacheUrl[0]+"."),property);
		});
		result.put("info", dat);
		result.put("dbSize", dbSize);
		List<Map<String, String>> pieList = new ArrayList<>();
		commandStats.stringPropertyNames().forEach(key -> {
			Map<String, String> data = new HashMap<>(2);
			String property = commandStats.getProperty(key);
			data.put("name", StringUtils.removeStart(key, "cmdstat_"));
			data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
			pieList.add(data);
		});
		result.put("commandStats", pieList);
		return R.ok(result);
	}
}
