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
 * 报修信息表
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:24
 */
@Data
@TableName("tb_repair")
public class RepairEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 房间id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long roomId;
	/**
	 * 业主id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;
	/**
	 * 楼栋id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long buildId;
	/**
	 * 房间号
	 */
	private String roomNo;
	/**
	 * 业主名称
	 */
	private String userName;
	/**
	 * 报修时间
	 */
	private Date applyTime;
	/**
	 * 原因
	 */
	private String reason;
	/**
	 * 处理状态
	 * 0:待分配，1：已分配待处理，2：已处理
	 */
	private Integer status;
	/**
	 * 维修人id
	 */
	private Long repairId;
	/**
	 * 维修人名称
	 */
	private String repairName;
	/**
	 * 设备id
	 */
	private Long facilityId;
	/**
	 * 设备名称
	 */
	private String facilityName;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 处理时间
	 */
	private Date repairTime;

}
