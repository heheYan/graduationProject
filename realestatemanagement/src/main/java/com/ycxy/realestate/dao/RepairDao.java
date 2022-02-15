package com.ycxy.realestate.dao;

import com.ycxy.realestate.entity.RepairEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 报修信息表
 * 
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:24
 */
@Mapper
public interface RepairDao extends BaseMapper<RepairEntity> {
	
}
