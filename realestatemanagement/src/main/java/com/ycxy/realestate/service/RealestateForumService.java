package com.ycxy.realestate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.realestate.entity.RealestateForumEntity;

import java.util.Map;

/**
 * 公共论坛信息
 *
 * @author Student
 * @email Student@ycxy.com
 * @date 2022-02-08 16:40:36
 */
public interface RealestateForumService extends IService<RealestateForumEntity> {

    PageUtils queryPage(Map<String, Object> params);
}
