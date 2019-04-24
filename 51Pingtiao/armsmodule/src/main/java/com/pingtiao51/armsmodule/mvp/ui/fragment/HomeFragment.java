package com.pingtiao51.armsmodule.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.meituan.android.walle.WalleChannelReader;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.di.component.DaggerHomeComponent;
import com.pingtiao51.armsmodule.mvp.contract.HomeContract;
import com.pingtiao51.armsmodule.mvp.model.api.Api;
import com.pingtiao51.armsmodule.mvp.presenter.HomePresenter;
import com.pingtiao51.armsmodule.mvp.ui.activity.BaseWebViewActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.LoginActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.WebViewActivity;
import com.pingtiao51.armsmodule.mvp.ui.adapter.HomeViewpagerAdapter;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.CycleDatePickerDialog;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.InputLoginView;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.ShareDialog;
import com.pingtiao51.armsmodule.mvp.ui.helper.MagicIndicatorHelp;
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst;
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/11/2019 15:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HomeFragment extends BaseArmFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.left_hetong)
    ImageView mHetong;
    @BindView(R.id.right_xiaoxi)
    RelativeLayout mXiaoxi;


    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;

    @BindView(R.id.home_viewpager)
    ViewPager viewpager;

    Unbinder unbinder;
    ShareDialog mShareDialog;
    CycleDatePickerDialog mCycleDatePickerDialog;

    @OnClick({R.id.right_xiaoxi, R.id.left_hetong})
    public void onPageClick(View v) {
        switch (v.getId()) {
            case R.id.left_hetong:
                //TODO 跳转H5 合同简介
                Bundle bundle = new Bundle();
                bundle.putString(BaseWebViewActivity.WEBVIEW_TITLE, "51凭条介绍");
                bundle.putString(BaseWebViewActivity.WEBVIEW_URL, Api.BASE_H5_URL + Api.CONTRACT);
                startActBundle(bundle, WebViewActivity.class);
/*                if(mShareDialog == null){
                    mShareDialog = new ShareDialog(getActivity());
                }
                 mShareDialog.show();
                */

/*                if(mCycleDatePickerDialog == null){
                    Calendar start = Calendar.getInstance();
                    start.setTime(new Date());
                    Calendar end = Calendar.getInstance();
                    end.setTime(new Date());
                    end.add(Calendar.YEAR,10);
                    mCycleDatePickerDialog = new CycleDatePickerDialog(getActivity(),start,end);
                }
                mCycleDatePickerDialog.show();*/

                break;
            case R.id.right_xiaoxi:
                //TODO 跳转H5 消息简介
                if (TextUtils.isEmpty(SavePreference.getStr(getActivity(), PingtiaoConst.KEY_TOKEN))) {
                    Bundle bundlex = new Bundle();
                    bundlex.putInt(LoginActivity.LOGIN_MODE, InputLoginView.CODE_LOGIN);
                    startActBundle(bundlex, LoginActivity.class);
                } else {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString(BaseWebViewActivity.WEBVIEW_TITLE, "消息中心");
                    bundle1.putString(BaseWebViewActivity.WEBVIEW_URL, Api.BASE_H5_URL + Api.MESSAGE);
                    startActBundle(bundle1, WebViewActivity.class);
                }
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            if (!TextUtils.isEmpty(SavePreference.getStr(getActivity(), PingtiaoConst.KEY_TOKEN))) {
                tokenReq();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(SavePreference.getStr(getActivity(), PingtiaoConst.KEY_TOKEN))) {
            tokenReq();
        }
    }

    private void tokenReq() {
        if (mPresenter != null) {
            mPresenter.getMessageStatistics();
        }
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private ArrayList<String> mTitles = new ArrayList<>();

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTitles.add("新建");
        mTitles.add("备份");
        Fragment[] frgs = new Fragment[]{
                NewPingtiaoFragment.newInstance(),
                CopyPingtiaoFragment.newInstance()
        };
        HomeViewpagerAdapter adapter = new HomeViewpagerAdapter(getChildFragmentManager(), frgs, mTitles);
        viewpager.setAdapter(adapter);
        MagicIndicatorHelp.initIndicatorView(viewpager, magicIndicator);
//        if (!TextUtils.isEmpty(SavePreference.getStr(getActivity(), PingtiaoConst.KEY_TOKEN))) {
//            tokenReq();
//        }

    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

    }

    @BindView(R.id.xiaoxi_hint)
    ImageView xiaoxi_hint;

    @Override
    public void onSucMessage(Object obj) {
        if (obj != null) {
            double msgs = (Double) obj;
            if (msgs > 0) {
                xiaoxi_hint.setVisibility(View.VISIBLE);
            } else {
                xiaoxi_hint.setVisibility(View.GONE);
            }
        } else {
            xiaoxi_hint.setVisibility(View.GONE);
        }
    }
}
