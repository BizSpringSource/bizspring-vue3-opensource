package cn.bizspring.cloud.common.storage.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.bizspring.cloud.common.storage.entity.StorageEntity;
import com.qiniu.storage.Region;
import lombok.AllArgsConstructor;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import cn.hutool.core.util.RandomUtil;
import java.io.File;
import java.util.UUID;

/**
 * 七牛云
 * 各操作调用
 * @bizspring
 */
@AllArgsConstructor
public class QiNiuUtils {
	private final StorageEntity storageEntity;

	/**
	 * 上传文件
	 * @param file
	 * @param path 用户上传文件时指定的文件夹。
	 */
	public String uploadFile(String path, File file) throws QiniuException {
		return uploadFile(path,file,"application/octet-stream");
	}

	/**
	 * 上传文件
	 * @param file
	 * @param path 用户上传文件时指定的文件夹。
	 */
	public String uploadFile(String path, File file, String contentType) throws QiniuException {
		//构造一个带指定 Region 对象的配置类
		Configuration cfg = new Configuration(Region.autoRegion());
		//...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		//...生成上传凭证，然后准备上传
		String accessKey = storageEntity.getCloudAccessKeyId();
		String secretKey = storageEntity.getCloudAccessKeySecret();
		String bucket = storageEntity.getCloudBucket();
		//默认不指定key的情况下，以文件内容的hash值作为文件名
		String fileName = file.getName();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		String key = preHandle(UUID.randomUUID()+ "." + suffix, path);
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket);
		Response response = uploadManager.put(file, key, upToken);
		String resultStr = getUrlPath(response);
		return resultStr;
	}

	private String preHandle(String fileName, String dir) {
		if (StrUtil.isNotBlank(dir) && !dir.startsWith("/")) {
			dir = "/" + dir;
		}
		String name = StrUtil.isBlank(fileName) ? RandomUtil.randomString(32) : fileName;
		if (StrUtil.isBlank(dir)) {
			return name;
		}
		String prefix = dir.replaceFirst("/", "");
		return (prefix.endsWith("/") ? prefix : prefix.concat("/")).concat(name);
	}

	private String getUrlPath(Response response) throws QiniuException {
		DefaultPutRet putRet = JSONUtil.toBean(response.bodyString(), DefaultPutRet.class);
		String key = putRet.key;
		return "http://" + storageEntity.getCloudEndpoint() + (key.startsWith("/") ? key : "/" + key);
	}

}
