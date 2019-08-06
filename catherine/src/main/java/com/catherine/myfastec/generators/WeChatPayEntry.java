package com.catherine.myfastec.generators;

import com.catherine.latte_annotions.annotation.PayEntryGenerator;
import com.catherine.latte_core.wechat.templates.WXPayEntryTemplate;

@PayEntryGenerator(
        packageName = "com.catherine.myfastec",
        payEntryTemplate = WXPayEntryTemplate.class
)
public interface WeChatPayEntry {
}
