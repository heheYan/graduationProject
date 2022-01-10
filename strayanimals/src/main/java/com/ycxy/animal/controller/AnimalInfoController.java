package com.ycxy.animal.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.ycxy.animal.entity.DealInstanceEntity;
import com.ycxy.animal.service.DealInstanceService;
import com.ycxy.common.utils.Constant;
import com.ycxy.modules.sys.controller.AbstractController;
import com.ycxy.modules.sys.entity.SysUserEntity;
import com.ycxy.modules.sys.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
 * @author Student
 * @email Student@ycxy.com
 * @date 2021-11-09 23:13:39
 */
@RestController
@RequestMapping("/animal/animalinfo")
public class AnimalInfoController extends AbstractController {
    @Autowired
    private AnimalInfoService animalInfoService;
    @Autowired
    private DealInstanceService dealInstanceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("animal:animalinfo:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = animalInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 可领养列表
     */
    @RequestMapping("/canAdoptList")
    public R canAdoptList(@RequestParam Map<String, Object> params) {
        PageUtils page = animalInfoService.canAdoptPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("animal:animalinfo:info")
    public R info(@PathVariable("id") Long id) {
        AnimalInfoEntity animalInfo = animalInfoService.getById(id);

        return R.ok().put("animalInfo", animalInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("animal:animalinfo:save")
    public R save(@RequestBody AnimalInfoEntity animalInfo) {
        final SysUserEntity sysUser = getUser();
        animalInfo.setRegister(getUserId());
        animalInfo.setRegisterName(sysUser.getUsername());
        animalInfo.setRegisterDate(new Date());
        animalInfo.setStatus(0);
        animalInfo.setIsAdopt(0);
        animalInfoService.save(animalInfo);

        // 保存流程信息
        DealInstanceEntity dealInstance = new DealInstanceEntity();
        dealInstance.setAnimalId(animalInfo.getId());
        dealInstance.setOpinion("初始入库登记");
        dealInstance.setOpId(getUserId());
        dealInstance.setOpName(sysUser.getUsername());
        dealInstance.setStatus(1);
        dealInstance.setWorkitem("登记入库");
        dealInstance.setPreWorkitemId(0L);
        dealInstance.setHandleUrl("animal-animalinfo?id=" + animalInfo.getId());
        dealInstance.setTitle("【登记入库】" + animalInfo.getName());
        dealInstance.setStartDate(new Date());
        dealInstance.setOpDate(new Date());
        dealInstanceService.save(dealInstance);

        // 下一步
        sendNext(animalInfo, dealInstance, "清洗检查");

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("animal:animalinfo:update")
    @Transactional
    public R update(@RequestBody AnimalInfoEntity animalInfo) {
        animalInfoService.updateById(animalInfo);

        return R.ok();
    }

    private void sendNext(@RequestBody AnimalInfoEntity animalInfo, DealInstanceEntity currentStep, String title) {
        DealInstanceEntity nextStep = new DealInstanceEntity();
        nextStep.setAnimalId(animalInfo.getId());
        nextStep.setStatus(0);
        nextStep.setWorkitem(title);
        nextStep.setPreWorkitemId(currentStep.getId());
        nextStep.setHandleUrl("animal-animalinfo?id=" + animalInfo.getId());
        nextStep.setTitle("【" + title + "】" + animalInfo.getName());
        nextStep.setStartDate(new Date());
        dealInstanceService.save(nextStep);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("animal:animalinfo:delete")
    public R delete(@RequestBody Long[] ids) {
        animalInfoService.removeByIds(Arrays.asList(ids));
        // 删除待办-流程
        dealInstanceService.removeByAnimalIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 处理
     */
    @RequestMapping("/deal")
    @RequiresPermissions("animal:animalinfo:deal")
    public R deal(@RequestBody AnimalInfoEntity viewEntity) {
        // 1. 更新动物状态
        AnimalInfoEntity animalInfo = animalInfoService.getById(viewEntity.getId());
        animalInfo.setStatus(animalInfo.getStatus() + 1);
        animalInfoService.updateById(animalInfo);

        // 保存处理流程
        // 2. 查找当前动物流程
        final DealInstanceEntity instance = dealInstanceService.getByAnimalId(animalInfo.getId());
        final SysUserEntity sysUser = getUser();
        instance.setOpDate(new Date());
        instance.setOpId(getUserId());
        instance.setOpName(sysUser.getUsername());
        instance.setStatus(1);
        instance.setOpDate(new Date());
        dealInstanceService.updateById(instance);
        // 3. 判断动物状态是否为2，如果是，则结束，否则新增流程
        if (!Constant.INT_TWO.equals(animalInfo.getStatus())) {
            // 下一步
            sendNext(animalInfo, instance,"防疫除菌");
        }
        return R.ok();
    }
}
