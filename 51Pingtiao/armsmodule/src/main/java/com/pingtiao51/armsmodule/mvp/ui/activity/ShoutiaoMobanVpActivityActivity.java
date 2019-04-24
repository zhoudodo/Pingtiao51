package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.pingtiao51.armsmodule.di.component.DaggerShoutiaoMobanVpActivityComponent;
import com.pingtiao51.armsmodule.mvp.contract.ShoutiaoMobanVpActivityContract;
import com.pingtiao51.armsmodule.mvp.presenter.ShoutiaoMobanVpActivityPresenter;

import com.pingtiao51.armsmodule.R;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/02/2019 11:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ShoutiaoMobanVpActivityActivity extends BaseArmsActivity<ShoutiaoMobanVpActivityPresenter> implements ShoutiaoMobanVpActivityContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerShoutiaoMobanVpActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_shoutiao_moban_vp; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.img3)
    ImageView img3;
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("收条模板");
        findViewById(R.id.toolbar).setBackground(getResources().getDrawable(R.color.transparent));
        initViewPager();
    }

    private void initViewPager(){
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }


            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view= null;
                switch (position){
                    case 0:
                        view = View.inflate(getBaseContext(),R.layout.view_shoutiao_moban1_layout,null);
                        break;
                    case 1:
                        view = View.inflate(getBaseContext(),R.layout.view_shoutiao_moban2_layout,null);
                        break;
                    case 2:
                        view = View.inflate(getBaseContext(),R.layout.view_shoutiao_moban3_layout,null);
                        view.findViewById(R.id.beifen_btn).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(ActivityUtils.isActivityExistsInStack(ZhizhiShoutiaoMainActivity.class)){
                                    finish();//关闭当前act
                                    ActivityUtils.finishActivity(ZhizhiMoban1Activity.class);
                                }else{
                                    startAct(ZhizhiShoutiaoMainActivity.class);
                                    finish();//关闭当前act
                                    ActivityUtils.finishActivity(ZhizhiMoban1Activity.class);
                                    ActivityUtils.finishActivity(ZhizhiJietiaoMainActivity.class);
                                }
                            }
                        });
                        break;
                }

                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                initIndicator(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initIndicator(int i){
        img1.setImageDrawable(getResources().getDrawable(R.drawable.moban_hui));
        img2.setImageDrawable(getResources().getDrawable(R.drawable.moban_hui));
        img3.setImageDrawable(getResources().getDrawable(R.drawable.moban_hui));

        switch (i){
            case 0:
                img1.setImageDrawable(getResources().getDrawable(R.drawable.moban_huang));
                break;
            case 1:
                img2.setImageDrawable(getResources().getDrawable(R.drawable.moban_huang));
                break;
            case 2:
                img3.setImageDrawable(getResources().getDrawable(R.drawable.moban_huang));
                break;
        }
    }

}
