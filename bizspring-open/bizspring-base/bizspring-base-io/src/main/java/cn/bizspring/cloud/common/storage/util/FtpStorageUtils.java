package cn.bizspring.cloud.common.storage.util;

import cn.bizspring.cloud.common.storage.entity.StorageEntity;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;

/**
 * Mode - FTP存储
 * 
 */
@AllArgsConstructor
public class FtpStorageUtils{

	private final StorageEntity storageEntity;

	/**
	 * 上传文件
	 * @param path 上传路劲
	 * @param file 文件
	 * @return
	 */
	public String uploadFile(String path, File file) {
		return uploadFile(path,file,"application/octet-stream");
	}

	/**
	 * 上传文件
	 * @param path 上传路劲
	 * @param file 文件
	 * @param contentType 文件类型
	 * @return
	 */
	public String uploadFile(String path, File file, String contentType) {
		if (storageEntity != null) {
			FTPClient ftpClient = new FTPClient();
			try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))){
				ftpClient.connect(storageEntity.getFtpHost(), storageEntity.getFtpPort());
				ftpClient.login(storageEntity.getFtpUsername(), storageEntity.getFtpPassword());
				ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ftpClient.enterLocalPassiveMode();
				if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
					String directory = StringUtils.substringBeforeLast(path, "/");
					String filename = StringUtils.substringAfterLast(path, "/");
					if (!ftpClient.changeWorkingDirectory(directory)) {
						String[] paths = StringUtils.split(directory, "/");
						String p = "/";
						ftpClient.changeWorkingDirectory(p);
						for (String s : paths) {
							p += s + "/";
							if (!ftpClient.changeWorkingDirectory(p)) {
								ftpClient.makeDirectory(s);
								ftpClient.changeWorkingDirectory(p);
							}
						}
					}
					ftpClient.storeFile(filename, inputStream);
					ftpClient.logout();
				}
			} catch (SocketException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return path;
	}

}