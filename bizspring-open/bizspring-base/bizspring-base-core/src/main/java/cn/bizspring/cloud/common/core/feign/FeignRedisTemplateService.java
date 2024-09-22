package cn.bizspring.cloud.common.core.feign;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.enums.ServiceNameEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author www.bizspring.cn
 */
@Service
@Lazy(false)
@FeignClient(contextId = "feignRedisTemplateService", value = ServiceNameEnum.UMPS_ADMIN_SERVICE)
public interface FeignRedisTemplateService {

		/**
		 * 保存属性
		 *
		 * @param key   key值
		 * @param value value值
		 * @param time  时间戳
		 */

		@PostMapping("/provider/redis_template/set_time")
		void Set(String key, Object value, long time, @RequestHeader(SecurityEnum.FROM) String from);

		/**
		 * 保存属性
		 *
		 * @param key   key值
		 * @param value value值
		 */
		@PostMapping("/provider/redis_template/set")
		void Set(String key, Object value, @RequestHeader(SecurityEnum.FROM) String from);


		/**
		 * 向Hash结构中放入一个属性
		 *
		 * @param key     外部key
		 * @param hashKey 内部key
		 * @param value   内部key的value
		 * @param time    过期时间
		 * @return 返回是否成功
		 */
		@PostMapping("/provider/redis_template/set_time")
		Boolean Set(String key, String hashKey, Object value, long time, @RequestHeader(SecurityEnum.FROM) String from);
		/**
		 * 向Hash结构中放入一个属性
		 *
		 * @param key     外部key
		 * @param hashKey 内部key
		 * @param value   内部key的value
		 */
		@PostMapping("/provider/redis_template/set_hashkey")
		void Set(String key, String hashKey, Object value, @RequestHeader(SecurityEnum.FROM) String from);

		/**
		 * 直接设置整个Hash结构
		 *
		 * @param key  外部key
		 * @param map  hashMap值
		 * @param time 过期时间
		 * @return 返回是否成功
		 */
		@PostMapping("/provider/redis_template/set_all_time")
		Boolean SetAll(String key, Map<String, Object> map, long time, @RequestHeader(SecurityEnum.FROM) String from);
		/**
		 * 直接设置整个Hash结构
		 *
		 * @param key 外部key
		 * @param map hashMap值
		 */
		@PostMapping("/provider/redis_template/set_all")
		void SetAll(String key, Map<String, ?> map, @RequestHeader(SecurityEnum.FROM) String from);


		/**
		 * 获取属性
		 *
		 * @param key key值
		 * @return 返回对象
		 */
		@PostMapping("/provider/redis_template/get")
		Object Get(String key, @RequestHeader(SecurityEnum.FROM) String from);
		/**
		 * 获取Hash结构中的属性
		 *
		 * @param key     外部key值
		 * @param hashKey 内部key值
		 * @return 返回内部key的value
		 */
		@PostMapping("/provider/redis_template/get_hashkey")
		Object Get(String key, String hashKey, @RequestHeader(SecurityEnum.FROM) String from);
		/**
		 * 直接获取整个Hash结构
		 *
		 * @param key 外部key值
		 * @return 返回hashMap
		 */
		@PostMapping("/provider/redis_template/get_all")
		Map<Object, Object> GetAll(String key, @RequestHeader(SecurityEnum.FROM) String from);
		/**
		 * 删除属性
		 *
		 * @param key key值
		 * @return 返回成功
		 */
		@PostMapping("/provider/redis_template/del")
		Boolean Del(String key, @RequestHeader(SecurityEnum.FROM) String from);

		/**
		 * 批量删除属性
		 *
		 * @param keys key值集合
		 * @return 返回删除数量
		 */
		@PostMapping("/provider/redis_template/dels")
		Long Del(List<String> keys, @RequestHeader(SecurityEnum.FROM) String from);
		/**
		 * 删除Hash结构中的属性
		 *
		 * @param key     外部key值
		 * @param hashKey 内部key值
		 */
		@PostMapping("/provider/redis_template/del_hashkey")
		void Del(String key, @RequestHeader(SecurityEnum.FROM) String from, Object... hashKey);
		/**
		 * 设置过期时间
		 *
		 * @param key  key值
		 * @param time 时间戳
		 * @return 返回成功
		 */
		@PostMapping("/provider/redis_template/expire")
		Boolean Expire(String key, long time, @RequestHeader(SecurityEnum.FROM) String from);
		/**
		 * 获取过期时间
		 *
		 * @param key key值
		 * @return 返回时间戳
		 */
		@PostMapping("/provider/redis_template/get_expire")
		Long GetExpire(String key, @RequestHeader(SecurityEnum.FROM) String from);
		/**
		 * 判断key是否存在
		 *
		 * @param key key值
		 * @return 返回
		 */
		@PostMapping("/provider/redis_template/has_key")
		Boolean HasKey(String key, @RequestHeader(SecurityEnum.FROM) String from);
		/**
		 * 按delta递增
		 *
		 * @param key   key值
		 * @param delta delta值
		 * @return 返回递增后结果
		 */
		@PostMapping("/provider/redis_template/incr")
		Long Incr(String key, long delta, @RequestHeader(SecurityEnum.FROM) String from);
		/**
		 * 按delta递减
		 *
		 * @param key   key值
		 * @param delta delta值
		 * @return 返回递减后结果
		 */
		@PostMapping("/provider/redis_template/decr")
		Long Decr(String key, long delta, @RequestHeader(SecurityEnum.FROM) String from);

		/**
		 * 判断Hash结构中是否有该属性
		 *
		 * @param key     外部key
		 * @param hashKey 内部key
		 * @return 返回是否存在
		 */
		@PostMapping("/provider/redis_template/has_key_hashkey")
		Boolean HasKey(String key, String hashKey, @RequestHeader(SecurityEnum.FROM) String from);
		/**
		 * Hash结构中属性递增
		 *
		 * @param key     外部key
		 * @param hashKey 内部key
		 * @param delta   递增条件
		 * @return 返回递增后的数据
		 */
		@PostMapping("/provider/redis_template/incr_delta")
		Long Incr(String key, String hashKey, Long delta, @RequestHeader(SecurityEnum.FROM) String from);
		/**
		 * Hash结构中属性递减
		 *
		 * @param key     外部key
		 * @param hashKey 内部key
		 * @param delta   递增条件
		 * @return 返回递减后的数据
		 */
		@PostMapping("/provider/redis_template/decr_hashkey")
		Long Decr(String key, String hashKey, Long delta, @RequestHeader(SecurityEnum.FROM) String from);
		/**
		 * 获取Set结构
		 *
		 * @param key key
		 * @return 返回set集合
		 */
		@PostMapping("/provider/redis_template/members")
		Set<Object> Members(String key, @RequestHeader(SecurityEnum.FROM) String from);
		/**
		 * 向Set结构中添加属性
		 *
		 * @param key    key
		 * @param values value集
		 * @return 返回增加数量
		 */
		@PostMapping("/provider/redis_template/add")
		Long Add(String key, @RequestHeader(SecurityEnum.FROM) String from, Object... values);
		/**
		 * 向Set结构中添加属性
		 *
		 * @param key    key
		 * @param time   过期时间
		 * @param values 值集合
		 * @return 返回添加的数量
		 */
		@PostMapping("/provider/redis_template/add_time")
		Long Add(String key, long time, @RequestHeader(SecurityEnum.FROM) String from, Object... values);
		/**
		 * 是否为Set中的属性
		 *
		 * @param key   key
		 * @param value value
		 * @return 返回是否存在
		 */
		@PostMapping("/provider/redis_template/is_member")
		Boolean IsMember(String key, Object value, @RequestHeader(SecurityEnum.FROM) String from);
		/**
		 * 获取Set结构的长度
		 *
		 * @param key key
		 * @return 返回长度
		 */
		@PostMapping("/provider/redis_template/size")
		Long Size(String key, @RequestHeader(SecurityEnum.FROM) String from);
		/**
		 * 删除Set结构中的属性
		 *
		 * @param key    key
		 * @param values value集合
		 * @return 删除掉的数据量
		 */
		@PostMapping("/provider/redis_template/remove")
		Long Remove(String key, @RequestHeader(SecurityEnum.FROM) String from, Object... values);
		/**
		 * 从List结构中移除属性
		 *
		 * @param key   key
		 * @param count 总量
		 * @param value value
		 * @return 返回删除后的长度
		 */
		@PostMapping("/provider/redis_template/remove_count")
		Long Remove(String key, long count, Object value, @RequestHeader(SecurityEnum.FROM) String from);

		/**
		 * 获取List结构中的属性
		 *
		 * @param key   key
		 * @param start 开始
		 * @param end   结束
		 * @return 返回查询的集合
		 */
		@PostMapping("/provider/redis_template/range")
		List<Object> Range(String key, long start, long end, @RequestHeader(SecurityEnum.FROM) String from);
		/**
		 * 根据索引获取List中的属性
		 *
		 * @param key   key
		 * @param index 索引
		 * @return 对象
		 */
		@PostMapping("/provider/redis_template/index")
		Object Index(String key, long index, @RequestHeader(SecurityEnum.FROM) String from);
		/**
		 * 向List结构中添加属性
		 *
		 * @param key   key
		 * @param value value
		 * @return 增加后的长度
		 */
		@PostMapping("/provider/redis_template/push")
		Long Push(String key, Object value, @RequestHeader(SecurityEnum.FROM) String from);
		/**
		 * 向List结构中添加属性
		 *
		 * @param key   key
		 * @param value value
		 * @param time  过期时间
		 * @return 增加后的长度
		 */
		@PostMapping("/provider/redis_template/push_time")
		Long Push(String key, Object value, long time, @RequestHeader(SecurityEnum.FROM) String from);
		/**
		 * 向List结构中批量添加属性
		 *
		 * @param key    key
		 * @param values value 集合
		 * @return 增加后的长度
		 */
		@PostMapping("/provider/redis_template/push_all")
		Long PushAll(String key, @RequestHeader(SecurityEnum.FROM) String from, Object... values);
		/**
		 * 向List结构中批量添加属性
		 *
		 * @param key    key
		 * @param time   过期时间
		 * @param values value集合
		 * @return 增加后的长度
		 */
		@PostMapping("/provider/redis_template/push_all_time")
		Long PushAll(String key, @RequestHeader(SecurityEnum.FROM) String from, Long time, Object... values);

}
