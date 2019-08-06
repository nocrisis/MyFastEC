package com.catherine.myfastec.generators;

import com.catherine.latte_annotions.annotation.AppRegisterGenerator;
import com.catherine.latte_core.wechat.templates.AppRegisterTemplate;

@AppRegisterGenerator(
        packageName = "com.catherine.myfastec",
        registerTemplate = AppRegisterTemplate.class
)
public interface AppRegister {
}
