package com.catherine.latte_core.app;

import android.content.Context;

public final class Latte {
    public static Configurator init(Context context) {
        Configurator.getInstance().getLatteConfigs().put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return Configurator.getInstance().getConfiguration(key);
    }

    public static Context getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

}
