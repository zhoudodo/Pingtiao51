package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.pingtiao51.armsmodule.di.component.DaggerZhizhiMobanComponent;
import com.pingtiao51.armsmodule.mvp.contract.ZhizhiMobanContract;
import com.pingtiao51.armsmodule.mvp.presenter.ZhizhiMobanPresenter;

import com.pingtiao51.armsmodule.R;


import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/20/2019 18:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

/**
 * 纸质借条 模板 纸质收条模板
 */
public class ZhizhiMobanActivity extends BaseArmsActivity<ZhizhiMobanPresenter> implements ZhizhiMobanContract.View {
    public static String TAG = ZhizhiMobanActivity.class.getSimpleName();
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerZhizhiMobanComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    public final static int ZHIZHI_JIETIAO = 0;
    public final static int ZHIZHI_SHOUTIAO = 1;
    private int mType = ZHIZHI_JIETIAO;

    private void initConfig(){
       mType = getIntent().getIntExtra(TAG,ZHIZHI_JIETIAO);
    }
    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        int resultId = R.layout.activity_zhizhi_moban;
        switch (mType){
            case ZHIZHI_JIETIAO:
                resultId = R.layout.activity_zhizhi_moban;
                break;
            case ZHIZHI_SHOUTIAO:
                resultId = R.layout.activity_zhizhi_moban_shoutiao;
                break;
        }

        return resultId; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        switch (mType){
            case ZHIZHI_JIETIAO:
                setTitle("纸质借条");
                break;
            case ZHIZHI_SHOUTIAO:
                setTitle("纸质收条");
                break;
        }
    }

    @OnClick({R.id.moban_img})
    public void onPageClick(View v){
        switch (v.getId()){
            case R.id.moban_img:
                Bundle bundle = new Bundle();
                bundle.putInt(ZhizhiMobanActivity.TAG,mType);
                startActBundle(bundle,ZhizhiMobanYulantuActivity.class);
                break;
        }
    }


}
