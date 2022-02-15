package com.ycxy.realestate.controller;

import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.ycxy.realestate.entity.BaseRoomEntity;
import com.ycxy.realestate.service.BaseRoomService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ycxy.realestate.entity.BaseBuildEntity;
import com.ycxy.realestate.service.BaseBuildService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.R;


/**
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:25
 */
@RestController
@RequestMapping("realestate/basebuild")
public class BaseBuildController {
    @Autowired
    private BaseBuildService baseBuildService;
    @Autowired
    private BaseRoomService baseRoomService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("realestate:basebuild:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = baseBuildService.queryPage(params);
        if (page != null && !page.getList().isEmpty()) {
            // 判断是否需要初始化房间
            for (Object object : page.getList()) {
                BaseBuildEntity baseBuild = (BaseBuildEntity) object;
                final Long id = baseBuild.getId();
                final int count = baseRoomService.countByBuildId(id);
                baseBuild.setNeedInitRoom(count == 0);
                baseBuild.setRowguid(id.toString());
            }
        }
        return R.ok().put("page", page);
    }

    /**
     * 下拉列表
     */
    @RequestMapping("/dropDataList")
    public R dropDataList(@RequestParam Map<String, Object> params) {
        final List<BaseBuildEntity> list = baseBuildService.list();
        List<Map<String, Object>> rtnObj = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            list.forEach(baseBuildEntity -> {
                Map<String, Object> map = new HashMap<>();
                // 值转换为string类型，避免前台数值转换错误
                map.put("value", baseBuildEntity.getId().toString());
                map.put("label", baseBuildEntity.getName());
                rtnObj.add(map);
            });
        }
        return R.ok().put("data", rtnObj);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("realestate:basebuild:info")
    public R info(@PathVariable("id") Long id) {
        BaseBuildEntity baseBuild = baseBuildService.getById(id);

        return R.ok().put("baseBuild", baseBuild);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("realestate:basebuild:save")
    public R save(@RequestBody BaseBuildEntity baseBuild) {
        baseBuildService.save(baseBuild);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("realestate:basebuild:update")
    public R update(@RequestBody BaseBuildEntity baseBuild) {
        baseBuildService.updateById(baseBuild);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("realestate:basebuild:delete")
    public R delete(@RequestBody Long[] ids) {
        baseBuildService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 快速初始化
     */
    @RequestMapping("/quickInit")
    @RequiresPermissions("realestate:basebuild:save")
    public R quickInit(@RequestBody JSONObject jsonObject) {
        // 查询当前已存在的楼栋数
        final int count = baseBuildService.count();
        final Date date = new Date();
        List<BaseBuildEntity> batchBuild = new ArrayList<>();
        // 从1栋开始，新增若干个
        int num = jsonObject.getInteger("no");
        for (int i = (count + 1); i <= count + num; i++) {
            BaseBuildEntity baseBuild = new BaseBuildEntity();
            baseBuild.setNo(i + "#");
            baseBuild.setName(i + "栋");
            baseBuild.setCreateTime(date);
            batchBuild.add(baseBuild);
        }
        // 批量保存
        baseBuildService.saveBatch(batchBuild);
        return R.ok();
    }


    /**
     * 初始化房间
     */
    @RequestMapping("/initRoom")
    @RequiresPermissions("realestate:basebuild:save")
    public R initRoom(@RequestBody JSONObject jsonObject) {
        // 总层数
        int level = jsonObject.getInteger("level");
        // 每层房间数
        int roomNum = jsonObject.getInteger("roomNum");
        // 楼栋id
        Long buildId = jsonObject.getLong("buildId");

        // 查询楼栋是否存在
        final BaseBuildEntity baseBuild = baseBuildService.getById(buildId);
        if (baseBuild != null) {
            List<BaseRoomEntity> roomEntityList = new ArrayList<>();
            // 双层循环，生成房间
            Date date = new Date();
            for (int i = 1; i <= level; i++) {
                for (int j = 1; j <= roomNum; j++) {
                    BaseRoomEntity baseRoom = new BaseRoomEntity();
                    String no = i + String.format("%03d", j);
                    baseRoom.setBuildNo(baseBuild.getNo());
                    baseRoom.setBuildId(buildId);
                    baseRoom.setNo(no);
                    baseRoom.setCreateTime(date);
                    roomEntityList.add(baseRoom);
                }
            }
            baseRoomService.saveBatch(roomEntityList);
            return R.ok();
        }
        return R.error("楼栋不存在，请确认");
    }

}
