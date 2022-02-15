package com.ycxy.realestate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.realestate.entity.RepairEntity;

import java.util.Map;

/**
 * 报修信息表
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:24
 */
public interface RepairService extends IService<RepairEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

