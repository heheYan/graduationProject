package com.ycxy.realestate.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.Query;

import com.ycxy.realestate.dao.TakeoffDao;
import com.ycxy.realestate.entity.TakeoffEntity;
import com.ycxy.realestate.service.TakeoffService;


@Service("takeoffService")
public class TakeoffServiceImpl extends ServiceImpl<TakeoffDao, TakeoffEntity> implements TakeoffService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TakeoffEntity> page = this.page(
                new Query<TakeoffEntity>().getPage(params),
                new QueryWrapper<TakeoffEntity>()
        );

        return new PageUtils(page);
    }

}