package cn.bizspring.cloud.upms.admin.service.impl;

import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.common.core.util.CommonUtils;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.upms.admin.mapper.RegionMapper;
import cn.bizspring.cloud.upms.admin.mapper.AddressMapper;
import cn.bizspring.cloud.upms.admin.service.AddressService;
import cn.bizspring.cloud.upms.common.entity.Region;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.Address;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service - 收货地址
 * 
 */
@Service
@AllArgsConstructor
public class AddressServiceImpl extends BizServiceImpl<Address> implements AddressService {

	private AddressMapper addressMapper;
	private RegionMapper regionMapper;

	@Override
	@Transactional(readOnly = true)
	public Address findDefault(Member member) {
		return addressMapper.findDefault(member);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Address> findList(Member member) {
		return addressMapper.findList(member);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Address> findPage(Member member, Pageable pageable) {
		IPage<Address> iPage = getPluginsPage(pageable);
		iPage.setRecords(addressMapper.findPage(iPage, getPageable(pageable), member));
		return super.findPage(iPage, pageable);
	}

	@Override
	@Transactional
	public boolean save(Address address) {
		if (BooleanUtils.isTrue(address.getIsDefault()) && address.getMemberId() != null) {
			addressMapper.clearDefault(address.getMemberId());
		}
		if (address.getRegionId() != null) {
			Region region = regionMapper.find(address.getRegionId());
			if(CommonUtils.isNotNull(region)){
				address.setRegionName(region.getFullName());
			}
		}
		return super.save(address);
	}

	@Override
	@Transactional
	public Address update(Address address) {
		if (address.getRegionId() != null) {
			Region region = regionMapper.find(address.getRegionId());
			if(CommonUtils.isNotNull(region)){
				address.setRegionName(region.getFullName());
			}
		}
		Address pAddress = super.update(address);
		if (BooleanUtils.isTrue(pAddress.getIsDefault()) && pAddress.getMemberId() != null) {
			addressMapper.clearDefaultExclude(pAddress.getMemberId(), pAddress.getId());
		}
		return pAddress;
	}

}