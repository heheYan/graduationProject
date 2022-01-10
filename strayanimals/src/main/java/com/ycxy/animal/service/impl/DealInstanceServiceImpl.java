package com.ycxy.animal.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ycxy.common.utils.Constant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.Query;

import com.ycxy.animal.dao.DealInstanceDao;
import com.ycxy.animal.entity.DealInstanceEntity;
import com.ycxy.animal.service.DealInstanceService;


@Service("dealInstanceService")
public class DealInstanceServiceImpl extends ServiceImpl<DealInstanceDao, DealInstanceEntity> implements DealInstanceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DealInstanceEntity> page = this.page(
                new Query<DealInstanceEntity>().getPage(params),
                new QueryWrapper<DealInstanceEntity>().lambda().eq(DealInstanceEntity::getStatus, 0)
        );

        return new PageUtils(page);
    }

    @Override
    public DealInstanceEntity getByAnimalId(Long animalId) {
        LambdaQueryWrapper<DealInstanceEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DealInstanceEntity::getAnimalId, animalId);
        wrapper.eq(DealInstanceEntity::getStatus, 0);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public void removeByAnimalIds(List<Long> animalIds) {
        LambdaQueryWrapper<DealInstanceEntity> wrapper = new LambdaQueryWrapper<>();
        for (Long animalId: animalIds) {
            wrapper.clear();
            wrapper.eq(DealInstanceEntity::getAnimalId, animalId);
            baseMapper.delete(wrapper);
        }
    }

    /**
     * 获取当前活动信息
     *
     * @param id 动物id
     */
    @Override
    public DealInstanceEntity getCurrentStep(Long id) {
        LambdaQueryWrapper<DealInstanceEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DealInstanceEntity::getAnimalId, id);
        wrapper.eq(DealInstanceEntity::getStatus, 0);
        return baseMapper.selectOne(wrapper);
    }

    /**
     * 根据animalid获取流转记录
     *
     * @param id 动物id
     * @return
     */
    @Override
    public List<DealInstanceEntity> listByAnimalId(Long id) {
        LambdaQueryWrapper<DealInstanceEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DealInstanceEntity::getAnimalId, id);
        wrapper.orderByAsc(DealInstanceEntity::getId);
        return baseMapper.selectList(wrapper);
    }

}
