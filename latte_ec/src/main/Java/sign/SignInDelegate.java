package sign;

import android.app.Activity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.catherine.latte.ec.R;
import com.catherine.latte.ec.R2;
import com.catherine.latte_core.delegate.LatteDelegate;
import com.catherine.latte_core.net.RestClient;
import com.catherine.latte_core.net.callback.ISuccess;
import com.catherine.latte_core.util.log.LatteLogger;
import com.catherine.latte_core.wechat.LatteWeChat;
import com.catherine.latte_core.wechat.callbacks.IWeChatSignInCallback;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.OnClick;


public class SignInDelegate extends LatteDelegate {


    @BindView(R2.id.edit_sign_in_mail)
    TextInputEditText mMail = null;


    @BindView(R2.id.edit_sign_in_psd)
    TextInputEditText mPsd = null;



    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;

        }
    }

    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn() {
        if (checkForm()) {
            RestClient.builder().url("https://www.easy-mock.com/mock/5d423b5993d2407e9ef5afc7/user_profile")
                    .params("mail",mMail.getText().toString())
                    .params("password",mPsd.getText().toString())
                    .success(new ISuccess() {
                @Override
                public void onSuccess(String response) {
                    LatteLogger.json("USER_PROFILE",response);
                    SignHandler.onSignIn(response,mISignListener);
                }
            }).build().post();
            Toast.makeText(getContext(), "验证通过", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R2.id.img_sign_in_wechat)
    void onClickSignInWeChat() {
        LatteWeChat.getInstance().onSignInSuccess(new IWeChatSignInCallback() {
            @Override
            public void onSignInSuccess(String userInfo) {

            }
        }).signIn();
    }

    @OnClick(R2.id.tv_link_sign_in)
    void onClickLink() {
        start(new SignUpDelegate());
    }

    private boolean checkForm() {
        final String email = mMail.getText().toString();
        final String password = mPsd.getText().toString();
        boolean isPass = true;
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mMail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mMail.setError(null);
        }
        if (password.isEmpty() || password.length() < 6) {
            mPsd.setError("请至少写6位数密码");
            isPass = false;
        } else {
            mPsd.setError(null);
        }
        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
