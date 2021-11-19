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

import com.ycxy.animal.entity.DealInstanceEntity;
import com.ycxy.animal.service.DealInstanceService;
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
@RequestMapping("/animal/dealinstance")
public class DealInstanceController {
    @Autowired
    private DealInstanceService dealInstanceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("animal:dealinstance:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dealInstanceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("animal:dealinstance:info")
    public R info(@PathVariable("id") Long id){
		DealInstanceEntity dealInstance = dealInstanceService.getById(id);

        return R.ok().put("dealInstance", dealInstance);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("animal:dealinstance:save")
    public R save(@RequestBody DealInstanceEntity dealInstance){
		dealInstanceService.save(dealInstance);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("animal:dealinstance:update")
    public R update(@RequestBody DealInstanceEntity dealInstance){
		dealInstanceService.updateById(dealInstance);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("animal:dealinstance:delete")
    public R delete(@RequestBody Long[] ids){
		dealInstanceService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
