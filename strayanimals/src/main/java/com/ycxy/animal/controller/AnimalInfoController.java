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

import com.ycxy.animal.entity.AnimalInfoEntity;
import com.ycxy.animal.service.AnimalInfoService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.R;



/**
 * 
 *
 * @author Student
 * @email Student@ycxy.com
 * @date 2021-11-09 23:13:39
 */
@RestController
@RequestMapping("/animal/animalinfo")
public class AnimalInfoController {
    @Autowired
    private AnimalInfoService animalInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("animal:animalinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = animalInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("animal:animalinfo:info")
    public R info(@PathVariable("id") Long id){
		AnimalInfoEntity animalInfo = animalInfoService.getById(id);

        return R.ok().put("animalInfo", animalInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("animal:animalinfo:save")
    public R save(@RequestBody AnimalInfoEntity animalInfo){
		animalInfoService.save(animalInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("animal:animalinfo:update")
    public R update(@RequestBody AnimalInfoEntity animalInfo){
		animalInfoService.updateById(animalInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("animal:animalinfo:delete")
    public R delete(@RequestBody Long[] ids){
		animalInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
