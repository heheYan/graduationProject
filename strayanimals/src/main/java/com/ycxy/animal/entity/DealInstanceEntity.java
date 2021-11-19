package com.ycxy.animal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author Student
 * @email Student@ycxy.com
 * @date 2021-11-09 23:13:39
 */
@Data
@TableName("deal_instance")
public class DealInstanceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.INPUT)
	private Long id;
	/**
	 * 
	 */
	private Long animalId;
	/**
	 * 操作人id
	 */
	private Long opId;
	/**
	 * 
	 */
	private String opName;
	/**
	 * 环节名称
	 */
	private String workitem;
	/**
	 * 上一步
	 */
	private Long preWorkitemId;
	/**
	 * 当前状态
	 */
	private Integer status;
	/**
	 * 处理意见
	 */
	private String opinion;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 接收时间
	 */
	private Date startDate;
	/**
	 * 处理时间
	 */
	private Date opDate;
	/**
	 * 处理地址
	 */
	private String handleUrl;

}
