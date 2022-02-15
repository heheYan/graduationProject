package com.ycxy.realestate.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.Query;

import com.ycxy.realestate.dao.CarspaceDao;
import com.ycxy.realestate.entity.CarspaceEntity;
import com.ycxy.realestate.service.CarspaceService;


@Service("carspaceService")
public class CarspaceServiceImpl extends ServiceImpl<CarspaceDao, CarspaceEntity> implements CarspaceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CarspaceEntity> page = this.page(
                new Query<CarspaceEntity>().getPage(params),
                new QueryWrapper<CarspaceEntity>()
        );

        return new PageUtils(page);
    }

}