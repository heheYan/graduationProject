package com.ycxy.realestate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.Query;
import com.ycxy.realestate.dao.RealestateForumDao;
import com.ycxy.realestate.entity.RealestateForumEntity;
import com.ycxy.realestate.service.RealestateForumService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("animalForumService")
public class RealestateForumServiceImpl extends ServiceImpl<RealestateForumDao, RealestateForumEntity> implements RealestateForumService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<RealestateForumEntity> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(params.get("key"))) {
            wrapper.like("title", params.get("key"));
        }
        if (!StringUtils.isEmpty(params.get("userId"))) {
            wrapper.eq("user_id", params.get("userId"));
        }
        if (!StringUtils.isEmpty(params.get("stat"))) {
            wrapper.eq("status", params.get("stat"));
        }

        wrapper.orderByDesc("create_time");
        IPage<RealestateForumEntity> page = this.page(
                new Query<RealestateForumEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

}
