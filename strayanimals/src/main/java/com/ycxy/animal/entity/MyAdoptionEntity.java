package com.ycxy.animal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName my_adoption
 */
@Data
@TableName("my_adoption")
public class MyAdoptionEntity implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 申请人id
     */
    private Long userId;

    /**
     * 申请人名称
     */
    private String userName;

    /**
     * 动物id
     */
    private Long animalId;

    /**
     * 动物名称
     */
    private String animalName;

    /**
     * 申请时间
     */
    private Date applyDate;

    /**
     * 领养原因
     */
    private String reason;

    /**
     * 审核状态
     */
    private Integer status;

    /**
     * 审核人id
     */
    private Long accepter;

    /**
     * 审核人名称
     */
    private String accepterName;

    /**
     * 审核时间
     */
    private Date acceptDate;

    /**
     * 审核意见
     */
    private String opinion;

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private AnimalInfoEntity animalInfo;
}
