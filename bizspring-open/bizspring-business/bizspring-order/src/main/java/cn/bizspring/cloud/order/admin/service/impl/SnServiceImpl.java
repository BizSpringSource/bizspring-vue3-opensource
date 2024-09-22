package cn.bizspring.cloud.order.admin.service.impl;

import cn.bizspring.cloud.common.core.entity.Sn;
import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.common.core.util.FUtils;
import cn.bizspring.cloud.order.admin.mapper.SnMapper;
import cn.bizspring.cloud.order.admin.service.SnService;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;

/**
 * Service - 序列号
 * 
 */
//@AllArgsConstructor //打开这个
@Service
public class SnServiceImpl extends BizServiceImpl<Sn> implements SnService, InitializingBean {

	@Autowired
	private SnMapper snMapper;

	/**
	 * 商品编号生成器
	 */
	private HiloOptimizer goodsHiloOptimizer;

	/**
	 * 订单编号生成器
	 */
	private HiloOptimizer orderHiloOptimizer;

	/**
	 * 订单支付编号生成器
	 */
	private HiloOptimizer orderPaymentHiloOptimizer;

	/**
	 * 订单发货编号生成器
	 */
	private HiloOptimizer orderShippingHiloOptimizer;

	/**
	 * 支付记录编号生成器
	 */
	private HiloOptimizer paymentLogHiloOptimizer;

	/**
	 * 平台服务编号生成器
	 */
	private HiloOptimizer platformServiceHiloOptimizer;

	@PersistenceContext
	private EntityManager entityManager;
	private String goodsPrefix;
	private int goodsMaxLo;
	private String orderPrefix;
	private int orderMaxLo;
	private String orderPaymentPrefix;
	private int orderPaymentMaxLo;
	private String orderRefundsPrefix;
	private int orderRefundsMaxLo;
	private String orderShippingPrefix;
	private int orderShippingMaxLo;
	private String orderReturnsPrefix;
	private int orderReturnsMaxLo;
	private String paymentLogPrefix;
	private int paymentLogMaxLo;
	private String platformServicePrefix;
	private int platformServiceMaxLo;

	/**
	 * 初始化
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		goodsHiloOptimizer = new HiloOptimizer(Sn.Type.GOODS, goodsPrefix, goodsMaxLo);
		orderHiloOptimizer = new HiloOptimizer(Sn.Type.ORDER, orderPrefix, orderMaxLo);
		orderPaymentHiloOptimizer = new HiloOptimizer(Sn.Type.ORDER_PAYMENT, orderPaymentPrefix, orderPaymentMaxLo);
		orderShippingHiloOptimizer = new HiloOptimizer(Sn.Type.ORDER_SHIPPING, orderShippingPrefix, orderShippingMaxLo);
		paymentLogHiloOptimizer = new HiloOptimizer(Sn.Type.PAYMENT_LOG, paymentLogPrefix, paymentLogMaxLo);
		platformServiceHiloOptimizer = new HiloOptimizer(Sn.Type.PLATFORM_SERVICE, platformServicePrefix, platformServiceMaxLo);
	}

	/**
	 * 生成序列号
	 * 
	 * @param type
	 *            类型
	 * @return 序列号
	 */
	@Override
	public String generate(Sn.Type type) {
		switch (type) {
		case GOODS:
			return goodsHiloOptimizer.generate();
		case ORDER:
			return orderHiloOptimizer.generate();
		case ORDER_PAYMENT:
			return orderPaymentHiloOptimizer.generate();
		case ORDER_SHIPPING:
			return orderShippingHiloOptimizer.generate();
		case PAYMENT_LOG:
			return paymentLogHiloOptimizer.generate();
		case PLATFORM_SERVICE:
			return platformServiceHiloOptimizer.generate();
		}
		return null;
	}

	/**
	 * 获取末值
	 * 
	 * @param type
	 *            类型
	 * @return 末值
	 */
	private long getLastValue(Sn.Type type) {
		Sn sn = snMapper.findByType(type.ordinal());
		long lastValue = sn.getLastValue();
		sn.setLastValue(lastValue + 1);
		super.update(sn);
		return lastValue;
	}

	/**
	 * 高低位算法生成器
	 */
	private class HiloOptimizer {

		/**
		 * 类型
		 */
		private Sn.Type type;

		/**
		 * 前缀
		 */
		private String prefix;

		/**
		 * 最大低位值
		 */
		private int maxLo;

		/**
		 * 低位值
		 */
		private int lo;

		/**
		 * 高位值
		 */
		private long hi;

		/**
		 * 末值
		 */
		private long lastValue;

		/**
		 * 构造方法
		 * 
		 * @param type
		 *            类型
		 * @param prefix
		 *            前缀
		 * @param maxLo
		 *            最大低位值
		 */
		HiloOptimizer(Sn.Type type, String prefix, int maxLo) {
			this.type = type;
			this.prefix = prefix != null ? prefix.replace("{", "${") : StringUtils.EMPTY;
			this.maxLo = maxLo;
			this.lo = maxLo + 1;
		}

		/**
		 * 生成序列号
		 * 
		 * @return 序列号
		 */
		public synchronized String generate() {
			if (lo > maxLo) {
				lastValue = getLastValue(type);
				lo = lastValue == 0 ? 1 : 0;
				hi = lastValue * (maxLo + 1);
			}
			try {
				return FUtils.process(prefix) + (hi + lo++);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (TemplateException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	public SnMapper getSnMapper() {
		return snMapper;
	}

	public void setSnMapper(SnMapper snMapper) {
		this.snMapper = snMapper;
	}
}