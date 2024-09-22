package cn.bizspring.cloud.order.admin.mapper;


import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.order.common.entity.CartItem;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper - 购物车项
 * 
 */
public interface CartItemMapper extends BizMapper<CartItem> {

    void select( @Param("entity")CartItem entity);
}