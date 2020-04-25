package com.vanilla.http;

import com.vanilla.enums.RequestMethodEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.MediaType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @description: request
 * @author: vae1970
 * @create: 2020-04-24 00:22
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseRequest<T> extends TypeReference<T> {

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
    /**
     * request response class
     */
    private Class<T> responseClass;

    @SuppressWarnings("unchecked")
    public Class<T> getResponseClass() {
        //  可以通过获取匿名内部类或者继承父类的方式获取Superclass
//        Type actualTypeArgument = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//        return (Class<T>) actualTypeArgument;
        return responseClass;
    }

    @Builder
    public BaseRequest(@NotNull String url, @NotNull RequestMethodEnum requestMethod, MediaType mediaType
            , Map<String, String> header, Map<String, ?> queryParameter, Map<String, ?> requestPayload) {
        this.url = url;
        this.requestMethod = requestMethod;
        this.mediaType = mediaType;
        this.header = header;
        this.queryParameter = queryParameter;
        this.requestPayload = requestPayload;
    }


}
