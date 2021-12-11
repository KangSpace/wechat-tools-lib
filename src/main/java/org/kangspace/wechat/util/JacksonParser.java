package org.kangspace.wechat.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

/**
 * <pre>
 * jackson-json工具类
 * </pre>
 *
 * @author kango2gler@gmail.com
 * @since 2020/11/11 15:58
 */
public class JacksonParser {
    private static Logger logger = LoggerFactory.getLogger(JacksonParser.class);
    /**
     * 对象转json字符串
     *
     * @param obj obj
     * @return String
     */
    public static String toJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            new JacksonParseException("obj can not parse to json string", e);
        }
        return "";
    }

    /**
     * 对象转json字符串
     *
     * @param value value
     * @param clazz clazz
     * @return T
     */
    public static <T> T toObject(String value, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(value, clazz);
        } catch (IOException e) {
            new JacksonParseException("value :["+value+"] can not parse to Object:" + clazz, e);
        }
        return null;
    }


    public static class JacksonParseException extends RuntimeException {
        public JacksonParseException() {
        }

        public JacksonParseException(String message) {
            super(message);
        }

        public JacksonParseException(String message, Throwable cause) {
            super(message, cause);
        }

        public JacksonParseException(Throwable cause) {
            super(cause);
        }
    }

    /**
     * Unix 时间戳日期转换
     */
    public static class UnixTimestampDeserializer extends JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            String timestamp = jp.getText().trim();
            try {
                return new Date(Long.valueOf(timestamp + "000"));
            } catch (NumberFormatException e) {
                logger.warn("Unable to deserialize timestamp: " + timestamp, e);
                return null;
            }
        }
    }
}
