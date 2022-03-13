package com.ycxy.realestate.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
/**
 * 车位信息表
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:24
 */
@Data
@TableName("tb_carspace")
public class CarspaceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 车位编号
	 */
	private String no;
	/**
	 * 车牌号
	 */
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private String carNo;
	/**
	 * 房间id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private Long roomId;
	/**
	 * 楼栋id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private Long buildId;
	/**
	 * 房间号
	 */
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private String roomNo;
	/**
	 * 金额
	 */
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private BigDecimal money;
	/**
	 * 状态。出租、临时、购买
	 */
	private Integer status;
	/**
	 * 变更时间
	 */
	private Date lastChangeTime;

}
