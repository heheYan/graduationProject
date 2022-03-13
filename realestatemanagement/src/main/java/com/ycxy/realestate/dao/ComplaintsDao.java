package com.ycxy.realestate.dao;

import com.ycxy.realestate.entity.ComplaintsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 投诉举报表
 * 
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-03-06 15:48:15
 */
@Mapper
public interface ComplaintsDao extends BaseMapper<ComplaintsEntity> {
	
}
