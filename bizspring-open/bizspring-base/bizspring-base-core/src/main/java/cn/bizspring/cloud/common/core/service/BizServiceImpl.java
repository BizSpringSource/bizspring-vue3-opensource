package cn.bizspring.cloud.common.core.service;

import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.common.core.entity.BizEntity;
import cn.bizspring.cloud.common.core.entity.SortEntity;
import cn.bizspring.cloud.common.core.util.Filter;
import cn.bizspring.cloud.common.core.util.Sort;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Service - 基类
 */
@Transactional
public abstract class BizServiceImpl<T extends BizEntity> extends ServiceImpl<BizMapper<T>, T> implements BizService<T> {

	/**
	 * 实体类类型
	 */
	private Class<T> entityClass;

	/**
	 * BizMapper
	 */
	@Autowired
	private BizMapper<T> bizMapper;

	/**
	 * 构造方法
	 */
	@SuppressWarnings("unchecked")
	public BizServiceImpl() {
		ResolvableType resolvableType = ResolvableType.forClass(getClass());
		entityClass = (Class<T>) resolvableType.as(BizServiceImpl.class).getGeneric().resolve();
	}

	public void setBizMapper(BizMapper<T> bizMapper) {
		this.bizMapper = bizMapper;
	}

	@Override
	@GlobalTransactional
	@Transactional(readOnly = true)
	public T find(String id) {
		return bizMapper.find(id);
	}

	@Override
	@GlobalTransactional
	@Transactional(readOnly = true)
	public List<T> findAll() {
		return bizMapper.findAll();
	}

	@Override
	@GlobalTransactional
	@Transactional(readOnly = true)
	public List<T> findList(String... ids) {
		List<T> result = new ArrayList<>();
		if (ids != null) {
			for (String id : ids) {
				T entity = find(id);
				if (entity != null) {
					result.add(entity);
				}
			}
		}
		return result;
	}

	@Override
	@GlobalTransactional
	@Transactional(readOnly = true)
	public List<T> findList(Integer count, List<Filter> filters, List<Sort> sorts) {
		return findList(null, count, filters, sorts);
	}

	@Override
	@GlobalTransactional
	@Transactional(readOnly = true)
	public List<T> findList(Integer first, Integer count, List<Filter> filters, List<Sort> sorts) {
		QueryWrapper<T> queryWrapper = createQueryWrapper(first, count, filters, sorts);
		return super.list(queryWrapper);
	}

	@Override
	@GlobalTransactional
	@Transactional(readOnly = true)
	public Page<T> findPage(Pageable pageable) {
		if (pageable.getSortDirection() == null && pageable.getSortProperty() == null) {
			pageable.setSortDirection(Sort.Direction.DESC);
			pageable.setSortProperty("createdTime");
		}
		IPage<T> iPage = super.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<T>(pageable.getPageNumber(), pageable.getPageSize()), getPageable(pageable));
		Page<T> page = new Page<T>(iPage.getRecords(), iPage.getTotal(), pageable);
		return page;
	}

	@Override
	@GlobalTransactional
	@Transactional(readOnly = true)
	public Page<T> findPageByEntity(Pageable pageable, T entity) {
		if (pageable.getSortDirection() == null && pageable.getSortProperty() == null) {
			pageable.setSortDirection(Sort.Direction.DESC);
			pageable.setSortProperty("createdTime");
		}
		IPage<T> iPage = getPluginsPage(pageable);
		iPage.setRecords(bizMapper.findPageByEntity(iPage, getPageable(pageable), entity));
		return findPage(iPage, pageable);
	}

	@Override
	@GlobalTransactional
	@Transactional(readOnly = true)
	public List<T> findAllByEntity(T entity) {
		return bizMapper.findAllByEntity(entity);
	}

	@GlobalTransactional
	@Transactional(readOnly = true)
	public Page<T> findPage(IPage<T> iPage, Pageable pageable) {
		Page<T> page = new Page<T>(iPage.getRecords(), iPage.getTotal(), pageable);
		return page;
	}

	@Override
	@GlobalTransactional
	@Transactional(readOnly = true)
	public boolean exists(String id) {
		return bizMapper.find(id) != null;
	}

	@Override
	@GlobalTransactional
	@Transactional
	public boolean save(T entity) {
		entity.setId(String.valueOf(IdWorker.getId()));
		entity.setCreatedTime(new Date());
		entity.setVersion(0L);
		return bizMapper.save(entity);
	}

	@Override
	@GlobalTransactional
	@Transactional
	public T update(T entity) {
		T persistant = this.find(entity.getId());
		if (persistant != null) {
			BeanUtils.copyProperties(entity, persistant, new String[]{"createdTime", "updatedTime", "version"});
			persistant.setUpdatedTime(new Date());
			bizMapper.update(persistant);
		}
		return entity;
	}

	@Override
	@GlobalTransactional
	@Transactional
	public T update(T entity, String... ignoreProperties) {
		T persistant = this.find(entity.getId());
		if (persistant != null) {
			BeanUtils.copyProperties(entity, persistant, (String[]) ArrayUtils.addAll(ignoreProperties, new String[]{"createdTime", "updatedTime", "version"}));
			persistant.setUpdatedTime(new Date());
			bizMapper.update(persistant);
		}
		return entity;
	}

	@Override
	@GlobalTransactional
	@Transactional
	public void delete(String id) {
		super.removeById(id);
	}

	@Override
	@GlobalTransactional
	@Transactional
	public void delete(String... ids) {
		if (ids != null) {
			super.removeByIds(Arrays.asList(ids));
		}
	}

	@Override
	@GlobalTransactional
	@Transactional
	public void delete(T entity) {
		if (entity != null) {
			super.removeById(entity.getId());
		}
	}

	/**
	 * 分页插件
	 */
	protected com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> getPluginsPage(Pageable pageable) {
		com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> pluginsPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<T>(pageable.getPageNumber(), pageable.getPageSize());
		return pluginsPage;
	}

	/**
	 * 条件构造器
	 *
	 * @param first
	 * @param count
	 * @param filters
	 * @param sorts
	 * @return queryWrapper
	 */
	protected QueryWrapper<T> createQueryWrapper(Integer first, Integer count, List<Filter> filters, List<Sort> sorts) {
		QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
		if (CollectionUtils.isNotEmpty(sorts)) {
			if (CollectionUtils.isNotEmpty(filters)) {
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
			}
		}

		// 排序
		if (CollectionUtils.isNotEmpty(sorts)) {
			for (Sort sort : sorts) {
				if (sort == null) {
					continue;
				}
				String property = cn.bizspring.cloud.common.core.util.StringUtils.camel2Underline(sort.getProperty());
				Sort.Direction direction = sort.getDirection();
				String[] columns = new String[]{property};
				switch (direction) {
					case ASC:
						queryWrapper.orderByAsc(columns);
						break;
					case DESC:
						queryWrapper.orderByDesc(columns);
						break;
				}
			}
		} else {
			if (SortEntity.class.isAssignableFrom(entityClass)) {
				queryWrapper.orderByAsc(SortEntity.SORT);
			} else {
				queryWrapper.orderByAsc(cn.bizspring.cloud.common.core.util.StringUtils.camel2Underline("createdTime"));
			}
		}

		// 限制行数
		if (first != null && count != null) {
			queryWrapper.last(" LIMIT " + first + ", " + count);
		}
		if (first == null && count != null) {
			queryWrapper.last(" LIMIT 0, " + count);
		}
		return queryWrapper;
	}

	/**
	 * 转换分页信息
	 */
	protected QueryWrapper<T> getPageable(Pageable pageable) {
		QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
		// 增加搜索属性、搜索值
		String searchProperty = pageable.getSearchProperty();
		String searchValue = pageable.getSearchValue();
		if (StringUtils.isNotEmpty(searchProperty) && StringUtils.isNotEmpty(searchValue)) {
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
	 *
	 * @param filters
	 * @return
	 */
	private QueryWrapper<T> convertFilter(List<Filter> filters) {
		QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
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
	 *
	 * @param sorts
	 * @return
	 */
	private QueryWrapper<T> convertSorts(List<Sort> sorts) {
		QueryWrapper<T> sortList = new QueryWrapper<T>();
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
			String[] columns = new String[]{property};
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
