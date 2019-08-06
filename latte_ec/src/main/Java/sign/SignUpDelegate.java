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
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.OnClick;


public class SignUpDelegate extends LatteDelegate {

    @BindView(R2.id.edit_sign_up_name)
    TextInputEditText mName = null;

    @BindView(R2.id.edit_sign_up_mail)
    TextInputEditText mMail = null;

    @BindView(R2.id.edit_sign_up_phone)
    TextInputEditText mPhone = null;

    @BindView(R2.id.edit_sign_up_psd)
    TextInputEditText mPsd = null;

    @BindView(R2.id.edit_sign_up_re_psd)
    TextInputEditText mRePsd = null;

    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;

        }
    }

    @OnClick(R2.id.btn_sign_up)
    void onClickSignUp() {
        if (checkForm()) {
            RestClient.builder().url("https://www.easy-mock.com/mock/5d423b5993d2407e9ef5afc7/user_profile")
                    .params("name",mName.getText().toString())
                    .params("mail",mMail.getText().toString())
                    .params("phone",mPhone.getText().toString())
                    .params("password",mPsd.getText().toString())
                    .success(new ISuccess() {
                @Override
                public void onSuccess(String response) {
                    LatteLogger.json("USER_PROFILE",response);
                    SignHandler.onSignUp(response,mISignListener);
                }
            }).build().post();
            Toast.makeText(getContext(), "验证通过", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R2.id.tv_link_sign_in)
    void onClickLink() {
        start(new SignInDelegate());
    }

    private boolean checkForm() {
        final String name = mName.getText().toString();
        final String email = mMail.getText().toString();
        final String phone = mPhone.getText().toString();
        final String password = mPsd.getText().toString();
        final String rePassword = mRePsd.getText().toString();
        boolean isPass = true;
        if (name.isEmpty()) {
            mName.setError("请输入姓名");
            isPass = false;
        } else {
            mName.setError(null);
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mMail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mMail.setError(null);
        }
        if (phone.isEmpty() || phone.length() != 11) {
            mPhone.setError("手机号码错误");
            isPass = false;
        } else {
            mPhone.setError(null);
        }
        if (password.isEmpty() || password.length() < 6) {
            mPsd.setError("请至少写6位数密码");
            isPass = false;
        } else {
            mPsd.setError(null);
        }
        if (rePassword.isEmpty() || rePassword.length() < 6 || !(rePassword.equals(password))) {
            mRePsd.setError("密码验证错误");
            isPass = false;
        } else {
            mRePsd.setError(null);
        }
        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
