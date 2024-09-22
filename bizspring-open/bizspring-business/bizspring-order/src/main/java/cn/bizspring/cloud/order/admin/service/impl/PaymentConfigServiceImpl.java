package cn.bizspring.cloud.order.admin.service.impl;

import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.order.admin.mapper.PaymentConfigMapper;
import cn.bizspring.cloud.order.admin.service.PaymentConfigService;
import cn.bizspring.cloud.order.common.entity.PaymentConfig;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 支付方式配置
 * 
 */
@Service
@AllArgsConstructor
public class PaymentConfigServiceImpl extends BizServiceImpl<PaymentConfig> implements PaymentConfigService {

	private PaymentConfigMapper paymentConfigMapper;

	@Override
	@Transactional
	@CacheEvict(value = "paymentConfig", allEntries = true)
	public boolean save(PaymentConfig config) {
		return super.save(config);
	}

	@Override
	@Transactional
	@CacheEvict(value = "paymentConfig", allEntries = true)
	public PaymentConfig update(PaymentConfig config) {
		return super.update(config);
	}

	@Override
	@Transactional
	@CacheEvict(value = "paymentConfig", allEntries = true)
	public PaymentConfig update(PaymentConfig config, String... ignoreProperties) {
		return super.update(config, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "paymentConfig", allEntries = true)
	public void delete(String id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "paymentConfig", allEntries = true)
	public void delete(String... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "paymentConfig", allEntries = true)
	public void delete(PaymentConfig config) {
		super.delete(config);
	}

}