package com.ycxy.realestate.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.ycxy.common.utils.Constant;
import com.ycxy.realestate.entity.BaseBuildEntity;
import com.ycxy.realestate.service.BaseBuildService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ycxy.realestate.entity.BaseRoomEntity;
import com.ycxy.realestate.service.BaseRoomService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.R;



/**
 *
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:25
 */
@RestController
@RequestMapping("realestate/baseroom")
public class BaseRoomController {
    @Autowired
    private BaseRoomService baseRoomService;

    @Autowired
    private BaseBuildService baseBuildService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("realestate:baseroom:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseRoomService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("realestate:baseroom:info")
    public R info(@PathVariable("id") Long id){
		BaseRoomEntity baseRoom = baseRoomService.getById(id);

        return R.ok().put("baseRoom", baseRoom);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("realestate:baseroom:save")
    public R save(@RequestBody BaseRoomEntity baseRoom){
        // 保存前判断当前房间号是否已存在
        if (baseRoomService.isExistByNoAndBuildId(baseRoom.getNo(), baseRoom.getBuildId(), baseRoom.getId())) {
            return R.error("当前楼栋已存在相同房间号");
        }
        // 查询楼栋信息
        final BaseBuildEntity baseBuild = baseBuildService.getById(baseRoom.getBuildId());
        baseRoom.setBuildNo(baseBuild.getNo());
        baseRoom.setCreateTime(new Date());
        baseRoom.setStatus(Constant.INT_ZERO);
        baseRoomService.save(baseRoom);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("realestate:baseroom:update")
    public R update(@RequestBody BaseRoomEntity baseRoom){
        // 保存前判断当前房间号是否已存在
        if (baseRoomService.isExistByNoAndBuildId(baseRoom.getNo(), baseRoom.getBuildId(), baseRoom.getId())) {
            return R.error("当前楼栋已存在相同房间号");
        }
		baseRoomService.updateById(baseRoom);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("realestate:baseroom:delete")
    public R delete(@RequestBody Long[] ids){
		baseRoomService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
