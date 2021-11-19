package com.ycxy.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.animal.entity.AnimalInfoEntity;

import java.util.Map;

/**
 * 
 *
 * @author Student
 * @email Student@ycxy.com
 * @date 2021-11-09 23:13:39
 */
public interface AnimalInfoService extends IService<AnimalInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

