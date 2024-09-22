package cn.bizspring.cloud.goods.admin.repository;

import cn.bizspring.cloud.goods.common.entity.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface GoodsRepository extends ElasticsearchRepository<Goods, String> {

}
