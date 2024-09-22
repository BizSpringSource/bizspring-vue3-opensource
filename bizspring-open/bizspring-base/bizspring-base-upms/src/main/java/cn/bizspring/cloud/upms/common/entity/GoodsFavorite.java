package cn.bizspring.cloud.upms.common.entity;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Entity - 商品收藏
 * 
 */
@Data
@ApiModel(description = "商品收藏")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_goods_favorite")
public class GoodsFavorite extends BizEntity<GoodsFavorite> {

	private static final long serialVersionUID = 1L;

	/**
	 * 最大商品收藏数量
	 */
	public static final Integer MAXIMUM_NUMBER_GOODS_FAVORITES = 10;

	/**
	 * 会员
	 */
	@JsonView(BaseView.class)
	@TableField(exist = false)
	private String memberId;
	@TableField(exist = false)
	private String memberName;

	/**
	 * 商品
	 */
	@JsonView(BaseView.class)
	@TableField(exist = false)
	private String goodsId;

	@TableField(exist = false)
	private String goodsName;

	@TableField(exist = false)
	private BigDecimal goodsPrice;

	@TableField(exist = false)
	private String goodsThumbnail;

}