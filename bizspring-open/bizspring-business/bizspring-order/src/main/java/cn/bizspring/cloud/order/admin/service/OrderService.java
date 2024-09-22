package cn.bizspring.cloud.order.admin.service;

import cn.bizspring.cloud.common.core.service.BizService;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.goods.common.entity.Goods;
import cn.bizspring.cloud.store.common.entity.PaymentPattern;
import cn.bizspring.cloud.store.common.entity.ShippingMethod;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.Address;
import cn.bizspring.cloud.upms.common.entity.User;
import cn.bizspring.cloud.order.common.entity.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Service - 订单
 * 
 */
public interface OrderService extends BizService<Order> {

	/**
	 * 根据编号查找订单
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 订单，若不存在则返回null
	 */
	Order findBySn(String sn);

	Page<Order> findOrderPaymentedPage(Store currentStore, Boolean isSuccess, Boolean hasExpired, String paymentId, Date beginDate, Date endDate,Pageable pageable);


		/**
		 * 查找订单分页
		 *
		 * @param isPendingReceive
		 *            是否等待收款
		 * @param isAllocatedStock
		 *            是否已分配库存
		 * @param hasExpired
		 *            是否已过期
		 * @param pageable
		 *            分页信息
		 * @return 订单分页
		 */
	Page<Order> findPage(Order entity,List<User> users, List<Store> stores, Boolean isPendingReceive, Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable);

	/**
	 * 查询订单数量
	 * 
	 * @param status
	 *            状态
	 * @param store
	 *            店铺
	 * @param member
	 *            会员
	 * @param goods
	 *            商品
	 * @param isPendingReceive
	 *            是否等待收款
	 * @param isAllocatedStock
	 *            是否已分配库存
	 * @param hasExpired
	 *            是否已过期
	 * @return 订单数量
	 */
	Long count(Order.Status status, Store store, Member member, Goods goods, Boolean isPendingReceive, Boolean isAllocatedStock, Boolean hasExpired);

	/**
	 * 计算订单金额
	 * 
	 * @param price
	 *            SKU价格
	 * @param freight
	 *            运费
	 * @param offsetAmount
	 *            调整金额
	 * @return 订单金额
	 */
	BigDecimal calculateAmount(BigDecimal price, BigDecimal freight, BigDecimal offsetAmount);

	/**
	 * 计算订单金额
	 * 
	 * @param order
	 *            订单
	 * @return 订单金额
	 */
	BigDecimal calculateAmount(Order order);

	/**
	 * 指定用户获取订单锁
	 *
	 * @param order
	 *            订单
	 * @param user
	 *            用户
	 * @return 是否获取成功
	 */
//	boolean acquireLock(Order order, User user);

	/**
	 * 当前登录用户获取订单锁
	 *
	 * @param order
	 *            订单
	 * @return 是否获取成功
	 */
//	boolean acquireLock(Order order);

	/**
	 * 订单生成
	 * 
	 * @param cart
	 *            购物车
	 * @param address
	 *            收货地址
	 * @param paymentPattern
	 *            支付方式
	 * @param shippingMethod
	 *            配送方式
	 * @param remark
	 *            附言
	 * @return 订单
	 */
	List<Order> generate(Boolean isDirectBuy,Boolean notJoinGroup, Cart cart, Address address, PaymentPattern paymentPattern, ShippingMethod shippingMethod, String[] remark);
	/**
	 * 订单创建
	 * 
	 * @param cart
	 *            购物车
	 * @param address
	 *            收货地址
	 * @param paymentPattern
	 *            支付方式
	 * @param shippingMethod
	 *            配送方式
	 * @param remark
	 *            附言
	 * @return 订单
	 */
	List<Order> create(Boolean notJoinGroup,Boolean isDirectBuy, final Cart cart, Address address, PaymentPattern paymentPattern, ShippingMethod shippingMethod, String[] remark);

	/**
	 * 订单更新
	 * 
	 * @param order
	 *            订单
	 */
	void modify(Order order);

	/**
	 * 订单取消
	 * 
	 * @param order
	 *            订单
	 */
	void cancel(Order order);

	/**
	 * 订单审核
	 * 
	 * @param order
	 *            订单
	 * @param passed
	 *            是否审核通过
	 */
	void review(Order order, boolean passed);

	/**
	 * 订单收款
	 * 
	 * @param order
	 *            订单
	 * @param orderPayment
	 *            订单支付
	 */
	void payment(Order order, OrderPayment orderPayment);

	/**
	 * 订单发货
	 * 
	 * @param order
	 *            订单
	 * @param orderShipping
	 *            订单发货
	 */
	void shipping(Order order, OrderShipping orderShipping);

	/**
	 * 订单收货
	 * 
	 * @param order
	 *            订单
	 */
	void receive(Order order);

	/**
	 * 订单完成
	 * 
	 * @param order
	 *            订单
	 */
	void complete(Order order);

	/**
	 * 订单失败
	 * 
	 * @param order
	 *            订单
	 */
	void fail(Order order);

}