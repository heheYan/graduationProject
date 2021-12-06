package com.ycxy.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycxy.animal.entity.PualificationsEntity;
import com.ycxy.common.utils.PageUtils;

import java.util.Map;

/**
 * 志愿者信息表
 *
 * @author Student
 * @email Student@ycxy.com
 * @date 2021-11-09 23:13:39
 */
public interface PualificationsService extends IService<PualificationsEntity> {
    PualificationsEntity findByUserId(Long userId);
    PageUtils queryPage(Map<String, Object> params);
}
