package com.pingtiao51.armsmodule.mvp.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.component.DaggerNewPingtiaoComponent;
import com.pingtiao51.armsmodule.mvp.contract.NewPingtiaoContract;
import com.pingtiao51.armsmodule.mvp.model.api.Api;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.ExitAppTag;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.HomeScrollMessageTag;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.LoginEventTag;
import com.pingtiao51.armsmodule.mvp.model.entity.response.HomeMessageScrollResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.HomePageComResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingTiaoSeachResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.ShouHuanKuanResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.banner.BannerParentInterface;
import com.pingtiao51.armsmodule.mvp.presenter.NewPingtiaoPresenter;

import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.ui.activity.BaseWebViewActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.CreateJietiaoActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.DianziJietiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.DianziPingtiaoAnliMobanActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.DianziShoutiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.MyPingtiaoActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.WebViewActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiJietiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiShoutiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.adapter.HomeMultiAdapter;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.ChoicePingtiaoTypeDialog;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.CtsScrollTextView;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.MoveView;
import com.pingtiao51.armsmodule.mvp.ui.helper.BannerHelper;
import com.pingtiao51.armsmodule.mvp.ui.helper.ClassUtils;
import com.pingtiao51.armsmodule.mvp.ui.helper.GlideProxyHelper;
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst;
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.pingtiao51.armsmodule.mvp.ui.activity.DianziJietiaoXiangqingActivity.PING_TIAO_XIANG_QING;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/11/2019 16:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class NewPingtiaoFragment extends BaseArmFragment<NewPingtiaoPresenter> implements NewPingtiaoContract.View {

    public static NewPingtiaoFragment newInstance() {
        NewPingtiaoFragment fragment = new NewPingtiaoFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerNewPingtiaoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_pingtiao, container, false);
    }

    @BindView(R.id.banner)
    ConvenientBanner mBanner;
    @BindView(R.id.message_content)
    CtsScrollTextView message_content;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.xinshouzhinan)
    MoveView xinshouzhinan;

    @BindView(R.id.input_name)
    TextView input_name;

    ChoicePingtiaoTypeDialog mChoicePingtiaoTypeDialog;


    @OnClick({R.id.woyaoxiepingtiao, R.id.more_relative, R.id.more_layout, R.id.click_more,
            R.id.daihuan_layout, R.id.daishou_layout, R.id.home_haihuan,
            R.id.home_shoukuan, R.id.xinshouzhinan})
    public void onPageClick(View v) {
        switch (v.getId()) {
            case R.id.xinshouzhinan:
                //TODO  跳转新手指南
                MobclickAgent.onEvent(getActivity(), "shouye_xinshoubangzhu", "首页\t点击“新手帮助”");
                SavePreference.save(PingtiaoConst.XINSHOU_ZHINAN, true);
                xinshouzhinan.setVisibility(View.GONE);//未点击显示
                Bundle bundlex1 = new Bundle();
                bundlex1.putString(BaseWebViewActivity.WEBVIEW_TITLE, "新手指南");
                bundlex1.putString(BaseWebViewActivity.WEBVIEW_URL, Api.BASE_H5_URL + Api.XINSHOU_GUIDE);
                startActBundle(bundlex1, WebViewActivity.class);
                break;
            case R.id.woyaoxiepingtiao:
                //我要写凭条
                MobclickAgent.onEvent(getActivity(), "woyaoxiepingtiao", "点击“我要写凭条”");
                if (TextUtils.isEmpty(SavePreference.getStr(getActivity(), PingtiaoConst.KEY_TOKEN))) {
                    tokenReq();
                } else {
                    startAct(CreateJietiaoActivity.class);
                }
                break;
            case R.id.more_layout:
                //更多页面
                if (getResources().getString(R.string.newplayerhelper).equals(more_tv.getText().toString())) {
                    //TODO 新手帮助
                    Bundle bundle1 = new Bundle();
                    bundle1.putString(BaseWebViewActivity.WEBVIEW_TITLE, "帮助与反馈");
                    bundle1.putString(BaseWebViewActivity.WEBVIEW_URL, Api.BASE_H5_URL + Api.CONTACT);
                    startActBundle(bundle1, WebViewActivity.class);
                } else {
//                    Bundle bundle = new Bundle();
//                    bundle.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.DIAN_ZI);
//                    startActBundle(bundle, MyPingtiaoActivity.class);
                }
                break;
            case R.id.more_relative:
            case R.id.click_more:
                //TODO 选择凭条类型
                if (mChoicePingtiaoTypeDialog == null) {
                    mChoicePingtiaoTypeDialog = new ChoicePingtiaoTypeDialog(getActivity(), new ChoicePingtiaoTypeDialog.ChoicePingtiaoTypeInterface() {
                        @Override
                        public void choicePingtiaoType(String choice) {
//                            ArmsUtils.snackbarText(choice);
                            Bundle bundle = new Bundle();
                            switch (choice) {
                                case "待还-电子借条":
                                    bundle.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.DIAN_ZI);
                                    bundle.putInt(MyPingtiaoActivity.JUESE, MyPingtiaoActivity.JIEKUANREN);
                                    break;
                                case "待收-电子借条":
                                    bundle.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.DIAN_ZI);
                                    bundle.putInt(MyPingtiaoActivity.JUESE, MyPingtiaoActivity.CHUJIEREN);
                                    break;
                                case "电子收条":
                                    bundle.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.DIAN_ZI_SHOU);
                                    bundle.putInt(MyPingtiaoActivity.JUESE, MyPingtiaoActivity.JUESEALL);
                                    break;
                                case "纸质借条":
                                    bundle.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.ZHI_ZHI);
                                    bundle.putInt(MyPingtiaoActivity.JUESE, MyPingtiaoActivity.JUESEALL);
                                    break;
                                case "纸质收条":
                                    bundle.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.ZHI_ZHI_SHOU);
                                    bundle.putInt(MyPingtiaoActivity.JUESE, MyPingtiaoActivity.JUESEALL);
                                    break;
                            }
                            startActBundle(bundle, MyPingtiaoActivity.class);
                        }
                    });
                }
                mChoicePingtiaoTypeDialog.show();
                break;

            case R.id.home_haihuan:
                //还款
                Bundle bundleX = new Bundle();
                bundleX.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.DIAN_ZI);
                bundleX.putInt(MyPingtiaoActivity.JUESE, MyPingtiaoActivity.JIEKUANREN);
                startActBundle(bundleX, MyPingtiaoActivity.class);
                break;
            case R.id.home_shoukuan:
                //收款
                Bundle bundleY = new Bundle();
                bundleY.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.DIAN_ZI);
                bundleY.putInt(MyPingtiaoActivity.JUESE, MyPingtiaoActivity.CHUJIEREN);
                startActBundle(bundleY, MyPingtiaoActivity.class);
                break;
            case R.id.daihuan_layout:
                //还款
                Bundle bundleX1 = new Bundle();
                bundleX1.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.DIAN_ZI);
                bundleX1.putInt(MyPingtiaoActivity.JUESE, MyPingtiaoActivity.JIEKUANREN);
                startActBundle(bundleX1, MyPingtiaoActivity.class);
                break;
            case R.id.daishou_layout:
                //收款
                Bundle bundleY1 = new Bundle();
                bundleY1.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.DIAN_ZI);
                bundleY1.putInt(MyPingtiaoActivity.JUESE, MyPingtiaoActivity.CHUJIEREN);
                startActBundle(bundleY1, MyPingtiaoActivity.class);
                break;
        }

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        xinshouzhinan.setMoveOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TODO  跳转新手指南
//                SavePreference.save(PingtiaoConst.XINSHOU_ZHINAN, true);
//                xinshouzhinan.setVisibility(View.GONE);//未点击显示
//                Bundle bundlex1 = new Bundle();
//                bundlex1.putString(BaseWebViewActivity.WEBVIEW_TITLE, "新手指南");
//                bundlex1.putString(BaseWebViewActivity.WEBVIEW_URL, Api.BASE_H5_URL + Api.XINSHOU_GUIDE);
//                startActBundle(bundlex1, WebViewActivity.class);
//            }
//        });
        initRv();
//        initHomeData();
        initHomeTimerTask();
        initShouHuankuan();
        initRefresh();
        initXinshouzhinan();
    }

//    @BindView(R.id.xinshouzhinan)
//    ImageView xinshouzhinan;

    private void initXinshouzhinan() {
        boolean hasClick = SavePreference.getBoolean(getActivity(), PingtiaoConst.XINSHOU_ZHINAN);
        xinshouzhinan.setVisibility(hasClick ? View.GONE : View.VISIBLE);//未点击显示
    }
   /* @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initHomeData();
        }
    }
*/


    private void tokenReq() {
//       mPresenter.getShouHuanKuanInfos();
        mPresenter.getPingTiaoFiveHistory();
    }

    @Override
    public void onResume() {
        super.onResume();
        initHomeData();
    }

    private void initHomeData() {
        if (mPresenter != null) {
            mPresenter.getHomeBanner();
            mPresenter.getFunctions();
            if (!TextUtils.isEmpty(SavePreference.getStr(getActivity(), PingtiaoConst.KEY_TOKEN))) {
                tokenReq();
            }
        }
    }

    private void initHomeTimerTask() {
        mPresenter.startTimerTask();
//        mPresenter.getHomeMessage();
    }

    /**
     * banner数据填充
     *
     * @param bannerList
     */
    @Override
    public void sucBanner(List<BannerParentInterface> bannerList) {
        BannerHelper.initBanner(mBanner, bannerList);
    }

    @Override
    public void sucMessage(List<HomeMessageScrollResponse> messages) {
        EventBus.getDefault().post(new HomeScrollMessageTag(messages));
        List<String> msgs = new ArrayList<>();
        for (HomeMessageScrollResponse temp : messages) {
            msgs.add(temp.getContent());
        }
        message_content.setTextList(msgs);
        message_content.startAutoScroll();
    }


    //
//    @Override
//    public void sucMessage(String message) {
//        message_content.setText(message);
//    }
    @BindView(R.id.click_more)
    TextView click_more;

    @Override
    public void sucRv(List<PingTiaoSeachResponse> datas) {
        if (datas != null && datas.size() > 0) {
            input_name.setText("电子凭条");
            mDatas.clear();
            mDatas.addAll(datas);
            if (mDatas.size() >= 5) {
                click_more.setVisibility(View.VISIBLE);
            } else {
                click_more.setVisibility(View.GONE);
            }
            more_tv.setText("");
            home_img_more.setVisibility(View.INVISIBLE);
        } else {
            getAnliData();
            more_tv.setText(getResources().getString(R.string.newplayerhelper));
            home_img_more.setVisibility(View.VISIBLE);
        }
        mHomeMultiAdapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh();
    }

    DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    public void sucShouHuanKuan(ShouHuanKuanResponse data) {
        double borrowAmonut = 0;
        double lendAmount = 0;
        try {
            borrowAmonut = Double.valueOf(data.getBorrowAmonut());
            lendAmount = Double.valueOf(data.getLendAmount());
        } catch (NumberFormatException e) {
//            e.printStackTrace();
        }

        if (borrowAmonut > 0 && lendAmount > 0) {
            all_show.setVisibility(View.VISIBLE);
            daihuan_layout.setVisibility(View.GONE);
            daishou_layout.setVisibility(View.GONE);

            home_huankuan_money_hint.setText(spanDaihuan);
            home_shoukuan_moeny_hint.setText(spanDaishou);

            home_huankuan_money.setText(decimalFormat.format(borrowAmonut) + "元");
            home_shoukuan_moeny.setText(decimalFormat.format(lendAmount) + "元");

        } else if (borrowAmonut > 0 && lendAmount <= 0) {
            all_show.setVisibility(View.GONE);
            daihuan_layout.setVisibility(View.VISIBLE);
            daishou_layout.setVisibility(View.GONE);
            home_huankuan_money_hint2.setText(spanDaihuan);
            home_huankuan_money2.setText(decimalFormat.format(borrowAmonut) + "元");
//             home_huankuan_money2.setText(decimalFormat.format(lendAmount)+"元");

        } else if (borrowAmonut <= 0 && lendAmount > 0) {//待还
            all_show.setVisibility(View.GONE);
            daihuan_layout.setVisibility(View.GONE);
            daishou_layout.setVisibility(View.VISIBLE);
            home_shoukuan_moeny_hint2.setText(spanDaishou);
//              home_shoukuan_moeny2.setText(decimalFormat.format(borrowAmonut)+"元");
            home_shoukuan_moeny2.setText(decimalFormat.format(lendAmount) + "元");
        } else {
            //全隐藏
            all_show.setVisibility(View.GONE);
            daihuan_layout.setVisibility(View.GONE);
            daishou_layout.setVisibility(View.GONE);
            home_huankuan_money_hint.setText(spanDaihuan);
            home_shoukuan_moeny_hint.setText(spanDaishou);
            home_huankuan_money.setText(decimalFormat.format(0.0f) + "元");
            home_shoukuan_moeny.setText(decimalFormat.format(0.0f) + "元");
        }
    }

    @BindView(R.id.jietiaomoban_first)
    LinearLayout jietiaomoban_first;
    @BindView(R.id.jietiaomoban)
    ImageView jietiaomoban;
    @BindView(R.id.jietiaomoban_tv)
    TextView jietiaomoban_tv;

    @BindView(R.id.shoutiaomoban_second)
    LinearLayout shoutiaomoban_second;
    @BindView(R.id.shoutiaomoban)
    ImageView shoutiaomoban;
    @BindView(R.id.shoutiaomoban_tv)
    TextView shoutiaomoban_tv;

    @BindView(R.id.jietiaozhenduan_third)
    LinearLayout jietiaozhenduan_third;
    @BindView(R.id.jietiaozhenduan)
    ImageView jietiaozhenduan;
    @BindView(R.id.jietiaozhenduan_tv)
    TextView jietiaozhenduan_tv;


    @BindView(R.id.chazhengxin_fourth)
    LinearLayout chazhengxin_fourth;
    @BindView(R.id.chazhengxin)
    ImageView chazhengxin;
    @BindView(R.id.chazhengxin_tv)
    TextView chazhengxin_tv;


    @Override
    public void sucFunction(List<HomePageComResponse> datas) {
        jietiaomoban_first.setVisibility(View.GONE);
        shoutiaomoban_second.setVisibility(View.GONE);
        jietiaozhenduan_third.setVisibility(View.GONE);
        chazhengxin_fourth.setVisibility(View.GONE);

        List<Class> activities = ClassUtils.getActivitiesClass(getActivity(), AppUtils.getAppPackageName(), null);

        if (datas != null && datas.size() > 0) {
            int datasSize = datas.size();
            for (int i = 0; i < datasSize; i++) {
                switch (i) {
                    case 0:
                        jietiaomoban_first.setVisibility(View.VISIBLE);
                        GlideProxyHelper.loadImgByPlaceholder(jietiaomoban, R.drawable.home_page_functions_bg, datas.get(0).getIconUrl());
                        jietiaomoban_tv.setText(datas.get(0).getName());
                        jietiaomoban_first.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MobclickAgent.onEvent(getActivity(), "chanxinyong", "首页\t点击-查信用\t");
                                functionsConfig(datas.get(0), activities);
                            }
                        });
                        break;
                    case 1:
                        shoutiaomoban_second.setVisibility(View.VISIBLE);
                        GlideProxyHelper.loadImgByPlaceholder(shoutiaomoban, R.drawable.home_page_functions_bg, datas.get(1).getIconUrl());
                        shoutiaomoban_tv.setText(datas.get(1).getName());
                        shoutiaomoban_second.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MobclickAgent.onEvent(getActivity(), "qiujiekuan", "首页\t点击求借款");
                                functionsConfig(datas.get(1), activities);
                            }
                        });
                        break;
                    case 2:
                        jietiaozhenduan_third.setVisibility(View.VISIBLE);
                        GlideProxyHelper.loadImgByPlaceholder(jietiaozhenduan, R.drawable.home_page_functions_bg, datas.get(2).getIconUrl());
                        jietiaozhenduan_tv.setText(datas.get(2).getName());
                        jietiaozhenduan_third.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MobclickAgent.onEvent(getActivity(), "jietiaomoban", "首页\t点击借条模板");
                                functionsConfig(datas.get(2), activities);
                            }
                        });
                        break;
                    case 3:
                        chazhengxin_fourth.setVisibility(View.VISIBLE);
                        GlideProxyHelper.loadImgByPlaceholder(chazhengxin, R.drawable.home_page_functions_bg, datas.get(3).getIconUrl());
                        chazhengxin_tv.setText(datas.get(3).getName());
                        chazhengxin_fourth.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MobclickAgent.onEvent(getActivity(), "shoutiaomoban", "首页\t点击收条模板");
                                functionsConfig(datas.get(3), activities);
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void functionsConfig(HomePageComResponse homePageComResponse, List<Class> activities) {
        if (homePageComResponse == null) {
            return;
        }
        boolean toWebPage = true;
        if (homePageComResponse.getParams() != null) {
            String name = homePageComResponse.getParams().getClassName();
            for (Class act : activities) {
                if (act.getSimpleName().equals(name)) {
                    //如果存在匹配的Activity 则 跳转至 activity
                    toWebPage = false;
                    ActivityUtils.startActivity(act);
                }
            }
        }
        if (toWebPage) {
            Bundle bundle1 = new Bundle();
            bundle1.putString(BaseWebViewActivity.WEBVIEW_TITLE, homePageComResponse.getName());
            bundle1.putString(BaseWebViewActivity.WEBVIEW_URL, homePageComResponse.getDetailUrl());
            startActBundle(bundle1, WebViewActivity.class);
        }
    }


    HomeMultiAdapter mHomeMultiAdapter;
    List<PingTiaoSeachResponse> mDatas = new ArrayList<>();

    private void getAnliData() {
        PingTiaoSeachResponse repJie = new PingTiaoSeachResponse();
        repJie.setAmount("2000");
        repJie.setTotalAmount("2019.73");
        repJie.setType("OWE_NOTE");//电子借条
        repJie.setLender("李四");//出借人
        repJie.setBorrower("张三");//借款人
        repJie.setOverDueDays("0");//逾期天数
        repJie.setRepaymentDate("2019-01-31");//还款时间
        repJie.setBorrowAndLendState("0");//待还
        repJie.setHasAnli(true);

        PingTiaoSeachResponse repShou = new PingTiaoSeachResponse();
        repShou.setAmount("30000");
        repShou.setTotalAmount("30000");
        repShou.setLender("李四");
        repShou.setType("RECEIPT_NOTE");
        repShou.setRepaymentDate("2019-01-01");
        repShou.setRepaymentDate("2019-10-03");
        repShou.setHasAnli(true);

        mDatas.clear();
        mDatas.add(repJie);
        mDatas.add(repShou);
        input_name.setText("电子凭条案例");
    }

    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;

    private void initRefresh() {
//        mRefreshLayout.setEnableAutoLoadMore(false);
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

    @BindView(R.id.more_tv)
    TextView more_tv;
    @BindView(R.id.home_img_more)
    ImageView home_img_more;

    private void initRv() {
        getAnliData();
        more_tv.setText(getString(R.string.newplayerhelper));
        mHomeMultiAdapter = new HomeMultiAdapter(mDatas);
        rv.setAdapter(mHomeMultiAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHomeMultiAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mHomeMultiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PingTiaoSeachResponse rep = (PingTiaoSeachResponse) adapter.getData().get(position);
                String hintName = (String) input_name.getText();
                if ("电子凭条案例".equals(hintName)) {
                    Bundle bundle1 = new Bundle();
                    switch (rep.getType()) {
                        case "OWE_NOTE":
                            bundle1.putInt(DianziPingtiaoAnliMobanActivity.TAG, DianziPingtiaoAnliMobanActivity.DIANZI_JIETIAO);
                            startActBundle(bundle1, DianziPingtiaoAnliMobanActivity.class);
                            break;
                        case "RECEIPT_NOTE":
                            bundle1.putInt(DianziPingtiaoAnliMobanActivity.TAG, DianziPingtiaoAnliMobanActivity.DIANZI_SHOUTIAO);
                            startActBundle(bundle1, DianziPingtiaoAnliMobanActivity.class);
                            break;
                        default:
                            break;
                    }

                } else {
                    //凭条类型 OWE_NOTE 电子借条 PAPER_OWE_NOTE纸质借条 PAPER_RECEIPT_NOTE 纸质收条 RECEIPT_NOTE电子收条
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

    /**
     * 初始化收还款
     */
    @BindView(R.id.all_show)
    LinearLayout all_show;
    @BindView(R.id.daihuan_layout)
    RelativeLayout daihuan_layout;
    @BindView(R.id.daishou_layout)
    RelativeLayout daishou_layout;

    @BindView(R.id.home_shoukuan_moeny2)
    TextView home_shoukuan_moeny2;

    @BindView(R.id.home_shoukuan_moeny_hint2)
    TextView home_shoukuan_moeny_hint2;

    @BindView(R.id.home_huankuan_money_hint2)
    TextView home_huankuan_money_hint2;

    @BindView(R.id.home_huankuan_money2)
    TextView home_huankuan_money2;


    @BindView(R.id.home_shoukuan)
    RelativeLayout home_shoukuan;//首页收款图
    @BindView(R.id.home_shoukuan_moeny)
    TextView home_shoukuan_moeny;//首页收款图 中的金额
    @BindView(R.id.home_shoukuan_moeny_hint)
    TextView home_shoukuan_moeny_hint;//首页收款图 中的金额
    @BindView(R.id.home_haihuan)
    RelativeLayout home_haihuan;//首页还款图
    @BindView(R.id.home_huankuan_money)
    TextView home_huankuan_money;//首页收款 金额
    @BindView(R.id.home_huankuan_money_hint)
    TextView home_huankuan_money_hint;//首页收款 金额

    SpannableStringBuilder spanDaihuan = new SpanUtils()
            .append("未来7日").setForegroundColor(Color.parseColor("#634126"))
            .append("待还").setForegroundColor(Color.parseColor("#FF554F"))
            .create();
    SpannableStringBuilder spanDaishou = new SpanUtils()
            .append("未来7日").setForegroundColor(Color.parseColor("#634126"))
            .append("待收").setForegroundColor(Color.parseColor("#FF554F"))
            .create();

    private void initShouHuankuan() {
        home_shoukuan_moeny.setText(decimalFormat.format(0) + "元");
        home_huankuan_money.setText(decimalFormat.format(0) + "元");
    }


    @Override
    public void setData(@Nullable Object data) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEventTag tag) {
        mRefreshLayout.autoRefresh();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void exitApp(ExitAppTag tag) {
        getAnliData();
        mHomeMultiAdapter.notifyDataSetChanged();
        all_show.setVisibility(View.GONE);
        daihuan_layout.setVisibility(View.GONE);
        daishou_layout.setVisibility(View.GONE);
        click_more.setVisibility(View.GONE);
        more_tv.setText(getResources().getString(R.string.newplayerhelper));
    }
}
