package com.catherine.latte_core.delegate.web.event;

import com.catherine.latte_core.util.log.LatteLogger;

public class UndefinedEvent extends Event{
    @Override
    public String execute(String params) {
        LatteLogger.e("UndefinedEvent",params);
        return null;
    }
}
