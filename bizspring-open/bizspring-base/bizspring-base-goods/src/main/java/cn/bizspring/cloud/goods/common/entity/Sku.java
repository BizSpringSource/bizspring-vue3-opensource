package cn.bizspring.cloud.goods.common.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PrePersist;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonView;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * Entity - SKU 规格细分交叉明细记录
 * 
 */
@Data
@ApiModel(description = "SKU")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_sku")
public class Sku extends BizEntity<Sku> {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@JsonView(BaseView.class)
	private String sn;

	/**
	 * 销售价
	 */
	@JsonView(BaseView.class)
	@NotNull
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	private BigDecimal price;

	/**
	 * 成本价
	 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	private BigDecimal cost;

	/**
	 * 市场价
	 */
	@JsonView(BaseView.class)
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	private BigDecimal marketPrice;

	/**
	 * 库存
	 */
	@NotNull(groups = Save.class)
	@Min(0)
	private Integer stock;

	/**
	 * 已分配库存
	 */
	private Integer allocatedStock;

	/**
	 * 是否默认
	 */
	@NotNull
	private Boolean isDefault;

	/**
	 * 商品
	 */
	@TableField(exist = false)
	private String goodsId;

	@TableField(exist = false)
	private Goods goods;

	/**
	 * 规格值
	 */
	@JsonView(BaseView.class)
	@TableField(exist = false)
	private List<SpecificationValue> specificationValues = new ArrayList<>();

	/**
	 * 获取可用库存
	 * 
	 * @return 可用库存
	 */
	@Transient
	public int getAvailableStock() {
		int availableStock = getStock() - getAllocatedStock();
		return availableStock >= 0 ? availableStock : 0;
	}



	/**
	 * 获取是否缺货
	 * 
	 * @return 是否缺货
	 */
	@Transient
	public boolean getIsOutOfStock() {
		return getAvailableStock() <= 0;
	}

	/**
	 * 获取规格
	 * 
	 * @return 规格
	 */
	@Transient
	public List<String> getSpecifications() {
		List<String> specifications = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(getSpecificationValues())) {
			for (SpecificationValue specificationValue : getSpecificationValues()) {
				specifications.add(specificationValue.getValue());
			}
		}
		return specifications;
	}

	/**
	 * 是否存在规格
	 * 
	 * @return 是否存在规格
	 */
	@Transient
	public boolean hasSpecification() {
		return CollectionUtils.isNotEmpty(getSpecificationValues());
	}


	/**
	 * 持久化前处理
	 */
	@PrePersist
	public void prePersist() {
		setSn(StringUtils.lowerCase(getSn()));
	}

}