package cn.bizspring.cloud.common.storage.entity;

import lombok.Data;

@Data
public class StorageEntity {
	private String cloudAccessKeyId;// 请填写您的AccessKeyId。
	private String cloudAccessKeySecret; // 请填写您的AccessKeySecret。
	private String cloudEndpoint; // 请填写您的 endpoint。
	private String cloudBucket; // 请填写您的 bucketname
	private String ftpHost; 	 // ftp ip
	private int ftpPort; 	 //ftp 端口
	private String ftpUsername;  //ftp 用户名
	private String ftpPassword;  //ftp 密码
	private String localUploadPath; // local 上传路劲
}
