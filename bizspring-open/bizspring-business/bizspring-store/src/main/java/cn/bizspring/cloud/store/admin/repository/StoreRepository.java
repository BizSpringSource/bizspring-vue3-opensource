package cn.bizspring.cloud.store.admin.repository;

import cn.bizspring.cloud.store.common.entity.Store;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface StoreRepository extends ElasticsearchRepository<Store, String> {

}
