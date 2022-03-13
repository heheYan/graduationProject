package com.ycxy.realestate.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.Query;

import com.ycxy.realestate.dao.ComplaintsDao;
import com.ycxy.realestate.entity.ComplaintsEntity;
import com.ycxy.realestate.service.ComplaintsService;


@Service("complaintsService")
public class ComplaintsServiceImpl extends ServiceImpl<ComplaintsDao, ComplaintsEntity> implements ComplaintsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<ComplaintsEntity> queryWrapper = new QueryWrapper<>();
        if (params.get("key") != null) {
            queryWrapper.like("content", params.get("key"));
        }
        if (params.get("status") != null && !"-1".equals(params.get("status"))) {
            queryWrapper.eq("status", params.get("status"));
        }
        if (params.get("user_id") != null) {
            queryWrapper.eq("user_id", params.get("user_id"));
        }
        if (params.get("type") != null && !"-1".equals(params.get("type"))) {
            queryWrapper.eq("type", params.get("type"));
        }
        IPage<ComplaintsEntity> page = this.page(
                new Query<ComplaintsEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}
