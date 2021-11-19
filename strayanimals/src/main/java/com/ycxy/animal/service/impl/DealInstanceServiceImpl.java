package com.ycxy.animal.service.impl;

import org.springframework.stereotype.Service;
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
                new QueryWrapper<DealInstanceEntity>()
        );

        return new PageUtils(page);
    }

}