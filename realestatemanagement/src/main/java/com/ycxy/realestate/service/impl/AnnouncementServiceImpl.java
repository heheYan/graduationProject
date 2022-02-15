package com.ycxy.realestate.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.Query;

import com.ycxy.realestate.dao.AnnouncementDao;
import com.ycxy.realestate.entity.AnnouncementEntity;
import com.ycxy.realestate.service.AnnouncementService;


@Service("announcementService")
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementDao, AnnouncementEntity> implements AnnouncementService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AnnouncementEntity> page = this.page(
                new Query<AnnouncementEntity>().getPage(params),
                new QueryWrapper<AnnouncementEntity>()
        );

        return new PageUtils(page);
    }

}