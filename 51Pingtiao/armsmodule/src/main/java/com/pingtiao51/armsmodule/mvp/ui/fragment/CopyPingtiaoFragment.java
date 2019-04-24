package com.pingtiao51.armsmodule.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.di.component.DaggerCopyPingtiaoComponent;
import com.pingtiao51.armsmodule.mvp.contract.CopyPingtiaoContract;
import com.pingtiao51.armsmodule.mvp.model.api.Api;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.ExitAppTag;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.HomeScrollMessageTag;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.LoginEventTag;
import com.pingtiao51.armsmodule.mvp.model.entity.response.CopyBanner;
import com.pingtiao51.armsmodule.mvp.model.entity.response.HomeMessageScrollResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingTiaoSeachResponse;
import com.pingtiao51.armsmodule.mvp.presenter.CopyPingtiaoPresenter;
import com.pingtiao51.armsmodule.mvp.ui.activity.BaseWebViewActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.CreateJietiaoActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.DianziJietiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.DianziShoutiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.MyPingtiaoActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.SecureCopyActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.WebViewActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiJietiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiShoutiaoAnliMobanActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiShoutiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.adapter.HomeMultiAdapter;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.CtsScrollTextView;
import com.pingtiao51.armsmodule.mvp.ui.helper.BannerHelper;
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst;
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.pingtiao51.armsmodule.mvp.ui.activity.DianziJietiaoXiangqingActivity.PING_TIAO_XIANG_QING;


public class CopyPingtiaoFragment extends BaseArmFragment<CopyPingtiaoPresenter> implements CopyPingtiaoContract.View {

    public static CopyPingtiaoFragment newInstance() {
        CopyPingtiaoFragment fragment = new CopyPingtiaoFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCopyPingtiaoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_copy_pingtiao, container, false);
    }

    @BindView(R.id.input_name)
    TextView input_name;
    @BindView(R.id.banner)
    ConvenientBanner mBanner;
    @BindView(R.id.message_content)
    CtsScrollTextView message_content;
    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.woyaoxiepingtiao_beifen)
    ImageView woyaoxiepingtiao_beifen;

    @OnClick({R.id.woyaoxiepingtiao_beifen, R.id.more_layout, R.id.click_more})
    public void onPageClick(View v) {
        switch (v.getId()) {
            case R.id.woyaoxiepingtiao_beifen:
                //我要备份凭条
                if(TextUtils.isEmpty(SavePreference.getStr(getActivity(),PingtiaoConst.KEY_TOKEN))){
                    tokenReq();
                }else {
                    startAct(SecureCopyActivity.class);
                }
                break;
            case R.id.more_layout:
                //更多页面

                if(getResources().getString(R.string.newplayerhelper).equals(more_tv.getText().toString())){
                    //TODO 新手帮助
                    Bundle bundle1 = new Bundle();
                    bundle1.putString(BaseWebViewActivity.WEBVIEW_TITLE, "帮助与反馈");
                    bundle1.putString(BaseWebViewActivity.WEBVIEW_URL, Api.BASE_H5_URL + Api.CONTACT);
                    startActBundle(bundle1, WebViewActivity.class);

                }else {
                    Bundle bundle = new Bundle();
                    bundle.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.ZHI_ZHI);
                    startActBundle(bundle, MyPingtiaoActivity.class);
                }
                break;
            case R.id.click_more:
                Bundle bundle1 = new Bundle();
                bundle1.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.ZHI_ZHI);
                startActBundle(bundle1, MyPingtiaoActivity.class);
                break;
        }

    }

    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;

    private void initRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initHomeData();
                    }
                }, 200);

                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.finishRefresh();
                    }
                }, 4000);
            }
        });
        //触发自动刷新
//        mRefreshLayout.autoRefresh();

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        woyaoxiepingtiao_beifen.setImageDrawable(getResources().getDrawable(R.drawable.anniu_copy));
        input_name.setText(getResources().getString(R.string.zhizhipingtiao));
        initBanner();//banner
//        initHomeData();
        initRv();
        initRefresh();
    }


    private void initBanner() {
        ArrayList banners = new ArrayList();
        banners.add(new CopyBanner(R.drawable.banner_beifen));
        BannerHelper.initBanner(mBanner, banners);
    }


  /*  @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initHomeData();
        }
    }*/


    @BindView(R.id.click_more)
    TextView click_more;
    @Override
    public void sucRv(List<PingTiaoSeachResponse> datas) {
        if (datas != null && datas.size() > 0) {
            input_name.setText("纸质凭条");
            mDatas.clear();
            mDatas.addAll(datas);
            if (mDatas.size() >= 5) {
                click_more.setVisibility(View.VISIBLE);
            } else {
                click_more.setVisibility(View.GONE);
            }
            more_tv.setText("全部");
        } else {
            getAnliData();
            more_tv.setText(getResources().getString(R.string.newplayerhelper));
        }
        mHomeMultiAdapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh();
    }

    private void getAnliData() {
        PingTiaoSeachResponse repJie = new PingTiaoSeachResponse();
        repJie.setAmount("2000");
        repJie.setTotalAmount("2000");
        repJie.setType("PAPER_OWE_NOTE");//纸质借条
        repJie.setLender("李四");//出借人
        repJie.setBorrower("张三");//借款人
        repJie.setOverDueDays("0");//逾期天数
        repJie.setRepaymentDate("2019-01-31");//还款时间
        repJie.setBorrowAndLendState("0");//待还
        repJie.setHasAnli(true);

        PingTiaoSeachResponse repShou = new PingTiaoSeachResponse();
        repShou.setAmount("3000");
        repShou.setTotalAmount("3000");
        repShou.setLender("李四");
        repShou.setType("PAPER_RECEIPT_NOTE");//纸质收条
        repShou.setRepaymentDate("2019-01-01");
        repShou.setHasAnli(true);

        mDatas.clear();
        mDatas.add(repJie);
        mDatas.add(repShou);
        input_name.setText("纸质凭条案例");
    }


    HomeMultiAdapter mHomeMultiAdapter;
    List<PingTiaoSeachResponse> mDatas = new ArrayList<>();

    @BindView(R.id.more_tv)
    TextView more_tv;
    private void initRv() {
        getAnliData();
        more_tv.setText(getResources().getString(R.string.newplayerhelper));
        mHomeMultiAdapter = new HomeMultiAdapter(mDatas);
        rv.setAdapter(mHomeMultiAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHomeMultiAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        mHomeMultiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PingTiaoSeachResponse rep = (PingTiaoSeachResponse) adapter.getData().get(position);
                String hintName = (String) input_name.getText();
                if ("纸质凭条案例".equals(hintName)) {
                    Bundle bundle1 = new Bundle();
                    switch (rep.getType()) {
                        case "PAPER_OWE_NOTE":
                            bundle1.putInt(ZhizhiShoutiaoAnliMobanActivity.TAG, ZhizhiShoutiaoAnliMobanActivity.ZHIZHI_JIETIAO);
                            startActBundle(bundle1, ZhizhiShoutiaoAnliMobanActivity.class);
                            break;
                        case "PAPER_RECEIPT_NOTE":
                            bundle1.putInt(ZhizhiShoutiaoAnliMobanActivity.TAG, ZhizhiShoutiaoAnliMobanActivity.ZHIZHI_SHOUTIAO);
                            startActBundle(bundle1, ZhizhiShoutiaoAnliMobanActivity.class);
                            break;
                        default:
                            break;
                    }

                } else {
                    //凭条类型 OWE_NOTE 电子借条 PAPER_OWE_NOTE 纸质借条 PAPER_RECEIPT_NOTE 纸质收条 RECEIPT_NOTE电子收条
                    Bundle bundle = new Bundle();
                    bundle.putInt(PING_TIAO_XIANG_QING, (int) rep.getId());
                    switch (rep.getType()) {
                        case "OWE_NOTE":
                            startActBundle(bundle, DianziJietiaoXiangqingActivity.class);
                            break;
                        case "PAPER_OWE_NOTE":
                            startActBundle(bundle, ZhizhiJietiaoXiangqingActivity.class);
                            break;
                        case "PAPER_RECEIPT_NOTE":
                            startActBundle(bundle, ZhizhiShoutiaoXiangqingActivity.class);
                            break;
                        case "RECEIPT_NOTE":
                            startActBundle(bundle, DianziShoutiaoXiangqingActivity.class);
                            break;
                        default:
                            break;
                    }
                }

            }

        });
    }

    private void tokenReq(){
        mPresenter.getPingTiaoFiveHistory();
    }

    @Override
    public void onResume() {
        super.onResume();
        initHomeData();
    }

    private void initHomeData() {
        if (mPresenter != null) {
            if(!TextUtils.isEmpty(SavePreference.getStr(getActivity(),PingtiaoConst.KEY_TOKEN))){
                tokenReq();
            }
        }
    }

    /**
     * 接收滚动字幕
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void HomeScrollMessagesXXXX(HomeScrollMessageTag tag) {
        List<HomeMessageScrollResponse> listMsg = tag.getMessages();
        List<String> msgs = new ArrayList<>();
        for (HomeMessageScrollResponse temp : listMsg) {
            msgs.add(temp.getContent());
        }
        message_content.setTextList(msgs);
        message_content.startAutoScroll();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEventTag tag) {
        mRefreshLayout.autoRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void exitApp(ExitAppTag tag){
        getAnliData();
        mHomeMultiAdapter.notifyDataSetChanged();
        click_more.setVisibility(View.GONE);
        more_tv.setText(getResources().getString(R.string.newplayerhelper));
    }
}
