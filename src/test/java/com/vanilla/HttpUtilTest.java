package com.vanilla;

import com.vanilla.enums.RequestMethodEnum;
import com.vanilla.http.BaseRequest;
import com.vanilla.utils.HttpUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: http util test
 * @author: vae1970
 * @create: 2020-04-24 22:40
 **/

public class HttpUtilTest {

    @Test
    public void getMethod() {
        Map<String, String> form = new HashMap<>();
        form.put("grant_type", "client_credential");
        form.put("appid", "wx17f0d4b40fcab3f3");
        form.put("secret", "1584a18f48972354a3a4c7e3c22b21f6");
        BaseRequest<String> getMethod = BaseRequest.<String>builder().requestMethod(RequestMethodEnum.GET)
                .url("https://api.weixin.qq.com/cgi-bin/token").queryParameter(form).build();
        getMethod.setResponseClass(String.class);
        String res = HttpUtil.submit(getMethod);
        System.out.println(res);
    }


}
