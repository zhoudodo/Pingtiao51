package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.pingtiao51.armsmodule.di.component.DaggerMyBankCardsComponent;
import com.pingtiao51.armsmodule.mvp.contract.MyBankCardsContract;
import com.pingtiao51.armsmodule.mvp.presenter.MyBankCardsPresenter;

import com.pingtiao51.armsmodule.R;


import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/30/2019 13:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MyBankCardsActivity extends BaseArmsActivity<MyBankCardsPresenter> implements MyBankCardsContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyBankCardsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_bank_cards; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
            initTitle();
    }
    private void initTitle(){
        setTitle("我的银行卡");
    }

    @OnClick({R.id.add_bank_layout})
    public void onPageClick(View v){
        switch (v.getId()){
            case R.id.add_bank_layout:
                //添加银行卡

                break;
        }
    }
}
