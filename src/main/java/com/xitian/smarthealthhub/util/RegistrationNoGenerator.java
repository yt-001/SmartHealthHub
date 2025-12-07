package com.xitian.smarthealthhub.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 预约号生成工具类
 */
public class RegistrationNoGenerator {
    
    /**
     * 生成预约号
     * 格式：REG + 年月日时分秒 + 3位随机数
     * 示例：REG20231207143022001
     * 
     * @return 生成的预约号
     */
    public static String generateRegistrationNo() {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 格式化时间为年月日时分秒
        String timeStr = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        // 生成3位随机数
        int randomNum = (int) (Math.random() * 1000);
        // 格式化为3位数字，不足补0
        String randomStr = String.format("%03d", randomNum);
        // 拼接预约号
        return "REG" + timeStr + randomStr;
    }
}