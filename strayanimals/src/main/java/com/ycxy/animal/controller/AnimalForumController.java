package com.ycxy.animal.controller;

import com.ycxy.animal.entity.AnimalForumEntity;
import com.ycxy.animal.service.AnimalForumService;
import com.ycxy.common.utils.Constant;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.common.utils.R;
import com.ycxy.modules.sys.controller.AbstractController;
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
@RequestMapping("animal/animalforum")
public class AnimalForumController extends AbstractController {
    @Autowired
    private AnimalForumService animalForumService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = animalForumService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/mylist")
    public R myList(@RequestParam Map<String, Object> params){
        params.put("userId", getUserId());
        PageUtils page = animalForumService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		AnimalForumEntity animalForum = animalForumService.getById(id);

        return R.ok().put("animalForum", animalForum);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AnimalForumEntity animalForum){
        animalForum.setCreateTime(new Date());
        animalForum.setUserId(getUserId());
        animalForum.setUserName(getUser().getUsername());
        animalForum.setIsDelete(Constant.INT_ZERO);
        animalForum.setUpdateTime(new Date());

		animalForumService.save(animalForum);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AnimalForumEntity animalForum){
        animalForum.setUserId(getUserId());
        animalForum.setUserName(getUser().getUsername());
        animalForum.setUpdateTime(new Date());
		animalForumService.updateById(animalForum);

        return R.ok();
    }

    /**
     * 修改状态
     */
    @RequestMapping("/updateStatus")
    public R updateStatus(@RequestBody AnimalForumEntity animalForum){

		animalForumService.updateById(animalForum);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		animalForumService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
