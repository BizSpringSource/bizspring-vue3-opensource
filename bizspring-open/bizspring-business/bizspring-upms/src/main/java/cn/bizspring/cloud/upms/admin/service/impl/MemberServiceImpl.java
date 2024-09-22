package cn.bizspring.cloud.upms.admin.service.impl;

import cn.bizspring.cloud.common.core.util.*;
import cn.bizspring.cloud.upms.admin.mapper.*;
import cn.bizspring.cloud.upms.admin.service.MemberService;
import cn.bizspring.cloud.upms.common.entity.*;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Service - 会员
 * 
 */
@Service
@AllArgsConstructor
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

	/**
	 * E-mail身份表达式
	 */
	private static final Pattern EMAIL_PATTERN = Pattern.compile(".*@.*");

	/**
	 * 手机身份表达式
	 */
	private static final Pattern MOBILE_PATTERN = Pattern.compile("\\d+");

	private MemberMapper memberMapper;

	@Override
	public Member find(String id) {
		return memberMapper.find(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Member> findList(String... ids) {
		List<Member> result = new ArrayList<>();
		if (null != ids) {
			for (String id : ids) {
				Member entity = this.find(id);
				if (entity != null) {
					result.add(entity);
				}
			}
		}
		return result;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Member getUser(Object principal) {
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
		return userClass != null && Member.class.isAssignableFrom(userClass);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean usernameExists(String username) {
		return memberMapper.exists("username", StringUtils.lowerCase(username));
	}

	@Override
	@Transactional(readOnly = true)
	public Member findByUsername(String username) {
		return memberMapper.findByProperty("username", username);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean emailExists(String email) {
		return memberMapper.exists("email", StringUtils.lowerCase(email));
	}

	@Override
	@Transactional(readOnly = true)
	public boolean emailUnique(String id, String email) {
		return memberMapper.unique(id, "email", StringUtils.lowerCase(email));
	}

	@Override
	@Transactional(readOnly = true)
	public Member findByEmail(String email) {
		return memberMapper.findByProperty("email", StringUtils.lowerCase(email));
	}

	@Override
	@Transactional(readOnly = true)
	public boolean mobileExists(String mobile) {
		return memberMapper.exists("mobile", StringUtils.lowerCase(mobile));
	}

	@Override
	@Transactional(readOnly = true)
	public boolean mobileUnique(String id, String mobile) {
		return memberMapper.unique(id, "mobile", StringUtils.lowerCase(mobile));
	}

	@Override
	@Transactional(readOnly = true)
	public Member findByMobile(String mobile) {
		return memberMapper.findByProperty("mobile", StringUtils.lowerCase(mobile));
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Member> findPage(Pageable pageable) {
		IPage<Member> iPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<Member>(pageable.getPageNumber(), pageable.getPageSize());
		iPage.setRecords(memberMapper.findPage(iPage, getPageable(pageable)));
		Page<Member> page = new Page<Member>(iPage.getRecords(), iPage.getTotal(), pageable);
		return page;
	}

	/**
	 * 根据member实体携带参数 查找会员分页
	 *
	 * @param pageable
	 *            分页信息
	 * @return 会员分页
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Member> findPageByEntity(Pageable pageable,Member member){
		IPage<Member> iPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<Member>(pageable.getPageNumber(), pageable.getPageSize());
		iPage.setRecords(memberMapper.findPageByEntity(iPage, getPageable(pageable), member));
		Page<Member> page = new Page<Member>(iPage.getRecords(), iPage.getTotal(), pageable);
		return page;
	}

	@Override
	@Transactional
	public boolean save(Member member) {
		member.setId(String.valueOf(IdWorker.getId()));
		member.setVersion(0L);
		member.setCreatedTime(new Date());
		return memberMapper.save(member);
	}

	@Override
	@Transactional
	public Member update(Member entity, String... ignoreProperties) {
		Member persistant = this.find(entity.getId());
		if (persistant != null) {
			BeanUtils.copyProperties(entity, persistant, (String[]) ArrayUtils.addAll(ignoreProperties, new String[]{"createdTime", "updatedTime", "version"}));
			persistant.setUpdatedTime(new Date());
			memberMapper.update(persistant);
		}
		return entity;
	}
	
	@Override
	@Transactional(readOnly = true)
	public long count(Date beginDate, Date endDate) {
		return memberMapper.count(beginDate, endDate);
	}
	
	@Override
	@Transactional
	public void delete(String... ids) {
		memberMapper.delete(Arrays.asList(ids));
	}
	
	/**
	 * 转换分页信息
	 * 
	 */
	protected QueryWrapper<Member> getPageable(Pageable pageable) {
		QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>();
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
	private QueryWrapper<Member> convertFilter(List<Filter> filters) {
		QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>();
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
	private QueryWrapper<Member> convertOrders(List<Sort> sorts) {
		QueryWrapper<Member> sortList = new QueryWrapper<Member>();
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