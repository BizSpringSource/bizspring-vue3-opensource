package cn.bizspring.cloud.upms.common.entity;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 客户端信息
 * </p>
 *
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_oauth_client")
public class OauthClient extends BizEntity<OauthClient> {

	private static final long serialVersionUID = 1L;

	/**
	 * 客户端密钥
	 */
	@NotNull(message = "客户端密钥 不能为空")
	private String clientSecret;

	/**
	 * 资源ID
	 */
	private String resourceIds;

	/**
	 * 作用域
	 */
	@NotNull(message = "作用域 不能为空")
	private String scope;

	/**
	 * 授权方式（A,B,C）
	 */
	private String authorizedGrantTypes;

	private String webServerRedirectUri;

	private String authorities;

	/**
	 * 请求令牌有效时间
	 */
	private Integer accessTokenValidity;

	/**
	 * 刷新令牌有效时间
	 */
	private Integer refreshTokenValidity;

	/**
	 * 扩展信息
	 */
	private String additionalInformation;

	/**
	 * 是否自动放行
	 */
	private String autoapprove;

	protected Serializable pkVal() {
		return getId();
	}

}
