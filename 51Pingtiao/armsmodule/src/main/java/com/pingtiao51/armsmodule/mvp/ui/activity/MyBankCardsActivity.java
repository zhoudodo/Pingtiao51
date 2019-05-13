package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.pingtiao51.armsmodule.di.component.DaggerMyBankCardsComponent;
import com.pingtiao51.armsmodule.mvp.contract.MyBankCardsContract;
import com.pingtiao51.armsmodule.mvp.model.entity.response.UserBankListResponse;
import com.pingtiao51.armsmodule.mvp.presenter.MyBankCardsPresenter;

import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.ui.adapter.BankCardListAdapter;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/30/2019 13:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MyBankCardsActivity extends BaseArmsActivity<MyBankCardsPresenter> implements MyBankCardsContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyBankCardsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_bank_cards; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
            initTitle();
            initRecycle();
    }
    private void initTitle(){
        setTitle("我的银行卡");
        if (findViewById(R.id.toolbar_title) != null) {
            ((TextView)findViewById(R.id.toolbar_title)).setTextColor(getResources().getColor(R.color.white_ffffff));
        }
        if (findViewById(R.id.right_img) != null) {
            ((ImageView)findViewById(R.id.right_img)).setVisibility(View.VISIBLE);
            ((ImageView)findViewById(R.id.right_img)).setImageDrawable(getResources().getDrawable(R.drawable.icon_addbankcard_right));
            ((ImageView)findViewById(R.id.right_img)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toAddBankCard();
                }
            });

        }


    }

    @OnClick({R.id.add_bankcard_btn})
    public void onPageClick(View v){
        switch (v.getId()){
            case R.id.add_bankcard_btn:
                //添加银行卡
                toAddBankCard();
                break;
        }
    }

    @BindView(R.id.recycler_view)
    RecyclerView  recycler_view;

    BankCardListAdapter mBankCardListAdapter;
    List<UserBankListResponse> mDatas = new ArrayList<>();

    private void initRecycle(){
        mBankCardListAdapter = new BankCardListAdapter(R.layout.item_mybankcards_layout,mDatas);
        recycler_view.setAdapter(mBankCardListAdapter);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        mBankCardListAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getBankList();
    }

    /**
     * 添加银行卡
     */
    private void toAddBankCard(){
        ActivityUtils.startActivity(AddBankCardActivity.class);
    }

    @Override
    public void onSuccessBanklist(List<UserBankListResponse> datas) {
        if(datas == null){
            return;
        }
        mDatas.clear();
        mDatas.addAll(datas);
        mBankCardListAdapter.notifyDataSetChanged();
    }
}
