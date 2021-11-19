package com.ycxy.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.animal.entity.AnimalTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Student
 * @email Student@ycxy.com
 * @date 2021-11-09 23:13:39
 */
public interface AnimalTypeService extends IService<AnimalTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<AnimalTypeEntity> listByParentId(Long parentId);

    void removeByParentId(Long parentId);
}
