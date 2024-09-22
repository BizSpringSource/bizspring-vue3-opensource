package cn.bizspring.cloud.order.admin.modes;

import cn.bizspring.cloud.order.common.entity.PaymentLog;
import cn.bizspring.cloud.order.common.modes.Payment;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * Mode - 支付宝
 * 
 */
@Component("alipayWapPayment")
public class AlipayWapPayment extends Payment {

	@Override
	public String getName() {
		return "alipay(Wap)";
	}

	@Override
	public boolean supports(HttpServletRequest request) {
		return true;
	}

	@Override
	public void postPayHandle(Payment payment, PaymentLog paymentLog, String paymentDescription, String extra, boolean isPaySuccess, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (StringUtils.equals(request.getParameter("notify_type"), "trade_status_sync")) {
			OutputStream outputStream = response.getOutputStream();
			IOUtils.write("success", outputStream, "UTF-8");
			outputStream.flush();
		} else {
			super.postPayHandle(payment, paymentLog, paymentDescription, extra, isPaySuccess, request, response);
		}
	}
}