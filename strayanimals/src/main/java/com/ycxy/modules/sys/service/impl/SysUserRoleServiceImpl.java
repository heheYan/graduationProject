package com.ycxy.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycxy.common.utils.MapUtils;
import com.ycxy.modules.sys.dao.SysUserRoleDao;
import com.ycxy.modules.sys.entity.SysUserRoleEntity;
import com.ycxy.modules.sys.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 用户与角色对应关系
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {

	@Override
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		//先删除用户与角色关系
		this.removeByMap(new MapUtils().put("user_id", userId));

		if(roleIdList == null || roleIdList.size() == 0){
			return ;
		}

		//保存用户与角色关系
		for(Long roleId : roleIdList){
			SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
			sysUserRoleEntity.setUserId(userId);
			sysUserRoleEntity.setRoleId(roleId);

			this.save(sysUserRoleEntity);
		}
	}

	@Override
	public List<Long> queryRoleIdList(Long userId) {
		return baseMapper.queryRoleIdList(userId);
	}

	@Override
	public int deleteBatch(Long[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}

	@Override
	public Boolean isOrganUser(Long userId) {
		LambdaQueryWrapper<SysUserRoleEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SysUserRoleEntity::getUserId, userId);
		wrapper.and(q -> {
			q.eq(SysUserRoleEntity::getRoleId, 1)
					.or()
					.eq(SysUserRoleEntity::getRoleId, 1);
		});
		return !baseMapper.selectList(wrapper).isEmpty();
	}
}
