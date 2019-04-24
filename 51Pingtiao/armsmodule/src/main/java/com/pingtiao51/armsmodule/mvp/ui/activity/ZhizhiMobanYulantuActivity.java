package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.pingtiao51.armsmodule.di.component.DaggerZhizhiMobanYulantuComponent;
import com.pingtiao51.armsmodule.mvp.contract.ZhizhiMobanYulantuContract;
import com.pingtiao51.armsmodule.mvp.presenter.ZhizhiMobanYulantuPresenter;

import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.ui.helper.GlideProxyHelper;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/21/2019 10:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ZhizhiMobanYulantuActivity extends BaseActivity<ZhizhiMobanYulantuPresenter> implements ZhizhiMobanYulantuContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerZhizhiMobanYulantuComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_zhizhi_moban_yulantu; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @BindView(R.id.moban_yulan_img)
    ImageView moban_yulan_img;
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
          Bundle bundle =  getIntent().getExtras();
          int type = bundle.getInt(ZhizhiMobanActivity.TAG,ZhizhiMobanActivity.ZHIZHI_JIETIAO);
          switch (type){
              case  ZhizhiMobanActivity.ZHIZHI_JIETIAO:
                  //TODO 添加纸质借条预览图
                  GlideProxyHelper.loadImgForRes(moban_yulan_img,R.drawable.zhizhijietiao_yulan_tu);
                  break;
              case ZhizhiMobanActivity.ZHIZHI_SHOUTIAO:
                  GlideProxyHelper.loadImgForRes(moban_yulan_img,R.drawable.zhizhishoutiao_yulan_tu);
                  break;
          }

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}
