package cn.bizspring.cloud.upms.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.bizspring.cloud.upms.admin.mapper.SysLogMapper;
import cn.bizspring.cloud.upms.admin.service.SysLogService;
import cn.bizspring.cloud.upms.common.entity.SysLog;
import cn.bizspring.cloud.upms.common.vo.PreLogVO;
import cn.bizspring.cloud.common.core.enums.CommonEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author
 */
@Service
@AllArgsConstructor
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

	/**
	 * 批量插入前端错误日志
	 *ss
	 * @param preLogVOList 日志信息
	 * @return true/false
	 */
	@Override
	public Boolean saveBatchLogs(List<PreLogVO> preLogVOList) {
		List<SysLog> sysLogs = preLogVOList.stream()
			.map(pre -> {
				SysLog log = new SysLog();
				log.setType(CommonEnum.LOG_TYPE_9);
				log.setTitle(pre.getInfo());
				log.setException(pre.getStack());
				log.setParams(pre.getMessage());
				log.setCreatedTime(new Date());
				log.setRequestUri(pre.getUrl());
				log.setCreateBy(pre.getUser());
				return log;
			})
			.collect(Collectors.toList());
		return this.saveBatch(sysLogs);
	}
}
