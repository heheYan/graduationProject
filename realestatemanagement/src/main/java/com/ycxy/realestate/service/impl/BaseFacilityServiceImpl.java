package com.ycxy.realestate.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.Query;

import com.ycxy.realestate.dao.BaseFacilityDao;
import com.ycxy.realestate.entity.BaseFacilityEntity;
import com.ycxy.realestate.service.BaseFacilityService;


@Service("baseFacilityService")
public class BaseFacilityServiceImpl extends ServiceImpl<BaseFacilityDao, BaseFacilityEntity> implements BaseFacilityService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BaseFacilityEntity> page = this.page(
                new Query<BaseFacilityEntity>().getPage(params),
                new QueryWrapper<BaseFacilityEntity>()
        );

        return new PageUtils(page);
    }

}