package com.pingtiao51.armsmodule.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.di.component.DaggerNewInfoComponent;
import com.pingtiao51.armsmodule.mvp.contract.NewInfoContract;
import com.pingtiao51.armsmodule.mvp.model.entity.qualifier.NewsList;
import com.pingtiao51.armsmodule.mvp.model.entity.response.NewsInfoResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.banner.BannerNewsInterface;
import com.pingtiao51.armsmodule.mvp.model.entity.response.pojospeical.NewsListInterface;
import com.pingtiao51.armsmodule.mvp.presenter.NewInfoPresenter;
import com.pingtiao51.armsmodule.mvp.ui.activity.NewsInfoDetailActivity;
import com.pingtiao51.armsmodule.mvp.ui.adapter.HomeNewsAdapter;
import com.pingtiao51.armsmodule.mvp.ui.helper.BannerHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


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
public class NewInfoFragment extends BaseArmFragment<NewInfoPresenter> implements NewInfoContract.View,BaseQuickAdapter.RequestLoadMoreListener {

    public static NewInfoFragment newInstance() {
        NewInfoFragment fragment = new NewInfoFragment();
        return fragment;
    }


    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.news_refresh_layout)
    SmartRefreshLayout news_refresh_layout;

    @BindView(R.id.news_banner)
    ConvenientBanner news_banner;

    @BindView(R.id.news_recycler_view)
    RecyclerView news_recycler_view;

    @NewsList
    @Inject
    List<NewsListInterface> mDatas;

    @Inject
    HomeNewsAdapter mHomeNewsAdapter;

    @BindView(R.id.no_news_layout)
    LinearLayout no_news_layout;

    @BindView(R.id.news_layout)
    LinearLayout news_layout;


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
        initRefresh();
//        initBanner();
        initRecyclerView();
    }

    @Override
    public void setData(@Nullable Object data) {

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
//            refreshLayout.autoRefresh();
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
                        refreshReset();
                        refresh();
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

        news_refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //重新加载 刷新操作
//                        progressWebView.loadUrl();
                        refreshReset();
                        refresh();
                    }
                }, 200);

                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        news_refresh_layout.finishRefresh();
                    }
                }, 4000);
            }
        });

        //触发自动刷新
        refreshLayout.autoRefresh();
    }

    private boolean noBanner = false;
    @Override
    public void onSucBanner(NewsInfoResponse rep) {
         bannerRefresh = true;
        refreshOk();
          noBanner = false;
          mBanners.clear();
        if(rep != null && rep.getList() != null && rep.getList().size() > 0){
            mBanners.addAll(rep.getList());
            BannerHelper.initNewsBanner(news_banner,mBanners);
        }else{
            noBanner = true;
            noDataPageShow();
        }
        mHomeNewsAdapter.setEnableLoadMore(true);
    }
    private void refreshOk(){
        if(newsRefresh && bannerRefresh){
            refreshLayout.finishRefresh();
            news_refresh_layout.finishRefresh();
        }
    }

    private boolean noNewsList = false;
    @Override
    public void onSucRefreshNewsList(NewsInfoResponse rep) {
        newsRefresh = true;
        refreshOk();
        noNewsList = false;
        mDatas.clear();
        if(rep != null && rep.getList() != null && rep.getList().size() > 0){
            mDatas.addAll(rep.getList());
            mHomeNewsAdapter.notifyDataSetChanged();
        }else{
            noNewsList = true;
            noDataPageShow();
        }
        mHomeNewsAdapter.setEnableLoadMore(true);
    }

    /**
     * 是否展示没有数据界面
     */
    private void noDataPageShow(){
        if(noBanner && noNewsList){
            no_news_layout.setVisibility(View.VISIBLE);
            news_layout.setVisibility(View.GONE);
        }else{
            no_news_layout.setVisibility(View.GONE);
            news_layout.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onSucLoadMore(NewsInfoResponse rep) {
        if(rep != null && rep.getList() != null && rep.getList().size() > 0){
            List<NewsInfoResponse.ListBean> list  = (List<NewsInfoResponse.ListBean>) rep.getList();
            if(list.size() >= SIZE){
                mDatas.addAll(list);
                mHomeNewsAdapter.loadMoreComplete();
            }else{
                mDatas.addAll(list);
                mHomeNewsAdapter.loadMoreEnd();
            }
        }else{
            mHomeNewsAdapter.loadMoreEnd();
        }
        loadMoreFinish();

    }


    List<BannerNewsInterface> mBanners = new ArrayList<>();



    private void initRecyclerView() {

        news_recycler_view.setAdapter(mHomeNewsAdapter);
        news_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        mHomeNewsAdapter.setOnLoadMoreListener(this,news_recycler_view);
        mHomeNewsAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mHomeNewsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //进入咨询详情
                NewsListInterface bean = (NewsListInterface) adapter.getData().get(position);
                int id = bean.getId();
                Bundle bundle = new Bundle();
                bundle.putInt(NewsInfoDetailActivity.DETAIL_ID,id);
                startActBundle(bundle,NewsInfoDetailActivity.class);
            }
        });
    }


    @Override
    public void onLoadMoreRequested() {
        refreshLayout.setEnabled(false);
        news_refresh_layout.setEnabled(false);
        //加载更多
        loadmoreReq();
    }

    //加载结束后
    private void loadMoreFinish(){
        mHomeNewsAdapter.notifyDataSetChanged();
        refreshLayout.setEnabled(true);
        news_refresh_layout.setEnabled(true);
    }

    private final static int SIZE = 10;
    private int page = 1;

    private boolean bannerRefresh = false;
    private boolean newsRefresh  = false;

    private void refreshReset(){
        page = 1;
        bannerRefresh = false;
        newsRefresh = false;
    }

    private void refresh(){
        mHomeNewsAdapter.setEnableLoadMore(false);
        mPresenter.refreshNews("OTHER",page,SIZE);
        getBanner();
    }

    private void getBanner(){
        mPresenter.getBanner("OTHER",page,SIZE);
    }


    private void loadmoreReq(){
        mPresenter.loadMoreNews("OTHER",page,SIZE);
    }

}
