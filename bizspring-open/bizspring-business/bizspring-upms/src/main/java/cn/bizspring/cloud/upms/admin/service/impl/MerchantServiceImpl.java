package cn.bizspring.cloud.upms.admin.service.impl;

import java.util.*;
import java.util.regex.Pattern;

import cn.bizspring.cloud.common.core.util.*;
import cn.bizspring.cloud.upms.admin.mapper.MerchantMapper;
import cn.bizspring.cloud.upms.admin.service.MerchantService;
import cn.bizspring.cloud.upms.common.entity.Merchant;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * Service - 商家
 * 
 */
@Service
@AllArgsConstructor
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements MerchantService {

	/**
	 * E-mail身份配比
	 */
	private static final Pattern EMAIL_PATTERN = Pattern.compile(".*@.*");

	/**
	 * 手机身份配比
	 */
	private static final Pattern MOBILE_PATTERN = Pattern.compile("\\d+");

	private MerchantMapper merchantMapper;

	@Override
	@Transactional(readOnly = true)
	public Merchant find(String id) {
		return merchantMapper.find(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Merchant> findPage(Pageable pageable) {
		IPage<Merchant> iPage = super.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<Merchant>(pageable.getPageNumber(), pageable.getPageSize()), getPageable(pageable));
		Page<Merchant> page = new Page<Merchant>(iPage.getRecords(), iPage.getTotal(), pageable);
		return page;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Merchant getUser(Object principal) {
		String value = String.valueOf(principal);
		if (EMAIL_PATTERN.matcher(value).matches()) {
			return findByEmail(value);
		} else if (MOBILE_PATTERN.matcher(value).matches()) {
			return findByMobile(value);
		} else {
			return findByUsername(value);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public boolean supports(Class<?> userClass) {
		return userClass != null && Merchant.class.isAssignableFrom(userClass);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean usernameExists(String username) {
		boolean value = merchantMapper.exists("username", StringUtils.lowerCase(username));
		return value;
	}

	@Override
	@Transactional(readOnly = true)
	public Merchant findByUsername(String username) {
		return merchantMapper.findByProperty("username", StringUtils.lowerCase(username));
	}

	@Override
	@Transactional(readOnly = true)
	public boolean emailExists(String email) {
		return merchantMapper.exists("email", StringUtils.lowerCase(email));
	}

	@Override
	@Transactional(readOnly = true)
	public boolean emailUnique(String id, String email) {
		return merchantMapper.unique(id, "email", StringUtils.lowerCase(email));
	}

	@Override
	@Transactional(readOnly = true)
	public Merchant findByEmail(String email) {
		return merchantMapper.findByProperty("email", StringUtils.lowerCase(email));
	}

	@Override
	@Transactional(readOnly = true)
	public boolean mobileExists(String mobile) {
		return merchantMapper.exists("mobile", StringUtils.lowerCase(mobile));
	}

	@Override
	@Transactional(readOnly = true)
	public boolean mobileUnique(String id, String mobile) {
		return merchantMapper.unique(id, "mobile", StringUtils.lowerCase(mobile));
	}

	@Override
	@Transactional(readOnly = true)
	public Merchant findByMobile(String mobile) {
		return merchantMapper.findByProperty("mobile", StringUtils.lowerCase(mobile));
	}

	@Override
	@Transactional
	public boolean save(Merchant merchant) {
		merchant.setId(String.valueOf(IdWorker.getId()));
		merchant.setCreatedTime(new Date());
		merchant.setVersion(0L);
		return merchantMapper.save(merchant);
	}

	@Override
	@Transactional
	public Merchant update(Merchant merchant) {
		Merchant persistant = this.find(merchant.getId());
		if (persistant != null) {
			BeanUtils.copyProperties(merchant, persistant, new String[]{"createdTime", "updatedTime", "version"});
					
			persistant.setUpdatedTime(new Date());
			merchantMapper.update(persistant);
		}
		return merchant;
	}

	@Override
	@Transactional
	public Merchant update(Merchant merchant, String... ignoreProperties) {
		Merchant persistant = this.find(merchant.getId());
		if (persistant != null) {
			BeanUtils.copyProperties(merchant, persistant, (String[]) ArrayUtils.addAll(ignoreProperties, new String[]{"createdTime", "updatedTime", "version"}));
			persistant.setUpdatedTime(new Date());
			merchantMapper.update(persistant);
		}
		return merchant;
	}
	
	@Override
	@Transactional(readOnly = true)
	public long count(Date beginDate, Date endDate) {
		return merchantMapper.count(beginDate, endDate);
	}
	
	@Override
	@Transactional
	public void delete(String id) {
		merchantMapper.delete(id);
	}

	@Override
	@Transactional
	public void delete(String... ids) {
		if (ids != null) {
			for (String id : ids) {
				merchantMapper.delete(id);
			}
		}
	}
	
	/**
	 * 转换分页信息
	 * 
	 */
	protected QueryWrapper<Merchant> getPageable(Pageable pageable) {
		QueryWrapper<Merchant> queryWrapper = new QueryWrapper<Merchant>();
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
			queryWrapper = convertSorts(pageable.getSorts());
		}
		return queryWrapper;
	}

	/**
	 * 转换为Filter
	 * @param filters
	 * @return
	 */
	private QueryWrapper<Merchant> convertFilter(List<Filter> filters) {
		QueryWrapper<Merchant> queryWrapper = new QueryWrapper<Merchant>();
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
	 * 转换为Sort
	 * @param sorts
	 * @return
	 */
	private QueryWrapper<Merchant> convertSorts(List<Sort> sorts) {
		QueryWrapper<Merchant> sortList = new QueryWrapper<Merchant>();
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