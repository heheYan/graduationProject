package com.ycxy.realestate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.realestate.entity.AnnouncementEntity;

import java.util.Map;

/**
 * 公共论坛信息
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:24
 */
public interface AnnouncementService extends IService<AnnouncementEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

