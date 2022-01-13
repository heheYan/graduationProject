package com.ycxy.animal.entity.VO;

import lombok.Data;

/**
 * 用户情况分析
 * @author YhhWork
 */
@Data
public class UserVO {

    private Integer internal;

    private Integer external;

    private Integer hasAdopt;

    private Integer noAdopt;

}
