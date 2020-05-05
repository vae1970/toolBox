package com.vanilla.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @description: json util
 * @author: vae1970
 * @create: 2020-04-24 01:40
 **/
public class JsonUtil {

    /**
     * object to json string
     *
     * @param object object
     * @return json string
     */
    public static String toJsonString(Object object) {
        ObjectMapper mapperObj = new ObjectMapper();
        try {
            return mapperObj.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static <T> T toObject(String json, Class<T> tClass) {
        ObjectMapper mapperObj = new ObjectMapper();
        try {
            return mapperObj.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
