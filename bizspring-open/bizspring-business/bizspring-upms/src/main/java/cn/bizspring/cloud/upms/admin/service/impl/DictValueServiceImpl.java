package cn.bizspring.cloud.upms.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.bizspring.cloud.upms.admin.mapper.DictValueMapper;
import cn.bizspring.cloud.upms.admin.service.DictValueService;
import cn.bizspring.cloud.upms.common.entity.DictValue;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import cn.bizspring.cloud.common.core.enums.CacheEnum;
import cn.bizspring.cloud.common.core.util.R;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
/**
 * 字典项
 *
 * @author
 */
@Service
@AllArgsConstructor
public class DictValueServiceImpl extends ServiceImpl<DictValueMapper, DictValue> implements DictValueService {

	/**
	 * 删除字典项
	 *
	 * @param id 字典项ID
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheEnum.DICT_CACHE, allEntries = true)
	public ResponseEntity removeDictItem(String id) {
		return R.ok(this.removeById(id));
	}

	/**
	 * 更新字典项
	 *
	 * @param item 字典项
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheEnum.DICT_CACHE, allEntries = true)
	public ResponseEntity updateDictItem(DictValue item) {
		return R.ok(this.updateById(item));
	}
}
