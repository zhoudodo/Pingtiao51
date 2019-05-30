package com.pingtiao51.armsmodule.mvp.ui.adapter;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingTiaoSeachResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoDetailResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.rvadapter.HomeParentInterface;
import com.pingtiao51.armsmodule.mvp.ui.helper.GlideProxyHelper;
import com.pingtiao51.armsmodule.mvp.ui.helper.UrlDecoderHelper;

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
        addItemType(PingTiaoSeachResponse.SHOU_STATUS, R.layout.item_newpingtiao_layout2);
        addItemType(PingTiaoSeachResponse.JIE_STATUS,R.layout.item_newpingtiao_layout2);
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

//                helper.setImageDrawable(R.id.img_status,mContext.getResources().getDrawable(R.drawable.shoudao_icon));
                helper.setImageDrawable(R.id.dianzipingtiao_icon_status,mContext.getResources().getDrawable(R.drawable.shou_icon));
                helper.setImageDrawable(R.id.jianluetu,mContext.getResources().getDrawable(R.drawable.icon_shoutiao_bg));
                //屏蔽逾期
                helper.setGone(R.id.yuqi,false);


                SpannableStringBuilder shoukuan = new SpanUtils()
                        .append("收到  ").setBold().setFontSize(16,true).setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                        .append("¥"+item.getAmount()).setFontSize(16,true).setForegroundColor(mContext.getResources().getColor(R.color.orange_color_FF7057))
                        .create();
                //待收款金额
                helper.setText(R.id.daihuan_money,shoukuan);


                SpannableStringBuilder dijiaoren = new SpanUtils()
                        .append("递交人:").setFontSize(14,true).
                                setForegroundColor(mContext.getResources().getColor(R.color.gray_color_828181))
                        .append(""+item.getLender()).setFontSize(14,true).
                                setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                        .create();
                //递交人：XXX
                helper.setText(R.id.jiekuan_money,dijiaoren);


                SpannableStringBuilder haikuanshijian2 = new SpanUtils()
                        .append("还款时间:").setFontSize(14,true).
                                setForegroundColor(mContext.getResources().getColor(R.color.gray_color_828181))
                        .append(item.getRepaymentDate()).setFontSize(14,true).
                                setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                        .create();
                helper.setText(R.id.huankuan_shijian,haikuanshijian2);

                helper.setGone(R.id.anli_icon2, item.isHasAnli());
                if("PAPER_RECEIPT_NOTE".equals(item.getType())){
                    //纸质收条 多一个经手人
                    helper.setGone(R.id.chujieren, true);
                    SpannableStringBuilder jingshouren = new SpanUtils()
                            .append("经手人:").setFontSize(14,true).
                                    setForegroundColor(mContext.getResources().getColor(R.color.gray_color_828181))
                            .append(""+item.getBorrower()).setFontSize(14,true).
                                    setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                            .create();
                    //递交人：XXX
                    helper.setText(R.id.chujieren,jingshouren);
                }else{
                    //电子收条 少一个经手人  so
                    helper.setGone(R.id.chujieren,false);
                }
                if(TextUtils.isEmpty(item.getViewUrl())) {
                    helper.setImageDrawable(R.id.jianluetu, mContext.getResources().getDrawable(R.drawable.icon_shoutiao_bg));
                }else{
                    GlideProxyHelper.loadImgByPlaceholder(helper.getView(R.id.jianluetu),R.drawable.icon_shoutiao_bg,UrlDecoderHelper.decode(item.getViewUrl()));
                }


                break;

        }
    }


    private void dianzijietiao(BaseViewHolder helper, PingTiaoSeachResponse item){
//        helper.setImageDrawable(R.id.img_status,mContext.getResources().getDrawable(R.drawable.daihuan_icon));
        helper.setImageDrawable(R.id.dianzipingtiao_icon_status,mContext.getResources().getDrawable(R.drawable.jie_icon));
        helper.setImageDrawable(R.id.jianluetu,mContext.getResources().getDrawable(R.drawable.icon_jietiao_bg));
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
        helper.setText(R.id.daihuan_money,haikuan);

        SpannableStringBuilder jiekuan = new SpanUtils()
                .append(jine+":").setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.gray_color_828181))
                .append("¥"+item.getAmount()).setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                .create();
        //借款金额
        helper.setText(R.id.jiekuan_money,jiekuan);


        SpannableStringBuilder chujieren = new SpanUtils()
                .append(manType+": ").setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.gray_color_828181))
                .append(manName).setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                .create();
        //出借人：XXX
        helper.setText(R.id.chujieren,chujieren);

//        long overdueDay = 0;
//        try {
//            overdueDay = Long.valueOf(item.getOverDueDays());
//        }catch (NumberFormatException e){
//        }
//        if(overdueDay > 0) {
//            helper.setGone(R.id.yuqi,true);
//            helper.setText(R.id.yuqi, "逾期" + overdueDay + "天");
//        }else {
//            helper.setGone(R.id.yuqi,false);
//        }

        String yuqi_flag = getStatus(helper, item);
        if (yuqi_flag.contains("完结")) {
            helper.setTextColor(R.id.yuqi, mContext.getResources().getColor(R.color.blue_color_467BB6));
            helper.getView(R.id.yuqi).setBackground(mContext.getResources().getDrawable(R.drawable.yuqi_bg2));
        } else {
            helper.setTextColor(R.id.yuqi, mContext.getResources().getColor(R.color.red_color_ED4641));
            if(TextUtils.isEmpty(yuqi_flag)){
                helper.getView(R.id.yuqi).setBackground(null);
            }else {
                helper.getView(R.id.yuqi).setBackground(mContext.getResources().getDrawable(R.drawable.yuqi_bg));
            }
        }
        helper.setText(R.id.yuqi, yuqi_flag);

        SpannableStringBuilder haikuanshijian = new SpanUtils()
                .append("还款时间:").setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.gray_color_828181))
                .append(item.getRepaymentDate()).setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                .create();
        helper.setText(R.id.huankuan_shijian,haikuanshijian);

        helper.setGone(R.id.anli_icon2, item.isHasAnli());
    }
    private void zhizhijietiao(BaseViewHolder helper, PingTiaoSeachResponse item){
//        helper.setImageDrawable(R.id.img_status,mContext.getResources().getDrawable(R.drawable.daihuan_icon));
        helper.setImageDrawable(R.id.dianzipingtiao_icon_status,mContext.getResources().getDrawable(R.drawable.jie_icon));
        if(TextUtils.isEmpty(item.getViewUrl())) {
            helper.setImageDrawable(R.id.jianluetu, mContext.getResources().getDrawable(R.drawable.icon_jietiao_bg));
        }else{
            GlideProxyHelper.loadImgByPlaceholder(helper.getView(R.id.jianluetu),R.drawable.icon_jietiao_bg,UrlDecoderHelper.decode(item.getViewUrl()));
        }
        String shouhuan = "借到";
        String manType = "出借人";
        String manName = item.getLender();
        String jine = "借款人";
        String jiekuanrenName = item.getBorrower();
        //凭条用户角色类型 0:待还 1:代收
        SpannableStringBuilder haikuan = new SpanUtils()
                .append(shouhuan+"  ").setBold().setFontSize(16,true).setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                .append("¥"+item.getAmount()).setFontSize(16,true).setForegroundColor(mContext.getResources().getColor(R.color.orange_color_FF7057))
                .create();
        //待还款金额
        helper.setText(R.id.daihuan_money,haikuan);

        SpannableStringBuilder jiekuan = new SpanUtils()
                .append(jine+":").setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.gray_color_828181))
                .append(jiekuanrenName).setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                .create();
        //借款金额
        helper.setText(R.id.jiekuan_money,jiekuan);


        SpannableStringBuilder chujieren = new SpanUtils()
                .append(manType+":").setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.gray_color_828181))
                .append(manName).setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                .create();
        //出借人：XXX
        helper.setText(R.id.chujieren,chujieren);

/*        long overdueDay = 0;
        try {
            overdueDay = Long.valueOf(item.getOverDueDays());
        }catch (NumberFormatException e){
        }
        if(overdueDay > 0) {
            helper.setGone(R.id.yuqi,true);
            helper.setText(R.id.yuqi, "逾期" + overdueDay + "天");
        }else {
            helper.setGone(R.id.yuqi,false);
        }*/

        String yuqi_flag = getStatus(helper, item);
        if (yuqi_flag.contains("完结")) {
            helper.setTextColor(R.id.yuqi, mContext.getResources().getColor(R.color.blue_color_467BB6));
            helper.getView(R.id.yuqi).setBackground(mContext.getResources().getDrawable(R.drawable.yuqi_bg2));
        } else {
            helper.setTextColor(R.id.yuqi, mContext.getResources().getColor(R.color.red_color_ED4641));
            if(TextUtils.isEmpty(yuqi_flag)){
                helper.getView(R.id.yuqi).setBackground(null);
            }else {
                helper.getView(R.id.yuqi).setBackground(mContext.getResources().getDrawable(R.drawable.yuqi_bg));
            }
        }
        helper.setText(R.id.yuqi, yuqi_flag);



        SpannableStringBuilder haikuanshijian = new SpanUtils()
                .append("还款时间:").setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.gray_color_828181))
                .append(item.getRepaymentDate()).setFontSize(14,true).
                        setForegroundColor(mContext.getResources().getColor(R.color.black_color_404040))
                .create();
        helper.setText(R.id.huankuan_shijian,haikuanshijian);

        helper.setGone(R.id.anli_icon2, item.isHasAnli());
    }

    public static String getStatus(BaseViewHolder helper, PingTiaoSeachResponse item) {
        String value = item.getStatus();
        String overDueDays = item.getOverDueDays();
        if(value == null || overDueDays == null){
            return "";
        }
        switch (value) {
            case "DRAFT":
                return "";
            case "UNHANDLED":
                return "待确认";
            case "OVERDUE":
                return "已逾期" + overDueDays + "天";
            case "LENDER_FINISHED":
                return "已完结";
            case "BORROWER_FINISHED":
                return "借款人完结";
            case "CONFIRMED"://确认的
                if("UNSIGNED".equals(item.getSignStatus())){
                    return "待确认";
                }
                return "";
            case "REJECTED"://REJECTED
                return "被驳回";
            case "BORROWER_CLOSED"://借款人删除
                return "";
            case "LENDER_CLOSED"://出借人删除
                return "";
            case "CLOSED"://CLOSED
                return "";
        }
        return "";
    }
}
