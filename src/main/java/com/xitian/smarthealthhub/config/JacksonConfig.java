package com.xitian.smarthealthhub.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {

    private static final DateTimeFormatter DATE_TIME_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> {
            // 1. 关闭"写日期为时间戳"
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            // 2. 空对象不抛异常
            builder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            // 3. 未知字段不抛异常
            builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            // 4. 允许 JSON 注释、单引号、控制字符（使用正确的特征类）
            builder.featuresToEnable(JsonParser.Feature.ALLOW_COMMENTS);
            builder.featuresToEnable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
            builder.featuresToEnable(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS);

            // 5. 非空才序列化
            builder.serializationInclusion(JsonInclude.Include.NON_NULL);

            // 6. JSR-310 时间模块 + 统一格式
            JavaTimeModule timeModule = new JavaTimeModule();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(fmt));
            timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(fmt));
            builder.modulesToInstall(timeModule);

            // 7. 时区统一 GMT+8
            builder.timeZone("GMT+8");
        };
    }
}
