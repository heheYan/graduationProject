package com.ycxy.realestate.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.R;
import com.ycxy.realestate.entity.BaseFacilityEntity;
import com.ycxy.realestate.service.BaseBuildService;
import com.ycxy.realestate.service.BaseFacilityService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


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

    @Autowired
    private BaseBuildService baseBuildService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("realestate:basefacility:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseFacilityService.queryPage(params);
        if (page != null && !page.getList().isEmpty()) {
            page.getList().forEach(obj -> {
                BaseFacilityEntity baseFacility = (BaseFacilityEntity) obj;
                if (baseFacility.getBuildId() != null) {
                    baseFacility.setBuildNo(baseBuildService.getById(baseFacility.getBuildId()).getNo());
                }
            });
        }
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
        baseFacility.setCreateTime(new Date());
        // 正常使用状态
        baseFacility.setStatus(1);
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

    @RequestMapping("/getTypeList")
    public R getTypeList() {
        return R.ok().put("typeList", JSON.parse("[{'name':'电梯','value':'1'}," +
                "{'name':'路灯','value':'2'}," +
                "{'name':'健身设施','value':'3'}," +
                "{'name':'儿童设施','value':'4'}," +
                "{'name':'大门','value':'5'}]"));
    }

    @RequestMapping("/dropData")
    public R dropData() {
        QueryWrapper<BaseFacilityEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        List<JSONObject> rtnList = new ArrayList<>();
        final List<BaseFacilityEntity> facilityEntityList = baseFacilityService.list(queryWrapper);
        if (!facilityEntityList.isEmpty()) {
            facilityEntityList.forEach(baseFacilityEntity -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("value", baseFacilityEntity.getId());
                jsonObject.put("label", baseFacilityEntity.getName() + "-" + baseFacilityEntity.getNo());
                rtnList.add(jsonObject);
            });
        }

        return R.ok().put("data", rtnList);
    }
}
