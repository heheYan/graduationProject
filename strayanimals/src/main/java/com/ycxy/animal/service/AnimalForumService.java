package com.ycxy.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.animal.entity.AnimalForumEntity;

import java.util.Map;

/**
 * 公共论坛信息
 *
 * @author Student
 * @email Student@ycxy.com
 * @date 2022-02-08 16:40:36
 */
public interface AnimalForumService extends IService<AnimalForumEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

