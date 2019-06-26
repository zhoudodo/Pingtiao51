package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.pingtiao51.armsmodule.di.component.DaggerNewsInfoDetailComponent;
import com.pingtiao51.armsmodule.mvp.contract.NewsInfoDetailContract;
import com.pingtiao51.armsmodule.mvp.model.entity.response.NewsDetailInfoResponse;
import com.pingtiao51.armsmodule.mvp.presenter.NewsInfoDetailPresenter;

import com.pingtiao51.armsmodule.R;
import com.zls.baselib.custom.view.webview.ProgressWebView;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/24/2019 14:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class NewsInfoDetailActivity extends BaseArmsActivity<NewsInfoDetailPresenter> implements NewsInfoDetailContract.View {
    public final static String DETAIL_ID = "ID";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNewsInfoDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_news_info_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @BindView(R.id.detail_title)
    TextView detail_title;
    @BindView(R.id.detail_category)
    TextView detail_category;
    @BindView(R.id.pwb)
    ProgressWebView progressWebView;

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("资迅详情");
        int id = getIntent().getIntExtra(DETAIL_ID, 0);
        mPresenter.getNewsDetail(id);
    }


    @Override
    public void onNewsDetailSuc(NewsDetailInfoResponse response) {
        detail_title.setText(response.getTitle());
        detail_category.setText(response.getCategory());
    }
}
