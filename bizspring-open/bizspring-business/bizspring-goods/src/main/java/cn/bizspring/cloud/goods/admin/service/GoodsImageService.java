package cn.bizspring.cloud.goods.admin.service;

import cn.bizspring.cloud.goods.common.entity.GoodsImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service - 商品图片
 * 
 */
public interface GoodsImageService {

	/**
	 * 商品图片过滤
	 * 
	 * @param goodsImages
	 *            商品图片
	 */
	void filter(List<GoodsImage> goodsImages);

	/**
	 * 生成商品图片
	 * 
	 * @param multipartFile
	 *            文件
	 * @return 商品图片
	 */
	GoodsImage generateImage(MultipartFile multipartFile,String path);
	/**
	 * 生成商品视频
	 *
	 * @param multipartFile
	 *            视频
	 * @return 商品视频
	 */
	GoodsImage generateVideo(MultipartFile multipartFile,String path);

}