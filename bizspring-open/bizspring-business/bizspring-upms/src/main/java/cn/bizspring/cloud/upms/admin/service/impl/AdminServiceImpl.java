package cn.bizspring.cloud.upms.admin.service.impl;

import cn.bizspring.cloud.common.core.util.Filter;
import cn.bizspring.cloud.common.core.util.Sort;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.upms.admin.mapper.AdminMapper;
import cn.bizspring.cloud.upms.admin.service.AdminService;
import cn.bizspring.cloud.upms.common.entity.Admin;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * Service - 管理员
 * 
 */
@Service
@AllArgsConstructor
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

	private AdminMapper adminMapper;

	@Override
	public Admin find(String id) {
		return adminMapper.find(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Admin> findList(String... ids) {
		List<Admin> result = new ArrayList<>();
		if (ids != null) {
			for (String id : ids) {
				Admin admin = this.find(id);
				if (admin != null) {
					result.add(admin);
				}
			}
		}
		return result;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Admin getUser(Object principal) {
		return findByUsername(String.valueOf(principal));
	}

	@Override
	@Transactional(readOnly = true)
	public boolean supports(Class<?> userClass) {
		return userClass != null && Admin.class.isAssignableFrom(userClass);
	}

	@Override
	@Transactional(readOnly = true)
	public Admin findByUsername(String username) {
		return adminMapper.findByProperty("username", StringUtils.lowerCase(username));
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Admin> findPage(Pageable pageable) {
		IPage<Admin> iPage = super.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<Admin>(pageable.getPageNumber(), pageable.getPageSize()), getPageable(pageable));
		Page<Admin> page = new Page<Admin>(iPage.getRecords(), iPage.getTotal(), pageable);
		return page;
	}
	
	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public boolean save(Admin admin) {
		admin.setId(String.valueOf(IdWorker.getId()));
		admin.setVersion(0L);
		admin.setCreatedTime(new Date());
		return adminMapper.save(admin);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public Admin update(Admin admin) {
		Admin persistant = this.find(admin.getId());
		if (persistant != null) {
			BeanUtils.copyProperties(admin, persistant, new String[]{"createdTime", "updatedTime", "version"});
					
			persistant.setUpdatedTime(new Date());
			adminMapper.update(persistant);
		}
		return admin;
	}
	

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public Admin update(Admin admin, String... ignoreProperties) {
		Admin persistant = this.find(admin.getId());
		if (persistant != null) {
			BeanUtils.copyProperties(admin, persistant, (String[]) ArrayUtils.addAll(ignoreProperties, new String[]{"createdTime", "updatedTime", "version"}));
			persistant.setUpdatedTime(new Date());
			adminMapper.update(persistant);
		}
		return admin;
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(String id) {
		adminMapper.delete(Arrays.asList(id));
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(String... ids) {
		adminMapper.delete(Arrays.asList(ids));
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Admin admin) {
		adminMapper.delete(Arrays.asList(admin.getId()));
	}

	/**
	 * 转换分页信息
	 * 
	 */
	protected QueryWrapper<Admin> getPageable(Pageable pageable) {
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<Admin>();
		// 增加搜索属性、搜索值
		String searchProperty = pageable.getSearchProperty();
		String searchValue = pageable.getSearchValue();
		if (StringUtils.isNotEmpty(searchProperty) && StringUtils.isNotEmpty(searchValue)) {
			//queryWrapper.and(wrapper -> wrapper.like(searchProperty, searchValue));
			queryWrapper.like(searchProperty, searchValue);
		}
		// 排序属性
		String sortProperty = cn.bizspring.cloud.common.core.util.StringUtils.camel2Underline(pageable.getSortProperty());
		if (StringUtils.isNotEmpty(sortProperty)) {
			if (pageable.getSortDirection().equals(Sort.Direction.ASC)) {
				queryWrapper.orderByAsc(true, sortProperty);
			}
			if (pageable.getSortDirection().equals(Sort.Direction.DESC)) {
				queryWrapper.orderByDesc(true, sortProperty);
			}
		}
		// 筛选
		if (CollectionUtils.isNotEmpty(pageable.getFilters())) {
			queryWrapper = convertFilter(pageable.getFilters());
		}
		// 排序
		if (CollectionUtils.isNotEmpty(pageable.getSorts())) {
			queryWrapper = convertOrders(pageable.getSorts());
		}
		return queryWrapper;
	}
	
	/**
	 * 转换为Filter
	 * @param filters
	 * @return
	 */
	private QueryWrapper<Admin> convertFilter(List<Filter> filters) {
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<Admin>();
		if (CollectionUtils.isEmpty(filters)) {
			return queryWrapper;
		}
		for (Filter filter : filters) {
			if (filter == null) {
				continue;
			}
			String property = filter.getProperty();
			Filter.Operator operator = filter.getOperator();
			Object value = filter.getValue();
			Boolean ignoreCase = filter.getIgnoreCase();
			if (StringUtils.isEmpty(property)) {
				continue;
			}
			switch (operator) {
			case EQ:
				if (value != null) {
					if (BooleanUtils.isTrue(ignoreCase) && String.class.isAssignableFrom(property.getClass()) && value instanceof String) {
						queryWrapper.eq(property, ((String) value).toLowerCase());
					} else {
						queryWrapper.eq(property, value);
					}
				} else {
					queryWrapper.isNull(property);
				}
				break;
			case NE:
				if (value != null) {
					if (BooleanUtils.isTrue(ignoreCase) && String.class.isAssignableFrom(property.getClass()) && value instanceof String) {
						queryWrapper.ne(property, ((String) value).toLowerCase());
					} else {
						queryWrapper.ne(property, value);
					}
				} else {
					queryWrapper.isNotNull(property);
				}
				break;
			case GT:
				if (Number.class.isAssignableFrom(property.getClass()) && value instanceof Number) {
					queryWrapper.gt(property, (Number) value);
				}
				break;
			case LT:
				if (Number.class.isAssignableFrom(property.getClass()) && value instanceof Number) {
					queryWrapper.lt(property, (Number) value);
				}
				break;
			case GE:
				if (Number.class.isAssignableFrom(property.getClass()) && value instanceof Number) {
					queryWrapper.ge(property, (Number) value);
				}
				break;
			case LE:
				if (Number.class.isAssignableFrom(property.getClass()) && value instanceof Number) {
					queryWrapper.le(property, (Number) value);
				}
				break;
			case LIKE:
				if (String.class.isAssignableFrom(property.getClass()) && value instanceof String) {
					if (BooleanUtils.isTrue(ignoreCase)) {
						queryWrapper.like(property, ((String) value).toLowerCase());
					} else {
						queryWrapper.like(property, (String) value);
					}
				}
				break;
			case IN:
				queryWrapper.in(property, (String) value);
				break;
			case IS_NULL:
				queryWrapper.isNull(property);
				break;
			case IS_NOT_NULL:
				queryWrapper.isNotNull(property);
				break;
			}
		}
		return queryWrapper;
	}

	/**
	 * 转换为Sorts
	 * @param sorts
	 * @return
	 */
	private QueryWrapper<Admin> convertOrders(List<Sort> sorts) {
		QueryWrapper<Admin> sortList = new QueryWrapper<Admin>();
		if (CollectionUtils.isEmpty(sorts)) {
			return sortList;
		}
		for (Sort sort : sorts) {
			if (sort == null) {
				continue;
			}
			String property = sort.getProperty();
			Sort.Direction direction = sort.getDirection();
			if (StringUtils.isEmpty(property) || direction == null) {
				continue;
			}
			String[] columns = new String[] { property };
			switch (direction) {
			case ASC:
				sortList.orderByAsc(columns);
				break;
			case DESC:
				sortList.orderByDesc(columns);
				break;
			}
		}
		return sortList;
	}
	
}