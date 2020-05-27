package com.mmall.util;

import com.mmall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
        // 对象的字段显示方式：
        // 1 Inclusion.ALWAYS 全部列入          2 Inclusion.NON_NULL 不为null
        // 3 Inclusion.NON_DEFAULT 不为默认     4 Inclusion.NON_EMPTY 不为空(更严格)
        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);
        // 取消默认转换timestamps形成
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATE_KEYS_AS_TIMESTAMPS, true);
        // 所有的日期格式都统一 yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));
        // 忽略空Bean转json的错误 true 抛异常  false 不抛异常
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);
        // 忽略在json字符串中存在，在java对象中不存在对应属性的情况 true 抛异常  false 不抛异常
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> String obj2String(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String)obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.info("Parse object to String error!", e);
        }
        return null;
    }

    public static <T> String obj2StringPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String)obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.info("Parse object to String error!", e);
        }
        return null;
    }

    public static <T> T string2Obj(String str, Class<T> clazz){
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class)? (T)str : objectMapper.readValue(str, clazz);
        } catch (IOException e) {
            log.info("string2Obj error!", e);
        }
        return null;
    }

    public static void main(String[] args) {
        User u1 = new User();
        u1.setId(1);
        u1.setUsername("test1");
        User u2 = new User();
        u2.setId(1);
        u2.setUsername("test2");
        List<User> list = new ArrayList<>();
        list.add(u1);
        list.add(u2);
        String str = JsonUtil.obj2StringPretty(list);
        log.info("str:{}", str);
        List<User> list2 = JsonUtil.string2Obj(str, new TypeReference<List<User>>() {});
        // 参数： String, 集合类型，里面元素类型
        List<User> list3 = JsonUtil.string2Obj(str, List.class, User.class);
        log.info("list2:{}", list2);
    }

    public static <T> T string2Obj (String str, TypeReference<T> typeReference) {
        if(StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str, typeReference));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T> T string2Obj (String str, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return objectMapper.readValue(str, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
