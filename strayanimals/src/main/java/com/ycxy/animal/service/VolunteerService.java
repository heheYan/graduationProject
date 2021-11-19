package com.ycxy.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.animal.entity.VolunteerEntity;

import java.util.Map;

/**
 * 志愿者信息表
 *
 * @author Student
 * @email Student@ycxy.com
 * @date 2021-11-09 23:13:39
 */
public interface VolunteerService extends IService<VolunteerEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

