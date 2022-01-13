package com.ycxy.animal.dao;

import com.ycxy.animal.entity.AnimalInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycxy.animal.entity.VO.AnimalInfoVO;
import com.ycxy.animal.entity.VO.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Student
 * @email Student@ycxy.com
 * @date 2021-11-09 23:13:39
 */
@Mapper
public interface AnimalInfoDao extends BaseMapper<AnimalInfoEntity> {

    /**
     * 按种类统计动物情况
     *
     * @return 结果集合
     */
    List<AnimalInfoVO> analysisByType();

    /**
     * 动物领养情况分析
     *
     * @return 结果集合
     */
    AnimalInfoVO animalAdoptionAnalysis();

    /**
     * 系统用户分析
     *
     * @return
     */
    UserVO userSituation();
}
