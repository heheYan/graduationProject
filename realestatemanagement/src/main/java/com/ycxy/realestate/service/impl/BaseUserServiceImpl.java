package com.ycxy.realestate.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.Query;

import com.ycxy.realestate.dao.BaseUserDao;
import com.ycxy.realestate.entity.BaseUserEntity;
import com.ycxy.realestate.service.BaseUserService;


@Service("baseUserService")
public class BaseUserServiceImpl extends ServiceImpl<BaseUserDao, BaseUserEntity> implements BaseUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BaseUserEntity> page = this.page(
                new Query<BaseUserEntity>().getPage(params),
                new QueryWrapper<BaseUserEntity>()
        );

        return new PageUtils(page);
    }

}