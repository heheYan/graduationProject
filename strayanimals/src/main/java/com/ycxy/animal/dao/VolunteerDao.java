package com.ycxy.animal.dao;

import com.ycxy.animal.entity.VolunteerEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 志愿者信息表
 * 
 * @author Student
 * @email Student@ycxy.com
 * @date 2021-11-09 23:13:39
 */
@Mapper
public interface VolunteerDao extends BaseMapper<VolunteerEntity> {
	
}
