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

import com.ycxy.realestate.entity.AttendanceEntity;
import com.ycxy.realestate.service.AttendanceService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.R;



/**
 * 考勤记录表
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:25
 */
@RestController
@RequestMapping("realestate/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("realestate:attendance:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attendanceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("realestate:attendance:info")
    public R info(@PathVariable("id") Long id){
		AttendanceEntity attendance = attendanceService.getById(id);

        return R.ok().put("attendance", attendance);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("realestate:attendance:save")
    public R save(@RequestBody AttendanceEntity attendance){
		attendanceService.save(attendance);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("realestate:attendance:update")
    public R update(@RequestBody AttendanceEntity attendance){
		attendanceService.updateById(attendance);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("realestate:attendance:delete")
    public R delete(@RequestBody Long[] ids){
		attendanceService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
