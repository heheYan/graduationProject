package com.ycxy.realestate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("base_user")
public class BaseUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 楼栋id
	 */
	private Long buildId;
	/**
	 * 房间id
	 */
	private Long roomId;
	/**
	 * 房间号
	 */
	private String roomNo;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 登录名
	 */
	private String loginid;
	/**
	 * 身份证号
	 */
	private String idcard;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 是否户主
	 */
	private Integer isMaster;
	/**
	 * 是否租户
	 */
	private Integer isTenantry;
	/**
	 * 入住时间
	 */
	private Date enterTime;
	/**
	 * 离开时间
	 */
	private Date outTime;
	/**
	 * 是否删除
	 */
	private String isDelete;

	private String buildNo;

}
