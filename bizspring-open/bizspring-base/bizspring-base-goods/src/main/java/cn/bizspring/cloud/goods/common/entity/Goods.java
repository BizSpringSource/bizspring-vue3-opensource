package cn.bizspring.cloud.goods.common.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Lob;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.elasticsearch.annotations.Document;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonView;
import cn.bizspring.cloud.common.core.entity.BizEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * Entity - 商品
 *
 */
@Data
@ApiModel(description = "商品")
@EqualsAndHashCode(callSuper = true)
@Document(indexName = "goods")
@TableName("biz_goods")
public class Goods extends BizEntity<Goods> {

	private static final long serialVersionUID = 1L;

	/**
	 * 排序类型
	 */
	public enum OrderType {

		/**
		 * 置顶降序
		 */
		TOP_DESC,

		/**
		 * 价格升序
		 */
		PRICE_ASC,

		/**
		 * 价格降序
		 */
		PRICE_DESC,

		/**
		 * 日期降序
		 */
		DATE_DESC
	}


	/**
	 * 编号
	 */
	@JsonView(BaseView.class)
	@Pattern(regexp = "^[0-9a-zA-Z_-]+$")
	private String sn;

	/**
	 * 名称
	 */
	@JsonView(BaseView.class)
	private String name;

	/**
	 * 副标题
	 */
	@JsonView(BaseView.class)
	private String caption;

	/**
	 * 销售价
	 */
	@JsonView(BaseView.class)
	private BigDecimal price;

	/**
	 * 成本价
	 */
	private BigDecimal cost;

	/**
	 * 市场价
	 */
	@JsonView(BaseView.class)
	private BigDecimal marketPrice;

	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 重量
	 */
	@Min(0)
	private Integer weight;

	/**
	 * 是否上架
	 */
	@JsonView(BaseView.class)
	private Boolean isMarketable;

	/**
	 * 是否列出
	 */
	private Boolean isList;

	/**
	 * 是否置顶
	 */
	private Boolean isTop;

	/**
	 * 是否需要物流
	 */
	private Boolean isDelivery;

	/**
	 * 是否有效
	 */
	@JsonView(BaseView.class)
	private Boolean isActive;

	/**
	 * 介绍
	 */
	@Lob
	private String introduction;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 搜索关键词
	 */
	private String keyword;

	/**
	 * 店铺
	 */
	@TableField(exist = false)
	private String storeId;
	@TableField(exist = false)
	private String storeName;
	/**
	 * 商品分类
	 */
	@TableField(exist = false)
	private String goodsCategoryId;
	@TableField(exist = false)
	private String goodsCategoryName;

	/**
	 * 店铺商品分类
	 */
	@TableField(exist = false)
	private String storeGoodsCategoryId;
	/**
	 * 品牌
	 */
	@TableField(exist = false)
	private String brandId;

	/**
	 * 商品图片
	 */
	@TableField(exist = false)
	private List<GoodsImage> goodsImages = new ArrayList<>();


	/**
	 * 规格项
	 */
	@TableField(exist = false)
	private List<SpecificationItem> specificationItems = new ArrayList<>();

	/**
	 * 设置搜索关键词
	 *
	 * @param keyword
	 *            搜索关键词
	 */
	public void setKeyword(String keyword) {
		if (keyword != null) {
			keyword = keyword.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", StringUtils.EMPTY);
		}
		this.keyword = keyword;
	}

	/**
	 * 获取缩略图
	 *
	 * @return 缩略图
	 */
	@JsonView(BaseView.class)
	@Transient
	public String getThumbnail() {
		if (CollectionUtils.isEmpty(getGoodsImages())) {
			return null;
		}
		return getGoodsImages().get(0).getThumbnail();
	}

	/**
	 * 获取规格项条目ID
	 *
	 * @return 规格项条目ID
	 */
	@Transient
	public List<Integer> getSpecificationItemEntryIds() {
		List<Integer> specificationItemEntryIds = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(getSpecificationItems())) {
			for (SpecificationItem specificationItem : getSpecificationItems()) {
				if (CollectionUtils.isNotEmpty(specificationItem.getEntries())) {
					for (SpecificationItem.Entry entry : specificationItem.getEntries()) {
						specificationItemEntryIds.add(entry.getId());
					}
				}
			}
			Collections.sort(specificationItemEntryIds);
		}
		return specificationItemEntryIds;
	}

	/**
	 * 是否存在规格
	 *
	 * @return 是否存在规格
	 */
	@Transient
	public boolean hasSpecification() {
		return CollectionUtils.isNotEmpty(getSpecificationItems());
	}

}