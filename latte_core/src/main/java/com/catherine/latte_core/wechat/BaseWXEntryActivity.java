package com.catherine.latte_core.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.catherine.latte_core.net.RestClient;
import com.catherine.latte_core.net.callback.IError;
import com.catherine.latte_core.net.callback.IFailure;
import com.catherine.latte_core.net.callback.ISuccess;
import com.catherine.latte_core.util.log.LatteLogger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;


public abstract class BaseWXEntryActivity extends BaseWXActivity {
    //用户登录成功后回调
    protected abstract void onSignInSuccess(String userInfo);

    //微信发送请求到第三方应用的回调
    @Override
    public void onReq(BaseReq baseReq) {

    }

    //https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
    //第三方应用发送请求到微信的回调
    @Override
    public void onResp(BaseResp baseResp) {
        final String code = ((SendAuth.Resp) baseResp).code;
        final StringBuilder authURL = new StringBuilder();
        authURL.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(LatteWeChat.APP_ID)
                .append("&secret=")
                .append(LatteWeChat.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        LatteLogger.d("authURL", authURL.toString());
        getAuth(authURL.toString());
    }

    private void getAuth(final String authURL) {
        RestClient.builder().url(authURL)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject jsonObject = JSON.parseObject(response);
                        String accessToken = jsonObject.getString("access_token");
                        String openId = jsonObject.getString("openid");
                        final StringBuilder userInfoUrl = new StringBuilder();
                        userInfoUrl
                                .append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                                .append(accessToken)
                                .append("&openid=")
                                .append(openId)
                                .append("&lang=")
                                .append("zh_CN");

                        LatteLogger.d("userInfoUrl", userInfoUrl.toString());
                        getUserInfo(userInfoUrl.toString());
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        LatteLogger.d("getAuth fail");
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        LatteLogger.d("getAuth error：" + code + msg);
                    }
                })
                .build().get();
    }

    private void getUserInfo(String userInfoUrl) {
        RestClient
                .builder()
                .url(userInfoUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        onSignInSuccess(response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();
    }
}
