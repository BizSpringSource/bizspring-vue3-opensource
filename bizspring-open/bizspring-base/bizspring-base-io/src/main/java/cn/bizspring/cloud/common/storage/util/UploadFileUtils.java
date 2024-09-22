package cn.bizspring.cloud.common.storage.util;

import cn.bizspring.cloud.common.storage.entity.StorageEntity;
import cn.bizspring.cloud.upms.common.entity.ConfigStorage;
import java.io.File;

/**
 * @bizspring
 * 文件上传工具类
 */
public class UploadFileUtils {

	/**
	 * 上传文件
	 * @param file
	 * @param path 用户上传文件时指定的文件夹。
	 * @param configStorage
	 */
	public static String uploadFile(String path, File file, ConfigStorage configStorage) throws Exception {
		return uploadFile(path,file,configStorage,"application/octet-stream");
	}

	/**
	 * 上传文件
	 * @param file
	 * @param path 用户上传文件时指定的文件夹。
	 * @param configStorage
	 */
	public static String uploadFile(String path, File file, ConfigStorage configStorage, String contentType) throws Exception {
		StorageEntity storageEntity = new StorageEntity();
		storageEntity.setCloudAccessKeyId(configStorage.getAccessKeyId());
		storageEntity.setCloudAccessKeySecret(configStorage.getAccessKeySecret());
		storageEntity.setCloudEndpoint(configStorage.getEndpoint());
		storageEntity.setCloudBucket(configStorage.getBucket());
		if("1".equals(configStorage.getStorageType())){//阿里OSS
			AliOssUtils aliOssUtils = new AliOssUtils(storageEntity);
			return aliOssUtils.uploadFile(path,file,contentType);
		}else if("2".equals(configStorage.getStorageType())){//七牛云
			QiNiuUtils qiNiuUtils = new QiNiuUtils(storageEntity);
			return qiNiuUtils.uploadFile(path,file,contentType);
		}else if("3".equals(configStorage.getStorageType())){//minio
			MinioUtils minioUtils = new MinioUtils(storageEntity);
			return minioUtils.uploadFile(path,file,contentType);
		}else if("4".equals(configStorage.getStorageType())){//腾讯cos
			QcloudCosUtils qcloudCosUtils = new QcloudCosUtils(storageEntity);
			return qcloudCosUtils.uploadFile(path,file,contentType);
		}else if("5".equals(configStorage.getStorageType())){//本地
			LocalStorageUtils localStorageUtils = new LocalStorageUtils(storageEntity);
			return localStorageUtils.uploadFile(path,file,contentType);
		}else if("6".equals(configStorage.getStorageType())){//ftp
			FtpStorageUtils ftpStorageUtils = new FtpStorageUtils(storageEntity);
			return ftpStorageUtils.uploadFile(path,file,contentType);
		}
		return "";
	}
}
