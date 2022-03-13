package com.ycxy.realestate.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ycxy.common.utils.R;
import com.ycxy.realestate.entity.BaseBuildEntity;
import com.ycxy.realestate.entity.BaseFacilityEntity;
import com.ycxy.realestate.entity.CarspaceEntity;
import com.ycxy.realestate.service.BaseBuildService;
import com.ycxy.realestate.service.BaseFacilityService;
import com.ycxy.realestate.service.BaseRoomService;
import com.ycxy.realestate.service.CarspaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Autowired
    BaseFacilityService baseFacilityService;

    @Autowired
    BaseBuildService BaseBuildService;

    @Autowired
    BaseRoomService baseRoomService;
    @Autowired
    CarspaceService carspaceService;

    /**
     * 公共设施分析图
     */
    @RequestMapping(value = "/facility", method = RequestMethod.GET)
    public R facility() {
        // 1. 统计各类设施的数量
        // 1:电梯；2：路灯 3:健身设施 4：儿童设施 5：大门
        JSONObject rtnObj = new JSONObject();
        int[] cntArr = new int[5];
        String[] perArr = new String[5];
        // 故障率
        final QueryWrapper<BaseFacilityEntity> queryWrapper = new QueryWrapper<>();
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        for (int i = 1; i <= 5; i++) {
            queryWrapper.clear();
            queryWrapper.eq("type", i);
            final int count = baseFacilityService.count(queryWrapper);
            // 设备数
            cntArr[i - 1] = count;
            // 故障数， 查询报修过的设备
            final int errCount = baseFacilityService.countErrorByType(i);
            String per = "0";
            if (count > 0) {
                per = decimalFormat.format(((float) errCount / count) * 100);
            }
            perArr[i - 1] = per;
        }
        rtnObj.put("cntArr", cntArr);
        rtnObj.put("perArr", perArr);
        return R.ok().put("data", rtnObj);
    }

    /**
     * 入住用户分析
     */
    @RequestMapping(value = "/userAnalysisByBuild", method = RequestMethod.GET)
    public R userAnalysisByBuild() {
        // 1. 统计各楼房间总数
        JSONObject rtnObj = new JSONObject();

        final List<BaseBuildEntity> buildList = BaseBuildService.list();
        List<JSONObject> buildNameList = new ArrayList<>();
        int[] yeZhuCnt = new int[buildList.size()];
        int[] zuHuCnt = new int[buildList.size()];
        int i = 0;
        for (BaseBuildEntity build : buildList) {
            JSONObject object = new JSONObject();
            final List<Map<String, Object>> mapList = baseRoomService.countByBuildIdAndStatus(build.getId());
            int total = 0;
            if (!mapList.isEmpty()) {
                for (Map<String, Object> map : mapList) {
                    total += Integer.parseInt(map.get("cnt").toString());
                    if ("1".equals(map.get("status").toString())) {
                        yeZhuCnt[i] = Integer.parseInt(map.get("cnt").toString());
                    }
                    if ("2".equals(map.get("status").toString())) {
                        zuHuCnt[i] = Integer.parseInt(map.get("cnt").toString());
                    }
                }
            }
            i++;
            object.put("name", build.getName());
            object.put("max", total);
            buildNameList.add(object);
        }
        rtnObj.put("maxdata", buildNameList);
        rtnObj.put("yezhu", yeZhuCnt);
        rtnObj.put("zuhu", zuHuCnt);

        return R.ok().put("data", rtnObj);
    }

    /**
     * 车位租售情况分析
     */
    @RequestMapping(value = "/carSpaceAnalysis", method = RequestMethod.GET)
    public R carSpaceAnalysis() {
        // 1. 统计各楼房间总数
        List<JSONObject> list = new ArrayList<>();
        QueryWrapper<CarspaceEntity> queryWrapper = new QueryWrapper<>();
        // 未售出
        queryWrapper.eq("status", 0);
        int count = carspaceService.count(queryWrapper);
        JSONObject wsc = new JSONObject();
        wsc.put("name", "未售出");
        wsc.put("value", count);
        queryWrapper.clear();
        queryWrapper.eq("status", 1);
        count = carspaceService.count(queryWrapper);
        JSONObject ysc = new JSONObject();
        ysc.put("name", "已售出");
        ysc.put("value", count);
        queryWrapper.clear();
        queryWrapper.eq("status", 2);
        count = carspaceService.count(queryWrapper);
        JSONObject ycz = new JSONObject();
        ycz.put("name", "已出租");
        ycz.put("value", count);

        list.add(wsc);
        list.add(ysc);
        list.add(ycz);
        return R.ok().put("data", list);
    }
}
