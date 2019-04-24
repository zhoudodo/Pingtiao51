package com.pingtiao51.armsmodule.mvp.ui.adapter;

import android.text.SpannableStringBuilder;

import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingTiaoSeachResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.rvadapter.HomeParentInterface;

import java.util.List;

public class HomeMultiAdapter extends BaseMultiItemQuickAdapter<PingTiaoSeachResponse,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public HomeMultiAdapter(List<PingTiaoSeachResponse> data) {
        super(data);
        addItemType(PingTiaoSeachResponse.SHOU_STATUS, R.layout.item_newpingtiao_layout);
        addItemType(PingTiaoSeachResponse.JIE_STATUS,R.layout.item_newpingtiao_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, PingTiaoSeachResponse item) {
        switch (helper.getItemViewType()){
            //借款
            case PingTiaoSeachResponse.JIE_STATUS:
                if("PAPER_OWE_NOTE".equals(item.getType())){
                    zhizhijietiao(helper,item);
                }else{
                    dianzijietiao(helper,item);
                }

                break;
            //收款
            case PingTiaoSeachResponse.SHOU_STATUS:
                helper.setImageDrawable(R.id.img_status,mContext.getResources().getDrawable(R.drawable.shoudao_icon));
                helper.setImageDrawable(R.id.money_icon_status,mContext.getResources().getDrawable(R.drawable.shou_icon));
                //屏蔽逾期
                helper.setGone(R.id.yuqi,false);
                //屏蔽借款金额
                helper.setGone(R.id.money_amount,false);

                SpannableStringBuilder shoukuan = new SpanUtils()
                        .append("收到  ").setBold().setFontSize(16,true).setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                        .append("¥"+item.getAmount()).setFontSize(16,true).setForegroundColor(mContext.getResources().getColor(R.color.orange_color_FF7057))
                        .create();
                //待收款金额
                helper.setText(R.id.back_money,shoukuan);


                SpannableStringBuilder dijiaoren = new SpanUtils()
                        .append("递交人:").setFontSize(14,true).
                                setForegroundColor(mContext.getResources().getColor(R.color.gray_color_828181))
                        .append(""+item.getLender()).setFontSize(14,true).
                                setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                        .create();
                //递交人：XXX
                helper.setText(R.id.commit_man,dijiaoren);


                SpannableStringBuilder haikuanshijian2 = new SpanUtils()
                        .append("还款时间:").setFontSize(14,true).
                                setForegroundColor(mContext.getResources().getColor(R.color.gray_color_828181))
                        .append(item.getRepaymentDate()).setFontSize(14,true).
                                setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                        .create();
                helper.setText(R.id.back_time,haikuanshijian2);

                helper.setGone(R.id.anli_icon, item.isHasAnli());
                break;

        }
    }


    private void dianzijietiao(BaseViewHolder helper, PingTiaoSeachResponse item){
        helper.setImageDrawable(R.id.img_status,mContext.getResources().getDrawable(R.drawable.daihuan_icon));
        helper.setImageDrawable(R.id.money_icon_status,mContext.getResources().getDrawable(R.drawable.jie_icon));
        String shouhuan = "待还";//待还 本人是借款人 显示出借人信息 代收本人是 出借人 显示借款人信息
        String manType = "出借人";
        String manName = "";
        String jine = "借款金额";
        //凭条用户角色类型 0:待还 1:代收
        if("0".equals(item.getBorrowAndLendState())){
            shouhuan = "待还";
            manType ="出借人";
            jine = "借款金额";
            manName = item.getLender();
        }else if("1".equals(item.getBorrowAndLendState())){
            shouhuan = "待收";
            manType = "借款人";
            jine = "出借金额";
            manName = item.getBorrower();
        }
        SpannableStringBuilder haikuan = new SpanUtils()
                .append(shouhuan+"  ").setBold().setFontSize(16,true).setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                .append("¥"+item.getTotalAmount()).setFontSize(16,true).setForegroundColor(mContext.getResources().getColor(R.color.orange_color_FF7057))
                .create();
        //待还款金额
        helper.setText(R.id.back_money,haikuan);

        SpannableStringBuilder jiekuan = new SpanUtils()
                .append(jine+":").setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.gray_color_828181))
                .append("¥"+item.getAmount()).setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                .create();
        //借款金额
        helper.setText(R.id.money_amount,jiekuan);


        SpannableStringBuilder chujieren = new SpanUtils()
                .append(manType+": ").setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.gray_color_828181))
                .append(manName).setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                .create();
        //出借人：XXX
        helper.setText(R.id.commit_man,chujieren);

        long overdueDay = 0;
        try {
            overdueDay = Long.valueOf(item.getOverDueDays());
        }catch (NumberFormatException e){
        }
        if(overdueDay > 0) {
            helper.setGone(R.id.yuqi,true);
            helper.setText(R.id.yuqi, "逾期" + overdueDay + "天");
        }else {
            helper.setGone(R.id.yuqi,false);
        }

        SpannableStringBuilder haikuanshijian = new SpanUtils()
                .append("还款时间:").setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.gray_color_828181))
                .append(item.getRepaymentDate()).setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                .create();
        helper.setText(R.id.back_time,haikuanshijian);

        helper.setGone(R.id.anli_icon, item.isHasAnli());
    }
    private void zhizhijietiao(BaseViewHolder helper, PingTiaoSeachResponse item){
        helper.setImageDrawable(R.id.img_status,mContext.getResources().getDrawable(R.drawable.daihuan_icon));
        helper.setImageDrawable(R.id.money_icon_status,mContext.getResources().getDrawable(R.drawable.jie_icon));
        String shouhuan = "借到";
        String manType = "出借人";
        String manName = item.getLender();
        String jine = "借款金额";
        //凭条用户角色类型 0:待还 1:代收
        SpannableStringBuilder haikuan = new SpanUtils()
                .append(shouhuan+"  ").setBold().setFontSize(16,true).setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                .append("¥"+item.getAmount()).setFontSize(16,true).setForegroundColor(mContext.getResources().getColor(R.color.orange_color_FF7057))
                .create();
        //待还款金额
        helper.setText(R.id.back_money,haikuan);

        SpannableStringBuilder jiekuan = new SpanUtils()
                .append(jine+":").setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.gray_color_828181))
                .append("¥"+item.getAmount()).setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                .create();
        //借款金额
        helper.setText(R.id.money_amount,jiekuan);


        SpannableStringBuilder chujieren = new SpanUtils()
                .append(manType+": ").setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.gray_color_828181))
                .append(manName).setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                .create();
        //出借人：XXX
        helper.setText(R.id.commit_man,chujieren);

        long overdueDay = 0;
        try {
            overdueDay = Long.valueOf(item.getOverDueDays());
        }catch (NumberFormatException e){
        }
        if(overdueDay > 0) {
            helper.setGone(R.id.yuqi,true);
            helper.setText(R.id.yuqi, "逾期" + overdueDay + "天");
        }else {
            helper.setGone(R.id.yuqi,false);
        }

        SpannableStringBuilder haikuanshijian = new SpanUtils()
                .append("还款时间:").setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.gray_color_828181))
                .append(item.getRepaymentDate()).setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                .create();
        helper.setText(R.id.back_time,haikuanshijian);

        helper.setGone(R.id.anli_icon, item.isHasAnli());
    }
}
