package com.ycxy.realestate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
/**
 * 投诉举报表
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-03-06 15:48:15
 */
@Data
@TableName("tb_complaints")
public class ComplaintsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 投诉人id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;
	/**
	 * 投诉人
	 */
	private String userName;
	/**
	 * 类型，1投诉2举报
	 */
	private Integer type;
	/**
	 * 投诉举报内容
	 */
	private String content;
	/**
	 * 处理人id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long handleId;
	/**
	 * 处理人
	 */
	private String handleName;
	/**
	 * 处理意见
	 */
	private String opinions;
	/**
	 * 处理时间
	 */
	private Date handleDate;
	/**
	 * 当前状态，0：未处理，1：已处理
	 */
	private Integer status;
	/**
	 * 投诉时间
	 */
	private Date complaintDate;

}
