package cn.bizspring.cloud.store.common.entity;

import java.util.Date;
import javax.persistence.Lob;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import cn.bizspring.cloud.common.core.entity.BizEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.elasticsearch.annotations.Document;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * Entity - 店铺
 * 
 */
@Data
@ApiModel(description = "店铺")
@EqualsAndHashCode(callSuper = true)
@Document(indexName = "store")
@TableName("biz_store")
public class Store extends BizEntity<Store> {

	private static final long serialVersionUID = 1L;

	/**
	 * 类型
	 */
	public enum Type implements IEnum<Integer> {

		/**
		 * 普通
		 */
		GENERAL(0),

		/**
		 * 自营
		 */
		SELF(1);
		
		private int value;

		Type(final int value) {
			this.value = value;
		}
		
		@Override
		public Integer getValue() {
			return this.value;
		}
	}

	/**
	 * 状态
	 */
	public enum Status implements IEnum<Integer> {

		/**
		 * 等待审核
		 */
		PENDING(0),

		/**
		 * 审核失败
		 */
		FAILED(1),

		/**
		 * 审核通过
		 */
		APPROVED(2),

		/**
		 * 开店成功
		 */
		SUCCESS(3);

		private int value;

		Status(final int value) {
			this.value = value;
		}

		Status() {
		}

		@Override
		public Integer getValue() {
			return this.value;
		}
	}

	/**
	 * 名称
	 */
	@JsonView(BaseView.class)
	@NotEmpty
	private String name;

	/**
	 * 类型
	 */
	@JsonView(BaseView.class)
	private Type type;

	/**
	 * 状态
	 */
	private Status status;

	/**
	 * logo
	 */
	@JsonView(BaseView.class)
	private String logo;

	/**
	 * E-mail
	 */
	@NotEmpty
	@Email
	private String email;

	/**
	 * 手机
	 */
	@NotEmpty
	private String mobile;

	/**
	 * 电话
	 */
	private String phone;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 邮编
	 */
	private String zipCode;

	/**
	 * 简介
	 */
	@Lob
	private String introduction;

	/**
	 * 搜索关键词
	 */
	private String keyword;

	/**
	 * 到期日期
	 */
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endDate;

	/**
	 * 是否启用
	 */
	@JsonView(BaseView.class)
	@NotNull
	private Boolean isEnabled;

	/**
	 * 商家
	 */
	@TableField(exist = false)
	private String merchantId;

	/**
	 * 店铺分类
	 */
	@TableField(exist = false)
	private String storeCategoryId;

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
	 * 判断店铺是否有效
	 * 
	 * @return 店铺是否有效
	 */
	@Transient
	public boolean isActive() {
		return BooleanUtils.isTrue(getIsEnabled()) && Status.SUCCESS.equals(getStatus()) && !hasExpired();
	}

	/**
	 * 判断店铺是否已过期
	 * 
	 * @return 店铺是否已过期
	 */
	@JsonView(BaseView.class)
	@Transient
	public boolean hasExpired() {
		return getEndDate() != null && !getEndDate().after(new Date());
	}

}