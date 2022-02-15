package com.ycxy.realestate.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ycxy.realestate.entity.TakeoffEntity;
import com.ycxy.realestate.service.TakeoffService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.R;



/**
 * 请假信息表
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:25
 */
@RestController
@RequestMapping("realestate/takeoff")
public class TakeoffController {
    @Autowired
    private TakeoffService takeoffService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("realestate:takeoff:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = takeoffService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("realestate:takeoff:info")
    public R info(@PathVariable("id") Long id){
		TakeoffEntity takeoff = takeoffService.getById(id);

        return R.ok().put("takeoff", takeoff);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("realestate:takeoff:save")
    public R save(@RequestBody TakeoffEntity takeoff){
		takeoffService.save(takeoff);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("realestate:takeoff:update")
    public R update(@RequestBody TakeoffEntity takeoff){
		takeoffService.updateById(takeoff);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("realestate:takeoff:delete")
    public R delete(@RequestBody Long[] ids){
		takeoffService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
