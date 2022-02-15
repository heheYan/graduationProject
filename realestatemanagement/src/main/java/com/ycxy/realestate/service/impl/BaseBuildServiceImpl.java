package com.ycxy.realestate.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.Query;

import com.ycxy.realestate.dao.BaseBuildDao;
import com.ycxy.realestate.entity.BaseBuildEntity;
import com.ycxy.realestate.service.BaseBuildService;


@Service("baseBuildService")
public class BaseBuildServiceImpl extends ServiceImpl<BaseBuildDao, BaseBuildEntity> implements BaseBuildService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BaseBuildEntity> page = this.page(
                new Query<BaseBuildEntity>().getPage(params),
                new QueryWrapper<BaseBuildEntity>()
        );

        return new PageUtils(page);
    }

}