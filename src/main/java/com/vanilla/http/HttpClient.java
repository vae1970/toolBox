package com.vanilla.http;

import com.vanilla.enums.ContentType;
import com.vanilla.utils.JsonUtil;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @description: HttpClient
 * @author: vae1970
 * @create: 2020-04-23 23:30
 **/

public class HttpClient {

    public static final int DEFAULT_MAX_IDLE_CONNECTIONS = 50;
    public static final int DEFAULT_KEEP_ALIVE_DURATION = 3;
    public static final OkHttpClient.Builder DEFAULT_OK_HTTP_CLIENT_BUILDER;
    public static final String CONTENT_TYPE = "content-type";

    private OkHttpClient client;

    static {
        ConnectionPool connectionPool = new ConnectionPool(DEFAULT_MAX_IDLE_CONNECTIONS, DEFAULT_KEEP_ALIVE_DURATION
                , TimeUnit.MINUTES);
        DEFAULT_OK_HTTP_CLIENT_BUILDER = new OkHttpClient.Builder().connectionPool(connectionPool)
                .connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS);
    }

    public HttpClient() {
        client = DEFAULT_OK_HTTP_CLIENT_BUILDER.build();
    }

    public void setClient(OkHttpClient client) {
        this.client = client;
    }

    public <T> T submit(BaseRequest<T> form) {
        Request request = buildRequest(form);
        if (request == null) {
            return null;
        }
        try {
            Response response = client.newCall(request).execute();
            return processResponse(response, form.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static <T> T processResponse(Response response, Class<T> clazz) {
        T res;
        ResponseBody body = response.body();
        String contentTypeString = response.header(CONTENT_TYPE);
        ContentType contentType = ContentType.getByCode(contentTypeString);
        contentType = contentType == null ? ContentType.TEXT_PLAIN_UTF_8 : contentType;
        String bodyString = null;
        try {
            assert body != null;
            bodyString = body.string();
        } catch (IOException ignored) {
        }
        switch (contentType) {
            case APPLICATION_JSON:
                res = JsonUtil.toObject(bodyString, clazz);
                break;
            default:
                res = (T) bodyString;
        }
        return res;
    }

    private static <T> Request buildRequest(BaseRequest<T> request) {
        //  generate url with query parameter
        final String url = buildUrl(request.getUrl(), request.getQueryParameter());
        if (url == null) {
            return null;
        }
        Request.Builder builder = new Request.Builder().url(url);
        //  add herder
        if (request.getHeader() != null) {
            for (Map.Entry<String, String> entry : request.getHeader().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                builder.addHeader(key, value);
            }
        }
        //  add body
        String method = request.getRequestMethod().toString();
        MediaType mediaType = Optional.of(request).map(BaseRequest::getMediaType).map(MediaType::toString)
                .map(MediaType::parse).orElse(null);
        RequestBody body = Optional.of(request).map(BaseRequest::getRequestPayload).map(JsonUtil::toJsonString)
                .map(content -> RequestBody.create(mediaType, content)).orElse(null);
        switch (request.getRequestMethod()) {
            case GET:
                builder.method(method, null);
                break;
            case POST:
                builder.method(method, body);
                break;
            default:
                break;
        }
        return builder.build();
    }

    /**
     * build url
     *
     * @param url            url
     * @param queryParameter queryParameter
     * @return Canonical URL
     */
    private static String buildUrl(String url, Map<String, ?> queryParameter) {
        HttpUrl httpUrl = HttpUrl.parse(url);
        if (httpUrl == null) {
            return null;
        } else {
            HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
            if (queryParameter != null) {
                for (Map.Entry<String, ?> entry : queryParameter.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    urlBuilder.addQueryParameter(key, Optional.ofNullable(value).map(Object::toString).orElse(null));
                }
            }
            return urlBuilder.build().toString();
        }
    }

}
