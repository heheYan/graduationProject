package com.ycxy.animal.controller;

import com.ycxy.animal.entity.VO.AnimalInfoVO;
import com.ycxy.animal.entity.VO.UserVO;
import com.ycxy.animal.service.AnimalInfoService;
import com.ycxy.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页用户分析模块
 * @author YhhWork
 */
@RestController
@RequestMapping("/analysis")
public class HomeAnalysisController {

    @Autowired
    private AnimalInfoService animalInfoService;

    /**
     * 按种类统计动物情况
     *
     * @return
     */
    @RequestMapping("/analysisByType")
    public R analysisByType() {
        return R.ok().put("data", animalInfoService.analysisByType());
    }

    /**
     * 系统用户分析
     *
     * @return
     */
    @RequestMapping("/userSituation")
    public R userSituation() {
        final UserVO userVO = animalInfoService.userSituation();
        List<Map<String, Object>> rtnObj = new ArrayList<>();
        // 内部用户数
        int internal = 0;
        // 外部用户数
        int external = 0;
        // 具备领养资格
        int hasAdopt = 0;
        // 不具备领养资格
        int noAdopt = 0;

        if (userVO != null) {
            internal = userVO.getInternal() == null ? 0 : userVO.getInternal();
            external = userVO.getExternal() == null ? 0 : userVO.getExternal();
            hasAdopt = userVO.getHasAdopt() == null ? 0 : userVO.getHasAdopt();
            noAdopt = userVO.getNoAdopt() == null ? 0 : userVO.getNoAdopt();
        }
        Map<String, Object> internalMap = new HashMap<>(2);
        internalMap.put("name", "内部员工");
        internalMap.put("value", internal);
        rtnObj.add(internalMap);

        Map<String, Object> externalMap = new HashMap<>(2);
        externalMap.put("name", "外部用户");
        externalMap.put("value", external);
        rtnObj.add(externalMap);

        Map<String, Object> hasAdoptMap = new HashMap<>(2);
        hasAdoptMap.put("name", "具备领养资格");
        hasAdoptMap.put("value", hasAdopt);
        rtnObj.add(hasAdoptMap);

        Map<String, Object> noAdoptMap = new HashMap<>(2);
        noAdoptMap.put("name", "未具备领养资格");
        noAdoptMap.put("value", noAdopt);
        rtnObj.add(noAdoptMap);

        return R.ok().put("data", rtnObj);
    }

    /**
     * 动物领养情况分析
     *
     * @return
     */
    @RequestMapping("/animalAdoptionAnalysis")
    public R animalAdoptionAnalysis() {
        final AnimalInfoVO animalInfoVO = animalInfoService.animalAdoptionAnalysis();
        List<Map<String, Object>> rtnObj = new ArrayList<>();
        int wly = 0;
        int shz = 0;
        int yly = 0;
        int wait = 0;
        int cant = 0;
        if (animalInfoVO != null) {
            wly = animalInfoVO.getWly() == null ? 0 : animalInfoVO.getWly();
            shz = animalInfoVO.getShz() == null ? 0 : animalInfoVO.getShz();
            yly = animalInfoVO.getYly() == null ? 0 : animalInfoVO.getYly();
            wait = animalInfoVO.getWait() == null ? 0 : animalInfoVO.getWait();
            cant = animalInfoVO.getCant()== null ? 0 : animalInfoVO.getCant();
        }
        Map<String, Object> wlyMap = new HashMap<>(2);
        wlyMap.put("name", "未领养");
        wlyMap.put("value", wly);
        rtnObj.add(wlyMap);
        Map<String, Object> shzMap = new HashMap<>(2);
        shzMap.put("name", "审核中");
        shzMap.put("value", shz);
        rtnObj.add(shzMap);
        Map<String, Object> ylyMap = new HashMap<>(2);
        ylyMap.put("name", "已领养");
        ylyMap.put("value", yly);
        rtnObj.add(ylyMap);
        Map<String, Object> waitMap = new HashMap<>(2);
        waitMap.put("name", "可领养");
        waitMap.put("value", wait);
        rtnObj.add(waitMap);
        Map<String, Object> cantMap = new HashMap<>(2);
        cantMap.put("name", "不可领养");
        cantMap.put("value", cant);
        rtnObj.add(cantMap);
        return R.ok().put("data", rtnObj);
    }
}
