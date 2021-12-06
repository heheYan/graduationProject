package com.ycxy.animal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Student
 * @email Student@ycxy.com
 * @date 2021-11-09 23:13:39
 */
@Data
@TableName("pualifications")
public class PualificationsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 昵称
	 */
	private String name;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 联系电话
	 */
	private String mobile;
	/**
	 * 申请人id
	 */
	private Long userId;
	/**
	 * 住址
	 */
	private String address;
	/**
	 * 审核人员id
	 */
	private Long checkId;
	/**
	 * 审核人
	 */
	private String checkName;
	/**
	 * 审核时间
	 */
	private Date checkDate;
	/**
	 * 审核状态，0：待审核，1：审核通过，2：审核未通过
	 */
	private Integer status;
	/**
	 * 理由
	 */
	private String reason;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 提交时间
	 */
	private Date createDate;

}
