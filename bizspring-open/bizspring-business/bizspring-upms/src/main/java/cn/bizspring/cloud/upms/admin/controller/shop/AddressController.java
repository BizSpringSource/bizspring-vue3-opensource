package cn.bizspring.cloud.upms.admin.controller.shop;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.core.util.SecurityUtils;
import cn.bizspring.cloud.upms.admin.service.RegionService;
import cn.bizspring.cloud.upms.admin.service.MemberService;
import cn.bizspring.cloud.upms.admin.service.AddressService;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.Address;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller - 收货地址
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "address", tags = "商城 - 收货地址")
@RestController("memberAddressController")
@RequestMapping("/member/address")
public class AddressController extends BizController {

	private RegionService regionService;
	private AddressService addressService;
	private MemberService memberService;

	/**
	 * 列表
	 */
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/list")
	public ResponseEntity list(Integer pageNumber) {
		Member currentUser =memberService.find(SecurityUtils.getUserId());
		Pageable pageable = new Pageable(pageNumber, Pageable.PAGE_SIZE);
		return R.ok(addressService.findPage(currentUser, pageable));
	}

	/**
	 * 添加
	 */
	@ApiOperation(value = "添加", notes = "添加")
	@ApiOperationSupport(order = 2)
	@GetMapping("/add")
	public ResponseEntity add() {
		Map<String, Object> data = new HashMap<>(2);
		data.put("regions", regionService.findChildren(null,true,null));
		return R.ok(data);
	}

	/**
	 * 保存
	 */
	@ApiOperation(value = "保存", notes = "保存")
	@ApiOperationSupport(order = 2)
	@PostMapping("/save")
	public ResponseEntity save(@RequestBody Address addressForm) {
		Member currentUser =memberService.find(SecurityUtils.getUserId());
		if (!isValid(addressForm)) {
			return R.INVALID_ENTITY;
		}
		addressForm.setMemberId(currentUser.getId());
		addressService.save(addressForm);
		return R.OK;
	}

	/**
	 * 编辑
	 */
	@ApiOperation(value = "编辑", notes = "编辑")
	@ApiOperationSupport(order = 2)
	@GetMapping("/edit")
	public ResponseEntity edit(String id) {
		Map<String, Object> data = new HashMap<>(2);
		Address address =addressService.find(id);
		if (address == null) {
			return R.failed();
		}
		data.put("regions", regionService.findChildren(null,true,null));
		data.put("address", address);
		return R.ok(data);
	}

	/**
	 * 更新
	 */
	@ApiOperation(value = "更新", notes = "更新")
	@ApiOperationSupport(order = 2)
	@PostMapping("/update")
	public ResponseEntity update(@RequestBody Address addressForm) {
		Address address=addressService.find(addressForm.getId());
		if (address == null) {
			return R.INVALID_ENTITY;
		}
		BeanUtils.copyProperties(addressForm, address, "id", "member");
		addressService.update(address);
		return R.OK;
	}

	/**
	 * 删除
	 */
	@ApiOperation(value = "删除", notes = "删除")
	@ApiOperationSupport(order = 2)
	@DeleteMapping("/delete")
	public ResponseEntity delete(String id) {
		Address address=addressService.find(id);
		if (address == null) {
			return R.NOT_FOUND;
		}
		addressService.delete(address);
		return R.OK;
	}

}