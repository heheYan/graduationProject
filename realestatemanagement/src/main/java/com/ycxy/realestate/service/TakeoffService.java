package com.ycxy.realestate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.realestate.entity.TakeoffEntity;

import java.util.Map;

/**
 * 请假信息表
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:25
 */
public interface TakeoffService extends IService<TakeoffEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

