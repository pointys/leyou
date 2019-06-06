package com.leyou.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.config.UploadProperties;
import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * 图片上传
 * 校验文件类型
 * 校验文件内容
 * 返回图片url
 */
@Slf4j
@Service
@EnableConfigurationProperties(UploadProperties.class)
public class UploadService {

private static final String imageWebsite="http://image.leyou.com/";

    //注入fastdfs客户端
    @Autowired
    FastFileStorageClient storageClient;

    //注入配置类
//    private UploadProperties uploadProperties;

    //允许的图片类型(已经放到yml)
    private static final List<String> allowTypes = Arrays.asList("image/jpeg", "image/png", "image/jpg", "image/bmp");

    public String uploadImage(MultipartFile file) {
        try {
            //校验文件类型防止炸弹
            //            // 1.校验后缀类型
            //            //2.校验文件内容是否图片
            if (ImageIO.read(file.getInputStream()) == null || !allowTypes.contains(file.getContentType())) {
                throw new CustomException(ExceptionEnum.FILE_TYPE_NO_ALLOW);
            } else {

                /*//1.1(核心代码)保存文件到本地 设置本地保存路径和文件名
                file.transferTo(new File("F:/workspace7/demo/upload", file.getOriginalFilename()));
                //1.2返回url
                return "http://image.leyou.com/upload" + file.getOriginalFilename();*/


                // 2、将图片上传到FastDFS服务器
                // 2.1、获取文件后缀名
                String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
                // 2.2、上传
                StorePath storePath = this.storageClient.uploadFile(
                        file.getInputStream(), file.getSize(), extension, null);
                // 2.3、返回完整路径
                String url=imageWebsite+storePath.getFullPath();
                return url;
            }
        } catch (IOException e) {
            //上传失败,控制台打印日志
            log.error(file.getOriginalFilename() + "上传文件失败", e);
            //抛自定义异常
            throw new CustomException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }

    }

}
