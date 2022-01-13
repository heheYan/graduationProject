package com.ycxy.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycxy.animal.entity.VO.AnimalInfoVO;
import com.ycxy.animal.entity.VO.UserVO;
import com.ycxy.common.utils.PageUtils;
import com.ycxy.animal.entity.AnimalInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Student
 * @email Student@ycxy.com
 * @date 2021-11-09 23:13:39
 */
public interface AnimalInfoService extends IService<AnimalInfoEntity> {
    /**
     * 分页数据
     *
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);
    /**
     * 可领养分页数据
     *
     * @return
     */
    PageUtils canAdoptPage(Map<String, Object> params);
    /**
     * 按种类统计动物情况
     *
     * @return
     */
    List<AnimalInfoVO> analysisByType();
    /**
     * 动物领养情况分析
     *
     * @return
     */
    AnimalInfoVO animalAdoptionAnalysis();

    /**
     * 系统用户分析
     *
     * @return
     */
    UserVO userSituation();
}
