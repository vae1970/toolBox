package com.vanilla.http;

import com.vanilla.utils.JsonUtil;
import lombok.Setter;
import okhttp3.*;

import java.io.IOException;
import java.time.Duration;
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
    public static final Duration DEFAULT_CALL_TIMEOUT = Duration.ofSeconds(60);
    public static final OkHttpClient.Builder DEFAULT_OK_HTTP_CLIENT_BUILDER;

    @Setter
    private OkHttpClient client;

    static {
        ConnectionPool connectionPool = new ConnectionPool(DEFAULT_MAX_IDLE_CONNECTIONS, DEFAULT_KEEP_ALIVE_DURATION
                , TimeUnit.MINUTES);
        DEFAULT_OK_HTTP_CLIENT_BUILDER = new OkHttpClient.Builder().connectionPool(connectionPool)
                .callTimeout(DEFAULT_CALL_TIMEOUT);
    }

    public HttpClient() {
        client = DEFAULT_OK_HTTP_CLIENT_BUILDER.build();
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
        ResponseBody body = response.body();
        String header = response.header("Content-Type");
        System.out.println(header);
        boolean successful = response.isSuccessful();
        try {
            assert body != null;
            System.out.println(body.string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (T) body.toString();
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
                .map(content -> RequestBody.create(content, mediaType)).orElse(null);
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
