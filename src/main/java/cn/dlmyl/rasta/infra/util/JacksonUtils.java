package cn.dlmyl.rasta.infra.util;

import cn.dlmyl.rasta.infra.exception.JsonException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON 工具
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
public enum JacksonUtils {

    /**
     * 表示单例
     */
    X;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    public String format(Object content) {
        try {
            return MAPPER.writeValueAsString(content);
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    public <T> T parse(String content, Class<T> targetClass) {
        try {
            return MAPPER.readValue(content, targetClass);
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    public <T> T parse(String content, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(content, typeReference);
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

}
