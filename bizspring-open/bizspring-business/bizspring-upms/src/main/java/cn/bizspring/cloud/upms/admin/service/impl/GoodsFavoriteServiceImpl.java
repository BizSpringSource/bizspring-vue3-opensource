package cn.bizspring.cloud.upms.admin.service.impl;

import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.common.core.util.Filter;
import cn.bizspring.cloud.common.core.util.Sort;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.goods.common.entity.Goods;
import cn.bizspring.cloud.upms.admin.mapper.MemberMapper;
import cn.bizspring.cloud.upms.admin.mapper.GoodsFavoriteMapper;
import cn.bizspring.cloud.upms.admin.service.GoodsFavoriteService;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.GoodsFavorite;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Service - 商品收藏
 * 
 */
@Service
@AllArgsConstructor
public class GoodsFavoriteServiceImpl extends BizServiceImpl<GoodsFavorite> implements GoodsFavoriteService {

	private GoodsFavoriteMapper goodsFavoriteMapper;
	private MemberMapper memberMapper;

	@Override
	@Transactional(readOnly = true)
	public boolean exists(Member member, String goodsId) {
		return goodsFavoriteMapper.exists(member, goodsId);
	}



	@Override
	@Transactional(readOnly = true)
	public List<GoodsFavorite> findList(Member member, Integer count, List<Filter> filters, List<Sort> sorts) {
		QueryWrapper<GoodsFavorite> queryWrapper = createQueryWrapper(null, count, filters, sorts);
		return goodsFavoriteMapper.findList(queryWrapper, member);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<GoodsFavorite> findPage(Member member, Pageable pageable) {
		IPage<GoodsFavorite> iPage = getPluginsPage(pageable);
		iPage.setRecords(goodsFavoriteMapper.findPage(iPage, getPageable(pageable), member));
		return super.findPage(iPage, pageable);
	}

	/**
	 * 查找店铺中商品收藏数据
	 * @param goodss
	 * 			店铺商品
	 * @param pageable
	 * 			分页
	 * @return
	 */
	@Override
	public Page findFavoritePageOfStore(List<Goods> goodss, Pageable pageable){
		IPage<GoodsFavorite> iPage = getPluginsPage(pageable);
		iPage.setRecords(goodsFavoriteMapper.findFavoritePageOfStore(iPage, getPageable(pageable), goodss));
		return super.findPage(iPage, pageable);
	}


	@Override
	@Transactional(readOnly = true)
	public Long count(Member member) {
		return goodsFavoriteMapper.count(member);
	}

	public Long countStoreGoods(List<Goods> goodss, Date beginDate, Date endDate){
		return goodsFavoriteMapper.countStoreGoods(goodss,  beginDate,  endDate);
	}


	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "goodsFavorite", condition = "#useCache")
	public List<GoodsFavorite> findList(String memberId, Integer count, List<Filter> filters, List<Sort> sorts, boolean useCache) {
		Member member = memberMapper.find(memberId);
		if (memberId != null && member == null) {
			return Collections.emptyList();
		}
		QueryWrapper<GoodsFavorite> queryWrapper = createQueryWrapper(null, count, filters, sorts);
		return goodsFavoriteMapper.findList(queryWrapper, member);
	}

	@Override
	@CacheEvict(value = "goodsFavorite", allEntries = true)
	public boolean save(GoodsFavorite goodsFavorite) {
		return super.save(goodsFavorite);
	}

	@Override
	@CacheEvict(value = "goodsFavorite", allEntries = true)
	public GoodsFavorite update(GoodsFavorite goodsFavorite) {
		return super.update(goodsFavorite);
	}

	@Override
	@CacheEvict(value = "goodsFavorite", allEntries = true)
	public GoodsFavorite update(GoodsFavorite goodsFavorite, String... ignoreProperties) {
		return super.update(goodsFavorite, ignoreProperties);
	}

	@Override
	@CacheEvict(value = "goodsFavorite", allEntries = true)
	public void delete(String id) {
		super.delete(id);
	}

	@Override
	@CacheEvict(value = "goodsFavorite", allEntries = true)
	public void delete(String... ids) {
		super.delete(ids);
	}

	@Override
	@CacheEvict(value = "goodsFavorite", allEntries = true)
	public void delete(GoodsFavorite goodsFavorite) {
		super.delete(goodsFavorite);
	}

}