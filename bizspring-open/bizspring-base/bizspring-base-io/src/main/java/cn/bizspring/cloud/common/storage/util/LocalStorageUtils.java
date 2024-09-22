package cn.bizspring.cloud.common.storage.util;

import cn.bizspring.cloud.common.storage.entity.StorageEntity;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Mode - 本地文件存储
 * 
 */
@AllArgsConstructor
public class LocalStorageUtils {

	private StorageEntity storageEntity;

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
		File destFile = new File(storageEntity.getLocalUploadPath(), path);
		try {
			FileUtils.moveFile(file, destFile);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return path;
	}

}