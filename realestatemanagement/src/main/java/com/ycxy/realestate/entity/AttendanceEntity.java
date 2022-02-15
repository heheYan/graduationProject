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
 * 考勤记录表
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:25
 */
@Data
@TableName("tb_attendance")
public class AttendanceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 用户标识
	 */
	private Long userId;
	/**
	 * 考勤日期
	 */
	private Date kqDate;
	/**
	 * 考勤时间
	 */
	private Date kqTime;
	/**
	 * 是否迟到
	 */
	private Integer isLate;
	/**
	 * 是否早退
	 */
	private Integer isEarly;

}
