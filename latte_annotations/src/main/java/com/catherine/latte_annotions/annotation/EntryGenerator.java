package com.catherine.latte_annotions.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)//用在类上
@Retention(RetentionPolicy.SOURCE)//源码阶段处理，打包成apk就不再用她了，对性能几乎没有影响
public @interface EntryGenerator {
    String packageName();

    Class<?> entryTemplate();
}
