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

import com.ycxy.realestate.entity.BaseFacilityEntity;
import com.ycxy.realestate.service.BaseFacilityService;
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
@RequestMapping("realestate/basefacility")
public class BaseFacilityController {
    @Autowired
    private BaseFacilityService baseFacilityService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("realestate:basefacility:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseFacilityService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("realestate:basefacility:info")
    public R info(@PathVariable("id") Long id){
		BaseFacilityEntity baseFacility = baseFacilityService.getById(id);

        return R.ok().put("baseFacility", baseFacility);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("realestate:basefacility:save")
    public R save(@RequestBody BaseFacilityEntity baseFacility){
		baseFacilityService.save(baseFacility);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("realestate:basefacility:update")
    public R update(@RequestBody BaseFacilityEntity baseFacility){
		baseFacilityService.updateById(baseFacility);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("realestate:basefacility:delete")
    public R delete(@RequestBody Long[] ids){
		baseFacilityService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
