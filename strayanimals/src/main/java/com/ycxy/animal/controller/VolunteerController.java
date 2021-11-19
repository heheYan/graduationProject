package com.ycxy.animal.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ycxy.animal.entity.VolunteerEntity;
import com.ycxy.animal.service.VolunteerService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.R;



/**
 * 志愿者信息表
 *
 * @author Student
 * @email Student@ycxy.com
 * @date 2021-11-09 23:13:39
 */
@RestController
@RequestMapping("/animal/volunteer")
public class VolunteerController {
    @Autowired
    private VolunteerService volunteerService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("animal:volunteer:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = volunteerService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("animal:volunteer:info")
    public R info(@PathVariable("id") Long id){
		VolunteerEntity volunteer = volunteerService.getById(id);

        return R.ok().put("volunteer", volunteer);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("animal:volunteer:save")
    public R save(@RequestBody VolunteerEntity volunteer){
		volunteerService.save(volunteer);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("animal:volunteer:update")
    public R update(@RequestBody VolunteerEntity volunteer){
		volunteerService.updateById(volunteer);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("animal:volunteer:delete")
    public R delete(@RequestBody Long[] ids){
		volunteerService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
