package com.ycxy.realestate.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.Query;

import com.ycxy.realestate.dao.BaseRoomDao;
import com.ycxy.realestate.entity.BaseRoomEntity;
import com.ycxy.realestate.service.BaseRoomService;
import org.springframework.util.StringUtils;


@Service("baseRoomService")
public class BaseRoomServiceImpl extends ServiceImpl<BaseRoomDao, BaseRoomEntity> implements BaseRoomService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<BaseRoomEntity> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(params.get("key"))) {
            wrapper.like("no", params.get("key"));
        }
        if (!StringUtils.isEmpty(params.get("buildId"))) {
            wrapper.eq("build_id", params.get("buildId"));
        }

        IPage<BaseRoomEntity> page = this.page(
                new Query<BaseRoomEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    /**
     * 根据楼栋id判断是否存在房间记录
     *
     * @param buildId
     * @return
     */
    @Override
    public int countByBuildId(Long buildId) {
        LambdaQueryWrapper<BaseRoomEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseRoomEntity::getBuildId, buildId);
        return count(queryWrapper);
    }

    @Override
    public List<Map<String, Object>> countByBuildIdAndStatus(Long buildId) {
        return baseMapper.countByBuildId(buildId);
    }

    /**
     * 查询除自己外本楼是否已存在当前房间号
     *
     * @param no      房号
     * @param buildId 楼栋id
     * @param id      当前记录
     * @return 是否存在
     */
    @Override
    public boolean isExistByNoAndBuildId(String no, Long buildId, Long id) {
        LambdaQueryWrapper<BaseRoomEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseRoomEntity::getNo, no);
        queryWrapper.eq(BaseRoomEntity::getBuildId, buildId);
        if (id != null) {
            queryWrapper.ne(BaseRoomEntity::getId, id);
        }
        return count(queryWrapper) > 0;
    }

    @Override
    public List<String> listBuilds() {
        return baseMapper.listBuilds();
    }

    @Override
    public List<BaseRoomEntity> listByBuildId(Long buildId) {
        LambdaQueryWrapper<BaseRoomEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseRoomEntity::getBuildId, buildId);
        return baseMapper.selectList(queryWrapper);
    }

}
