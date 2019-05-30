package com.pingtiao51.armsmodule.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.makeramen.roundedimageview.RoundedImageView;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.di.component.DaggerMyComponent;
import com.pingtiao51.armsmodule.mvp.contract.MyContract;
import com.pingtiao51.armsmodule.mvp.model.api.Api;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.ExitAppTag;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.LoginEventTag;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.UserAvatarChangeTag;
import com.pingtiao51.armsmodule.mvp.model.entity.response.UserDetailInfoResponse;
import com.pingtiao51.armsmodule.mvp.presenter.MyPresenter;
import com.pingtiao51.armsmodule.mvp.ui.activity.BaseWebViewActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.LoginActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.MyPingtiaoActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.SettingActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.ShimingrenzhengActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.WebViewActivity;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.InputLoginView;
import com.pingtiao51.armsmodule.mvp.ui.helper.GlideProxyHelper;
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst;
import com.pingtiao51.armsmodule.mvp.ui.helper.UrlDecoderHelper;
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/11/2019 15:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MyFragment extends BaseArmFragment<MyPresenter> implements MyContract.View {

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @OnClick({R.id.user_name, R.id.rengzhengstatus, R.id.wode_huan_layout, R.id.wode_shou_layout, R.id.xiaofeimingxi, R.id.helper, R.id.shezhi
    ,R.id.my_dianzishoutiao,R.id.my_zhizhijietiao,R.id.my_zhizhishoutiao,R.id.user_avatar})
    public void onPageClick(View v) {
        Bundle bundleAll = new Bundle();
        if (!isLoginFlag) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.putExtra(LoginActivity.LOGIN_MODE, InputLoginView.CODE_LOGIN);
            startActivity(intent);
            return;
        }
        switch (v.getId()) {
            case R.id.rengzhengstatus:
                //TODO 实名认证
                if (mUserDetailInfoResponse != null) {
                    if (!mUserDetailInfoResponse.isVerified()) {
                        shimingrenzheng();
                    }
                } else {
                    shimingrenzheng();
                }
                break;
            case R.id.wode_huan_layout:
                //TODO 个人还款
                Bundle bundleX = new Bundle();
                bundleX.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.DIAN_ZI);
                bundleX.putInt(MyPingtiaoActivity.JUESE, MyPingtiaoActivity.JIEKUANREN);
                startActBundle(bundleX, MyPingtiaoActivity.class);

                break;
            case R.id.wode_shou_layout:
                //TODO 个人收款
                Bundle bundleY = new Bundle();
                bundleY.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.DIAN_ZI);
                bundleY.putInt(MyPingtiaoActivity.JUESE, MyPingtiaoActivity.CHUJIEREN);
                startActBundle(bundleY, MyPingtiaoActivity.class);

                break;
            case R.id.xiaofeimingxi:
                //TODO 消费明细
                Bundle bundle = new Bundle();
                bundle.putString(BaseWebViewActivity.WEBVIEW_TITLE, "消费明细");
                bundle.putString(BaseWebViewActivity.WEBVIEW_URL, Api.BASE_H5_URL + Api.TRANSACTION_DETAIL);
                startActBundle(bundle, WebViewActivity.class);
                break;
            case R.id.helper:
                //TODO 帮助反馈
                Bundle bundle1 = new Bundle();
                bundle1.putString(BaseWebViewActivity.WEBVIEW_TITLE, "帮助与反馈");
                bundle1.putString(BaseWebViewActivity.WEBVIEW_URL, Api.BASE_H5_URL + Api.CONTACT);
                startActBundle(bundle1, WebViewActivity.class);
                break;
            case R.id.user_avatar:
            case R.id.shezhi:
                //TODO 设置
                bundleAll.putString(SettingActivity.AVATAR, mUserDetailInfoResponse.getHeadUrl());
                startActBundle(bundleAll,SettingActivity.class);
                break;

            case R.id.my_dianzishoutiao:
                bundleAll.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.DIAN_ZI_SHOU);
                bundleAll.putInt(MyPingtiaoActivity.JUESE, MyPingtiaoActivity.JUESEALL);
                startActBundle(bundleAll, MyPingtiaoActivity.class);
                break;
            case R.id.my_zhizhijietiao:
                bundleAll.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.ZHI_ZHI);
                bundleAll.putInt(MyPingtiaoActivity.JUESE, MyPingtiaoActivity.JUESEALL);
                startActBundle(bundleAll, MyPingtiaoActivity.class);
                break;
            case R.id.my_zhizhishoutiao:
                bundleAll.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.ZHI_ZHI_SHOU);
                bundleAll.putInt(MyPingtiaoActivity.JUESE, MyPingtiaoActivity.JUESEALL);
                startActBundle(bundleAll, MyPingtiaoActivity.class);
                break;
        }

    }

    @BindView(R.id.user_avatar)
    RoundedImageView user_avatar;

    @BindView(R.id.user_name)
    TextView user_name;

    @BindView(R.id.rengzhengstatus)
    TextView rengzhengstatus;


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initUserInfo();
        }
    }

    private void initUserInfo() {
        if (mPresenter != null) {
            mPresenter.getUserDetailInfo();
        }
    }

    @BindView(R.id.wode_huan_money)
    TextView wode_huan_money;

    @BindView(R.id.wode_shou_money)
    TextView wode_shou_money;

    private void initMoneyInfo() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        double huan = 0;
        double shou = 0;
        try {
            huan = Double.valueOf(mUserDetailInfoResponse.getWaitToRepaid());
            shou = Double.valueOf(mUserDetailInfoResponse.getWaitToCollect());
        } catch (Exception e) {
        }
        if (mUserDetailInfoResponse != null) {

            wode_huan_money.setText(decimalFormat.format(huan) + "元");
            wode_shou_money.setText(decimalFormat.format(shou) + "元");

        }
    }


    @BindView(R.id.input_name)
    TextView input_name;
    @BindView(R.id.more_layout)
    LinearLayout more_layout;

    @BindView(R.id.xiaofeimingxi)
    RelativeLayout xiaofeimingxi;
    @BindView(R.id.helper)
    RelativeLayout helper;
    @BindView(R.id.shezhi)
    RelativeLayout shezhi;

    private void initBottomLayout() {
        input_name.setText("常用功能");
        more_layout.setVisibility(View.GONE);
        GlideProxyHelper.loadImgForRes(xiaofeimingxi.findViewById(R.id.show_icon), R.drawable.mingxi);
        TextView xiaofeimingxiTv = xiaofeimingxi.findViewById(R.id.show_name);
        xiaofeimingxiTv.setText("消费明细");

        GlideProxyHelper.loadImgForRes(helper.findViewById(R.id.show_icon), R.drawable.bangzhu);
        TextView helperTv = helper.findViewById(R.id.show_name);
        helperTv.setText("帮助与反馈");

        GlideProxyHelper.loadImgForRes(shezhi.findViewById(R.id.show_icon), R.drawable.shezhi);
        TextView shezhiTv = shezhi.findViewById(R.id.show_name);
        shezhiTv.setText("设置");
    }

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbar_back;


    private void initTilte() {
        toolbar_back.setVisibility(View.GONE);
        toolbar_title.setText("我的");
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initTilte();
        visibleMyPage(false);
        initUserInfo();
        initMoneyInfo();
        initBottomLayout();
        initRefresh();
    }

    private boolean isLoginFlag = false;

    private void visibleMyPage(boolean isLogin) {
        isLoginFlag = isLogin;
        rengzhengstatus.setVisibility(isLogin ? View.VISIBLE : View.GONE);
        if (!isLogin) {
            user_name.setText("登录/注册");
        }
    }


    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.login_view)
    LinearLayout login_view;

    private void initRefresh() {
//        mRefreshLayout.setEnableAutoLoadMore(false);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initUserInfo();
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
    public void setData(@Nullable Object data) {

    }

    UserDetailInfoResponse mUserDetailInfoResponse;

    /**
     * 获取用户详情成功返回
     *
     * @param rep
     */
    @Override
    public void showUserVertifyDialog(UserDetailInfoResponse rep) {
        visibleMyPage(true);
        mRefreshLayout.finishRefresh();
        mUserDetailInfoResponse = rep;
        SavePreference.save(PingtiaoConst.USER_NAME, rep.getRealname());
        SavePreference.save(PingtiaoConst.USER_PHONE, rep.getPhone());
        SavePreference.save(PingtiaoConst.USER_ID_CARD, rep.getIdentityNo());
//        if(!TextUtils.isEmpty(rep.getHeadUrl())){
            //不为null 则
            GlideProxyHelper.loadImgByPlaceholder(user_avatar,R.drawable.wode_touxiang,UrlDecoderHelper.decode(rep.getHeadUrl()));
//        }

        if (rep.getPhone() != null && rep.getPhone().length() >= 11) {
            String showPhone = rep.getPhone().substring(0, 3) + "****" + rep.getPhone().substring(7, rep.getPhone().length());
            user_name.setText(showPhone);//用户手机号展示
        }


        if (!rep.isVerified()) {
            rengzhengstatus.setBackground(getResources().getDrawable(R.drawable.qushiming));
            rengzhengstatus.setText("");
        } else {
            rengzhengstatus.setBackground(getResources().getDrawable(R.color.transparent));
            rengzhengstatus.setText(getString(R.string.yishiming));
            rengzhengstatus.setTextColor(Color.parseColor("#ff52d590"));
        }

        initMoneyInfo();

        mRefreshLayout.finishRefresh();//刷新结束

    }

    @Override
    public void tokenInvalid(String message) {
        mRefreshLayout.finishRefresh();
        visibleMyPage(false);
//        ArmsUtils.snackbarText(message);
    }

    private void shimingrenzheng() {
        startAct(ShimingrenzhengActivity.class);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEventTag tag) {
        mRefreshLayout.autoRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void exitApp(ExitAppTag tag) {
        user_name.setText("登录/注册");
        rengzhengstatus.setBackground(getResources().getDrawable(R.drawable.qushiming));
        rengzhengstatus.setText("");
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        double huan = 0;
        double shou = 0;
        wode_huan_money.setText(decimalFormat.format(huan) + "元");
        wode_shou_money.setText(decimalFormat.format(shou) + "元");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userAvatarChangeAction(UserAvatarChangeTag tag) {
        mRefreshLayout.autoRefresh();
    }

}
