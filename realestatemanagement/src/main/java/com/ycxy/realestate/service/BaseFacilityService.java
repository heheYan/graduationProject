package com.ycxy.realestate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.realestate.entity.BaseFacilityEntity;

import java.util.Map;

/**
 * 
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:25
 */
public interface BaseFacilityService extends IService<BaseFacilityEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

