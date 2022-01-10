package com.ycxy.animal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycxy.animal.dao.MyAdoptionDao;
import com.ycxy.animal.entity.AnimalInfoEntity;
import com.ycxy.animal.entity.MyAdoptionEntity;
import com.ycxy.animal.service.AnimalInfoService;
import com.ycxy.animal.service.MyAdoptionService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * @author YhhWork
 */
@Service("myAdoptionService")
public class MyAdoptionServiceImpl extends ServiceImpl<MyAdoptionDao, MyAdoptionEntity> implements MyAdoptionService {

    @Autowired
    AnimalInfoService animalInfoService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<MyAdoptionEntity> wrapper = new QueryWrapper<>();
        if (params.containsKey("userId")) {
            wrapper.eq("user_id", params.get("userId"));
        }
        wrapper.orderByDesc("apply_date");
        wrapper.orderByAsc("status");
        IPage<MyAdoptionEntity> page = this.page(
                new Query<MyAdoptionEntity>().getPage(params),
                wrapper
        );

        page.getRecords().forEach(myAdoptionEntity -> {
            AnimalInfoEntity animalInfo = animalInfoService.getById(myAdoptionEntity.getAnimalId());
            myAdoptionEntity.setAnimalInfo(animalInfo);
        });

        return new PageUtils(page);
    }

}
