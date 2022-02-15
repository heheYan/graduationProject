package com.ycxy.realestate.dao;

import com.ycxy.realestate.entity.AttendanceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 考勤记录表
 * 
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:25
 */
@Mapper
public interface AttendanceDao extends BaseMapper<AttendanceEntity> {
	
}
