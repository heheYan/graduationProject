package com.ycxy.realestate.service.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<CarspaceEntity> listAll(Map<String, Object> params) {
        QueryWrapper<CarspaceEntity> queryWrapper = new QueryWrapper<>();
        if (params.get("key") != null) {
            queryWrapper.like("no", params.get("key"));
        }
        if (params.get("status") != null && !"-1".equals(params.get("status"))) {
            queryWrapper.eq("status", params.get("status"));
        }
        return baseMapper.selectList(queryWrapper);
    }

}
