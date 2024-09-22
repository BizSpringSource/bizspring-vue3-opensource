package cn.bizspring.cloud.upms.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.bizspring.cloud.upms.common.entity.Dict;
import org.springframework.http.ResponseEntity;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author
 */
public interface DictService extends IService<Dict> {
	/**
	 * 根据ID 删除字典
	 *
	 * @param id
	 * @return
	 */
	ResponseEntity removeDict(String id);

	/**
	 * 更新字典
	 *
	 * @param dict 字典
	 * @return
	 */
	ResponseEntity updateDict(Dict dict);
}
