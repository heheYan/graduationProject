package com.ycxy.realestate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.realestate.entity.AttendanceEntity;

import java.util.Map;

/**
 * 考勤记录表
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:25
 */
public interface AttendanceService extends IService<AttendanceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

