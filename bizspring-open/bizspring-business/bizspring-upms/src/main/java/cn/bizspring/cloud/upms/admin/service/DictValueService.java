package cn.bizspring.cloud.upms.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.bizspring.cloud.upms.common.entity.DictValue;
import org.springframework.http.ResponseEntity;

/**
 * 字典项
 */
public interface DictValueService extends IService<DictValue> {

	/**
	 * 删除字典项
	 *
	 * @param id 字典项ID
	 * @return
	 */
	ResponseEntity removeDictItem(String id);

	/**
	 * 更新字典项
	 *
	 * @param item 字典项
	 * @return
	 */
	ResponseEntity updateDictItem(DictValue item);
}
