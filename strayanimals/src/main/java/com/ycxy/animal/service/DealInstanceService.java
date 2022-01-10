package com.ycxy.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.animal.entity.DealInstanceEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Student
 * @email Student@ycxy.com
 * @date 2021-11-09 23:13:39
 */
public interface DealInstanceService extends IService<DealInstanceEntity> {

    PageUtils queryPage(Map<String, Object> params);

    DealInstanceEntity getByAnimalId(Long animalId);

    void removeByAnimalIds(List<Long> animalIds);

    /**
     * 获取当前活动信息
     *
     * @param id    动物id
     */
    DealInstanceEntity getCurrentStep(Long id);

    /**
     * 根据animalid获取流转记录
     * @param id    动物id
     * @return
     */
    List<DealInstanceEntity> listByAnimalId(Long id);
}
