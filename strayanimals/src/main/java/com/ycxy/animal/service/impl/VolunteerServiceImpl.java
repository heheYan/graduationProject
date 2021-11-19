package com.ycxy.animal.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.Query;

import com.ycxy.animal.dao.VolunteerDao;
import com.ycxy.animal.entity.VolunteerEntity;
import com.ycxy.animal.service.VolunteerService;


@Service("volunteerService")
public class VolunteerServiceImpl extends ServiceImpl<VolunteerDao, VolunteerEntity> implements VolunteerService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<VolunteerEntity> page = this.page(
                new Query<VolunteerEntity>().getPage(params),
                new QueryWrapper<VolunteerEntity>()
        );

        return new PageUtils(page);
    }

}