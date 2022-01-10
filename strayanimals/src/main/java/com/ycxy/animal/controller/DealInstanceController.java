package com.ycxy.animal.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.ycxy.common.utils.Constant;
import com.ycxy.modules.sys.controller.AbstractController;
import com.ycxy.modules.sys.entity.SysUserEntity;
import com.ycxy.modules.sys.service.SysUserRoleService;
import com.ycxy.modules.sys.service.SysUserService;
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
 * @author Student
 * @email Student@ycxy.com
 * @date 2021-11-09 23:13:39
 */
@RestController
@RequestMapping("/animal/dealinstance")
public class DealInstanceController extends AbstractController {
    @Autowired
    private DealInstanceService dealInstanceService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        // 非外部角色用户可查看列表
        PageUtils page = null;
        if (sysUserRoleService.isOrganUser(getUserId())) {
            page = dealInstanceService.queryPage(params);
            // 首页只展示10条
            page.setCurrPage(0);
            page.setPageSize(10);
        }
        return R.ok().put("page", page);
    }
    /**
     * 列表
     */
    @RequestMapping("/handleList")
    public R handleList(@RequestParam Map<String, Object> params) {
        // 非外部角色用户可查看列表
        PageUtils page = null;
        if (sysUserRoleService.isOrganUser(getUserId())) {
            page = dealInstanceService.queryPage(params);
        }
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        DealInstanceEntity dealInstance = dealInstanceService.getById(id);

        return R.ok().put("dealInstance", dealInstance);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DealInstanceEntity dealInstance) {
        dealInstanceService.save(dealInstance);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody DealInstanceEntity dealInstance) {
        dealInstanceService.updateById(dealInstance);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        dealInstanceService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 处理流程
     */
    @RequestMapping("/list/{animalId}")
    public R getListByAnimalId(@PathVariable("animalId") Long id) {
        List<DealInstanceEntity> list = dealInstanceService.listByAnimalId(id);
        return R.ok().put("dataList", list);
    }

}
