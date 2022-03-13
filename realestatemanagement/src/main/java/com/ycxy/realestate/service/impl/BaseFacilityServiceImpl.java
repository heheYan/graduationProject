package com.ycxy.realestate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.Query;
import com.ycxy.realestate.dao.BaseFacilityDao;
import com.ycxy.realestate.entity.BaseFacilityEntity;
import com.ycxy.realestate.service.BaseFacilityService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("baseFacilityService")
public class BaseFacilityServiceImpl extends ServiceImpl<BaseFacilityDao, BaseFacilityEntity> implements BaseFacilityService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<BaseFacilityEntity> queryWrapper = new QueryWrapper<>();
        if (params.get("key") != null) {
            queryWrapper.like("name", params.get("key"));
        }
        IPage<BaseFacilityEntity> page = this.page(
                new Query<BaseFacilityEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public int countErrorByType(int type) {
        return baseMapper.countErrorByType(type);
    }

}
