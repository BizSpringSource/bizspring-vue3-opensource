package cn.bizspring.cloud.common.storage.util;

import cn.bizspring.cloud.common.storage.entity.StorageEntity;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import lombok.AllArgsConstructor;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * @bizspring
 * 腾讯cos
 * 各操作调用
 */
@AllArgsConstructor
public class QcloudCosUtils {
	private final StorageEntity storageEntity;

	/**
	 * 上传文件
	 * @param file
	 * @param path 用户上传文件时指定的文件夹。
	 */
	public String uploadFile(String path, File file) {
		return uploadFile(path,file,"application/octet-stream");
	}

	/**
	 * 上传文件
	 * @param file
	 * @param path 用户上传文件时指定的文件夹。
	 */
	public String uploadFile(String path, File file, String contentType) {
		// 1 初始化用户身份信息（secretId, secretKey）。
		String secretId = storageEntity.getCloudAccessKeyId();
		String secretKey = storageEntity.getCloudAccessKeySecret();
		COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
		// 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
		// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
		Region region = new Region(storageEntity.getCloudEndpoint());
		ClientConfig clientConfig = new ClientConfig(region);
		// 3 生成 cos 客户端。
		COSClient cosClient = new COSClient(cred, clientConfig);
		// 指定要上传到的存储桶
		String bucketName = storageEntity.getCloudBucket();
		String fileName = file.getName();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		String key = path + UUID.randomUUID() + "." + suffix;
		ObjectMetadata objectMetadata = new ObjectMetadata();
		// 设置 Content type, 默认是 application/octet-stream
		objectMetadata.setContentType(contentType);
		try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);
			// 上传文件。
			cosClient.putObject(putObjectRequest);
			// 关闭OSSClient。
			cosClient.shutdown();
			Date expiration = new Date(new Date().getTime() + 5 * 60 * 10000);
			URL url = cosClient.generatePresignedUrl(bucketName, key, expiration);
			String resultStr = "https://" + url.getHost() + "/" + key;
			return resultStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
