package com.xitian.smarthealthhub.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

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
            
            // 8. 配置字段可见性，支持字段的序列化和反序列化
            builder.visibility(FIELD, ANY);
            
            // 9. Long类型序列化为字符串，防止前端精度丢失
            builder.serializerByType(Long.class, new JsonSerializer<Long>() {
                @Override
                public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                    if (value != null) {
                        gen.writeString(value.toString());
                    }
                }
            });
            
            builder.serializerByType(Long.TYPE, new JsonSerializer<Long>() {
                @Override
                public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                    gen.writeString(value.toString());
                }
            });
        };
    }
}