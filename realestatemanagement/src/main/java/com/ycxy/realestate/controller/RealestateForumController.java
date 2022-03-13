package com.ycxy.realestate.controller;

import com.ycxy.common.utils.Constant;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.R;
import com.ycxy.modules.sys.controller.AbstractController;
import com.ycxy.realestate.entity.RealestateForumEntity;
import com.ycxy.realestate.service.RealestateForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * 公共论坛信息
 *
 * @author Student
 * @email Student@ycxy.com
 * @date 2022-02-08 16:40:36
 */
@RestController
@RequestMapping("realestate/realestateforum")
public class RealestateForumController extends AbstractController {
    @Autowired
    private RealestateForumService realestateForumService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = realestateForumService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/mylist")
    public R myList(@RequestParam Map<String, Object> params) {
        params.put("userId", getUserId());
        PageUtils page = realestateForumService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        RealestateForumEntity realestateForum = realestateForumService.getById(id);

        return R.ok().put("realestateForum", realestateForum);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody RealestateForumEntity realestateForum) {
        realestateForum.setCreateTime(new Date());
        realestateForum.setUserId(getUserId());
        realestateForum.setUserName(getUser().getNickName());
        realestateForum.setIsDelete(Constant.INT_ZERO);
        realestateForum.setUpdateTime(new Date());

        realestateForumService.save(realestateForum);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody RealestateForumEntity realestateForum) {
        realestateForum.setUserId(getUserId());
        realestateForum.setUserName(getUser().getUsername());
        realestateForum.setUpdateTime(new Date());
        realestateForumService.updateById(realestateForum);

        return R.ok();
    }

    /**
     * 修改状态
     */
    @RequestMapping("/updateStatus")
    public R updateStatus(@RequestBody RealestateForumEntity realestateForum) {

        realestateForumService.updateById(realestateForum);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        realestateForumService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
