package com.catherine.latte_core.wechat;

import android.app.Activity;

import com.catherine.latte_core.app.ConfigKeys;
import com.catherine.latte_core.app.Latte;
import com.catherine.latte_core.wechat.callbacks.IWeChatSignInCallback;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class LatteWeChat {
    static final String APP_ID = Latte.getConfiguration(ConfigKeys.WE_CHAT_APP_ID);
    static final String APP_SECRET = Latte.getConfiguration(ConfigKeys.WE_CHAT_APP_SECRET);
    private final IWXAPI WXAPI;
    private IWeChatSignInCallback mSignInCallback;

    private static final class Holder{
        private static LatteWeChat INSTANCE = new LatteWeChat();
    }

    public static final LatteWeChat getInstance() {
        return Holder.INSTANCE;
    }

    private LatteWeChat() {
        final Activity activity = Latte.getConfiguration(ConfigKeys.ACTIVITY);
        WXAPI = WXAPIFactory.createWXAPI(activity, APP_ID, true);//这里已经注册过了，所以校验为true
        WXAPI.registerApp(APP_SECRET);
    }

    public final IWXAPI getWXAPI() {
        return WXAPI;
    }

    public final void signIn() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "random_state";
        WXAPI.sendReq(req);
    }

    public IWeChatSignInCallback getSignInCallback() {
        return mSignInCallback;
    }

    public LatteWeChat onSignInSuccess(IWeChatSignInCallback signInCallback) {
        this.mSignInCallback = signInCallback;
        return this;
    }
}
