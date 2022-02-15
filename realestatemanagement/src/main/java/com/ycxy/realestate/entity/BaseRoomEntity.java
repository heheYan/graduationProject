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
 *
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:25
 */
@Data
@TableName("base_room")
public class BaseRoomEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 房间号
	 */
	private String no;
	/**
	 * 楼栋号
	 */
	private String buildNo;
	/**
	 * 楼栋id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long buildId;
	/**
	 * 状态，0：未售，1：业主入住，2：出租
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
