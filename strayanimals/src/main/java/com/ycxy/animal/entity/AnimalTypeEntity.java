package com.ycxy.animal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * 
 * @author Student
 * @email Student@ycxy.com
 * @date 2021-11-09 23:13:39
 */
@Data
@TableName("animal_type")
public class AnimalTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.INPUT)
	private Long id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 父类id
	 */
	private Long parentId;

	/**
	 * 父类名称
	 */
	private String parentName;
	/**
	 * 创建人
	 */
	private Long createId;
	/**
	 * 创建时间
	 */
	private Date createDate;

	@TableField(exist = false)
	private List<AnimalTypeEntity> children;


}
