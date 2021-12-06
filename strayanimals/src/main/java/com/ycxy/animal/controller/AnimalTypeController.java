package com.ycxy.animal.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ycxy.modules.sys.controller.AbstractController;
import com.ycxy.modules.sys.entity.SysMenuEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ycxy.animal.entity.AnimalTypeEntity;
import com.ycxy.animal.service.AnimalTypeService;
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
@RequestMapping("/animal/animaltype")
public class AnimalTypeController extends AbstractController {
    @Autowired
    private AnimalTypeService animalTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("animal:animaltype:list")
    public R list(){
        List<AnimalTypeEntity> animalTypeEntities = animalTypeService.listByParentId(0L);
        return R.ok().put("data", animalTypeEntities);
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @GetMapping("/select")
    public R select(){
        //查询列表数据
        List<AnimalTypeEntity> typeList = animalTypeService.listByParentId(0L);

        //添加顶级菜单
        AnimalTypeEntity root = new AnimalTypeEntity();
        root.setId(0L);
        root.setName("顶级分类");
        root.setParentId(-1L);
        typeList.add(root);

        return R.ok().put("typeList", typeList);
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("animal:animaltype:info")
    public R info(@PathVariable("id") Long id){
		AnimalTypeEntity animalType = animalTypeService.getById(id);

        return R.ok().put("animalType", animalType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("animal:animaltype:save")
    public R save(@RequestBody AnimalTypeEntity animalType){

        animalType.setCreateId(getUserId());
        animalType.setCreateDate(new Date());
		animalTypeService.save(animalType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("animal:animaltype:update")
    public R update(@RequestBody AnimalTypeEntity animalType){
		animalTypeService.updateById(animalType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("animal:animaltype:delete")
    public R delete(@RequestBody Long[] ids){
		animalTypeService.removeByIds(Arrays.asList(ids));
        for (Long id : ids) {
            animalTypeService.removeByParentId(id);
        }
        return R.ok();
    }

}
