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
 * 公共论坛信息
 *
 * @author Joker
 * @email Student@ycxy.com
 * @date 2022-02-10 14:01:24
 */
@Data
@TableName("tb_announcement")
public class AnnouncementEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 发布用户id
	 */
	private Long userId;
	/**
	 * 发布用户姓名
	 */
	private String userName;
	/**
	 * 发布状态，1：已发布，0：已下架
	 */
	private Integer status;
	/**
	 * 发布的内容
	 */
	private String content;
	/**
	 * 发布时间
	 */
	private Date createTime;
	/**
	 * 是否删除，0：未删除，1：已删除
	 */
	private Integer isDelete;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 标题
	 */
	private String title;

}
