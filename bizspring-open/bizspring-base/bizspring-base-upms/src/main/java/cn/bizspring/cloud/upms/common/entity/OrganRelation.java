package cn.bizspring.cloud.upms.common.entity;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 机构关系
 * </p>
 *
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_organ_relation")
public class OrganRelation extends BizEntity<OrganRelation> {

	private static final long serialVersionUID = 1L;

	/**
	 * 祖先节点
	 */
	private String ancestor;
	/**
	 * 后代节点
	 */
	private String descendant;

	/**
	 * 租户ID
	 */
	private String tenantId;
}
