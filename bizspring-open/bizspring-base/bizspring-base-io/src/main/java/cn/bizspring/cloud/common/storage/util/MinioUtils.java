package cn.bizspring.cloud.common.storage.util;

import cn.bizspring.cloud.common.storage.entity.StorageEntity;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.AllArgsConstructor;
import org.xmlpull.v1.XmlPullParserException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @bizspring
 * ali OSS
 * 各操作调用
 */
@AllArgsConstructor
public class MinioUtils {
	private final StorageEntity storageEntity;

	/**
	 * 上传文件
	 * @param file
	 * @param path 用户上传文件时指定的文件夹。
	 */
	public String uploadFile(String path, File file) throws InvalidBucketNameException, InsufficientDataException, IOException, NoSuchAlgorithmException, InvalidPortException, InvalidKeyException, InvalidEndpointException, InternalException, InvalidArgumentException, XmlPullParserException, ErrorResponseException, NoResponseException {
		return uploadFile(path,file,"application/octet-stream");
	}

	/**
	 * 上传文件
	 * @param file
	 * @param path 用户上传文件时指定的文件夹。
	 */
	public String uploadFile(String path, File file, String contentType)  throws InvalidPortException, InvalidEndpointException, IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, InvalidArgumentException {
		MinioClient minioClient = new MinioClient(storageEntity.getCloudEndpoint(), storageEntity.getCloudAccessKeyId(), storageEntity.getCloudAccessKeySecret());
		String fileName = file.getName();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		String key = path + UUID.randomUUID()+ "." + suffix;
		minioClient.putObject(storageEntity.getCloudBucket(), key, new FileInputStream(file), file.length(), null, null, contentType);
		String resultStr = minioClient.getObjectUrl(storageEntity.getCloudBucket(), key);
		return resultStr;
	}

}
