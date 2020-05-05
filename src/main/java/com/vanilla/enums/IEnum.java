package com.vanilla.enums;

/**
 * @description: interface enum
 * @author: vae1970
 * @create: 2020-05-05 22:13
 **/
public interface IEnum<C> {

    /**
     * get code
     *
     * @return code
     */
    C getCode();

    /**
     * get desc
     *
     * @return desc
     */
    default String getDesc() {
        return getCode().toString();
    }

}
