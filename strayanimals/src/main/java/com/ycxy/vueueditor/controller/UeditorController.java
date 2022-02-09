package com.ycxy.vueueditor.controller;


import com.alibaba.fastjson.JSONObject;
import com.ycxy.vueueditor.util.FileUtil;
import com.ycxy.vueueditor.vo.Ueditor;
import com.ycxy.vueueditor.vo.UeditorImage;
import com.ycxy.vueueditor.vo.UeditorOnlineImage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName UeditorController
 * @Description TODO
 * @Author lxt
 * @Date 2021/7/22 22:21
 */
@Slf4j
@RequestMapping("/ueditor")
@RestController
public class UeditorController {

	@Value("${upload.server}")
	private String server;

	//用户名
	private String userName = "admin";

	// 模拟数据库 存储已上传的图片资源，userName 模拟用户标识为key分类
	private final Map<String, UeditorOnlineImage> imageMap = new ConcurrentHashMap<>();

	private void add(Ueditor ueditor,String id){
		UeditorOnlineImage ueditorOnlineImage = imageMap.get(id);
		if(ueditorOnlineImage == null){
			ueditorOnlineImage = new UeditorOnlineImage();
			ueditorOnlineImage.setState(Ueditor.ACTION_SUCCESS);
		}
		UeditorImage ueditorImage = new UeditorImage();
		ueditorImage.setMtime(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
		ueditorImage.setUrl(ueditor.getUrl());
		ueditorOnlineImage.getList().add(ueditorImage);
		imageMap.put(id,ueditorOnlineImage);
	}

	/**
	 * @Description: 富文本编辑器服务端
	 * @param action
	 * @param callback 回调参数
	 * @param upfile
	 * @return: java.lang.Object
	 * @Author: lxt
	 * @Date: 2021/7/22 23:51
	 */
	@PostMapping("/server")
    public Object server(String action, String callback, MultipartFile upfile){
		Object result = null;
		switch (action){
			case Ueditor.ACTION_CONFIG:
				result = callback+"("+Ueditor.UEDITOR_CONFIG+")";
				break;
			case Ueditor.ACTION_UPLOADIMAGE:
			case Ueditor.ACTION_UPLOADVIDEO:
			case Ueditor.ACTION_UPLOADFILE:
				result = uploadFile(upfile);
				break;
			case Ueditor.ACTION_LISTIMAGE:
				result = callback+"("+ JSONObject.toJSONString(imageMap.get(userName))+")";
				break;
			default:
		}
        return result;
    }
	/**
	 * @Description: 富文本编辑器服务端
	 * @param action
	 * @param callback 回调参数
	 * @param upfile
	 * @return: java.lang.Object
	 * @Author: lxt
	 * @Date: 2021/7/22 23:51
	 */
	@GetMapping("/server")
	public Object serverGet(String action, String callback, MultipartFile upfile){
		Object result = null;
		switch (action){
			case Ueditor.ACTION_CONFIG:
				result = callback+"("+Ueditor.UEDITOR_CONFIG+")";
				break;
			case Ueditor.ACTION_UPLOADIMAGE:
			case Ueditor.ACTION_UPLOADVIDEO:
			case Ueditor.ACTION_UPLOADFILE:
				result = uploadFile(upfile);
				break;
			case Ueditor.ACTION_LISTIMAGE:
				result = callback+"("+ JSONObject.toJSONString(imageMap.get(userName))+")";
				break;
			default:
		}
		return result;
	}

    private Ueditor uploadFile(MultipartFile upfile){
		Ueditor ueditor = new Ueditor();
		ueditor.setState(Ueditor.ACTION_SUCCESS);
		try{
			//上传文件
			String filePath = server+FileUtil.INSTANCE.uploadFile(upfile);
			ueditor.setUrl(filePath);
			ueditor.setTitle(upfile.getName());
			ueditor.setOriginal(upfile.getOriginalFilename());
		}catch (Exception e){
			ueditor.setState(Ueditor.ACTION_FAIL);
			log.error(e.getMessage(),e);
		}
		return ueditor;
	}
}
