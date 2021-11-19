package com.ycxy.animal.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.Query;

import com.ycxy.animal.dao.AnimalTypeDao;
import com.ycxy.animal.entity.AnimalTypeEntity;
import com.ycxy.animal.service.AnimalTypeService;


@Service("animalTypeService")
public class AnimalTypeServiceImpl extends ServiceImpl<AnimalTypeDao, AnimalTypeEntity> implements AnimalTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AnimalTypeEntity> page = this.page(
                new Query<AnimalTypeEntity>().getPage(params),
                new QueryWrapper<AnimalTypeEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<AnimalTypeEntity> listByParentId(Long parentId) {
        QueryWrapper<AnimalTypeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        List<AnimalTypeEntity> dataList = baseMapper.selectList(queryWrapper);
        queryChild(dataList);
        return dataList;
    }

    @Override
    public void removeByParentId(Long parentId) {
        final QueryWrapper<AnimalTypeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        baseMapper.delete(queryWrapper);
    }

    private void queryChild(List<AnimalTypeEntity> dataList) {
        if (!dataList.isEmpty()) {
            dataList.forEach(animalTypeEntity -> {
                animalTypeEntity.setChildren(listByParentId(animalTypeEntity.getId()));
            });
        }
    }
}
