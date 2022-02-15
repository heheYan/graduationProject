package com.ycxy.modules.oss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycxy.modules.oss.entity.SysOssEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件上传
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysOssDao extends BaseMapper<SysOssEntity> {
	
}
