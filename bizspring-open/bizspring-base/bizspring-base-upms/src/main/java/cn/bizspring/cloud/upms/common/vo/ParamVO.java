package cn.bizspring.cloud.upms.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * @author
 * <p>
 * 公共参数
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParamVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 公共参数键
     */
    private String publicKey;
    /**
     * 公共参数值
     */
    private String publicValue;
    /**
     * 公共参数名
     */
    private String publicName;
}