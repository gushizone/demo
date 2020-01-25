/**
 * Copyright (c) https://github.com/gushizone
 */
package tk.gushizone.web.upload.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author gushizone@gmail.com
 * @date 2020-01-15 16:40
 */
@Slf4j
@RestController
@RequestMapping("/mvc/upload")
public class UploadController {

    private static final String UPLOAD_PATH = FileUtils.getUserDirectoryPath() + File.separator + "Downloads" + File.separator + "upload";


    /**
     * 单文件上传
     */
    @PostMapping("/file")
    public String file(MultipartFile file) throws IOException {

        String filepath = UPLOAD_PATH + File.separator  + System.currentTimeMillis() + file.getOriginalFilename();
        saveFile(file, filepath);

        return "upload finished";
    }

    /**
     * 多文件上传
     */
    @PostMapping("/files")
    public String files(MultipartFile[] files) throws IOException {

        int count = 0;
        for (MultipartFile file : files) {

            String filepath = UPLOAD_PATH + File.separator  + System.currentTimeMillis() + file.getOriginalFilename();
            if (saveFile(file, filepath)) {
                count++;
            }
        }

        return "upload finished, success count : " + count;
    }

    private boolean saveFile(MultipartFile file, String filepath) throws IOException {

        if(file.isEmpty()){
            log.info("file is empty: {}", file.getOriginalFilename());
            return false;
        }

        // 自动创建不存在文件夹
        FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filepath));
        // 需要手动创建文件夹
        // file.transferTo(new File(filepath));

        log.info("upload success : {}", filepath);
        return true;
    }

}
