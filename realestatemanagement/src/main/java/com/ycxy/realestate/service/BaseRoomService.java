package com.ycxy.realestate.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.realestate.entity.BaseRoomEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:25
 */
public interface BaseRoomService extends IService<BaseRoomEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据楼栋id判断是否存在房间记录
     *
     * @param buildId   楼栋id
     * @return
     */
    int countByBuildId(Long buildId);

    List<Map<String, Object>> countByBuildIdAndStatus(Long buildId);

    /**
     * 查询除自己外本楼是否已存在当前房间号
     *
     * @param no      房号
     * @param buildId 楼栋id
     * @param id      当前记录
     * @return 是否存在
     */
    boolean isExistByNoAndBuildId(String no, Long buildId, Long id);

    List<String> listBuilds();

    List<BaseRoomEntity> listByBuildId(Long buildId);
}
