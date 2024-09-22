package cn.bizspring.cloud.upms.common.vo;

import lombok.Data;

/**
 * @author
 * 前端日志vo
 */
@Data
public class PreLogVO {
	private String url;
	private String time;
	private String user;
	private String type;
	private String message;
	private String stack;
	private String info;
}
