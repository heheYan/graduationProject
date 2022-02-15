package com.ycxy.realestate.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.Query;

import com.ycxy.realestate.dao.RepairDao;
import com.ycxy.realestate.entity.RepairEntity;
import com.ycxy.realestate.service.RepairService;


@Service("repairService")
public class RepairServiceImpl extends ServiceImpl<RepairDao, RepairEntity> implements RepairService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RepairEntity> page = this.page(
                new Query<RepairEntity>().getPage(params),
                new QueryWrapper<RepairEntity>()
        );

        return new PageUtils(page);
    }

}