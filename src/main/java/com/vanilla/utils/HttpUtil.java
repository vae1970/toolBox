package com.vanilla.utils;

import com.vanilla.http.BaseRequest;
import com.vanilla.http.HttpClient;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import java.util.List;

/**
 * @description: http util
 * @author: vae1970
 * @create: 2020-04-24 01:53
 **/
public class HttpUtil {

    /**
     * HttpClient
     */
    private static final HttpClient HTTP_CLIENT;

    static {
        HTTP_CLIENT = new HttpClient();
    }

    public static void setClient(OkHttpClient client) {
        HTTP_CLIENT.setClient(client);
    }

    /**
     * add interceptor
     *
     * @param interceptorList interceptor list
     */
    public static void addInterceptor(List<Interceptor> interceptorList) {
        OkHttpClient.Builder builder = HttpClient.DEFAULT_OK_HTTP_CLIENT_BUILDER;
        for (Interceptor interceptor : interceptorList) {
            builder.addInterceptor(interceptor);
        }
        OkHttpClient client = builder.build();
        setClient(client);
    }

    public static <T> T submit(BaseRequest<T> form) {
        return HTTP_CLIENT.submit(form);
    }

}