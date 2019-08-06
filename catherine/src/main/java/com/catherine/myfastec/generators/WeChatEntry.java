package com.catherine.myfastec.generators;

import com.catherine.latte_annotions.annotation.EntryGenerator;
import com.catherine.latte_core.wechat.templates.WXEntryTemplate;

@EntryGenerator(
        packageName = "com.catherine.myfastec",
        entryTemplate = WXEntryTemplate.class
)
public interface WeChatEntry {
}
