package com.wbl.aop;

import com.wbl.modal.Enum.Activity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with Simple_love
 * Date: 2016/5/3.
 * Time: 20:32
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ProvAnnotation {
        Activity value();
}
