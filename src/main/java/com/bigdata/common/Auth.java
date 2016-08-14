package com.bigdata.common;

import java.lang.annotation.*;

/**
 * Created by stone on 2016/5/16.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
    boolean validate() default false;
}
