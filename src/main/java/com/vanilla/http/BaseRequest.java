package com.vanilla.http;

import com.vanilla.enums.RequestMethodEnum;
import okhttp3.MediaType;

import javax.validation.constraints.NotNull;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * @description: request
 * @author: vae1970
 * @create: 2020-04-24 00:22
 **/
public class BaseRequest<T> {

    /**
     * request url
     */
    @NotNull
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

    public BaseRequest() {
    }

    public BaseRequest(@NotNull String url, @NotNull RequestMethodEnum requestMethod, MediaType mediaType, Map<String, String> header, Map<String, ?> queryParameter, Map<String, ?> requestPayload) {
        this.url = url;
        this.requestMethod = requestMethod;
        this.mediaType = mediaType;
        this.header = header;
        this.queryParameter = queryParameter;
        this.requestPayload = requestPayload;
    }

    @NotNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NotNull String url) {
        this.url = url;
    }

    @NotNull
    public RequestMethodEnum getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(@NotNull RequestMethodEnum requestMethod) {
        this.requestMethod = requestMethod;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public Map<String, ?> getQueryParameter() {
        return queryParameter;
    }

    public void setQueryParameter(Map<String, ?> queryParameter) {
        this.queryParameter = queryParameter;
    }

    public Map<String, ?> getRequestPayload() {
        return requestPayload;
    }

    public void setRequestPayload(Map<String, ?> requestPayload) {
        this.requestPayload = requestPayload;
    }
}
