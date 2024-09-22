package cn.bizspring.cloud.goods.admin.mapper;

import java.util.List;

import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.goods.common.entity.Brand;
import cn.bizspring.cloud.goods.common.entity.GoodsCategory;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * Mapper - 品牌
 * 
 */
public interface BrandMapper extends BizMapper<Brand> {

	/**
	 * 查找品牌
	 * 
	 * @param goodsCategory
	 *            商品分类
	 * @return 品牌
	 */
	List<Brand> findList(@Param("queryWrapper")QueryWrapper<Brand> queryWrapper, @Param("goodsCategory") GoodsCategory goodsCategory);

}