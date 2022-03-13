package com.ycxy.realestate.controller;

import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.ycxy.common.utils.Constant;
import com.ycxy.realestate.entity.BaseBuildEntity;
import com.ycxy.realestate.entity.BaseRoomEntity;
import com.ycxy.realestate.service.BaseRoomService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ycxy.realestate.entity.CarspaceEntity;
import com.ycxy.realestate.service.CarspaceService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.R;



/**
 * 车位信息表
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:24
 */
@RestController
@RequestMapping("realestate/carspace")
public class CarspaceController {
    @Autowired
    private CarspaceService carspaceService;
    @Autowired
    private BaseRoomService baseRoomService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("realestate:carspace:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = carspaceService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/listAll")
    public R listAll(@RequestParam Map<String, Object> params){

        return R.ok().put("list", carspaceService.listAll(params));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("realestate:carspace:info")
    public R info(@PathVariable("id") Long id){
		CarspaceEntity carspace = carspaceService.getById(id);

        return R.ok().put("carspace", carspace);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("realestate:carspace:save")
    public R save(@RequestBody CarspaceEntity carspace){
        BaseRoomEntity baseRoom = baseRoomService.getById(carspace.getRoomId());
        carspace.setRoomNo(baseRoom.getNo());

        carspace.setLastChangeTime(new Date());

		carspaceService.save(carspace);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("realestate:carspace:update")
    public R update(@RequestBody CarspaceEntity carspace){
        carspace.setLastChangeTime(new Date());

		carspaceService.updateById(carspace);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("realestate:carspace:delete")
    public R delete(@RequestBody Long[] ids){
		carspaceService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 快速初始化
     */
    @RequestMapping("/quickInit")
    public R quickInit(@RequestBody JSONObject jsonObject) {
        // 查询当前已存在的车位数
        final int count = carspaceService.count();
        final Date date = new Date();
        List<CarspaceEntity> batchCarspace = new ArrayList<>();
        // 从1栋开始，新增若干个
        int num = jsonObject.getInteger("no");
        // 如果计数超过9999，取消新增
        if (count + num > 9999) {
            return R.error("车位总数超过9999");
        }
        for (int i = (count + 1); i <= count + num; i++) {
            if (i > 9999) {
                break;
            }
            CarspaceEntity carspace = new CarspaceEntity();
            // 初始化编码
            carspace.setNo(String.format("P-%04d", i));
            // 未售状态
            carspace.setStatus(Constant.INT_ZERO);
            carspace.setLastChangeTime(date);
            batchCarspace.add(carspace);
        }
        // 批量保存
        carspaceService.saveBatch(batchCarspace);
        return R.ok();
    }
}
