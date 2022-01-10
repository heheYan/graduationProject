package com.ycxy.animal.controller;

import com.ycxy.animal.entity.AnimalInfoEntity;
import com.ycxy.animal.entity.PualificationsEntity;
import com.ycxy.animal.service.AnimalInfoService;
import com.ycxy.animal.service.PualificationsService;
import com.ycxy.common.utils.Constant;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.R;
import com.ycxy.modules.sys.controller.AbstractController;
import com.ycxy.modules.sys.entity.SysUserEntity;
import com.ycxy.modules.sys.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * 
 *
 * @author Student
 * @email Student@ycxy.com
 * @date 2021-11-09 23:13:39
 */
@RestController
@RequestMapping("/pualifications")
public class PualificationsController extends AbstractController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PualificationsService pualificationsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = pualificationsService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 查询
     */
    @RequestMapping("/detail")
    public R detail(){
        final SysUserEntity sysUser = getUser();
        PualificationsEntity pualifications = pualificationsService.findByUserId(getUserId());
        if (pualifications == null) {
            pualifications = new PualificationsEntity();
            pualifications.setUserId(getUserId());
            // TODO 用户昵称
            pualifications.setName(sysUser.getUsername());
            pualifications.setStatus(9);
            pualifications.setCreateDate(new Date());
            pualificationsService.save(pualifications);
        }
        return R.ok().put("data", pualifications);
    }

    /**
     * 查询
     */
    @RequestMapping("/detail/{userId}")
    public R detail(@PathVariable("userId") Long userId){
        PualificationsEntity pualifications = pualificationsService.findByUserId(userId);

        return R.ok().put("data", pualifications);
    }


    /**
     * 查询是否具有领养权限
     */
    @RequestMapping("/hasAdoptAuthority")
    public R hasAdoptAuthority(){
        final SysUserEntity user = getUser();
        return R.ok().put("data", Constant.INT_ONE.equals(user.getAdoptStatus()));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody PualificationsEntity pualifications){

        pualifications.setStatus(0);
        pualifications.setCreateDate(new Date());
        pualificationsService.updateById(pualifications);

        return R.ok();
    }

    /**
     * 保存
     */
    @PostMapping("/update")
    public R update(@RequestBody PualificationsEntity pualifications){
        SysUserEntity sysUser = sysUserService.getById(getUserId());

        pualifications.setCheckId(getUserId());
        pualifications.setCheckName(sysUser.getUsername());
        pualifications.setCheckDate(new Date());
        pualificationsService.updateById(pualifications);
        if (1 == pualifications.getStatus()) {
            sysUser.setAdoptStatus(1);
            sysUserService.update(sysUser);
        }
        return R.ok().put("data", "审核成功");
    }

}
