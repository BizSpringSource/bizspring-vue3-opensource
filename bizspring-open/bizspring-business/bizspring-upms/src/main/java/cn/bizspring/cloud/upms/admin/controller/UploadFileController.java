package cn.bizspring.cloud.upms.admin.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.bizspring.cloud.common.core.enums.CommonEnum;
import cn.bizspring.cloud.common.core.util.FileUtils;
import cn.bizspring.cloud.common.core.util.WaterMarkUtils;
import cn.bizspring.cloud.common.data.tenant.TenantContextHolder;
import cn.bizspring.cloud.common.storage.util.UploadFileUtils;
import cn.bizspring.cloud.upms.admin.service.ConfigStorageService;
import cn.bizspring.cloud.upms.common.entity.ConfigStorage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author www.bizspring.cn
 * @date 2023/06/16
 * 文件上传
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/file")
@Api(value = "file", tags = "文件上传")
public class UploadFileController {

	private final ConfigStorageService configStorageService;

	/**
	 * 上传文件
	 * @param mulFile
	 * @param path 文件存放目录
	 * @param fileType 文件类型 image:图片
	 * @return
	 */
	@ApiOperation(value = "上传文件")
	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile mulFile,
							 @RequestParam("path") String path,
							 @RequestParam("fileType") String fileType) throws Exception {
		File file = FileUtils.multipartFileToFile(mulFile);
		Map<Object, Object> responseData = new HashMap<>();
		path = StrUtil.format("{}/{}",TenantContextHolder.getTenantId(),  path);
		ConfigStorage configStorage = configStorageService.getOne(Wrappers.emptyWrapper());
		if(configStorage == null){
			throw new RuntimeException("请先配置文件存储信息");
		}
		if(CommonEnum.FILE_TYPE_IMG.equals(fileType) &&
				StrUtil.isNotBlank(configStorage.getWaterMarkContent())){//图片添加水印
			//添加水印
			file = WaterMarkUtils.markStr(file, Color.GRAY, configStorage.getWaterMarkContent());
		}
		responseData.put("link", UploadFileUtils.uploadFile(path,file,configStorage));
		return JSONUtil.toJsonStr(responseData);
	}
}
