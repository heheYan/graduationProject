package com.ycxy.animal.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.Query;

import com.ycxy.animal.dao.AnimalInfoDao;
import com.ycxy.animal.entity.AnimalInfoEntity;
import com.ycxy.animal.service.AnimalInfoService;


@Service("animalInfoService")
public class AnimalInfoServiceImpl extends ServiceImpl<AnimalInfoDao, AnimalInfoEntity> implements AnimalInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AnimalInfoEntity> page = this.page(
                new Query<AnimalInfoEntity>().getPage(params),
                new QueryWrapper<AnimalInfoEntity>()
        );

        return new PageUtils(page);
    }

}