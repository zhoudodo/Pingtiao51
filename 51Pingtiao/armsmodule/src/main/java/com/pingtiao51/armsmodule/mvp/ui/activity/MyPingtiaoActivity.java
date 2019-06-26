package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.di.component.DaggerMyPingtiaoComponent;
import com.pingtiao51.armsmodule.mvp.contract.MyPingtiaoContract;
import com.pingtiao51.armsmodule.mvp.presenter.MyPingtiaoPresenter;
import com.pingtiao51.armsmodule.mvp.ui.adapter.ChoicePingtiaoPageAdapter;
import com.pingtiao51.armsmodule.mvp.ui.adapter.PingtiaoPagerAdapter;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.ChoicePingtiaoPageDialog;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.PingtiaoChoiceView2;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.XinjianPingtiaoDialog;
import com.pingtiao51.armsmodule.mvp.ui.fragment.DianziJietiaoFragment;
import com.pingtiao51.armsmodule.mvp.ui.fragment.DianziShoutiaoFragment;
import com.pingtiao51.armsmodule.mvp.ui.fragment.ZhizhiJietiaoFragment;
import com.pingtiao51.armsmodule.mvp.ui.fragment.ZhizhiShoutiaoFragment;
import com.pingtiao51.armsmodule.mvp.ui.helper.others.KeyBoardUtil;
import com.pingtiao51.armsmodule.mvp.ui.interfaces.SearchPingtiaoListInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;


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
    private int mEnoteType = DIAN_ZI;

    public final static String JUESE = "juese";
    public final static int CHUJIEREN = 2;
    public final static int JIEKUANREN = 1;
    public final static int JUESEALL = 0;
    private int mUserRoleType = CHUJIEREN;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mBackType == BACK_FINISH_CREATE) {
            ActivityUtils.finishActivity(CreateJietiaoActivity.class);
            ActivityUtils.finishActivity(CreateDianziJietiaoActivity.class);
            ActivityUtils.finishActivity(CreateDianziShoutiaoActivity.class);
        }
    }

    Bundle recBundle;
    private int mBackType = 0;

    private void initRecOptions() {
        recBundle = getIntent().getExtras();
        if (recBundle != null) {
            mEnoteType = recBundle.getInt(TAG, DIAN_ZI);
            mUserRoleType = recBundle.getInt(JUESE,0);
            mBackType = recBundle.getInt(FINISH_CREATE, 0);
        }
    }

    XinjianPingtiaoDialog mXinjianPingtiaoDialog;

    List<String> mTitles;
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
         mTitles = Arrays.asList(getResources().getStringArray(R.array.xuanzepingtiaoleixing));
        initRecOptions();
        initDrawlayout();
        initRecyclerView();
        pingtiaoShaixuanLayout();
        initSearchLayout();
        initAll();
    }

    /**
     * 侧滑栏2个rv
     */
    @BindView(R.id.recycler_view_jiekuanqixian)
    RecyclerView recycler_view_jiekuanqixian;
    ChoicePingtiaoPageAdapter mJiekuanqixianAdapter;
    private List<String> mJiekuanqixianList = new ArrayList<>();
    private String mChoiceJiekuanqixian = "";
    private int  mLoanPeriodTypePos = 0;
    private String mLoanPeriodType = "0";

    @BindView(R.id.recycler_view_huankuanriqi)
    RecyclerView recycler_view_huankuanriqi;
    ChoicePingtiaoPageAdapter mHuankuanriqiAdapter;
    private List<String> mJulihuankuanriqiList = new ArrayList<>();
    private String mChoiceJulihuankuanriqi = "";
    private String mRemainderRepayDaysType = "0";
    private int  mRemainderRepayDaysTypePos = 0;

    private void initRecyclerView() {
        //借款期限
        mJiekuanqixianList = Arrays.asList(getResources().getStringArray(R.array.jiekuanqixian));
        mChoiceJiekuanqixian = mJiekuanqixianList.get(0);
        mJiekuanqixianAdapter = new ChoicePingtiaoPageAdapter(R.layout.item_jiekuanqixian_item_layout, mJiekuanqixianList);
        mJiekuanqixianAdapter.setCheckPosition(0);
        recycler_view_jiekuanqixian.setLayoutManager(new GridLayoutManager(this, 3));
        recycler_view_jiekuanqixian.setAdapter(mJiekuanqixianAdapter);
        mJiekuanqixianAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mJiekuanqixianAdapter.setCheckPosition(position);
                mJiekuanqixianAdapter.notifyDataSetChanged();
                mChoiceJiekuanqixian = (String) adapter.getData().get(position);
                mLoanPeriodTypePos = position;
            }
        });

        //距离还款时间
        mJulihuankuanriqiList = Arrays.asList(getResources().getStringArray(R.array.julihuankuanshijian));
        mChoiceJulihuankuanriqi = mJulihuankuanriqiList.get(0);
        mHuankuanriqiAdapter = new ChoicePingtiaoPageAdapter(R.layout.item_jiekuanqixian_item_layout, mJulihuankuanriqiList);
        mHuankuanriqiAdapter.setCheckPosition(0);
        recycler_view_huankuanriqi.setLayoutManager(new GridLayoutManager(this, 3));
        recycler_view_huankuanriqi.setAdapter(mHuankuanriqiAdapter);
        mHuankuanriqiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mHuankuanriqiAdapter.setCheckPosition(position);
                mHuankuanriqiAdapter.notifyDataSetChanged();
                mChoiceJulihuankuanriqi = (String) adapter.getData().get(position);
                mRemainderRepayDaysTypePos = position;
            }
        });

    }

    /**
     * 筛选器 界面
     */
    @BindView(R.id.pingtiao_choice_layout)
    PingtiaoChoiceView2 mPingtiaoChoiceView2;

    @BindView(R.id.linear_layout)
    LinearLayout linear_layout;

    private void showPingtiaoShaixuanLayout(boolean isShow) {
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        rlp.addRule(RelativeLayout.BELOW, R.id.choice_layout);
        if (isShow) {
            rlp.topMargin = AutoSizeUtils.dp2px(this, 60);
            mPingtiaoChoiceView2.setVisibility(View.VISIBLE);

        } else {
            rlp.topMargin = AutoSizeUtils.dp2px(this, 10);
            mPingtiaoChoiceView2.setVisibility(View.GONE);
        }

        linear_layout.setLayoutParams(rlp);
        linear_layout.requestLayout();
    }

    private String  mQueryScopeType = "0";
    private String mSortType = "0";
    private void pingtiaoShaixuanLayout() {
        mPingtiaoChoiceView2.setChoiceListener(new PingtiaoChoiceView2.ListenerChoice() {
            @Override
            public void choiceChanged(String status, String sort) {
                mQueryScopeType = status;
                mSortType = sort;
                refreshFragment();
            }

            @Override
            public void shaixuan() {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.END);
                }
            }
        });
    }

    private void initDrawlayout() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
    }

    /**
     * 新建弹框
     */
    private void showCreateNewDialog() {
        if (mXinjianPingtiaoDialog == null) {
            mXinjianPingtiaoDialog = new XinjianPingtiaoDialog(MyPingtiaoActivity.this);
        }
        mXinjianPingtiaoDialog.show();
    }

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;


    //    @BindView(R.id.indicator)
//    MagicIndicator indicator;
//    @BindView(R.id.view_pager)
//    ViewPager viewPager;
    PingtiaoPagerAdapter mPingtiaoPagerAdapter;
    List<Fragment> mFragment = new ArrayList<>();

    private void initAll() {
        mFragment.add(DianziJietiaoFragment.newInstance());
        mFragment.add(DianziShoutiaoFragment.newInstance());
        mFragment.add(ZhizhiJietiaoFragment.newInstance());
        mFragment.add(ZhizhiShoutiaoFragment.newInstance());
//        mFragment.get(0).setArguments(recBundle);

            switch (mEnoteType){
                case DIAN_ZI:
                    switch (mUserRoleType){
                        case JIEKUANREN:
                            beforeChangePageFirst(mTitles.get(0));
                            break;
                        case CHUJIEREN:
                            beforeChangePageFirst(mTitles.get(1));
                            break;
                        default:
                            beforeChangePageFirst(mTitles.get(0));
                            break;
                    }
                    break;
                case DIAN_ZI_SHOU:
                    beforeChangePageFirst(mTitles.get(2));
                    break;
                case ZHI_ZHI:
                    beforeChangePageFirst(mTitles.get(3));
                    break;
                case ZHI_ZHI_SHOU:
                    beforeChangePageFirst(mTitles.get(4));
                    break;
                default:
                    beforeChangePageFirst(mTitles.get(0));
                    break;
            }
//        showPingtiaoShaixuanLayout(true);
    }


    ChoicePingtiaoPageDialog mChoicePingtiaoPageDialog;

    @OnClick({R.id.toolbar_back, R.id.create_new, R.id.choice_layout, R.id.queding, R.id.chongzhi, R.id.search_img})
    public void onPageClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                onBackPressed();
                break;
            case R.id.create_new:
                showCreateNewDialog();
                break;
            case R.id.choice_layout:
                showChoiceDialog();
                break;
            case R.id.queding:
                sureDrawerLayout();
                break;
            case R.id.chongzhi:
                resetDrawerLayout();
                break;
            case R.id.search_img:
                clickSearchName();
                break;

        }
    }

    /**
     * 重置按钮
     */
    private void resetDrawerLayout() {
        mJiekuanqixianAdapter.setCheckPosition(0);
        mJiekuanqixianAdapter.notifyDataSetChanged();
        mChoiceJiekuanqixian = mJiekuanqixianList.get(0);

        mHuankuanriqiAdapter.setCheckPosition(0);
        mHuankuanriqiAdapter.notifyDataSetChanged();
        mChoiceJulihuankuanriqi = mJulihuankuanriqiList.get(0);

        mLoanPeriodType = "0";
        mLoanPeriodTypePos = 0;
        mRemainderRepayDaysType = "0";
        mRemainderRepayDaysTypePos = 0;
    }

    /**
     * 确认按钮
     */
    private void sureDrawerLayout() {
        mDrawerLayout.closeDrawer(GravityCompat.END);
        mLoanPeriodType = mLoanPeriodTypePos +"";
        mRemainderRepayDaysType = mRemainderRepayDaysTypePos + "";
        refreshFragment();
    }

    @BindView(R.id.pingtiao_type_tv)
    TextView pingtiao_type_tv;
    private int mPos = 0;
    private void showChoiceDialog() {
        if (mChoicePingtiaoPageDialog == null) {
            mChoicePingtiaoPageDialog = new ChoicePingtiaoPageDialog(this, new ChoicePingtiaoPageDialog.ChoicePingtiaoPageInterface() {
                @Override
                public void choicePingtiaoPage(String choice) {
                    beforeChangePageNotFirst(choice);
                }
            });
            mChoicePingtiaoPageDialog.setPosition(mPos);
        }
        mChoicePingtiaoPageDialog.show();
    }

    private void beforeChangePageNotFirst(String choice){
        beforeChangePage(choice,false);
    }

    private void beforeChangePageFirst(String choice){
        beforeChangePage(choice,true);
    }



    /**
     *
     * @param choice  进入的fragment页面的title
     * @param isFirst 是否是首次进入加载
     */
    private void beforeChangePage(String choice,boolean isFirst){
        pingtiao_type_tv.setText(choice);
        switch (choice) {
            case "待还-电子借条":
                mEnoteType = 0;
                mUserRoleType = 1;
                mPos = 0;
                if(isFirst){
                    currentTabIndex = 0;
                    showPingtiaoShaixuanLayout(currentTabIndex==0);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.framelayout, mFragment.get(0))
                            .add(R.id.framelayout, mFragment.get(1))
                            .add(R.id.framelayout, mFragment.get(2))
                            .add(R.id.framelayout, mFragment.get(3))
                            .hide(mFragment.get(3)).hide(mFragment.get(2)).hide(mFragment.get(1))
                            .show(mFragment.get(0)).commit();

                    refreshFragment();
                }else {
                    fragmentChange(0);
                }
                break;
            case "待收-电子借条":
                mEnoteType = 0;
                mUserRoleType = 2;
                mPos = 1;
                if(isFirst){
                    currentTabIndex = 0;
                    showPingtiaoShaixuanLayout(currentTabIndex==0);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.framelayout, mFragment.get(0))
                            .add(R.id.framelayout, mFragment.get(1))
                            .add(R.id.framelayout, mFragment.get(2))
                            .add(R.id.framelayout, mFragment.get(3))
                            .hide(mFragment.get(3)).hide(mFragment.get(2)).hide(mFragment.get(1))
                            .show(mFragment.get(0)).commit();
                    refreshFragment();
                }else {
                    fragmentChange(0);
                }
                break;
            case "电子收条":
                mEnoteType = 1;
                mUserRoleType = 0;
                mPos = 2;
                if(isFirst){
                    currentTabIndex =1;
                    showPingtiaoShaixuanLayout(currentTabIndex==0);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.framelayout, mFragment.get(0))
                            .add(R.id.framelayout, mFragment.get(1))
                            .add(R.id.framelayout, mFragment.get(2))
                            .add(R.id.framelayout, mFragment.get(3))
                            .hide(mFragment.get(3)).hide(mFragment.get(2)).hide(mFragment.get(0))
                            .show(mFragment.get(1)).commit();
                    refreshFragment();
                }else {
                    fragmentChange(1);
                }
                break;
            case "纸质借条":
                mEnoteType = 2;
                mUserRoleType = 0;
                mPos = 3;
                if(isFirst){
                    currentTabIndex = 2;
                    showPingtiaoShaixuanLayout(currentTabIndex==0);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.framelayout, mFragment.get(0))
                            .add(R.id.framelayout, mFragment.get(1))
                            .add(R.id.framelayout, mFragment.get(2))
                            .add(R.id.framelayout, mFragment.get(3))
                            .hide(mFragment.get(3)).hide(mFragment.get(0)).hide(mFragment.get(1))
                            .show(mFragment.get(2)).commit();
                    refreshFragment();
                }else {
                    fragmentChange(2);
                }
                break;
            case "纸质收条":
                mEnoteType = 3;
                mUserRoleType = 0;
                mPos = 4;
                if(isFirst){
                    currentTabIndex = 3;
                    showPingtiaoShaixuanLayout(currentTabIndex==0);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.framelayout, mFragment.get(0))
                            .add(R.id.framelayout, mFragment.get(1))
                            .add(R.id.framelayout, mFragment.get(2))
                            .add(R.id.framelayout, mFragment.get(3))
                            .hide(mFragment.get(0)).hide(mFragment.get(2)).hide(mFragment.get(1))
                            .show(mFragment.get(3)).commit();
                    refreshFragment();
                }else {
                    fragmentChange(3);
                }
                break;
        }
    }

    @BindView(R.id.search_et)
    EditText search_et;

    String mQueryName = "";

    /**
     * 进入搜索
     */
    private void clickSearchName() {
        mQueryName = search_et.getText().toString();
        //搜索
        refreshFragment();
    }

    private void initSearchLayout() {
        search_et.setCursorVisible(false);//光标隐藏
        search_et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                search_et.setCursorVisible(true);
                return false;
            }
        });
        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mQueryName = s.toString();
            }
        });
        search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    // 当按了搜索之后关闭软键盘
                    KeyBoardUtil.hideKeyboard(search_et);
                    clickSearchName();
                    return true;
                }

                return false;
            }
        });
    }

    // 当前fragment的 index
    private int currentTabIndex = 0;

    private void fragmentChange(int tag) {
        if (tag != currentTabIndex) {
            showPingtiaoShaixuanLayout(tag==0);
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(mFragment.get(currentTabIndex));
            if (!mFragment.get(tag).isAdded()) {
                trx.add(R.id.framelayout, mFragment.get(tag));
            }
            trx.show(mFragment.get(tag)).commitAllowingStateLoss();
            currentTabIndex = tag;
            resetDrawerLayout();
        }
        refreshFragment();
    }

    private void refreshFragment(){
       if(mFragment.get(currentTabIndex) instanceof SearchPingtiaoListInterface){
           ((SearchPingtiaoListInterface)mFragment.get(currentTabIndex)).getPingtiaoList(
                   mEnoteType+"",
                   1,
                   mQueryName,
                   mQueryScopeType,
                   10,
                   mSortType,
                   mUserRoleType+"",
                    mLoanPeriodType,
                   mRemainderRepayDaysType
           );
       }
    }


}
