package com.ycxy.realestate.controller;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ycxy.common.utils.Constant;
import com.ycxy.realestate.entity.BaseRoomEntity;
import com.ycxy.realestate.service.BaseRoomService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ycxy.realestate.entity.BaseUserEntity;
import com.ycxy.realestate.service.BaseUserService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.R;


/**
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:25
 */
@RestController
@RequestMapping("realestate/baseuser")
public class BaseUserController {
    @Autowired
    private BaseUserService baseUserService;
    @Autowired
    private BaseRoomService baseRoomService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("realestate:baseuser:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = baseUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("realestate:baseuser:info")
    public R info(@PathVariable("id") Long id) {
        BaseUserEntity baseUser = baseUserService.getById(id);

        return R.ok().put("baseUser", baseUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("realestate:baseuser:save")
    public R save(@RequestBody BaseUserEntity baseUser) {
        BaseRoomEntity baseRoom = baseRoomService.getById(baseUser.getRoomId());
        baseUser.setRoomNo(baseRoom.getNo());
        // 默认未删除
        baseUser.setIsDelete(Constant.STR_ZERO);
        baseUser.setBuildNo(baseRoom.getBuildNo());
        baseUser.setEnterTime(new Date());
        baseUserService.save(baseUser);
        // 如果是业主，修改房间状态为 业主入住
        if (Constant.INT_ONE.equals(baseUser.getIsMaster())) {
            baseRoom.setStatus(Constant.INT_ONE);
        } else {
            baseRoom.setStatus(Constant.INT_TWO);
        }
        baseRoomService.updateById(baseRoom);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("realestate:baseuser:update")
    public R update(@RequestBody BaseUserEntity baseUser) {
        baseUserService.updateById(baseUser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("realestate:baseuser:delete")
    public R delete(@RequestBody Long[] ids) {
        List<BaseUserEntity> userEntityList = new ArrayList<>();
        HashSet<Long> roomIds = new HashSet<>();
        for (Long id : ids) {
            BaseUserEntity entity = baseUserService.getById(id);
            entity.setIsDelete("1");
            entity.setOutTime(new Date());
            userEntityList.add(entity);
            roomIds.add(entity.getRoomId());
        }
        baseUserService.updateBatchById(userEntityList);

        // 判断房间是否空闲
        QueryWrapper<BaseUserEntity> queryWrapper = new QueryWrapper<>();
        for (Long roomId : roomIds) {
            queryWrapper.clear();
            queryWrapper.eq("room_id", roomId);
            queryWrapper.eq("is_delete", Constant.STR_ZERO);
            int count = baseUserService.count(queryWrapper);
            // 未搬走人数为0，标识该房间已空闲
            if (count == 0) {
                BaseRoomEntity baseRoom = baseRoomService.getById(roomId);
                baseRoom.setStatus(Constant.INT_ZERO);
                baseRoomService.updateById(baseRoom);
            }
        }

        return R.ok();
    }

}
