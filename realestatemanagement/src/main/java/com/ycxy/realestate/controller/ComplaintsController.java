package com.ycxy.realestate.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.ycxy.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ycxy.realestate.entity.ComplaintsEntity;
import com.ycxy.realestate.service.ComplaintsService;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.R;



/**
 * 投诉举报表
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-03-06 15:48:15
 */
@RestController
@RequestMapping("realestate/complaints")
public class ComplaintsController extends AbstractController {
    @Autowired
    private ComplaintsService complaintsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = complaintsService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/mylist")
    public R myList(@RequestParam Map<String, Object> params){
        params.put("user_id", getUserId());
        PageUtils page = complaintsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ComplaintsEntity complaints = complaintsService.getById(id);

        return R.ok().put("complaints", complaints);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ComplaintsEntity complaints){
        complaints.setComplaintDate(new Date());
        complaints.setStatus(0);
		complaintsService.save(complaints);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ComplaintsEntity complaints){
		complaintsService.updateById(complaints);

        return R.ok();
    }

    /**
     * 处理
     */
    @RequestMapping("/handle")
    public R handle(@RequestBody ComplaintsEntity complaints){
        complaints.setHandleId(getUserId());
        complaints.setHandleName(getUser().getNickName());
        complaints.setHandleDate(new Date());
        complaints.setStatus(1);
        complaintsService.updateById(complaints);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		complaintsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
