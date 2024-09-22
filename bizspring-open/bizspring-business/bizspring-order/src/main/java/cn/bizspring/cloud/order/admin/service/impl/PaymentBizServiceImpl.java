package cn.bizspring.cloud.order.admin.service.impl;

import cn.bizspring.cloud.order.admin.service.PaymentBizService;
import cn.bizspring.cloud.order.common.modes.Payment;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Service - 支付方式 to goods
 * 
 */
@AllArgsConstructor
@Service
public class PaymentBizServiceImpl implements PaymentBizService {

	private List<Payment> payments = new ArrayList<>();
	private Map<String, Payment> paymentMap = new HashMap<>();

	@Override
	public List<Payment> getPayments(final boolean isEnabled) {
		List<Payment> result = new ArrayList<>();
		CollectionUtils.select(payments, new Predicate() {
			public boolean evaluate(Object object) {
				Payment payment = (Payment) object;
				return true;
			}
		}, result);
		Collections.sort(result);
		return result;
	}

	@Override
	public List<Payment> getActivePayments(final HttpServletRequest request) {
		List<Payment> result = new ArrayList<>();
		CollectionUtils.select(getPayments(true), new Predicate() {
			public boolean evaluate(Object object) {
				Payment payment = (Payment) object;
				return payment.supports(request);
			}
		}, result);
		return result;
	}

	@Override
	public Payment getPayment(String id) {
		return paymentMap.get(id);
	}

}