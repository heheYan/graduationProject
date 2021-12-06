package com.ycxy.animal.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycxy.animal.dao.PualificationsDao;
import com.ycxy.animal.dao.VolunteerDao;
import com.ycxy.animal.entity.PualificationsEntity;
import com.ycxy.animal.entity.VolunteerEntity;
import com.ycxy.animal.service.PualificationsService;
import com.ycxy.animal.service.VolunteerService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("pualificationsService")
public class PualificationServiceImpl extends ServiceImpl<PualificationsDao, PualificationsEntity> implements PualificationsService {


    @Override
    public PualificationsEntity findByUserId(Long userId) {
        QueryWrapper<PualificationsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PualificationsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("status", 9);
        queryWrapper.orderByAsc("status");
        if (!StringUtils.isEmpty(params.get("key"))) {
            queryWrapper.and(q -> {
                q.like("name", params.get("key"))
                        .or()
                        .like("real_name", params.get("key"));
            });
        }
        IPage<PualificationsEntity> page = this.page(
                new Query<PualificationsEntity>().getPage(params), queryWrapper
        );

        return new PageUtils(page);
    }
}
