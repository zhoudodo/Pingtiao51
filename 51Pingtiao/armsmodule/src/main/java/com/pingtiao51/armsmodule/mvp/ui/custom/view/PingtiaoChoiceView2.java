package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.ui.adapter.NormalAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.autosize.utils.AutoSizeUtils;

public class PingtiaoChoiceView2 extends LinearLayout {

    public interface ListenerChoice {
        void choiceChanged(String status, String sort);
        void shaixuan();
    }

    ListenerChoice mListenerChoice;

    public void setChoiceListener(ListenerChoice lc) {
        mListenerChoice = lc;
    }

    public PingtiaoChoiceView2(Context context) {
        this(context, null);
    }

    public PingtiaoChoiceView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PingtiaoChoiceView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mUnbinder.unbind();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PingtiaoChoiceView2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    View rootView;
    Unbinder mUnbinder;

    private void initView() {
        this.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(rlp);
        rootView = View.inflate(getContext(), R.layout.include_pingtiao_choice2, null);
        this.addView(rootView);
        mUnbinder = ButterKnife.bind(this, rootView);
        initRecycler();
        initDatas();
    }

    @BindView(R.id.rv)
    RecyclerView recyclerView;
    NormalAdapter normalAdapter;
    private List<String> mDatas = new ArrayList<>();

    private void initRecycler() {
        normalAdapter = new NormalAdapter(R.layout.item_name_layout, mDatas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        normalAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        normalAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String name = (String) adapter.getData().get(position);
                choice(name);
            }
        });
    }

    private List<String> mJieKuanStatus, mSort;


    @BindView(R.id.jietiaostatus_tv)
    TextView jietiaostatus_tv;
    @BindView(R.id.paixufangshi_tv)
    TextView paixufangshi_tv;
    @BindView(R.id.show_layout)
    LinearLayout show_layout;


    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.img3)
    ImageView img3;

    private void initDatas() {


        show_layout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                show_layout.setVisibility(GONE);
                img2.setRotation(0);
                img3.setRotation(0);
                return true;
            }
        });

        mJieKuanStatus = Arrays.asList(getResources().getStringArray(R.array.jietiao_status));
        mSort = Arrays.asList(getResources().getStringArray(R.array.sort_fun));
        setFirstNames( 0, 0);
    }


    /**
     * @param jiekuan
     * @param sort
     */
    public void setFirstNames(int jiekuan, int sort) {
//        juese_tv.setText(mJuese.get(juese));
        jietiaostatus_tv.setText(mJieKuanStatus.get(jiekuan));
        //
//        paixufangshi_tv.setText(mSort.get(sort));
        paixufangshi_tv.setText("排序方式");
    }

    private int mType = 0;
    public final static int ALL = 0;//点击全部选项
    public final static int STATUS = 1;//点击借条状态选项
    public final static int SORT = 2;//点击排序方式选项

    /**
     * 角色选择结果
     * @return
     */
//    private String getJuese() {
//        int choice = 0;
//        String temp = juese_tv.getText().toString();
//        for(int i=0; i<mJuese.size(); i++){
//            if(mJuese.get(i).equals(temp)){
//                choice = i;
//                break;
//            }
//        }
//        return choice+"";
//    }

    /**
     * 凭条状态
     *
     * @return
     */
    public String getStatus() {


        int choice = 0;
        String temp = jietiaostatus_tv.getText().toString();
        for (int i = 0; i < mJieKuanStatus.size(); i++) {
            if (mJieKuanStatus.get(i).equals(temp)) {
                choice = i;
                break;
            }
        }
        return choice + "";
    }

    /**
     * 排序方式
     *
     * @return
     */
    public String getSort() {
        int choice = 0;
        String temp = paixufangshi_tv.getText().toString();
        for (int i = 0; i < mSort.size(); i++) {
            if (mSort.get(i).equals(temp)) {
                choice = i;
                break;
            }
        }
        if (choice == 0) {
            return null;
        } else {
            return (choice - 1) + "";
        }
    }

    private void choice(String name) {
        switch (mType) {
            case ALL:
//                juese_tv.setText(name);
                break;
            case STATUS:
                jietiaostatus_tv.setText(name);
                break;

            case SORT:
                paixufangshi_tv.setText(name);
                break;
        }
        mDatas.clear();
        normalAdapter.notifyDataSetChanged();
        show_layout.setVisibility(GONE);
//        img1.setRotation(0);
        img2.setRotation(0);
        img3.setRotation(0);
        if (mListenerChoice != null) {
            mListenerChoice.choiceChanged(getStatus(), getSort());
        }
    }

    private void showRv() {
        switch (mType) {
            case ALL:
                mDatas.clear();
//                mDatas.addAll(mJuese);

                break;
            case STATUS:
                mDatas.clear();
                mDatas.addAll(mJieKuanStatus);
                break;

            case SORT:
                mDatas.clear();
                mDatas.addAll(mSort);
                break;
        }
        int height = mDatas.size() * (AutoSizeUtils.dp2px(getContext(), 40) + 1);
        LinearLayout.LayoutParams llp = new LayoutParams(LayoutParams.MATCH_PARENT, height);
        recyclerView.setLayoutParams(llp);
        recyclerView.setAdapter(normalAdapter);
//        normalAdapter.notifyDataSetChanged(); 重新设置LayoutParam之后adapter无法通过此方法更新UI只能重设adapter
    }

    @OnClick({R.id.juese_layout, R.id.jietiaostatus_layout, R.id.paixufangshi_layout, R.id.shaixuan_layout})
    public void onPageClick(View v) {
        switch (v.getId()) {
            case R.id.juese_layout:
                mType = ALL;
                if (show_layout.getVisibility() == View.GONE) {
                    img1.setRotation(180);
                    img2.setRotation(0);
                    img3.setRotation(0);
                } else {
                    img1.setRotation(0);
                    img2.setRotation(0);
                    img3.setRotation(0);
                }
                if (show_layout.getVisibility() == View.GONE) {
                    show_layout.setVisibility(View.VISIBLE);
                    showRv();
                } else {
                    show_layout.setVisibility(View.GONE);
                    mDatas.clear();
                    normalAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.jietiaostatus_layout:
                mType = STATUS;
                if (show_layout.getVisibility() == View.GONE) {
                    img1.setRotation(0);
                    img2.setRotation(180);
                    img3.setRotation(0);
                } else {
                    img1.setRotation(0);
                    img2.setRotation(0);
                    img3.setRotation(0);
                }
                if (show_layout.getVisibility() == View.GONE) {
                    show_layout.setVisibility(View.VISIBLE);
                    showRv();
                } else {
                    show_layout.setVisibility(View.GONE);
                    mDatas.clear();
                    normalAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.paixufangshi_layout:
                mType = SORT;
                if (show_layout.getVisibility() == View.GONE) {
                    img1.setRotation(0);
                    img2.setRotation(0);
                    img3.setRotation(180);
                } else {
                    img1.setRotation(0);
                    img2.setRotation(0);
                    img3.setRotation(0);
                }
                if (show_layout.getVisibility() == View.GONE) {
                    show_layout.setVisibility(View.VISIBLE);
                    showRv();
                } else {
                    show_layout.setVisibility(View.GONE);
                    mDatas.clear();
                    normalAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.shaixuan_layout:
                if(mListenerChoice != null){
                    mListenerChoice.shaixuan();
                }
                if (show_layout.getVisibility() == View.GONE) {
                  //无操作
                } else {
                    //如果有展开则关闭
                    show_layout.setVisibility(View.GONE);
                    mDatas.clear();
                    normalAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
}



