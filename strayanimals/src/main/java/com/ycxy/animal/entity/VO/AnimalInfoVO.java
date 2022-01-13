package com.ycxy.animal.entity.VO;

import lombok.Data;

/**
 * 用于保存统计数据
 *
 * @author YhhWork
 */
@Data
public class AnimalInfoVO {

    private String type;

    private String typeName;

    private Integer cnt;
    // 未领养
    private Integer wly;
    // 审核中
    private Integer shz;
    // 已领养
    private Integer yly;
    // 可领养
    private Integer wait;
    // 不可领养
    private Integer cant;
}
