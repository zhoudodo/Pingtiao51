package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.pingtiao51.armsmodule.di.component.DaggerSettingComponent;
import com.pingtiao51.armsmodule.mvp.contract.SettingContract;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.ExitAppTag;
import com.pingtiao51.armsmodule.mvp.presenter.SettingPresenter;

import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.InputLoginView;


import org.greenrobot.eventbus.EventBus;

import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/13/2019 17:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SettingActivity extends BaseArmsActivity<SettingPresenter> implements SettingContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSettingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_setting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
            setTitle("设置");
    }

    @OnClick({R.id.xiugaidenglumima,R.id.xiugaiqianyuemima,R.id.exit_app})
    public void onPageClick(View view){
        switch (view.getId()){
            case R.id.xiugaidenglumima:
                //TODO 修改登录密码
                Intent intent = new Intent(this,LoginActivity.class);
                intent.putExtra(LoginActivity.LOGIN_MODE,InputLoginView.CODE_LOGIN);
                startActivity(intent);
                break;

            case R.id.xiugaiqianyuemima:
                //TODO 修改签约密码
//                Intent intent = new Intent(this,LoginActivity.class);
//                intent.putExtra(LoginActivity.LOGIN_MODE,InputLoginView.CODE_LOGIN);
//                startActivity(intent);
                break;

            case R.id.exit_app:
                Bundle bundle = new Bundle();
                bundle.putInt(LoginActivity.LOGIN_MODE, InputLoginView.CODE_LOGIN);
                ActivityUtils.startActivity(bundle, LoginActivity.class);
                EventBus.getDefault().post(new ExitAppTag());
                finish();
                //TODO 退出登录app
                break;
        }
    }

}
