package com.ycxy.animal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 
 * 
 * @author Student
 * @email Student@ycxy.com
 * @date 2021-11-09 23:13:39
 */
@Data
@TableName("animal_info")
public class AnimalInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String icon;
	/**
	 * 
	 */
	private String imgurl;
	/**
	 * 
	 */
	private Long type;
	/**
	 *
	 */
	private String typename;
	/**
	 * 登记入库人员
	 */
	private Long register;
	/**
	 * 
	 */
	private String registerName;
	/**
	 * 
	 */
	private Date registerDate;
	/**
	 * 动物状态，0：初始登记，1：已清洗，2：已防疫
	 */
	private Integer status;
	/**
	 * 是否被领养，0：否，1：是
	 */
	private Integer isAdopt;
	/**
	 * 领养人
	 */
	private Long adopter;
	/**
	 * 
	 */
	private String adopterName;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 领养时间
	 */
	private Date adoptDate;

}
