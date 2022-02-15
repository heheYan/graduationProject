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

import com.ycxy.realestate.entity.BaseUserEntity;
import com.ycxy.realestate.service.BaseUserService;
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
@RequestMapping("realestate/baseuser")
public class BaseUserController {
    @Autowired
    private BaseUserService baseUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("realestate:baseuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("realestate:baseuser:info")
    public R info(@PathVariable("id") Long id){
		BaseUserEntity baseUser = baseUserService.getById(id);

        return R.ok().put("baseUser", baseUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("realestate:baseuser:save")
    public R save(@RequestBody BaseUserEntity baseUser){
		baseUserService.save(baseUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("realestate:baseuser:update")
    public R update(@RequestBody BaseUserEntity baseUser){
		baseUserService.updateById(baseUser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("realestate:baseuser:delete")
    public R delete(@RequestBody Long[] ids){
		baseUserService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
