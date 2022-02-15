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

import com.ycxy.realestate.entity.RepairEntity;
import com.ycxy.realestate.service.RepairService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.R;



/**
 * 报修信息表
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:24
 */
@RestController
@RequestMapping("realestate/repair")
public class RepairController {
    @Autowired
    private RepairService repairService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("realestate:repair:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = repairService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("realestate:repair:info")
    public R info(@PathVariable("id") Long id){
		RepairEntity repair = repairService.getById(id);

        return R.ok().put("repair", repair);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("realestate:repair:save")
    public R save(@RequestBody RepairEntity repair){
		repairService.save(repair);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("realestate:repair:update")
    public R update(@RequestBody RepairEntity repair){
		repairService.updateById(repair);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("realestate:repair:delete")
    public R delete(@RequestBody Long[] ids){
		repairService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
