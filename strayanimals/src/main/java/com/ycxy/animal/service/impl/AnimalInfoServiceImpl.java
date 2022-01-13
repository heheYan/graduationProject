package com.ycxy.animal.service.impl;

import com.ycxy.animal.entity.VO.AnimalInfoVO;
import com.ycxy.animal.entity.VO.UserVO;
import com.ycxy.common.utils.Constant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.Query;

import com.ycxy.animal.dao.AnimalInfoDao;
import com.ycxy.animal.entity.AnimalInfoEntity;
import com.ycxy.animal.service.AnimalInfoService;
import org.springframework.util.StringUtils;


@Service("animalInfoService")
public class AnimalInfoServiceImpl extends ServiceImpl<AnimalInfoDao, AnimalInfoEntity> implements AnimalInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<AnimalInfoEntity> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(params.get("key"))) {
            wrapper.like("name", params.get("key"));
        }
        if (!StringUtils.isEmpty(params.get("id"))) {
            wrapper.eq("id", params.get("id"));
        }


        IPage<AnimalInfoEntity> page = this.page(
                new Query<AnimalInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils canAdoptPage(Map<String, Object> params) {
        QueryWrapper<AnimalInfoEntity> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(params.get("key"))) {
            wrapper.like("name", params.get("key"));
        }
        if (!StringUtils.isEmpty(params.get("id"))) {
            wrapper.eq("id", params.get("id"));
        }

        // 未被领养
        wrapper.eq("is_adopt", Constant.YesOrNo.NO);
        // 已防疫状态
        wrapper.eq("status", Constant.INT_TWO);
        IPage<AnimalInfoEntity> page = this.page(
                new Query<AnimalInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<AnimalInfoVO> analysisByType() {
        return baseMapper.analysisByType();
    }

    @Override
    public AnimalInfoVO animalAdoptionAnalysis() {
        return baseMapper.animalAdoptionAnalysis();
    }

    /**
     * 系统用户分析
     *
     * @return
     */
    @Override
    public UserVO userSituation() {
        return baseMapper.userSituation();
    }

}
