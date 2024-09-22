package cn.bizspring.cloud.upms.admin.service.impl;

import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.upms.admin.mapper.ConfigMapper;
import cn.bizspring.cloud.upms.admin.service.ConfigService;
import cn.bizspring.cloud.upms.common.entity.Config;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 配置
 * 
 */
@Service
@AllArgsConstructor
public class ConfigServiceImpl extends BizServiceImpl<Config> implements ConfigService {

	private ConfigMapper configMapper;

	@Override
	@Transactional(readOnly = true)
	@Cacheable("config")
	public Config findByModeId(String modeId) {
		return configMapper.findByProperty("mode_id", modeId);
	}

	@Override
	@Transactional
	@CacheEvict(value = "config", allEntries = true)
	public boolean save(Config config) {
		return super.save(config);
	}

	@Override
	@Transactional
	@CacheEvict(value = "config", allEntries = true)
	public Config update(Config config) {
		return super.update(config);
	}

	@Override
	@Transactional
	@CacheEvict(value = "config", allEntries = true)
	public Config update(Config config, String... ignoreProperties) {
		return super.update(config, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "config", allEntries = true)
	public void delete(String id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "config", allEntries = true)
	public void delete(String... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "config", allEntries = true)
	public void delete(Config config) {
		super.delete(config);
	}

}