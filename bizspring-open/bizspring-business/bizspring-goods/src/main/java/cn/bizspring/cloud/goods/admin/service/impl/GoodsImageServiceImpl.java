package cn.bizspring.cloud.goods.admin.service.impl;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.util.ImageUtils;
import cn.bizspring.cloud.common.core.util.WaterMarkUtils;
import cn.bizspring.cloud.common.storage.util.UploadFileUtils;
import cn.bizspring.cloud.goods.admin.service.GoodsImageService;
import cn.bizspring.cloud.goods.common.entity.GoodsImage;
import cn.bizspring.cloud.upms.common.entity.ConfigStorage;
import cn.bizspring.cloud.upms.common.feign.FeignConfigStorageService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Service - 商品图片
 * 
 */
@Service
@AllArgsConstructor
public class GoodsImageServiceImpl implements GoodsImageService {

	/**
	 * 临时文件扩展名
	 */
	private static final String TEMP_FILE_EXTENSION = "tmp";

	private TaskExecutor taskExecutor;
	private FeignConfigStorageService feignConfigStorageService;

	@Override
	public void filter(List<GoodsImage> goodsImages) {
		CollectionUtils.filter(goodsImages, new Predicate() {
			public boolean evaluate(Object object) {
				GoodsImage goodsImage = (GoodsImage) object;
				return goodsImage != null && StringUtils.isNotEmpty(goodsImage.getSource()) && StringUtils.isNotEmpty(goodsImage.getLarge()) && StringUtils.isNotEmpty(goodsImage.getMedium()) && StringUtils.isNotEmpty(goodsImage.getThumbnail());
			}
		});
	}

	/**
	 * 商品视频处理
	 * @param multipartFile
	 *            视频
	 * @param path
	 * @return
	 */
	@Override
	public GoodsImage generateVideo(MultipartFile multipartFile,String path) {
		GoodsImage goodsImage = new GoodsImage();
		ConfigStorage configStorage = feignConfigStorageService.getOne(SecurityEnum.FROM_IN);
		if (configStorage == null) {
			throw new RuntimeException("请先配置文件存储信息");
		}
		Map<String, Object> model = new HashMap<>();
		model.put("uuid", String.valueOf(UUID.randomUUID()));

		String uuid = String.valueOf(UUID.randomUUID());
		String sourcePath = path + String.format(GoodsImage.SOURCE_FILE_NAME, uuid, FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
		File tempFile = new File(FileUtils.getTempDirectory(), uuid + "." + TEMP_FILE_EXTENSION);

		try {
			multipartFile.transferTo(tempFile);
			String sourceUrl = UploadFileUtils.uploadFile(sourcePath, tempFile, configStorage, multipartFile.getContentType());
			goodsImage.setSource(sourceUrl);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			FileUtils.deleteQuietly(tempFile);
		}
		return goodsImage;
	}

	/**
	 * 商品图片处理
	 * @param multipartFile
	 *            文件
	 * @param path
	 * @return
	 */
	@Override
	public GoodsImage generateImage(MultipartFile multipartFile,String path) {
		GoodsImage goodsImage = new GoodsImage();
		ConfigStorage configStorage = feignConfigStorageService.getOne(SecurityEnum.FROM_IN);
		if (configStorage == null) {
			throw new RuntimeException("请先配置文件存储信息");
		}
		Map<String, Object> model = new HashMap<>();
		model.put("uuid", String.valueOf(UUID.randomUUID()));
		String uuid = String.valueOf(UUID.randomUUID());
		String sourcePath = path + String.format(GoodsImage.SOURCE_FILE_NAME, uuid, FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
		String largePath = path + String.format(GoodsImage.LARGE_FILE_NAME, uuid, GoodsImage.FILE_EXTENSION);
		String mediumPath = path + String.format(GoodsImage.MEDIUM_FILE_NAME, uuid, GoodsImage.FILE_EXTENSION);
		String thumbnailPath = path + String.format(GoodsImage.THUMBNAIL_FILE_NAME, uuid, GoodsImage.FILE_EXTENSION);
		File tempFile = new File(FileUtils.getTempDirectory(), uuid + "." + TEMP_FILE_EXTENSION);
		File largeTempFile = new File(FileUtils.getTempDirectory(), UUID.randomUUID() + "." + GoodsImage.FILE_EXTENSION);
		File mediumTempFile = new File(FileUtils.getTempDirectory(), UUID.randomUUID() + "." + GoodsImage.FILE_EXTENSION);
		File thumbnailTempFile = new File(FileUtils.getTempDirectory(), UUID.randomUUID() + "." + GoodsImage.FILE_EXTENSION);
		try {
			multipartFile.transferTo(tempFile);
			ImageUtils.zoom(tempFile, largeTempFile, 800, 800);
			largeTempFile = WaterMarkUtils.markStr(largeTempFile, Color.GRAY, configStorage.getWaterMarkContent());
			ImageUtils.zoom(tempFile, mediumTempFile, 700, 700);
			mediumTempFile = WaterMarkUtils.markStr(mediumTempFile, Color.GRAY, configStorage.getWaterMarkContent());
			ImageUtils.zoom(tempFile, thumbnailTempFile, 600, 600);
			String sourceUrl = UploadFileUtils.uploadFile(sourcePath, tempFile, configStorage, multipartFile.getContentType());
			String largeUrl = UploadFileUtils.uploadFile(largePath, largeTempFile, configStorage, multipartFile.getContentType());
			String mediumUrl = UploadFileUtils.uploadFile(mediumPath, mediumTempFile, configStorage, multipartFile.getContentType());
			String thumbnailUrl = UploadFileUtils.uploadFile(thumbnailPath, thumbnailTempFile, configStorage,multipartFile.getContentType());
			goodsImage.setSource(sourceUrl);
			goodsImage.setLarge(largeUrl);
			goodsImage.setMedium(mediumUrl);
			goodsImage.setThumbnail(thumbnailUrl);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			FileUtils.deleteQuietly(tempFile);
			FileUtils.deleteQuietly(largeTempFile);
			FileUtils.deleteQuietly(mediumTempFile);
			FileUtils.deleteQuietly(thumbnailTempFile);
		}
		return goodsImage;
	}

}