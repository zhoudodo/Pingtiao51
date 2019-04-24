package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.di.component.DaggerShimingrenzhengComponent;
import com.pingtiao51.armsmodule.mvp.contract.ShimingrenzhengContract;
import com.pingtiao51.armsmodule.mvp.presenter.ShimingrenzhengPresenter;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.InputLoginView;
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst;
import com.pingtiao51.armsmodule.mvp.ui.helper.YzmRequest;
import com.pingtiao51.armsmodule.mvp.ui.helper.others.IdCardUtils;
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference;
import com.zls.baselib.custom.view.dialog.DialogHintNormal;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/20/2019 14:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ShimingrenzhengActivity extends BaseArmsActivity<ShimingrenzhengPresenter> implements ShimingrenzhengContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerShimingrenzhengComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_shimingrenzheng; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @BindView(R.id.shimingrenzheng_shoujihao_edit)
    EditText shimingrenzheng_shoujihao_edit;
    @BindView(R.id.shimingrenzheng_xingming_edit)
    EditText shimingrenzheng_xingming_edit;
    @BindView(R.id.shimingrenzheng_shenfenzhenghao_edit)
    EditText shimingrenzheng_shenfenzhenghao_edit;

    @BindView(R.id.cancel1)
    ImageView cancel1;
    @BindView(R.id.cancel2)
    ImageView cancel2;
    @BindView(R.id.cancel3)
    ImageView cancel3;

    @BindView(R.id.yzm)
    EditText yzm;

    @BindView(R.id.get_yzm)
    TextView get_yzm;

    @OnClick({R.id.shimingrenzheng_commit_btn, R.id.cancel1, R.id.cancel2, R.id.cancel3, R.id.get_yzm})
    public void onPageClick(View v) {
        switch (v.getId()) {
            case R.id.shimingrenzheng_commit_btn:
                if (checkCanCommit()) {
                    //TODO 实名认证成功
                    commitShimingrenzheng();
                }
                break;
            case R.id.cancel1:
//                shimingrenzheng_shoujihao_edit.setText("");
                break;
            case R.id.cancel2:
                shimingrenzheng_xingming_edit.setText("");
                break;
            case R.id.cancel3:
                shimingrenzheng_shenfenzhenghao_edit.setText("");
                break;
            case R.id.get_yzm:
                mYzmRequest.getPhoneYzm();
                break;
        }
    }

    DialogHintNormal mDialogHintNormal;

    private void showHintDialog() {
        if (mDialogHintNormal == null) {
            mDialogHintNormal = new DialogHintNormal.HintBuilder()
                    .setTitle("提示")
                    .setContent("检测到您未满18周岁，\n 无法通过实名认证")
                    .setContext(this)
                    .setBtn2Content("返回首页")
                    .setOnClickListener(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDialogHintNormal.dismiss();
                            //TODO 返回首页
                        }
                    }).build();
        }
        mDialogHintNormal.show();

    }

    /**
     * 提交实名认证
     */
    private void commitShimingrenzheng() {
        mPresenter.getCarrierAuth(
                shimingrenzheng_shenfenzhenghao_edit.getText().toString(),
                shimingrenzheng_xingming_edit.getText().toString(),
                shimingrenzheng_shoujihao_edit.getText().toString(),
                yzm.getText().toString()
        );
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initEdits();
        initYzm();
    }

    private YzmRequest mYzmRequest;

    private void initYzm() {
        if (mYzmRequest == null) {
            mYzmRequest = new YzmRequest(get_yzm, shimingrenzheng_shoujihao_edit);
            mYzmRequest.setmYzmReqListener(new YzmRequest.YzmReqListener() {
                @Override
                public void getYzmCode() {
                    //获取验证码
                    mPresenter.sendCode(shimingrenzheng_shoujihao_edit.getText().toString().trim(), InputLoginView.CodeType.IDENTITY_AUTH.getType());
                }
            });
        }
    }

    private boolean checkCanCommit() {
        if (TextUtils.isEmpty(shimingrenzheng_shoujihao_edit.getText().toString())) {
            ArmsUtils.snackbarText("请填写手机号码");
            return false;
        }
        if (TextUtils.isEmpty(shimingrenzheng_xingming_edit.getText().toString())) {
            ArmsUtils.snackbarText("请填写姓名");
            return false;
        }
        if (TextUtils.isEmpty(shimingrenzheng_shenfenzhenghao_edit.getText().toString())) {
            ArmsUtils.snackbarText("请填写身份证号");
            return false;
        }
        if (!IdCardUtils.personIdValidation(shimingrenzheng_shenfenzhenghao_edit.getText().toString())) {
            ArmsUtils.snackbarText("请填写正确的身份证号");
            return false;
        }
//        if (TextUtils.isEmpty(yzm.getText().toString())) {
//            ArmsUtils.snackbarText("请输入验证码");
//            return false;
//        }
        return true;
    }

    private void initEdits() {
        shimingrenzheng_shoujihao_edit.setText(SavePreference.getStr(this,PingtiaoConst.USER_PHONE));
        shimingrenzheng_xingming_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if (s1.length() > 0) {
                    cancel2.setVisibility(View.VISIBLE);
                } else {
                    cancel2.setVisibility(View.GONE);
                }
            }
        });
        shimingrenzheng_shenfenzhenghao_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if (s1.length() > 0) {
                    cancel3.setVisibility(View.VISIBLE);
                } else {
                    cancel3.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void sucSendCode() {
        //短信验证码发送成功
        ArmsUtils.snackbarText("短信验证码发送成功");
    }

    @Override
    public void sucCarrierAuth() {
        ArmsUtils.snackbarText("实名认证成功");
        shimingrenzheng_shenfenzhenghao_edit.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 500
        );

    }
}
