package com.vanilla.http;

import com.vanilla.enums.RequestMethodEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.MediaType;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * @description: request
 * @author: vae1970
 * @create: 2020-04-24 00:22
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseRequest<T> {

    /**
     * request url
     */
    @NotNull()
    private String url;
    /**
     * http type
     */
    @NotNull()
    private RequestMethodEnum requestMethod;
    /**
     * request mediaType
     */
    private MediaType mediaType;
    /**
     * request header
     */
    private Map<String, String> header;
    /**
     * request query parameter
     */
    private Map<String, ?> queryParameter;
    /**
     * request request payload
     */
    private Map<String, ?> requestPayload;

    @SuppressWarnings("unchecked")
    public Class<T> getType() {
        //  get super class by anonymous inner class
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

}
