package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.ResultBean;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Tag(name = "文件上传接口")
@RestController
@RequestMapping({"/upload", "/files"})
@Slf4j
public class FileUploadController {

    @Value("${file.upload-path}")
    private String uploadPath;

    // 图片限制 5MB
    private static final long IMAGE_MAX_SIZE = 5 * 1024 * 1024;
    private static final List<String> IMAGE_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "webp");

    // 视频限制 200MB
    private static final long VIDEO_MAX_SIZE = 200 * 1024 * 1024;
    private static final List<String> VIDEO_EXTENSIONS = Arrays.asList("mp4", "avi", "mov", "wmv", "flv", "mkv");

    @Operation(summary = "上传图片")
    @PostMapping({"/image", "/upload-image"})
    public ResultBean<String> uploadImage(@RequestParam("file") MultipartFile file) {
        return uploadFile(file, "images", IMAGE_MAX_SIZE, IMAGE_EXTENSIONS);
    }

    @Operation(summary = "上传视频")
    @PostMapping({"/video", "/upload-video"})
    public ResultBean<String> uploadVideo(@RequestParam("file") MultipartFile file) {
        return uploadFile(file, "videos", VIDEO_MAX_SIZE, VIDEO_EXTENSIONS);
    }

    private ResultBean<String> uploadFile(MultipartFile file, String subDir, long maxSize, List<String> allowedExtensions) {
        if (file.isEmpty()) {
            return ResultBean.fail(com.xitian.smarthealthhub.bean.StatusCode.PARAMETER_ERROR, "上传文件不能为空");
        }

        // 检查大小
        if (file.getSize() > maxSize) {
            return ResultBean.fail(com.xitian.smarthealthhub.bean.StatusCode.PARAMETER_ERROR, "文件大小超过限制 (" + (maxSize / 1024 / 1024) + "MB)");
        }

        // 检查扩展名
        String originalFilename = file.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(originalFilename);
        if (extension == null || !allowedExtensions.contains(extension.toLowerCase())) {
            return ResultBean.fail(com.xitian.smarthealthhub.bean.StatusCode.PARAMETER_ERROR, "不支持的文件格式: " + extension);
        }

        try {
            // 构建存储路径: uploadPath/subDir/uuid.ext
            // 确保 uploadPath 以分隔符结尾
            String basePath = uploadPath.endsWith(File.separator) || uploadPath.endsWith("/") ? uploadPath : uploadPath + File.separator;
            String finalDir = basePath + subDir;
            File dir = new File(finalDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String newFilename = UUID.randomUUID().toString().replace("-", "") + "." + extension;
            File dest = new File(dir, newFilename);
            file.transferTo(dest);

            // 返回可访问的URL
            // 假设 static-access-path 是 /uploads/**
            // 返回格式: /uploads/subDir/uuid.ext
            String url = "/uploads/" + subDir + "/" + newFilename;
            return ResultBean.success(url).msg("上传成功");

        } catch (IOException e) {
            log.error("文件上传失败", e);
            return ResultBean.fail(com.xitian.smarthealthhub.bean.StatusCode.INTERNAL_SERVER_ERROR, "文件上传失败: " + e.getMessage());
        }
    }
}
