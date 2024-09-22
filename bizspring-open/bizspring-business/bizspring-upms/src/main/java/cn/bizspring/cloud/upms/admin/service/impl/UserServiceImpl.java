package cn.bizspring.cloud.upms.admin.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.common.core.util.*;
import cn.bizspring.cloud.upms.common.entity.*;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.bizspring.cloud.upms.admin.mapper.UserMapper;
import cn.bizspring.cloud.upms.admin.service.OrganRelationService;
import cn.bizspring.cloud.upms.admin.service.OrganService;
import cn.bizspring.cloud.upms.admin.service.MenuService;
import cn.bizspring.cloud.upms.admin.service.RoleMenuService;
import cn.bizspring.cloud.upms.admin.service.RoleService;
import cn.bizspring.cloud.upms.admin.service.UserRoleService;
import cn.bizspring.cloud.upms.admin.service.UserService;
import cn.bizspring.cloud.upms.common.dto.UserDTO;
import cn.bizspring.cloud.upms.common.dto.UserInfo;
import cn.bizspring.cloud.upms.common.dto.UserRegister;
import cn.bizspring.cloud.upms.common.vo.MenuVO;
import cn.bizspring.cloud.upms.common.vo.UserVO;
import cn.bizspring.cloud.common.core.enums.CacheEnum;
import cn.bizspring.cloud.common.core.enums.CommonEnum;
import cn.bizspring.cloud.common.data.tenant.TenantContextHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl extends BizServiceImpl<User> implements UserService {
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
	private final MenuService menuService;
	private final RoleService roleService;
	private final OrganService organService;
	private final UserRoleService userRoleService;
	private final OrganRelationService organRelationService;
	private final RoleMenuService roleMenuService;
	private final CacheManager cacheManager;

	private final UserMapper userMapper;

	@Transactional(rollbackFor = Exception.class)
	public User autoRegisterUser(String mobile) {
		//新建用户
		User user = new User();
		user.setId(String.valueOf(IdWorker.getId()));
		user.setUsername(mobile);
		user.setMobile(mobile);
		user.setIsEnabled(true);
		user.setDelFlag(false);
		user.setLockFlag(false);
		user.setPassword(ENCODER.encode(CommonEnum.DEFAULT_PASSWORD));
		user.setType(User.Type.MEMBER);
		user.setVersion(1l);
		user.setTenantId(TenantContextHolder.getTenantId());
		TenantContextHolder.setTenantId("1");
		userMapper.autoRegisterUser(user);

		return user;
	}

	/**
	 * 保存用户信息
	 *
	 * @param userDto DTO 对象
	 * @return ok/fail
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveUser(UserDTO userDto) {
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		user.setIsEnabled(true);
		user.setLockFlag(false);
		user.setType(User.Type.ADMIN);
		user.setVersion(CommonEnum.VERSION);
		user.setDelFlag(CommonEnum.STATUS_NORMAL);
		user.setPassword(ENCODER.encode(userDto.getPassword()));
		user.setId(String.valueOf(IdWorker.getId()));
		user.setCreatedTime(new Date());
		userMapper.saveAdmin(user);
		List<UserRole> userRoleList = userDto.getRoleIds()
				.stream().map(roleId -> {
					UserRole userRole = new UserRole();
					userRole.setUserId(user.getId());
					userRole.setTenantId(TenantContextHolder.getTenantId());
					userRole.setRoleId(roleId);
					return userRole;
				}).collect(Collectors.toList());
		return userRoleService.saveBatch(userRoleList);
	}

	/**
	 * 通过查用户的全部信息
	 *
	 * @param user 用户
	 * @return
	 */
	@Override
	public UserInfo findUserInfo(User user) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUser(user);
		//设置角色列表  （ID）
		List<String> roleIds = roleService.findRoleIdsByUserId(user.getId());

		//设置权限列表（menu.permission）
		Set<String> permissions = new HashSet<>();
		if(CommonUtils.isNotNull(roleIds)){
			userInfo.setRoles(ArrayUtil.toArray(roleIds, String.class));
			roleIds.forEach(roleId -> {
				List<String> permissionList = menuService.findMenuByRoleId(roleId)
						.stream()
						.filter(menuVo -> StringUtils.isNotEmpty(menuVo.getPermission()))
						.map(MenuVO::getPermission)
						.collect(Collectors.toList());
				permissions.addAll(permissionList);
			});
		}
		userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));
		return userInfo;
	}

	/**
	 * 分页查询用户信息（含有角色信息）
	 *
	 * @param page    分页对象
	 * @param userDTO 参数列表
	 * @return
	 */
	@Override
	public IPage getUsersWithRolePage(Page page, UserDTO userDTO) {
		return userMapper.getUserVosPage(page, userDTO);
	}

	/**
	 * 通过ID查询用户信息
	 *
	 * @param id 用户ID
	 * @return 用户信息
	 */
	@Override
	public UserVO selectUserVoById(String id) {
		return userMapper.getUserVoById(id);
	}

	/**
	 * 删除用户
	 *
	 * @param user 用户
	 * @return Boolean
	 */
	@Override
	@CacheEvict(value = CacheEnum.USER_CACHE, key = "#user.username")
	public Boolean deleteUserById(User user) {
		userRoleService.deleteByUserId(user.getId());
		this.removeById(user.getId());
		return Boolean.TRUE;
	}

	@Override
	@CacheEvict(value = CacheEnum.USER_CACHE, key = "#userDto.username")
	public Boolean updateUserInfo(UserDTO userDto) {
		UserVO userVO = userMapper.getUserVoById(userDto.getId());
		User user = new User();
		if (StrUtil.isNotBlank(userDto.getPassword())
				&& StrUtil.isNotBlank(userDto.getNewpassword1())) {//修改密码
			if (ENCODER.matches(userDto.getPassword(), userVO.getPassword())) {
				user.setPassword(ENCODER.encode(userDto.getNewpassword1()));
			} else {
				log.warn("原密码错误，修改密码失败:{}", userDto.getUsername());
				throw new RuntimeException("原密码错误，修改失败");
			}
		}
		user.setId(userVO.getId());
		user.setAvatar(userDto.getAvatar());
		user.setEmail(userDto.getEmail());
		baseMapper.updateById(user);
		return Boolean.TRUE;
	}

	@Override
	@CacheEvict(value = CacheEnum.USER_CACHE, key = "#userDto.username")
	@Transactional(rollbackFor = Exception.class)
	public Boolean updateUser(UserDTO userDto) {
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		user.setUpdatedTime(new Date());
		user.setPassword(null);
		this.updateById(user);

		userRoleService.remove(Wrappers.<UserRole>update().lambda()
				.eq(UserRole::getUserId, userDto.getId()));
		userDto.getRoleIds().forEach(roleId -> {
			UserRole userRole = new UserRole();
			userRole.setTenantId(TenantContextHolder.getTenantId());
			userRole.setUserId(user.getId());
			userRole.setRoleId(roleId);
			userRole.insert();
		});
		return Boolean.TRUE;
	}

	/**
	 * 查询上级机构的用户信息
	 *
	 * @param username 用户名
	 * @return R
	 */
	@Override
	public List<User> listAncestorUsers(String username) {
		User user = this.getOne(Wrappers.<User>query().lambda()
				.eq(User::getUsername, username));

		Organ organ = organService.getById(user.getOrganId());
		if (organ == null) {
			return null;
		}

		String parentId = organ.getParentId();
		return this.list(Wrappers.<User>query().lambda()
				.eq(User::getOrganId, parentId));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity register(UserRegister userRegister) {
		String tenantId = IdUtil.simpleUUID();
		TenantContextHolder.setTenantId(tenantId);
		//新建机构
		Organ organ = new Organ();
		organ.setId(tenantId);
		organ.setName(userRegister.getOrganname());
		organ.setParentId(CommonEnum.PARENT_ID);
		organ.setType(CommonEnum.ORGAN_TYPE_1);
		organ.setCode("10000");
		organService.save(organ);
		//新建机构关联
		organRelationService.insertOrganRelation(organ);
		//新建用户
		User user = new User();
		user.setOrganId(organ.getId());
		user.setUsername(userRegister.getUsername());
		user.setEmail(userRegister.getEmail());
		user.setPassword(ENCODER.encode(userRegister.getPassword()));
		baseMapper.insert(user);
		//新建角色
		Role role = new Role();
		role.setRoleName("管理员");
		role.setRoleCode(CommonEnum.ROLE_CODE_ADMIN);
		role.setRoleDesc(userRegister.getOrganname()+"管理员");
		role.setDsType(CommonEnum.DS_TYPE_0);
		roleService.save(role);
		//新建用户角色
		UserRole userRole = new UserRole();
		userRole.setRoleId(role.getId());
		userRole.setUserId(user.getId());
		userRoleService.save(userRole);
		//新建角色菜单，将role_id为2的角色所拥有的权限赋给新注册用户的角色
		List<RoleMenu> listRoleMenu = roleMenuService
				.list(Wrappers.<RoleMenu>query().lambda()
				.eq(RoleMenu::getRoleId,2))
				.stream().map(roleMenu -> {
					roleMenu.setRoleId(role.getId());
					return roleMenu;
				}).collect(Collectors.toList());
		roleMenuService.saveBatch(listRoleMenu);
		return R.ok();
	}

	@Override
	public User getByNoTenant(User user) {
		User u = userMapper.getByNoTenant(user);
		return u;
	}

	@Override
	public void bindPhone(UserDTO userDto) {
		User user = baseMapper.selectById(userDto.getId());
		LambdaUpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<User>().lambda();
		userUpdateWrapper.eq(User::getId,userDto.getId());
		if("2".equals(userDto.getDoType())){//绑定手机
			if(StrUtil.isNotBlank(user.getMobile())){
				throw new RuntimeException("该账号已有手机号绑定，请先解绑");
			}
			userUpdateWrapper.set(User::getMobile, userDto.getMobile());
		}else if("3".equals(userDto.getDoType())){//解绑
			userUpdateWrapper.set(User::getMobile, null);
		}
		this.update(userUpdateWrapper);
		//更新緩存
		cacheManager.getCache(CacheEnum.USER_CACHE).evict(user.getUsername());
	}


	@Override
	@Transactional(readOnly = true)
	public cn.bizspring.cloud.common.core.util.Page<User> findPage(Pageable pageable,User.Type type) {
			IPage<User> iPage = getPluginsPage(pageable);
			iPage.setRecords(userMapper.findPage(iPage, getPageable(pageable), type));
			return super.findPage(iPage, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public cn.bizspring.cloud.common.core.util.Page<User> findPageUser(Pageable pageable,User user,User.Type type) {
		IPage<User> iPage = getPluginsPage(pageable);
		iPage.setRecords(userMapper.findPageUser(iPage, getPageable(pageable),user, type));
		return super.findPage(iPage, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findAllBydType(User.Type type) {
		return userMapper.findAllBydType(type);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findAllByEntity(User entity) {
		return userMapper.findAllByEntity(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public List keywordSearchBydType(String keyword,User.Type type,Integer count){
		return userMapper.keywordSearchBydType(keyword,type,count);
	}

	@Override
	@Transactional(readOnly = true)
	public User getCurrent() {
		return getCurrent(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public <T extends User> T getCurrent(Class<T> userClass) {
		Subject subject = SecurityUtils.getSubject();
		User principal = subject != null && subject.getPrincipal() instanceof User ? (User) subject.getPrincipal() : null;
		if (principal != null) {
			User user = userMapper.find(principal.getId());
			if (userClass == null || (user != null && userClass.isAssignableFrom(user.getClass()))) {
				return (T) user;
			}
		}
		return null;
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public boolean save(User user) {
		return super.save(user);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public User update(User user) {
		return super.update(user);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public User update(User user, String... ignoreProperties) {
		return super.update(user, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(String id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(String... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(User user) {
		super.delete(user);
	}


}
