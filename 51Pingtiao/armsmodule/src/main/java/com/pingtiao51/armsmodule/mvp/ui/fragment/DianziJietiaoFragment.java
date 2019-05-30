package com.pingtiao51.armsmodule.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.di.component.DaggerDianziJietiaoComponent;
import com.pingtiao51.armsmodule.mvp.contract.DianziJietiaoContract;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.YiHuanKuanTag;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoDetailListResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoDetailResponse;
import com.pingtiao51.armsmodule.mvp.presenter.DianziJietiaoPresenter;
import com.pingtiao51.armsmodule.mvp.ui.activity.DianziJietiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.DianziShoutiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiJietiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiShoutiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.adapter.PingtiaoMultiAdapter;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.AdvanceSwipeRefreshLayout;
import com.pingtiao51.armsmodule.mvp.ui.helper.others.MoneyFormatUtils;
import com.pingtiao51.armsmodule.mvp.ui.interfaces.SearchPingtiaoListInterface;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.autosize.utils.AutoSizeUtils;

import static com.pingtiao51.armsmodule.mvp.ui.activity.DianziJietiaoXiangqingActivity.PING_TIAO_XIANG_QING;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/14/2019 21:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class DianziJietiaoFragment extends BaseArmFragment<DianziJietiaoPresenter> implements DianziJietiaoContract.View
        , SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, SearchPingtiaoListInterface {

    public static DianziJietiaoFragment newInstance() {
        DianziJietiaoFragment fragment = new DianziJietiaoFragment();
        return fragment;
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerDianziJietiaoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }


    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dianzi_jietiao2, container, false);
    }

    @BindView(R.id.pingtiao_infos)
    LinearLayout pingtiao_infos;


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        initInfos();
        initXiangqingLine();
        initRefresh();
        initRecyclerView();
        initPingtiaoInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPage = 1;
        reqDatas(searchName, statusReq, sortReq, jueseReq, loanPeriodType, remainderRepayDaysType);
    }


    String searchName = "";
    String jueseReq = "0";
    String statusReq = "0";
    String sortReq = null;
    String loanPeriodType = "0";
    String remainderRepayDaysType = "0";


    private void changeHints(String juese) {
        switch (juese) {
            case "1":
                jiekuanzonge_hint.setText(getResources().getString(R.string.jiekuanzonge));
                daihuanzonge_hint.setText(getResources().getString(R.string.daihuanzonge));
                break;
            case "2":
                jiekuanzonge_hint.setText(getResources().getString(R.string.chujiezonge));
                daihuanzonge_hint.setText(getResources().getString(R.string.daishouzonge));
                break;
            default:
                break;
        }
    }

    @BindView(R.id.daihuanzonge_hint)
    TextView daihuanzonge_hint;

    @BindView(R.id.jiekuanzonge_hint)
    TextView jiekuanzonge_hint;

    //借条数
    @BindView(R.id.jietiao_nums)
    TextView jietiao_nums;

    //借款总额元
    @BindView(R.id.jiekuanzonge)
    TextView jiekuanzonge;

    //待还总额元
    @BindView(R.id.daihuanzonge)
    TextView daihuanzonge;

    private void initInfos(PingtiaoDetailListResponse response) {
        jietiao_nums.setText(response.getTotal() + "");
        jiekuanzonge.setText(MoneyFormatUtils.coverTenThousand(response.getBorrowAmount()));
        daihuanzonge.setText(MoneyFormatUtils.coverTenThousand(response.getRetAmount()));
    }

    @BindView(R.id.input_name)
    TextView input_name;

    @BindView(R.id.more_layout)
    LinearLayout more_layout;

    private void initXiangqingLine() {
        more_layout.setVisibility(View.GONE);
        input_name.setText("电子借条详情");
    }

    @BindView(R.id.refresh_layout)
    AdvanceSwipeRefreshLayout refresh_layout;

    private void initRefresh() {
        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setOnPreInterceptTouchEventDelegate(new AdvanceSwipeRefreshLayout.OnPreInterceptTouchEventDelegate() {
            @Override
            public boolean shouldDisallowInterceptTouchEvent(MotionEvent ev) {
                boolean isFirstItemVisible = linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0;
                boolean isCanRefresh = recyclerView.getTop() >= rvTop;
//                Log.d("dodo"," isFirstItemVisible = " + isFirstItemVisible);
                if (isFirstItemVisible && isCanRefresh) {
                    return false;
                } else {
                    return true;
                }
            }
        });
    }


    @BindView(R.id.no_layout)
    LinearLayout no_layout;

    @BindView(R.id.stick_layout)
    RelativeLayout stick_layout;
    @BindView(R.id.pingtiaoxiangqing)
    RelativeLayout pingtiaoxiangqing;
    private int height = 0;
    private int rvTop = 0;


    @BindView(R.id.rv)
    RecyclerView recyclerView;
    PingtiaoMultiAdapter mPingtiaoMultiAdapter;
    LinearLayoutManager linearLayoutManager;

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        mPingtiaoMultiAdapter = new PingtiaoMultiAdapter(mDatas);
        recyclerView.setAdapter(mPingtiaoMultiAdapter);

//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        mPingtiaoMultiAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mPingtiaoMultiAdapter.setOnLoadMoreListener(this, recyclerView);
        mPingtiaoMultiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PingtiaoDetailResponse rep = (PingtiaoDetailResponse) adapter.getData().get(position);
                //凭条类型 OWE_NOTE 电子借条 PAPPER_OWE_NOTE纸质借条 PAPER_RECEIPT_NOTE 纸质收条 RECEIPT_NOTE电子收条
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

        });
    }

    private List<PingtiaoDetailResponse> mDatas = new ArrayList<>();


    @Override
    public void setData(@Nullable Object data) {

    }

    boolean isRefresh = false;

    @Override
    public void onRefresh() {
        mPingtiaoMultiAdapter.setEnableLoadMore(false);
        mPage = 1;
        isRefresh = true;
        isLoadMore = false;
        reqDatas(searchName, statusReq, sortReq, jueseReq, loanPeriodType, remainderRepayDaysType);
    }

    boolean isLoadMore = false;

    @Override
    public void onLoadMoreRequested() {
        refresh_layout.setEnabled(false);
        mPingtiaoMultiAdapter.setEnableLoadMore(true);
        isLoadMore = true;
        isRefresh = false;
        mPage++;
        reqDatas(searchName, statusReq, sortReq, jueseReq, loanPeriodType, remainderRepayDaysType);
//        mPingtiaoMultiAdapter.notifyDataSetChanged();
//        mPingtiaoMultiAdapter.loadMoreComplete();
//        refresh_layout.setEnabled(false);
    }

    @Override
    public void onSucPingtiaoList(PingtiaoDetailListResponse rep) {
        initInfos(rep);
        //数据返回
        List<PingtiaoDetailResponse> list = rep.getList();
        for (PingtiaoDetailResponse response : list) {
            response.itemType = PingtiaoDetailResponse.DIANZI_JIETIAO;
        }
        if (list.size() <= 0 && !isLoadMore) {
            no_layout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            refresh_layout.setRefreshing(false);
            return;
        } else {
            no_layout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        if (isRefresh) {
            //刷新
            refresh_layout.setRefreshing(false);
            mDatas.clear();
            mDatas.addAll(list);
            mPingtiaoMultiAdapter.notifyDataSetChanged();
        } else if (isLoadMore) {
            //加载更多
            mDatas.addAll(list);
            mPingtiaoMultiAdapter.notifyDataSetChanged();
            if (list.size() >= SIZE) {
                mPingtiaoMultiAdapter.loadMoreComplete();
            } else {
                mPingtiaoMultiAdapter.loadMoreEnd();
            }
        } else {
            //首次加载
            mDatas.clear();
            mDatas.addAll(list);
            mPingtiaoMultiAdapter.notifyDataSetChanged();
        }
        isRefresh = false;
        isLoadMore = false;
        refresh_layout.setEnabled(true);
        mPingtiaoMultiAdapter.setEnableLoadMore(true);
        if (isYihuankuan) {
            isYihuankuan = false;
            recyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void onErrorPingtiaoList(String msg) {
        ArmsUtils.snackbarText(msg);
        if (isRefresh) {
            refresh_layout.setRefreshing(false);
        }
        if (isLoadMore) {
            mPingtiaoMultiAdapter.loadMoreFail();
        }
        isRefresh = false;
        isLoadMore = false;
        refresh_layout.setEnabled(true);
        mPingtiaoMultiAdapter.setEnableLoadMore(true);
    }

    private int mPage = 1;
    private final int SIZE = 10;

    public void reqDatas(
            String queryName,
            String queryScopeType,
            String sortType,
            String userRoleType,
            String loanPeriodType,
            String remainderRepayDaysType
    ) {
        mPresenter.getPingtiaoList(
                "0",// "0:电子借条 1:电子收条2：纸质借条3：纸质收条",
                mPage,
                queryName,
                queryScopeType,//查询范围类型 0：全部 1：未到期 2：已逾期 3：未生效4：已完结
                SIZE,
                sortType,//0:还款时间从晚到早 1: 还款时间从早到晚 2:借款金额从少到多 3:借款金额从多到少
                userRoleType,//用户角色 0：全部 1:借款人 2：出借人
                loanPeriodType,
                remainderRepayDaysType
        );
    }


    boolean isYihuankuan = false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void yihuankuan(YiHuanKuanTag tag) {
        onRefresh();
        isYihuankuan = true;
    }

    private void initPingtiaoInfo() {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AutoSizeUtils.dp2px(getActivity(), 120));
        lp.addRule(RelativeLayout.BELOW, R.id.search_layout);
        lp.leftMargin = AutoSizeUtils.dp2px(getActivity(), 8);
        lp.rightMargin = AutoSizeUtils.dp2px(getActivity(), 8);
        pingtiao_infos.setLayoutParams(lp);
        pingtiao_infos.requestLayout();
    }

    @Override
    public void getPingtiaoList(String enoteType, int page, String queryName, String queryScopeType, int size, String sortType, String userRoleType, String loanPeriodType, String remainderRepayDaysType) {
        searchName = queryName;
        statusReq = queryScopeType;
        sortReq = sortType;
        jueseReq = userRoleType;
        this.loanPeriodType = loanPeriodType;
        this.remainderRepayDaysType = remainderRepayDaysType;
        if(mPresenter != null) {
            mPresenter.getPingtiaoList(enoteType, page, queryName, queryScopeType, size, sortType, userRoleType, loanPeriodType, remainderRepayDaysType);
        }
        }
}
