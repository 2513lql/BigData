package com.wbl.aop;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Created with Simple_love
 * Date: 2016/5/4.
 * Time: 9:58
 */
public class AopPointCut {
        @Pointcut("@annotation(com.wbl.aop.ProvAnnotation)")
        public void operate(){}

        @Pointcut("execution(* com.wbl.service.*.*(..)) || execution(* com.bigdata.service.*.*(..))")
        public static void setDataSource(){}
        //stone
        @Pointcut("@annotation(com.wbl.aop.ProvActivity)")
        public void recordProv(){}
}
