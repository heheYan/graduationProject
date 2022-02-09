package com.ycxy.animal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycxy.animal.dao.AnimalForumDao;
import com.ycxy.animal.entity.AnimalForumEntity;
import com.ycxy.animal.service.AnimalForumService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("animalForumService")
public class AnimalForumServiceImpl extends ServiceImpl<AnimalForumDao, AnimalForumEntity> implements AnimalForumService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<AnimalForumEntity> wrapper = new QueryWrapper<>();
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
        IPage<AnimalForumEntity> page = this.page(
                new Query<AnimalForumEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

}
