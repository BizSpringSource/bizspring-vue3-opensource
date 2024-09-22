package cn.bizspring.cloud.upms.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.bizspring.cloud.upms.admin.mapper.DictValueMapper;
import cn.bizspring.cloud.upms.admin.mapper.DictMapper;
import cn.bizspring.cloud.upms.admin.service.DictService;
import cn.bizspring.cloud.upms.common.entity.Dict;
import cn.bizspring.cloud.upms.common.entity.DictValue;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.bizspring.cloud.common.core.enums.CacheEnum;
import cn.bizspring.cloud.common.core.util.R;

import java.util.Date;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author
 */
@Service
@AllArgsConstructor
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
	private final DictValueMapper dictValueMapper;

	/**
	 * 根据ID 删除字典
	 *
	 * @param id 字典ID
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheEnum.DICT_CACHE, allEntries = true)
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity removeDict(String id) {
		baseMapper.deleteById(id);
		dictValueMapper.delete(Wrappers.<DictValue>lambdaQuery()
				.eq(DictValue::getDictId, id));
		return R.ok();
	}

	/**
	 * 更新字典
	 *
	 * @param dict 字典
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity updateDict(Dict dict) {
		this.updateById(dict);
		DictValue dictValue = new DictValue();
		dictValue.setType(dict.getType());
		dictValue.setUpdatedTime(new Date());
		dictValueMapper.update(dictValue,Wrappers.<DictValue>lambdaQuery()
				.eq(DictValue::getDictId, dict.getId()));
		return R.ok();
	}
}
