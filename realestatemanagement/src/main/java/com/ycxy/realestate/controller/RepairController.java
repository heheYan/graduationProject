package com.ycxy.realestate.controller;

import com.ycxy.common.utils.Constant;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.R;
import com.ycxy.modules.sys.controller.AbstractController;
import com.ycxy.modules.sys.entity.SysUserEntity;
import com.ycxy.modules.sys.service.SysUserService;
import com.ycxy.realestate.entity.BaseBuildEntity;
import com.ycxy.realestate.entity.BaseFacilityEntity;
import com.ycxy.realestate.entity.BaseRoomEntity;
import com.ycxy.realestate.entity.RepairEntity;
import com.ycxy.realestate.service.BaseBuildService;
import com.ycxy.realestate.service.BaseFacilityService;
import com.ycxy.realestate.service.BaseRoomService;
import com.ycxy.realestate.service.RepairService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 报修信息表
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:24
 */
@RestController
@RequestMapping("realestate/repair")
public class RepairController extends AbstractController {
    @Autowired
    private RepairService repairService;
    @Autowired
    private BaseBuildService baseBuildService;
    @Autowired
    private BaseRoomService baseRoomService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private BaseFacilityService baseFacilityService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("realestate:repair:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = repairService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("realestate:repair:info")
    public R info(@PathVariable("id") Long id) {
        RepairEntity repair = repairService.getById(id);

        return R.ok().put("repair", repair);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("realestate:repair:save")
    public R save(@RequestBody RepairEntity repair) {
        // 获取楼栋号
        BaseBuildEntity baseBuild = baseBuildService.getById(repair.getBuildId());
        BaseRoomEntity baseRoom = baseRoomService.getById(repair.getRoomId());
        repair.setRoomNo(baseBuild.getNo() + "-" + baseRoom.getNo());
        repair.setStatus(Constant.INT_ZERO);
        repair.setUserId(getUserId());
        repair.setApplyTime(new Date());
        // 如果设备编号不为空，同步修改基础设施状态
        if (repair.getFacilityId() != null) {
            BaseFacilityEntity facility = baseFacilityService.getById(repair.getFacilityId());
            if (facility != null) {
                facility.setStatus(0);
                baseFacilityService.updateById(facility);

                repair.setFacilityName(facility.getName() + "-" + facility.getNo());
            }
        }

        repairService.save(repair);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("realestate:repair:update")
    public R update(@RequestBody RepairEntity repair) {
        final SysUserEntity user = sysUserService.getById(repair.getRepairId());
        if (user != null) {
            repair.setRepairName(user.getNickName());
        }
        if (!Constant.INT_ONE.equals(repair.getStatus())) {
            repair.setStatus(Constant.INT_ONE);
        }
        repairService.updateById(repair);

        return R.ok();
    }

    /**
     * 反馈
     */
    @RequestMapping("/feedback")
    @RequiresPermissions("realestate:repair:update")
    public R feedback(@RequestBody RepairEntity repair) {
        repair.setStatus(Constant.INT_TWO);
        repair.setRepairTime(new Date());

        // 如果设备id不空，修改设备状态为1正常
        if (repair.getFacilityId() != null) {
            BaseFacilityEntity facility = baseFacilityService.getById(repair.getFacilityId());
            if (facility != null) {
                facility.setStatus(1);
                baseFacilityService.updateById(facility);
            }
        }

        repairService.updateById(repair);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("realestate:repair:delete")
    public R delete(@RequestBody Long[] ids) {
        repairService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 是否可维护
     */
    @RequestMapping("/isAuthFeedback/{id}")
    public R isAuthFeedback(@PathVariable("id") Long id) {
        RepairEntity repair = repairService.getById(id);
        boolean isAuth = false;
        if (getUserId().equals(repair.getRepairId())) {
            isAuth = true;
        }
        return R.ok().put("isAuth", isAuth);
    }

    /**
     * 查看维修人员列表
     */
    @RequestMapping("/getRepairUserList")
    public R getRepairUserList() {
        List<SysUserEntity> userList = sysUserService.getRepairUserList("维修人员");

        return R.ok().put("userList", userList);
    }

}
