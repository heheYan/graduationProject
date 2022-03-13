package com.ycxy.animal.controller;

import com.ycxy.animal.entity.AnimalInfoEntity;
import com.ycxy.animal.entity.DealInstanceEntity;
import com.ycxy.animal.entity.MyAdoptionEntity;
import com.ycxy.animal.service.AnimalInfoService;
import com.ycxy.animal.service.DealInstanceService;
import com.ycxy.animal.service.MyAdoptionService;
import com.ycxy.common.utils.Constant;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.R;
import com.ycxy.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * 我的领养记录接口
 * @author YhhWork
 */
@RestController
@RequestMapping("/myAdoption")
public class MyAdoptionController extends AbstractController {

    @Autowired
    MyAdoptionService myAdoptionService;

    @Autowired
    AnimalInfoService animalInfoService;
    @Autowired
    private DealInstanceService dealInstanceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        params.put("userId", getUserId());
        PageUtils page = myAdoptionService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/listAll")
    public R listAll(@RequestParam Map<String, Object> params) {
        PageUtils page = myAdoptionService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        MyAdoptionEntity myAdoption = myAdoptionService.getById(id);

        AnimalInfoEntity animalInfo = animalInfoService.getById(myAdoption.getAnimalId());
        myAdoption.setAnimalInfo(animalInfo);

        return R.ok().put("myAdoption", myAdoption);
    }

    /**
     * 提交申请
     */
    @PostMapping("/apply")
    public R apply(@RequestBody MyAdoptionEntity myAdoption) {
        // 查询动物信息
        AnimalInfoEntity animalInfo = animalInfoService.getById(myAdoption.getAnimalId());
        // 1. 保存申请信息
        myAdoption.setStatus(Constant.INT_ZERO);
        myAdoption.setUserId(getUserId());
        myAdoption.setUserName(getUser().getUsername());
        myAdoption.setApplyDate(new Date());
        myAdoption.setAnimalName(animalInfo.getName());
        myAdoptionService.save(myAdoption);
        // 2. 更新动物状态
        // 已有人认领状态
        animalInfo.setIsAdopt(Constant.INT_ONE);
        animalInfo.setAdopter(getUserId());
        animalInfo.setAdopterName(getUser().getUsername());
        animalInfo.setAdoptDate(new Date());
        animalInfoService.updateById(animalInfo);


        // 保存流程信息
        DealInstanceEntity dealInstance = new DealInstanceEntity();
        dealInstance.setAnimalId(animalInfo.getId());
        dealInstance.setOpinion("领养申请");
        dealInstance.setOpId(getUserId());
        dealInstance.setOpName(getUser().getUsername());
        dealInstance.setStatus(1);
        dealInstance.setWorkitem("领养申请");
        dealInstance.setPreWorkitemId(0L);
        dealInstance.setHandleUrl("animal-adoption?id=" + animalInfo.getId());
        dealInstance.setTitle("【领养申请】" + animalInfo.getName());
        dealInstance.setStartDate(new Date());
        dealInstance.setOpDate(new Date());
        dealInstanceService.save(dealInstance);

        // 下一步
        sendNext(animalInfo, dealInstance, "领养申请审核");

        return R.ok();
    }


    /**
     * 审核
     */
    @PostMapping("/audit")
    public R audit(@RequestBody MyAdoptionEntity myAdoption) {
        // 查询动物信息
        AnimalInfoEntity animalInfo = animalInfoService.getById(myAdoption.getAnimalId());
        // 1. 保存申请信息
        myAdoption.setAccepter(getUserId());
        myAdoption.setAccepterName(getUser().getUsername());
        myAdoption.setAcceptDate(new Date());
        myAdoptionService.updateById(myAdoption);
        // 2. 更新动物状态

        // 根据审核状态判断认领状态，是否被领养，0：未领养，1：领养审核中, 2: 已领养
        animalInfo.setIsAdopt((myAdoption.getStatus() + 1) % 3);
        animalInfoService.updateById(animalInfo);

        // 更新流程
        DealInstanceEntity currentStep = dealInstanceService.getCurrentStep(animalInfo.getId());
        currentStep.setStatus(Constant.INT_ONE);
        currentStep.setOpId(getUserId());
        currentStep.setOpName(getUser().getUsername());
        currentStep.setOpDate(new Date());
        dealInstanceService.updateById(currentStep);

        return R.ok();
    }

    private void sendNext(@RequestBody AnimalInfoEntity animalInfo, DealInstanceEntity currentStep, String title) {
        DealInstanceEntity nextStep = new DealInstanceEntity();
        nextStep.setAnimalId(animalInfo.getId());
        nextStep.setStatus(0);
        nextStep.setWorkitem(title);
        nextStep.setPreWorkitemId(currentStep.getId());
        nextStep.setHandleUrl("animal-adoption?id=" + animalInfo.getId());
        nextStep.setTitle("【" + title + "】" + animalInfo.getName());
        nextStep.setStartDate(new Date());
        dealInstanceService.save(nextStep);
    }
}
