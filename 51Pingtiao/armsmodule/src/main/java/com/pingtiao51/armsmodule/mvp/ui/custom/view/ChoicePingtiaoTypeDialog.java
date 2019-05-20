package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.ui.adapter.ChoicePingtiaoAdapter;
import com.zls.baselib.custom.view.dialog.FrameDialog;

import java.util.Arrays;
import java.util.List;

public  class ChoicePingtiaoTypeDialog extends FrameDialog {

    public interface ChoicePingtiaoTypeInterface{
        public void choicePingtiaoType(String choice);
    }

    public ChoicePingtiaoTypeDialog(Activity context,ChoicePingtiaoTypeInterface interfaces) {
        super(context);
        mChoicePingtiaoTypeInterface = interfaces;
    }
    ChoicePingtiaoTypeInterface mChoicePingtiaoTypeInterface;
    @Override
    protected int getViewIds() {
        return R.layout.dialog_choice_pingtiao_type_layout;
    }

    RecyclerView mRecyclerView = null;
    ChoicePingtiaoAdapter mChoicePingtiaoAdapter;
    @Override
    protected void initView() {
        super.initView();
       List<String> datas = Arrays.asList(getContext().getResources().getStringArray(R.array.xuanzepingtiaoleixing));
        mChoicePingtiaoAdapter = new ChoicePingtiaoAdapter(R.layout.item_choice_pingtiao_type_layout,datas);
        mRecyclerView = findViews(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mChoicePingtiaoAdapter);
        mChoicePingtiaoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                dismiss();
                String choice = (String) adapter.getData().get(position);
                if(mChoicePingtiaoTypeInterface != null){
                    mChoicePingtiaoTypeInterface.choicePingtiaoType(choice);
                }
            }
        });
    }
}
