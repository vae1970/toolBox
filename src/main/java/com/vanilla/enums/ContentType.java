package com.vanilla.enums;

/**
 * @description: Content-Type
 * @author: vae1970
 * @create: 2020-04-24 21:43
 **/
public enum ContentType implements IEnum<String> {

    //  Content-Type
    APPLICATION_JSON("application/json"),
    TEXT_PLAIN_UTF_8("text/plain; charset=UTF-8"),
    ;

    private String code;

    ContentType() {
    }

    ContentType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static ContentType getByCode(String code) {
        for (ContentType item : ContentType.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

}
