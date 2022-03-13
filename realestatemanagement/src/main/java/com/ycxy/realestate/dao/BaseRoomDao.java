package com.ycxy.realestate.dao;

import com.ycxy.realestate.entity.BaseRoomEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:25
 */
@Mapper
public interface BaseRoomDao extends BaseMapper<BaseRoomEntity> {

    List<String> listBuilds();

    @MapKey("status")
    List<Map<String, Object>> countByBuildId(@Param("buildId") Long buildId);
}
