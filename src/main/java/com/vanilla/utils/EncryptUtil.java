package com.vanilla.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @description: encrypt util
 * @author: vae1970
 * @create: 2020-05-06 22:51
 **/
public class EncryptUtil {

    /**
     * md5 encode
     *
     * @param source source
     * @return md5
     * @throws NoSuchAlgorithmException exception
     */
    public static String encodeMd5(Object... source) throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();
        for (Object o : source) {
            sb.append(o.toString());
        }
        return encodeMd5(sb.toString());
    }

    /**
     * md5 encode
     *
     * @param source source
     * @return md5
     * @throws NoSuchAlgorithmException exception
     */
    public static String encodeMd5(String source) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(source.getBytes());
        return new BigInteger(1, md.digest()).toString(16);
    }

}
