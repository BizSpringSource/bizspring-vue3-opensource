package cn.bizspring.cloud.order.admin.mapper;

import java.util.Date;
import java.util.List;

import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.order.common.entity.Order;
import cn.bizspring.cloud.goods.common.entity.Goods;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.User;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;


/**
 * Mapper - 订单
 * 
 */
public interface OrderMapper extends BizMapper<Order> {


	/**
	 * 查找订单分页
	 * 
	 * @param isPendingReceive
	 *            是否等待收款
	 * @param isAllocatedStock
	 *            是否已分配库存
	 * @param hasExpired
	 *            是否已过期
	 * @param iPage
	 *            分页信息
	 * @return 订单分页
	 */
	List<Order> findPage(IPage<Order> iPage, @Param("queryWrapper")QueryWrapper<Order> queryWrapper, @Param("entity")Order entity,@Param("stores")List<Store> stores,  @Param("users")List<User> users, @Param("isPendingReceive")Boolean isPendingReceive, @Param("isAllocatedStock")Boolean isAllocatedStock, @Param("hasExpired")Boolean hasExpired);

	/**
	 * 查找已支付订单
	 * @param store
	 * 			店铺
	 * @param isSuccess
	 * 			是否支付成功
	 * @param hasExpired
	 * 			是否过期
	 * @param paymentId
	 * 			支付方式
	 * @param beginDate
	 * 			起始时间
	 * @param endDate
	 * 			结束时间
	 * @return
	 */
	List<Order> findOrderPaymentedPage(@Param("store") Store store,  @Param("isSuccess") Boolean isSuccess, @Param("hasExpired") Boolean hasExpired, @Param("paymentId") String paymentId, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

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
	Long count( @Param("status")Order.Status status, @Param("store") Store store, @Param("member") Member member, @Param("goods")Goods goods, @Param("isPendingReceive")Boolean isPendingReceive, @Param("isAllocatedStock")Boolean isAllocatedStock, @Param("hasExpired")Boolean hasExpired);

}