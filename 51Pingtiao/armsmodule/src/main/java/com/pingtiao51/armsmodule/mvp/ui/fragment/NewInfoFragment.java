package com.pingtiao51.armsmodule.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.pingtiao51.armsmodule.di.component.DaggerNewInfoComponent;
import com.pingtiao51.armsmodule.mvp.contract.NewInfoContract;
import com.pingtiao51.armsmodule.mvp.presenter.NewInfoPresenter;

import com.pingtiao51.armsmodule.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zls.baselib.custom.view.webview.ProgressWebView;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/20/2019 17:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class NewInfoFragment extends BaseArmFragment<NewInfoPresenter> implements NewInfoContract.View {

    public static NewInfoFragment newInstance() {
        NewInfoFragment fragment = new NewInfoFragment();
        return fragment;
    }

    @BindView(R.id.progress_webview)
    ProgressWebView progressWebView;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerNewInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_info, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initWebview();
        initRefresh();
    }

    @Override
    public void setData(@Nullable Object data) {

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden){
            refreshLayout.autoRefresh();
        }
    }



    private void initRefresh() {
//        mRefreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //重新加载 刷新操作
//                        progressWebView.loadUrl();
                    }
                }, 200);

                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefresh();
                    }
                }, 4000);
            }
        });
        //触发自动刷新
        refreshLayout.autoRefresh();
    }

    private void initWebview(){
        progressWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefresh();
                    }
                });
            }
        });
    }


}
