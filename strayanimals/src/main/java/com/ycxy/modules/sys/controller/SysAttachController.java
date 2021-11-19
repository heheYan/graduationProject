package com.ycxy.modules.sys.controller;

import com.ycxy.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/sys/attach")
public class SysAttachController {

    @Value("${upload.uploadFolder}")
    private String uploadFolder;

    @PostMapping("/upload")
    public R upload(MultipartFile file, HttpServletRequest req) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
        String format = sdf.format(new Date());
        String realPath = uploadFolder + format;
        log.info(realPath);
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID() + (oldName != null ? oldName.substring(oldName.lastIndexOf(".")) : null);
        file.transferTo(new File(folder,newName));
        String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/stray-anmial/upload" + format + newName;
        log.info(url);
        return R.ok("上传成功!").put("filepath", url);
    }
}
