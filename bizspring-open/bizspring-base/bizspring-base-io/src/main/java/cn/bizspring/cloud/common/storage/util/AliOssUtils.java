package cn.bizspring.cloud.common.storage.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import cn.bizspring.cloud.common.storage.entity.StorageEntity;
import lombok.AllArgsConstructor;

import java.io.File;
import java.util.UUID;

/**
 * @bizspring
 * ali OSS
 * 各操作调用
 */
@AllArgsConstructor
public class AliOssUtils {
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
		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(storageEntity.getCloudEndpoint(), storageEntity.getCloudAccessKeyId(), storageEntity.getCloudAccessKeySecret());

		String fileName = file.getName();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		String key = path + UUID.randomUUID() + "." + suffix;
		// 创建PutObjectRequest对象。
		PutObjectRequest putObjectRequest = new PutObjectRequest(storageEntity.getCloudBucket(), key, file);
		// 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
//		 ObjectMetadata metadata = new ObjectMetadata();
//		 metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
//		 metadata.setObjectAcl(CannedAccessControlList.Private);
//		 putObjectRequest.setMetadata(metadata);

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(contentType);
		objectMetadata.setContentLength(file.length());
		putObjectRequest.setMetadata(objectMetadata);
		// 上传文件。
		ossClient.putObject(putObjectRequest);
		// 关闭OSSClient。
		ossClient.shutdown();
		// 解析结果
		String resultStr = "https://" + storageEntity.getCloudBucket() + "." + storageEntity.getCloudEndpoint() + "/" + key;
		return resultStr;
	}
}
