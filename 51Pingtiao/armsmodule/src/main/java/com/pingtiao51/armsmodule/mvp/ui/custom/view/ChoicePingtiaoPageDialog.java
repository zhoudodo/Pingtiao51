package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.ui.adapter.ChoicePingtiaoPageAdapter;
import com.zls.baselib.custom.view.dialog.FrameDialog;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class ChoicePingtiaoPageDialog extends FrameDialog {

    public interface ChoicePingtiaoPageInterface{
        public void choicePingtiaoPage(String choice);
    }

    public ChoicePingtiaoPageDialog(Activity context, ChoicePingtiaoPageInterface interfaces) {
        super(context,R.style.custom_dialog_top);
        mChoicePingtiaoPageInterface = interfaces;
    }
    ChoicePingtiaoPageInterface mChoicePingtiaoPageInterface;

    public void setPosition(int pos){
        mChoicePingtiaoPageAdapter.setCheckPosition(pos);
        mChoicePingtiaoPageAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getViewIds() {
        return R.layout.dialog_choice_pingtiao_page_layout;
    }


    RecyclerView recyclerView;
    ChoicePingtiaoPageAdapter mChoicePingtiaoPageAdapter;
    @Override
    protected void initView() {
        super.initView();
        List<String> datas = Arrays.asList(getContext().getResources().getStringArray(R.array.xuanzepingtiaoleixing));
        mChoicePingtiaoPageAdapter = new ChoicePingtiaoPageAdapter(R.layout.item_choice_pingtiao_page_layout,datas);
        findViewsId(R.id.close_btn, true);
        recyclerView = findViews(R.id.recycler_view);
        recyclerView.setAdapter(mChoicePingtiaoPageAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity,3));
        mChoicePingtiaoPageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mChoicePingtiaoPageAdapter.setCheckPosition(position);
                mChoicePingtiaoPageAdapter.notifyDataSetChanged();
                    if(mChoicePingtiaoPageInterface != null){
                        mChoicePingtiaoPageInterface.choicePingtiaoPage((String) adapter.getData().get(position));
                    }
                Observable.timer(200,TimeUnit.MILLISECONDS)
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                dismiss();
                            }
                        }).isDisposed();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_btn:
                dismiss();
                break;

        }
    }
    protected void initLocation() {
        WindowManager.LayoutParams params = getWindow()
                .getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setGravity(Gravity.TOP);
    }

}
