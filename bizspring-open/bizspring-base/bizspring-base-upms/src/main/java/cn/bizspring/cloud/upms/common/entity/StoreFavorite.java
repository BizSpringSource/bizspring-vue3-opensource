package cn.bizspring.cloud.upms.common.entity;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entity - 店铺收藏
 * 
 */
@Data
@ApiModel(description = "店铺收藏")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_store_favorite")
public class StoreFavorite extends BizEntity<StoreFavorite> {

	private static final long serialVersionUID = 1L;

	/**
	 * 最大店铺收藏数
	 */
	public static final Integer MAXIMUM_NUMBER_STORE_FAVORITES = 10;

	/**
	 * 会员
	 */
	@JsonView(BaseView.class)
	@TableField(exist = false)
	private String memberId;
	@TableField(exist = false)
	private String memberName;

	/**
	 * 店铺
	 */
	@JsonView(BaseView.class)
	@TableField(exist = false)
	private String storeId;

	/**
	 * 获取会员
	 * 
	 * @return 会员
	 */
	public String getMemberId() {
		return memberId;
	}

	@TableField(exist = false)
	private String storeName;

	@TableField(exist = false)
	private String storeLogo;

}