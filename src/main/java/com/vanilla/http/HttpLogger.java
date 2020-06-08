package com.vanilla.http;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @description:
 * @author: vae1970
 * @create: 2020-06-07 21:09
 **/
public class HttpLogger implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String message) {
//        log.info(, message);//okHttp的详细日志会打印出来
        System.out.println("HttpLogInfo: " + message);
    }


}
