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

import com.ycxy.realestate.entity.CarspaceEntity;
import com.ycxy.realestate.service.CarspaceService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.R;



/**
 * 车位信息表
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:24
 */
@RestController
@RequestMapping("realestate/carspace")
public class CarspaceController {
    @Autowired
    private CarspaceService carspaceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("realestate:carspace:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = carspaceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("realestate:carspace:info")
    public R info(@PathVariable("id") Long id){
		CarspaceEntity carspace = carspaceService.getById(id);

        return R.ok().put("carspace", carspace);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("realestate:carspace:save")
    public R save(@RequestBody CarspaceEntity carspace){
		carspaceService.save(carspace);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("realestate:carspace:update")
    public R update(@RequestBody CarspaceEntity carspace){
		carspaceService.updateById(carspace);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("realestate:carspace:delete")
    public R delete(@RequestBody Long[] ids){
		carspaceService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
