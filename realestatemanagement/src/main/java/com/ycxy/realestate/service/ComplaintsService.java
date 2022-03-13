package com.ycxy.realestate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.realestate.entity.ComplaintsEntity;

import java.util.Map;

/**
 * 投诉举报表
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-03-06 15:48:15
 */
public interface ComplaintsService extends IService<ComplaintsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

