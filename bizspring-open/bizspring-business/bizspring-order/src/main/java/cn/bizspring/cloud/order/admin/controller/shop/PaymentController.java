package cn.bizspring.cloud.order.admin.controller.shop;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.order.admin.service.PaymentBizService;
import cn.bizspring.cloud.order.admin.service.PaymentLogService;
import cn.bizspring.cloud.order.common.entity.PaymentItem;
import cn.bizspring.cloud.order.common.entity.PaymentLog;
import cn.bizspring.cloud.order.common.modes.Payment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import cn.bizspring.cloud.common.core.util.R;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Controller - 支付
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "payment", tags = "商城 - 支付")
@RestController("memberPaymentController")
@RequestMapping("/member/payment")
public class PaymentController extends BizController {

	private PaymentLogService paymentLogService;
	private PaymentBizService paymentBizService;

	/**
	 * 支付前 生成支付记录
	 */
	@ApiOperation(value = "首页", notes = "首页")
	@ApiOperationSupport(order = 2)
	@PostMapping("/load")
	public ResponseEntity load( @RequestBody PaymentItemListForm paymentItemListForm,@RequestParam("paymentId") String paymentId,@RequestParam("rePayUrl")  String rePayUrl) {

		Map<String, Object> data = new HashMap<>();
		Payment payment = paymentBizService.getPayment(paymentId);
		List<PaymentItem> paymentItems = paymentItemListForm != null ? paymentItemListForm.getPaymentItemList() : null;
		if (CollectionUtils.isEmpty(paymentItems)) {
			return R.invalidEntity();
		}
		PaymentLog paymentLog = null;
		//多个订单，合并支付
		if (paymentItems.size() > 1) {
			Set<PaymentLog.LineItem> lineItems = new HashSet<>();
			for (PaymentItem paymentItem : paymentItems) {
				PaymentLog.LineItem lineItem = paymentLogService.generate(paymentItem);
				if (lineItem != null) {
					lineItems.add(lineItem);
				}
			}
			paymentLog = paymentLogService.generateParent(lineItems, payment, rePayUrl);
		//单个订单支付项目，单支付
		} else {
			PaymentItem paymentItem = paymentItems.get(0);
			PaymentLog.LineItem lineItem = paymentLogService.generate(paymentItem);
			paymentLog = paymentLogService.generate(lineItem, payment, rePayUrl);
		}
		data.put("paymentLog",paymentLog);
		return R.ok(data);
	}

	/**
	 * 完成支付
	 */
	@ApiOperation(value = "支付后处理", notes = "支付后处理")
	@ApiOperationSupport(order = 2)
	@RequestMapping({ "/test_post_pay_{paymentLogSn:[^_]+}", "/test_post_pay_{paymentLogSn:[^_]+}_{extra}" })
	public ResponseEntity testPostPay(@PathVariable String paymentLogSn, @PathVariable(required = false) String extra, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PaymentLog paymentLog = paymentLogService.findBySn(paymentLogSn);
		if (paymentLog == null) {
			return R.invalidEntity();
		}
		paymentLogService.handle(paymentLog);
		return R.ok();
	}

	/**
	 * FormBean - 支付项
	 * 
	 */
	public static class PaymentItemListForm {

		/**
		 * 支付项
		 */
		private List<PaymentItem> paymentItemList;

		/**
		 * 获取支付项
		 * 
		 * @return 支付项
		 */
		public List<PaymentItem> getPaymentItemList() {
			return paymentItemList;
		}

		/**
		 * 设置支付项
		 * 
		 * @param paymentItemList
		 *            支付项
		 */
		public void setPaymentItemList(List<PaymentItem> paymentItemList) {
			this.paymentItemList = paymentItemList;
		}
	}

}