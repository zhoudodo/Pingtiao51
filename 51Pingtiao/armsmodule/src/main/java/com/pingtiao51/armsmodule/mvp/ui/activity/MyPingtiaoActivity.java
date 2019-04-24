package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.pingtiao51.armsmodule.di.component.DaggerMyPingtiaoComponent;
import com.pingtiao51.armsmodule.mvp.contract.MyPingtiaoContract;
import com.pingtiao51.armsmodule.mvp.presenter.MyPingtiaoPresenter;

import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.ui.adapter.PingtiaoPagerAdapter;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.XinjianPingtiaoDialog;
import com.pingtiao51.armsmodule.mvp.ui.fragment.DianziJietiaoFragment;
import com.pingtiao51.armsmodule.mvp.ui.fragment.DianziShoutiaoFragment;
import com.pingtiao51.armsmodule.mvp.ui.fragment.ZhizhiJietiaoFragment;
import com.pingtiao51.armsmodule.mvp.ui.fragment.ZhizhiShoutiaoFragment;
import com.pingtiao51.armsmodule.mvp.ui.helper.MagicIndicatorHelp;


import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/14/2019 17:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MyPingtiaoActivity extends BaseArmsActivity<MyPingtiaoPresenter> implements MyPingtiaoContract.View {

    public final static String TAG = MyPingtiaoActivity.class.getSimpleName();


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyPingtiaoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }


    public final static int BACK_FINISH_CREATE = 0xF0;
    public final static String FINISH_CREATE = "finish_create";

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_pingtiao; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    public final static int DIAN_ZI = 0;//电子凭条
    public final static int DIAN_ZI_SHOU = 1;//电子收
    public final static int ZHI_ZHI = 2;//纸质凭条
    public final static int ZHI_ZHI_SHOU = 3;//纸质凭条
    private int mType = DIAN_ZI;

    public final static String JUESE = "juese";
    public final static int CHUJIEREN = 2;
    public final static int JIEKUANREN = 1;
    private int mJuese = CHUJIEREN;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mBackType == BACK_FINISH_CREATE){
            ActivityUtils.finishActivity(CreateJietiaoActivity.class);
            ActivityUtils.finishActivity(CreateDianziJietiaoActivity.class);
        }
    }

    Bundle recBundle;
    private int mBackType = 0;
    private void initRecOptions() {
        recBundle = getIntent().getExtras();
        if (recBundle != null) {
            mType = recBundle.getInt(TAG, DIAN_ZI);
            mBackType = recBundle.getInt(FINISH_CREATE, 0);
//            mJuese = recBundle.getInt(JUESE,0);
        }
    }

    XinjianPingtiaoDialog mXinjianPingtiaoDialog;

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("我的凭条");
        TextView rightTv = findViewById(R.id.right_tv);
        rightTv.setText("新建");
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mXinjianPingtiaoDialog == null) {
                    mXinjianPingtiaoDialog = new XinjianPingtiaoDialog(MyPingtiaoActivity.this);
                }
                mXinjianPingtiaoDialog.show();
               /* if(viewPager.getCurrentItem()< 2) {
                    startAct(CreateJietiaoActivity.class);
                }else{
                    startAct(SecureCopyActivity.class);
                }*/
            }
        });
        initRecOptions();
        initAll();
    }

    @BindView(R.id.indicator)
    MagicIndicator indicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    List<String> mTitle = new ArrayList<>();
    PingtiaoPagerAdapter mPingtiaoPagerAdapter;
    List<Fragment> mFragment = new ArrayList<>();

    private void initAll() {

        mTitle.add("电子借条");
        mTitle.add("电子收条");
        mTitle.add("纸质借条");
        mTitle.add("纸质收条");
        mFragment.add(DianziJietiaoFragment.newInstance());
        mFragment.add(DianziShoutiaoFragment.newInstance());
        mFragment.add(ZhizhiJietiaoFragment.newInstance());
        mFragment.add(ZhizhiShoutiaoFragment.newInstance());
        mFragment.get(0).setArguments(recBundle);
        mPingtiaoPagerAdapter = new PingtiaoPagerAdapter(getSupportFragmentManager(), mFragment, mTitle);
        viewPager.setAdapter(mPingtiaoPagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        MagicIndicatorHelp.initMyPingtiaoIndicator(viewPager, indicator);
        switch (mType) {
            case DIAN_ZI:
                viewPager.setCurrentItem(0, false);
                break;
            case DIAN_ZI_SHOU:
                viewPager.setCurrentItem(1, false);
                break;
            case ZHI_ZHI:
                viewPager.setCurrentItem(2, false);
                break;
            case ZHI_ZHI_SHOU:
                viewPager.setCurrentItem(3, false);
                break;
        }
    }


}
