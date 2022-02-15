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

import com.ycxy.realestate.entity.AnnouncementEntity;
import com.ycxy.realestate.service.AnnouncementService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.R;



/**
 * 公共论坛信息
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:24
 */
@RestController
@RequestMapping("realestate/announcement")
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("realestate:announcement:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = announcementService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("realestate:announcement:info")
    public R info(@PathVariable("id") Long id){
		AnnouncementEntity announcement = announcementService.getById(id);

        return R.ok().put("announcement", announcement);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("realestate:announcement:save")
    public R save(@RequestBody AnnouncementEntity announcement){
		announcementService.save(announcement);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("realestate:announcement:update")
    public R update(@RequestBody AnnouncementEntity announcement){
		announcementService.updateById(announcement);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("realestate:announcement:delete")
    public R delete(@RequestBody Long[] ids){
		announcementService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
