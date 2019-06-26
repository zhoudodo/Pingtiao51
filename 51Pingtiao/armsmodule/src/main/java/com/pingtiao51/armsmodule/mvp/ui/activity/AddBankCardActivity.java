package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.di.component.DaggerAddBankCardComponent;
import com.pingtiao51.armsmodule.mvp.contract.AddBankCardContract;
import com.pingtiao51.armsmodule.mvp.model.api.Api;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.AddBankSucTag;
import com.pingtiao51.armsmodule.mvp.presenter.AddBankCardPresenter;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.BankTextWatcher;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.InputLoginView;
import com.pingtiao51.armsmodule.mvp.ui.helper.EditCheckHelper;
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst;
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/20/2019 17:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

/**
 * 添加银行卡
 */
public class AddBankCardActivity extends BaseArmsActivity<AddBankCardPresenter> implements AddBankCardContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddBankCardComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_add_bank_card; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @BindView(R.id.addbank_mingzi)
    TextView addbank_mingzi;
    @BindView(R.id.addbank_idcard_nums)
    TextView addbank_idcard_nums;
    @BindView(R.id.shuruyinhangkhao)
    EditText shuruyinhangkhao;
    @BindView(R.id.addbank_shoujihao_edit)
    EditText addbank_shoujihao_edit;
    @BindView(R.id.yzm)
    EditText yzm;

    @BindView(R.id.get_yzm)
    TextView mGetYzm;

    @OnClick({R.id.get_yzm, R.id.addbank_commit_btn, R.id.get_support_banks})
    public void onPageClick(View v) {
        switch (v.getId()) {
            case R.id.get_yzm:
                getPhoneYzm();
                break;
            case R.id.addbank_commit_btn:
                List<TextView> tvs = new ArrayList<>();
                tvs.add(shuruyinhangkhao);
                tvs.add(addbank_shoujihao_edit);
                tvs.add(yzm);
                if(checkCanCommit(tvs)){
                    //TODO 提交绑卡信息
                    mPresenter.bindBankCard(
                            shuruyinhangkhao.getText().toString().replaceAll(" ",""),
                            addbank_shoujihao_edit.getText().toString(),
                            yzm.getText().toString()
                    );
                }
                break;
            case R.id.get_support_banks:
                //TODO 跳转至支持银行列表
                Bundle bundle1 = new Bundle();
                bundle1.putString(BaseWebViewActivity.WEBVIEW_TITLE, "支持银行卡");
                bundle1.putString(BaseWebViewActivity.WEBVIEW_URL, Api.BASE_H5_URL + Api.SUPPORT_BANK);
                startActBundle(bundle1, WebViewActivity.class);
                break;
        }
    }

    private boolean checkCanCommit(List<TextView> tvs){
        for(TextView temp : tvs){
            if(TextUtils.isEmpty(temp.getText().toString())) {
                String hints = (String) temp.getHint();
                ArmsUtils.snackbarText(hints);
                return false;
            }
        }
        return true;
    }

    TextWatcher bankTextWatcher;
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("添加银行卡");
        addbank_mingzi.setText(SavePreference.getStr(this,PingtiaoConst.USER_NAME));
        String idcard = SavePreference.getStr(this, PingtiaoConst.USER_ID_CARD);
        addbank_idcard_nums.setText(idcard);
//        bankTextWatcher = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String numbers = s.toString();
//                numbers = numbers.replaceAll(" ","");
//                char[] chars = numbers.toCharArray();
//                StringBuffer sb = new StringBuffer();
//                if(chars.length > 4) {
//                    for (int i = 0; i < chars.length; i++) {
//                        sb.append(chars[i]);
//                        if ((i+1) % 4 == 0) {
//                            sb.append(" ");
//                        }
//                    }
//                }else{
//                    sb.append(s.toString());
//                }
//                String res = sb.toString();
//                if (res.endsWith(" ")) {
//                    res = res.substring(0, res.length() - 1);
//                }
//                shuruyinhangkhao.removeTextChangedListener(bankTextWatcher);
//                shuruyinhangkhao.setText(res);
//                shuruyinhangkhao.addTextChangedListener(bankTextWatcher);
//                shuruyinhangkhao.setSelection(shuruyinhangkhao.getText().toString().length());
//            }
//        };
//        shuruyinhangkhao.addTextChangedListener(bankTextWatcher);
        new BankTextWatcher().bind(shuruyinhangkhao, new int[]{4});
    }

    private AuthCodeTimer authTimer;

    @Override
    public void onSucAddCard() {
        ArmsUtils.snackbarText("绑定银行卡成功");
        EventBus.getDefault().post(new AddBankSucTag());
        Observable.timer(1000,TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        killMyself();
                    }
                }).isDisposed();
    }

    @Override
    public void onSucSendCode() {
        ArmsUtils.snackbarText("验证码已发送");

    }

    /* 定义一个倒计时的内部类 */
    private class AuthCodeTimer extends CountDownTimer {
        private boolean isGetAuth = false; //是否已经发送请求

        public AuthCodeTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


        @Override
        public void onFinish() {
            if (mGetYzm == null) {
                return;
            }
            mGetYzm.setText("重发");
            mGetYzm.setEnabled(true);
            isGetAuth = false;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (mGetYzm == null) {
                return;
            }
            if (!isGetAuth) {
                isGetAuth = true;
                getAuthCode();
            }
            mGetYzm.setText(String.format("重发(%s)", millisUntilFinished / 1000));
            mGetYzm.setEnabled(false);
        }
    }
    private void getPhoneYzm() {
        if (!EditCheckHelper.checkInputPhoneToast(addbank_shoujihao_edit)) {
            return;
        }
        if (authTimer == null) {
            authTimer = new AuthCodeTimer(60000, 1000); // 第一参数是总的时间，第二个是间隔时间
        }
        mGetYzm.setEnabled(false);
        authTimer.start();
    }

    //获取验证码
    public void getAuthCode() {
        mPresenter.sendCode(addbank_shoujihao_edit.getText().toString(),InputLoginView.CodeType.IDENTITY_AUTH.getType());
    }


}
