package com.ycxy.realestate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.realestate.entity.CarspaceEntity;

import java.util.Map;

/**
 * 车位信息表
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:24
 */
public interface CarspaceService extends IService<CarspaceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

