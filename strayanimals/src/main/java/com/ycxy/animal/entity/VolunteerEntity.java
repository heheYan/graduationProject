package com.ycxy.animal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 志愿者信息表
 * 
 * @author Student
 * @email Student@ycxy.com
 * @date 2021-11-09 23:13:39
 */
@Data
@TableName("volunteer")
public class VolunteerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.INPUT)
	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 申请日期
	 */
	private Date applyDate;
	/**
	 * 申请状态，0：已申请，1：审核通过，2：审核退回
	 */
	private Integer status;
	/**
	 * 处理人id
	 */
	private Long handlerId;
	/**
	 * 处理人
	 */
	private String handlerName;
	/**
	 * 处理时间
	 */
	private Date handleDate;

}
