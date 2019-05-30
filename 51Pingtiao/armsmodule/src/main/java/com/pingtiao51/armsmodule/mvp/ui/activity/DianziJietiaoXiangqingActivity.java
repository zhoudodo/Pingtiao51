package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.app.utils.OssManager;
import com.pingtiao51.armsmodule.di.component.DaggerDianziJietiaoXiangqingComponent;
import com.pingtiao51.armsmodule.mvp.contract.DianziJietiaoXiangqingContract;
import com.pingtiao51.armsmodule.mvp.model.api.Api;
import com.pingtiao51.armsmodule.mvp.model.api.service.PingtiaoApi;
import com.pingtiao51.armsmodule.mvp.model.entity.request.CloseElectronicNoteRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.FinishElectronicNoteRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoDetailResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoXiangqingResponse;
import com.pingtiao51.armsmodule.mvp.presenter.DianziJietiaoXiangqingPresenter;
import com.pingtiao51.armsmodule.mvp.ui.adapter.PingtiaoXqImgAdapter;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.DownloadPingtiaoDialog;
import com.pingtiao51.armsmodule.mvp.ui.helper.DateUtils;
import com.pingtiao51.armsmodule.mvp.ui.helper.ImagePaizhaoHelper;
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst;
import com.pingtiao51.armsmodule.mvp.ui.helper.UrlDecoderHelper;
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference;
import com.zls.baselib.custom.view.dialog.DialogChooseNormal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/26/2019 15:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class DianziJietiaoXiangqingActivity extends BaseArmsActivity<DianziJietiaoXiangqingPresenter> implements DianziJietiaoXiangqingContract.View {

    public final static String PING_TIAO_XIANG_QING = "PING_TIAO_XIANG_QING";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDianziJietiaoXiangqingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @BindView(R.id.nsv)
    NestedScrollView nsv;

    @BindView(R.id.dianzijietiao_rootview_more)
    LinearLayout dianzijietiao_rootview_more;
    @BindView(R.id.jietiao_shousuolan)
    LinearLayout jietiao_shousuolan;
    @BindView(R.id.jietiao_shousuolan_mingcheng)
    TextView jietiao_shousuolan_mingcheng;


    @BindView(R.id.dianzijietiao_huankuan_jiantou)
    ImageView dianzijietiao_huankuan_jiantou;


    @BindView(R.id.yihuan_jine_layout)
    RelativeLayout yihuan_jine_layout;

    @BindView(R.id.jietiao_xq_history_yihuan)
    TextView jietiao_xq_history_yihuan;


    @BindView(R.id.jietiao_xq_chakan)
    TextView jietiao_xq_chakan;
    @BindView(R.id.jietiao_xq_xiazai)
    TextView jietiao_xq_xiazai;

    @BindView(R.id.jietiao_xq_jiekuanren)
    TextView jietiao_xq_jiekuanren;
    @BindView(R.id.jietiao_xq_chujieren)
    TextView jietiao_xq_chujieren;
    @BindView(R.id.jietiao_xq_jiekuanjine)
    TextView jietiao_xq_jiekuanjine;
    @BindView(R.id.jietiao_xq_jiekuanshijian)
    TextView jietiao_xq_jiekuanshijian;
    @BindView(R.id.jietiao_xq_huankuanshijian)
    TextView jietiao_xq_huankuanshijian;
    @BindView(R.id.jietiao_xq_huankuanfangshi)
    TextView jietiao_xq_huankuanfangshi;
    @BindView(R.id.jietiao_xq_nianhualilv)
    TextView jietiao_xq_nianhualilv;
    @BindView(R.id.jietiao_xq_lixiheji)
    TextView jietiao_xq_lixiheji;
    @BindView(R.id.jietiao_xq_jiekuanyongtu)
    TextView jietiao_xq_jiekuanyongtu;
    @BindView(R.id.jietiao_xq_pingtiaobianhao)
    TextView jietiao_xq_pingtiaobianhao;
    @BindView(R.id.jietiao_xq_chuangjianshijian)
    TextView jietiao_xq_chuangjianshijian;
    @BindView(R.id.jietiao_xq_pingtiaozhuangtai)
    TextView jietiao_xq_pingtiaozhuangtai;
    @BindView(R.id.jietiao_xq_pingzhengtupian)
    ImageView jietiao_xq_pingzhengtupian;

    @BindView(R.id.pingzheng_hint)
    TextView pingzheng_hint;


    @BindView(R.id.falv_layout)
    LinearLayout falv_layout;
    @BindView(R.id.gongzhengchu)
    TextView gongzhengchu;
    @BindView(R.id.gongzhengbianhao)
    TextView gongzhengbianhao;
    @BindView(R.id.cunzhengshijian)
    TextView cunzhengshijian;


    @BindView(R.id.jietiao_xq_history_ququeren_layout)
    RelativeLayout jietiao_xq_history_ququeren_layout;
    @BindView(R.id.jietiao_xq_history_huankuan)
    TextView jietiao_xq_history_huankuan;
    @BindView(R.id.jietiao_xq_history_req_time)
    TextView jietiao_xq_history_req_time;
    @BindView(R.id.jietiao_xq_history_ququeren)
    ImageView jietiao_xq_history_ququeren;

    @BindView(R.id.huankuanjilu_layout)
    LinearLayout huankuanjilu_layout;


    private double historyYihuanjine;

    private void initHuankuanLayout(PingtiaoXiangqingResponse rep) {
        hintChujieren(false, "", "");
        boolean isVisible = ("1".equals(rep.getHasApplyRepayRecord())) && "1".equals(rep.getBorrowAndLendState());
        for (PingtiaoXiangqingResponse.RepayRecords records : rep.getRepayRecords()) {
            if ("APPLY".equals(records.getStatus())) {
                hintChujieren(isVisible, records.getAmount() + "", records.getCreateTime());
            } else if ("SUCCESS".equals(records.getStatus())) {
                historyYihuanjine = records.getAmount();
            }
        }
    }

    /**
     * 还款之后提醒借款人layout
     *
     * @param isVisble
     * @param huankuanAmount
     * @param time
     */

    private void hintChujieren(boolean isVisble, String huankuanAmount, String time) {
        jietiao_xq_history_ququeren_layout.setVisibility(isVisble ? View.VISIBLE : View.GONE);
        if (!isVisble && mPingtiaoXiangqingResponse.getRepayRecords().size() <= 0) {
            huankuanjilu_layout.setVisibility(View.GONE);
        } else {
            huankuanjilu_layout.setVisibility(View.VISIBLE);
        }
        SpannableStringBuilder ssb1 = new SpanUtils()
                .append("还款").setForegroundColor(getResources().getColor(R.color.black_color_333333))
                .append("¥" + huankuanAmount + "元").setForegroundColor(getResources().getColor(R.color.orange_color_FF6142))
                .create();
        jietiao_xq_history_huankuan.setText(ssb1);


        SpannableStringBuilder ssb2 = new SpanUtils()
                .append("申请时间 ")
                .append(time)
                .create();
        jietiao_xq_history_req_time.setText(ssb2);

    }


    @BindView(R.id.jietiao_xq_chujieren1)
    TextView jietiao_xq_chujieren1;
    @BindView(R.id.chujiejiekuan)
    TextView chujiejiekuan;
    @BindView(R.id.jietiao_xq_daoqishijian)
    TextView jietiao_xq_daoqishijian;
    @BindView(R.id.daihuanshou)
    TextView daihuanshou;
    @BindView(R.id.jietiao_xq_daihuanjine)
    TextView jietiao_xq_daihuanjine;

    /**
     * 借条简介
     */
    private void jietiaojianjie(PingtiaoXiangqingResponse rep) {
        String borrowAndLendState = rep.getBorrowAndLendState();// 0：借款人 1：出借人
        //显示对方信息
        switch (borrowAndLendState) {
            case "1":
                daihuanshou.setText("待收：");
                setDaihuanjine(rep.getTotalAmount());
                chujiejiekuan.setText("借款人：");
                jietiao_xq_chujieren1.setText(rep.getBorrower());
                jietiao_xq_daoqishijian.setText(rep.getRepaymentDate());
                break;
            case "0":
                daihuanshou.setText("待还：");
                setDaihuanjine(rep.getTotalAmount());
                chujiejiekuan.setText("出借人：");
                jietiao_xq_chujieren1.setText(rep.getLender());
                jietiao_xq_daoqishijian.setText(rep.getRepaymentDate());

                break;
        }
    }

    /**
     * 法律保证
     */
    private void falvbaozheng() {
        if (mPingtiaoXiangqingResponse != null && mPingtiaoXiangqingResponse.getOwnershipRecordDto() != null
                && mVisible && !"REJECTED".equals(mPingtiaoXiangqingResponse.getStatus())) {
            falv_layout.setVisibility(View.VISIBLE);
            gongzhengchu.setText(mPingtiaoXiangqingResponse.getOwnershipRecordDto().getCertificateStructure());
            gongzhengbianhao.setText(mPingtiaoXiangqingResponse.getOwnershipRecordDto().getOwnershipOrderNo());
            cunzhengshijian.setText(mPingtiaoXiangqingResponse.getOwnershipRecordDto().getCertificateDate());
        } else {
            falv_layout.setVisibility(View.GONE);
        }
       /* @BindView(R.id.falv_layout)
        LinearLayout falv_layout;
        @BindView(R.id.gongzhengchu)
        TextView gongzhengchu;
        @BindView(R.id.gongzhengbianhao)
        TextView gongzhengbianhao;
        @BindView(R.id.cunzhengshijian)
        TextView cunzhengshijian;*/
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_dianzi_jietiao_xiangqing; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    private final static int MAX_PIC = 6;//照片最多6张
    private long id;
    PingtiaoXqImgAdapter mPingtiaoXqImgAdapter;
    private List<String> mDatas = new ArrayList<>();

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("电子借条");
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra(PING_TIAO_XIANG_QING, 0);
        }
        mPresenter.getPingtiaoById(id);

        mPingtiaoXqImgAdapter = new PingtiaoXqImgAdapter(R.layout.item_pingtiao_xq_img_layout, mDatas);
        jietiao_xq_rv.setLayoutManager(new GridLayoutManager(this, 3));
        jietiao_xq_rv.setAdapter(mPingtiaoXqImgAdapter);
        mPingtiaoXqImgAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String itemStr = (String) adapter.getData().get(position);
                List<String> datas = adapter.getData();
                int overPicNum = 0;
                List<String> shows = new ArrayList<>();
                boolean isMax = false;
                if (PingtiaoXqImgAdapter.ADD_BTN.equals(datas.get(0))) {
                    overPicNum = MAX_PIC - datas.size() + 1;
                    shows.addAll(datas.subList(1, datas.size()));
                    isMax = false;
                } else {
                    isMax = true;
                    overPicNum = MAX_PIC - datas.size();
                    shows.addAll(datas);
                }
                if (PingtiaoXqImgAdapter.ADD_BTN.equals(itemStr)) {
                    //添加图片
                    ImagePaizhaoHelper.modifyPingzheng(DianziJietiaoXiangqingActivity.this, overPicNum);
                } else {
                    //展示图片
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList(PhotoViewPagerActivity.TAG, (ArrayList<String>) shows);
                    bundle.putString(PhotoViewPagerActivity.TITLE, "图片");
                    bundle.putInt(PhotoViewPagerActivity.POSITION, isMax ? position : position - 1);
                    startActBundle(bundle, PhotoViewPagerActivity.class);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //由于图片加载机制不允许在onResume加载数据
//        mPresenter.getPingtiaoById(id);
    }

    private List<LocalMedia> selectList = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==  NEXT_REQ){
            mPresenter.getPingtiaoById(id);
            return;
        }

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调

                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (int i = 0; i < selectList.size(); i++) {
                        LocalMedia media = selectList.get(i);
                        mDatas.add(media.getCompressPath());
                        uploadFile(media, i);
                    }
                    if (mDatas.size() > MAX_PIC && mDatas.get(0).equals(PingtiaoXqImgAdapter.ADD_BTN)) {
                        mDatas.remove(0);
                        pingzheng_hint.setVisibility(View.GONE);
                    }
                    mPingtiaoXqImgAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }


    private List<String> upLoadUrls = new ArrayList<>();

    private void uploadFile(LocalMedia media, int position) {
//        File file = new File(media.getCompressPath());
        OssManager.getInstance().upload(this, position, media.getCompressPath(), new OssManager.OnUploadListener() {
            @Override
            public void onProgress(int position, long currentSize, long totalSize) {

            }

            @Override
            public void onSuccess(int position, String uploadPath, String imageUrl) {
                upLoadUrls.add(imageUrl);
                if (upLoadUrls.size() >= selectList.size()) {
                    mPresenter.modifyPingtiao(mPingtiaoXiangqingResponse.getId(), upLoadUrls);
                }
            }

            @Override
            public void onFailure(int position) {
            }
        });

    }


    @BindView(R.id.jietiao_xq_rv)
    RecyclerView jietiao_xq_rv;
    PingtiaoXiangqingResponse mPingtiaoXiangqingResponse;


    @Override
    public void onSucJietiaoXq(PingtiaoXiangqingResponse rep) {
        mPingtiaoXiangqingResponse = rep;
        jietiaojianjie(rep);
        initHuankuanLayout(rep);
        goneDown(rep);
        falvbaozheng();
        jietiao_xq_jiekuanren.setText(rep.getBorrower());
        jietiao_xq_chujieren.setText(rep.getLender());
        jietiao_xq_jiekuanjine.setText(rep.getAmount() + "元");
        jietiao_xq_jiekuanshijian.setText(rep.getLoanDate());
        jietiao_xq_huankuanshijian.setText(rep.getRepaymentDate());
        jietiao_xq_nianhualilv.setText(rep.getYearRate());
        jietiao_xq_lixiheji.setText(rep.getTotalInterest() + "元");
        jietiao_xq_jiekuanyongtu.setText(rep.getLoanUsage());
        jietiao_xq_pingtiaobianhao.setText(rep.getNoteNo());
        jietiao_xq_chuangjianshijian.setText(rep.getCreateTime());
        jietiao_xq_pingtiaozhuangtai.setText(getStatus(rep));
        setBtnType(rep);
        setDaihuanjine(rep.getTotalAmount() + "");
        if (rep.getUrls() != null && rep.getUrls().size() > 0) {
            jietiao_xq_rv.setVisibility(View.VISIBLE);
        }
        mDatas.clear();
        if (rep.getUrls() == null || rep.getUrls().size() <= 0) {
            //没有图片
            mDatas.add(PingtiaoXqImgAdapter.ADD_BTN);
        } else if (rep.getUrls().size() >= 6) {
            pingzheng_hint.setVisibility(View.GONE);
            mDatas.addAll(rep.getUrls());
        } else {
            mDatas.add(PingtiaoXqImgAdapter.ADD_BTN);
            mDatas.addAll(rep.getUrls());
        }
        mPingtiaoXqImgAdapter.notifyDataSetChanged();
        setHistoryLayout();
        showJietiaoXiangqing(true);
    }

    @BindView(R.id.xiaozhang_btn)
    TextView xiaozhang_btn;
    @BindView(R.id.jietiao_xq_zaicifasong)
    TextView jietiao_xq_zaicifasong;
    @BindView(R.id.jietiao_xq_btn)
    TextView jietiao_xq_btn;
    @BindView(R.id.shanchu_btn)
    TextView shanchu_btn;
    List<TextView> showTvs = new ArrayList<>();

    private void setBtnType(PingtiaoXiangqingResponse item) {
        String status = item.getStatus();
        showTvs.clear();
        //删除借条按钮显示
        if ("UNHANDLED".equals(status) || "LENDER_FINISHED".equals(status) || "REJECTED".equals(status) || "UNSIGNED".equals(item.getSignStatus())) {
            shanchu_btn.setVisibility(View.VISIBLE);
            showTvs.add(shanchu_btn);
        } else {
            shanchu_btn.setVisibility(View.GONE);
        }
        //删除点击事件
        shanchu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog(item);
            }
        });

        if ("1".equals(item.getBorrowAndLendState())) {//出借人 才有还款 审批 和 销账
            //0:待还  借款人  1:代收 出借人
            //销账  按钮显示
            if (("CONFIRMED".equals(status) || "OVERDUE".equals(status)) && "SIGNED".equals(item.getSignStatus())) {
                xiaozhang_btn.setVisibility(View.VISIBLE);
                showTvs.add(xiaozhang_btn);
            } else {
                xiaozhang_btn.setVisibility(View.GONE);
            }
        } else {
            xiaozhang_btn.setVisibility(View.GONE);
        }

        //销账点击事件
        xiaozhang_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hintDialog(item);

            }
        });


        //再次发送 按钮显示
        if (("UNHANDLED".equals(status) || "UNSIGNED".equals(item.getSignStatus())) && !"REJECTED".equals(status)) {
            jietiao_xq_zaicifasong.setVisibility(View.VISIBLE);
            showTvs.add(jietiao_xq_zaicifasong);
        } else {
            jietiao_xq_zaicifasong.setVisibility(View.GONE);
        }

        //再次发送点击事件
        jietiao_xq_zaicifasong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putString(BaseWebViewActivity.WEBVIEW_TITLE, "二维码分享");
                bundle1.putInt(WebViewShareActivity.NOTE_ID, (int) item.getId());
                bundle1.putInt(WebViewShareActivity.USER_TYPE, Integer.parseInt(item.getBorrowAndLendState()));
                bundle1.putString(BaseWebViewActivity.WEBVIEW_URL, Api.BASE_H5_URL + "borrowShare?id=" + item.getId() + "&userType=" + item.getBorrowAndLendState());

                startNextActivity(bundle1, WebViewShareActivity.class);
            }
        });

        if ("0".equals(item.getBorrowAndLendState())) {
            //还款状态 已还款 按钮显示
            if (("CONFIRMED".equals(status) || "OVERDUE".equals(status)) && "0".equals(item.getHasApplyRepayRecord()) && "SIGNED".equals(item.getSignStatus())) {
                jietiao_xq_btn.setVisibility(View.VISIBLE);
                showTvs.add(jietiao_xq_btn);
                jietiao_xq_btn.setText("已还款?");
                jietiao_xq_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO 已还款
                        Bundle bundle1 = new Bundle();
                        bundle1.putString(XiaoZhangActivity.BORROW, item.getBorrower());
                        bundle1.putString(XiaoZhangActivity.LENDER, item.getLender());
                        bundle1.putDouble(XiaoZhangActivity.AMOUNT, Double.valueOf(item.getTotalAmount()));
                        bundle1.putInt(XiaoZhangActivity.NOTEID, (int) item.getId());
                        startNextActivity(bundle1, HuankuanFangshiActivity.class);
                    }
                });
            } else if (("CONFIRMED".equals(status) || "OVERDUE".equals(status)) && "1".equals(item.getHasApplyRepayRecord()) && "SIGNED".equals(item.getSignStatus())) {
                jietiao_xq_btn.setVisibility(View.VISIBLE);
                showTvs.add(jietiao_xq_btn);
                jietiao_xq_btn.setText("还款状态");
                jietiao_xq_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO 还款状态
                        Bundle bundle1 = new Bundle();
                        bundle1.putInt(HuankuanStatusActivity.NOTE_ID, (int) item.getId());
                        bundle1.putInt(HuankuanStatusActivity.USER_TYPE, Integer.parseInt(item.getBorrowAndLendState()));
                        startNextActivity(bundle1, HuankuanStatusActivity.class);

                    }
                });
            } else {
                jietiao_xq_btn.setVisibility(View.GONE);
            }
        } else {
            jietiao_xq_btn.setVisibility(View.GONE);
        }

        if (showTvs.size() == 1) {
            showTvs.get(0).setBackgroundColor(Color.parseColor("#cf986f"));
            showTvs.get(0).setTextColor(Color.parseColor("#FFFFFF"));
        } else if (showTvs.size() == 2) {
            showTvs.get(0).setBackgroundColor(Color.parseColor("#FFD583"));
            showTvs.get(0).setTextColor(Color.parseColor("#CFA16f"));
            showTvs.get(1).setBackgroundColor(Color.parseColor("#cf986f"));
            showTvs.get(1).setTextColor(Color.parseColor("#FFFFFF"));
        }

    }

    private boolean mVisible = false;

    private void goneDown(PingtiaoXiangqingResponse rep) {
        mVisible = false;
        if ("UNSIGNED".equals(rep.getSignStatus()) || "UNHANDLED".equals(rep.getStatus())) {
            mVisible = false;
        } else {
            mVisible = true;
        }

        jietiao_xq_xiazai.setVisibility(mVisible ? View.VISIBLE : View.GONE);
        jietiao_xq_chakan.setText(mVisible ? "查看" : "预览");

    }

    @Override
    public void onSucModifyPingtiao() {
        ArmsUtils.snackbarText("添加凭证成功");
    }

    @Override
    public void onSucDownload(String savePath) {
        ArmsUtils.snackbarText("文件保存位置:" + savePath);
    }


    private DownloadPingtiaoDialog mDownloadPingtiaoDialog;

    @OnClick({R.id.jietiao_xq_chakan, R.id.jietiao_xq_xiazai, R.id.jietiao_xq_btn, R.id.jietiao_xq_zaicifasong, R.id.jietiao_shousuolan
            , R.id.jietiao_huankuan_shousuolan, R.id.cunzhengzhengming_btn, R.id.jietiao_xq_history_ququeren})
    public void onPageClick(View view) {
        switch (view.getId()) {
            case R.id.jietiao_xq_chakan:
              /*
                Bundle bundle = new Bundle();
                ArrayList<String> list = new ArrayList<>();
                list.add(mPingtiaoXiangqingResponse.getViewPdfUrl());
                bundle.putStringArrayList(PhotoViewPagerActivity.TAG, (ArrayList<String>) list);
                bundle.putString(PhotoViewPagerActivity.TITLE, "电子借条");
                startActBundle(bundle, PhotoViewPagerActivity.class);
                */
                if (mVisible) {
                    Bundle bundle = new Bundle();
                    bundle.putString(BaseWebViewActivity.WEBVIEW_TITLE, "文件查看");
                    bundle.putString(BaseWebViewActivity.WEBVIEW_URL, mPingtiaoXiangqingResponse.getViewPdfUrl());
                    startActBundle(bundle, WebViewActivity.class);
                } else {
                    yulanJietiao();
                }
                break;
            case R.id.jietiao_xq_xiazai:
/*                String downUrl = mPingtiaoXiangqingResponse.getDownUrl();
                downUrl = UrlDecoderHelper.decode(downUrl);
                String filename = "pingtiao";
                try {
                    String[] templist1 = downUrl.split("\\?");
                    String[] templist2 = templist1[0].split("/");
                    filename = templist2[templist2.length - 1];
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(filename)) {
                    filename = "pingtiao";
                }
                mPresenter.downLoadFile(downUrl, filename);*/
                if (mDownloadPingtiaoDialog == null) {
                    mDownloadPingtiaoDialog = new DownloadPingtiaoDialog(this, "下载电子借条", (int) id);
                }
                mDownloadPingtiaoDialog.show();
                break;
            case R.id.jietiao_xq_btn://已经还款
                //数据回调一并处理
//                showDialog(findViewById(R.id.jietiao_xq_btn), mPingtiaoXiangqingResponse.getId());
                break;
            case R.id.jietiao_xq_zaicifasong:
                //数据回调一并处理

//                Bundle bundle1 = new Bundle();
//                bundle1.putString(BaseWebViewActivity.WEBVIEW_TITLE, "二维码分享");
//                bundle1.putInt(WebViewShareActivity.NOTE_ID, mPingtiaoXiangqingResponse.getId());
//                bundle1.putInt(WebViewShareActivity.USER_TYPE, Integer.parseInt(mPingtiaoXiangqingResponse.getInitiator()));
//                bundle1.putString(BaseWebViewActivity.WEBVIEW_URL, Api.BASE_H5_URL + "borrowShare?id=" + mPingtiaoXiangqingResponse.getId() + "&userType=" + mPingtiaoXiangqingResponse.getInitiator());
//                startActBundle(bundle1, WebViewShareActivity.class);
                break;
            case R.id.jietiao_shousuolan:
                showJietiaoXiangqing(dianzijietiao_rootview_more.getVisibility() == View.VISIBLE);
                break;
            case R.id.jietiao_huankuan_shousuolan:
                if (isGoneHistory) {
                    isGoneHistory = !isGoneHistory;
                    jietiao_huankuan_hint.setText("折叠全部");
                    dianzijietiao_jiantou2.setRotation(0);
                    setHuankuanHistoryLayout(mPingtiaoXiangqingResponse.getRepayRecords().size());//还款历史记录的N条
                } else {
                    isGoneHistory = !isGoneHistory;
                    jietiao_huankuan_hint.setText("查看全部");
                    dianzijietiao_jiantou2.setRotation(180);
                    setHuankuanHistoryLayout(mPingtiaoXiangqingResponse.getRepayRecords().size() > 2 ? 2 : mPingtiaoXiangqingResponse.getRepayRecords().size());//默认显示的2条v
                }
                break;

            case R.id.jietiao_xq_history_ququeren:
                //还款记录 去审批按钮
                huankuanshenpi();
                break;

            case R.id.cunzhengzhengming_btn:
                //法律  存证证明查看
                Bundle bundlex01 = new Bundle();
                bundlex01.putString(WebViewZXActivity.WEBVIEW_TITLE, "存证证明");
                bundlex01.putString(WebViewZXActivity.WEBVIEW_URL, UrlDecoderHelper.decode(mPingtiaoXiangqingResponse.getOwnershipRecordDto().getCertificateUrl()));
                startNextActivity(bundlex01, WebViewZXActivity.class);
          /*      Uri uri = Uri.parse(UrlDecoderHelper.decode(mPingtiaoXiangqingResponse.getOwnershipRecordDto().getCertificateUrl()));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);*/
                break;
        }
    }


    /**
     * 展开关闭详情页面
     *
     * @param isShow
     */
    private void showJietiaoXiangqing(boolean isShow) {
        if (isShow) {
            dianzijietiao_rootview_more.setVisibility(View.GONE);
            dianzijietiao_huankuan_jiantou.setRotation(180);
            jietiao_shousuolan_mingcheng.setText("显示借条详情");
            nsv.fling(0);
            nsv.smoothScrollTo(0, 0);

        } else {
            dianzijietiao_rootview_more.setVisibility(View.VISIBLE);
            dianzijietiao_huankuan_jiantou.setRotation(0);
            jietiao_shousuolan_mingcheng.setText("折叠借条详情");
        }
    }


    DialogChooseNormal mDialogChooseNormal;

    private void showDialog(View view,
                            long id) {
        if (mDialogChooseNormal == null) {
            mDialogChooseNormal = new DialogChooseNormal.ChooseBuilder()
                    .setTitle("提示")
                    .setContext(ActivityUtils.getTopActivity())
                    .setContent("该凭条确定已经结清？")
                    .setBtn1Content("取消").setOnClickListener1(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDialogChooseNormal.dismiss();
                        }
                    })
                    .setBtn1Colort(R.color.gray_color_7D7D7D)
                    .setBtn3Content("确定")
                    .setOnClickListener3(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDialogChooseNormal.dismiss();
                            finishElectronicNote(findViewById(R.id.jietiao_xq_btn), mPingtiaoXiangqingResponse.getId());

                        }
                    }).build();
        }
        mDialogChooseNormal.show();
    }

    /**
     * 已还款按钮
     */
    private void finishElectronicNote(
            View view,
            long id
    ) {
        ArmsUtils.obtainAppComponentFromContext(this).repositoryManager().obtainRetrofitService(PingtiaoApi.class).finishElectronicNote(
                new FinishElectronicNoteRequest(
                        AppUtils.getAppVersionName(),
                        id,
                        "ANDRIOD",
                        null
                )
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseJson<Object>>() {
                    @Override
                    public void accept(BaseJson<Object> objectBaseJson) throws Exception {
                        if (objectBaseJson.isSuccess()) {
                            //TODO 已还款成功
                            findViewById(R.id.jietiao_xq_btn).setVisibility(View.GONE);
                            mPresenter.getPingtiaoById(id);
                        }
                    }
                }).isDisposed();
    }

    @BindView(R.id.xq_chakan_xiazai)
    RelativeLayout xq_chakan_xiazai;

    public String getStatus(PingtiaoXiangqingResponse item) {
        xq_chakan_xiazai.setVisibility(View.VISIBLE);
        String value = item.getStatus();
//        String overDueDays = item.getOv();
        switch (value) {
            case "DRAFT":
                return "";
            case "UNHANDLED":
                return "待确认";
            case "OVERDUE":
                return "已逾期";
//                + overDueDays + "天";
            case "LENDER_FINISHED":
                return "已完结";
            case "BORROWER_FINISHED":
                return "借款人完结";
            case "CONFIRMED"://确认的
                if ("UNSIGNED".equals(item.getSignStatus())) {
                    return "待确认";
                }
                return "未到期";
            case "REJECTED"://REJECTED
                xq_chakan_xiazai.setVisibility(View.GONE);
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


    /**
     * 预览借条
     */
    private void yulanJietiao() {
        Intent intent = new Intent(this, YulanJietiaoActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putString(YulanJietiaoActivity.jiekuanjine, mPingtiaoXiangqingResponse.getAmount());
        mBundle.putString(YulanJietiaoActivity.jiekuanriqi, mPingtiaoXiangqingResponse.getLoanDate());
        mBundle.putString(YulanJietiaoActivity.huankuanriqi, mPingtiaoXiangqingResponse.getRepaymentDate());
        String bexihe = "0";
        try {
            bexihe = String.valueOf(Double.valueOf(mPingtiaoXiangqingResponse.getAmount()) + Double.valueOf(mPingtiaoXiangqingResponse.getTotalInterest()));
        } catch (Exception e) {

        }
        mBundle.putString(YulanJietiaoActivity.benxihe, bexihe);
        mBundle.putString(YulanJietiaoActivity.nianhualilv, mPingtiaoXiangqingResponse.getYearRate());
        mBundle.putString(YulanJietiaoActivity.lixizongji, mPingtiaoXiangqingResponse.getTotalInterest());

        mBundle.putString(YulanJietiaoActivity.chujierenshenfenzheng, mPingtiaoXiangqingResponse.getBorrower());
        mBundle.putString(YulanJietiaoActivity.jiekuanyongtu, mPingtiaoXiangqingResponse.getLoanUsage());

        long between_days = DateUtils.bettweenDays(mPingtiaoXiangqingResponse.getLoanDate(), mPingtiaoXiangqingResponse.getRepaymentDate());
        mBundle.putString(YulanJietiaoActivity.tianshu, between_days + "");
        String userName = SavePreference.getStr(this, PingtiaoConst.USER_NAME);
        int type = XieJietiaoActivity.JIEKUANREN;//借款人0 出借人1
        if (userName.equals(mPingtiaoXiangqingResponse.getBorrower())) {
            mBundle.putString(YulanJietiaoActivity.chujierenmingzi, mPingtiaoXiangqingResponse.getLender());
            type = XieJietiaoActivity.JIEKUANREN;
        } else if (userName.equals(mPingtiaoXiangqingResponse.getLender())) {
            mBundle.putString(YulanJietiaoActivity.chujierenmingzi, mPingtiaoXiangqingResponse.getBorrower());
            type = XieJietiaoActivity.CHUJIEREN;
        }
        mBundle.putInt(XieJietiaoActivity.XieJietiaoActivity, type);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    /**
     * 代还金额设置
     *
     * @param money
     */
    private void setDaihuanjine(String money) {
        SpannableStringBuilder ssb = new SpanUtils()
                .append(money).setFontSize(30, true)
                .append("元").setFontSize(14, true)
                .create();
        jietiao_xq_daihuanjine.setText(ssb);
    }

    private void setHistoryYihuan(String money) {
        SpannableStringBuilder ssb = new SpanUtils()
                .append("已还  ").setFontSize(14, true).setForegroundColor(getResources().getColor(R.color.black_color_333333))
                .append("¥" + money).setFontSize(24, true).setForegroundColor(getResources().getColor(R.color.green_64BC3D))
                .append("元").setFontSize(14, true).setForegroundColor(getResources().getColor(R.color.green_64BC3D))
                .create();
        jietiao_xq_history_yihuan.setText(ssb);
    }

    @BindView(R.id.jietiao_huankuan_shousuolan)
    LinearLayout jietiao_huankuan_shousuolan;

    @BindView(R.id.jietiao_huankuan_hint)
    TextView jietiao_huankuan_hint;

    @BindView(R.id.dianzijietiao_jiantou2)
    ImageView dianzijietiao_jiantou2;

    @BindView(R.id.jietiao_huankuan_history_layout)
    LinearLayout jietiao_huankuan_history_layout;

    private boolean isGoneHistory = true;

    //还款记录
    private void setHistoryLayout() {
        initYihuanLayout();
        jietiao_huankuan_history_layout.removeAllViews();
        int realsize = mPingtiaoXiangqingResponse.getRepayRecords().size();//还款记录真实条目
        int size = realsize > 2 ? 2 : realsize;
        setHuankuanHistoryLayout(size);
        if (realsize > 2) {
            jietiao_huankuan_shousuolan.setVisibility(View.VISIBLE);
            jietiao_huankuan_hint.setText("查看全部");
            dianzijietiao_jiantou2.setRotation(180);
        } else {
            jietiao_huankuan_shousuolan.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化 已还XXX元 界面
     */
    private void initYihuanLayout() {
        yihuan_jine_layout.setVisibility(View.GONE);
        if ("1".equals(mPingtiaoXiangqingResponse.getBorrowAndLendState())) {
            //出借人
            if ("1".equals(mPingtiaoXiangqingResponse.getHasApplyRepayRecord())) {
                //如果有还款提醒
                yihuan_jine_layout.setVisibility(View.GONE);
            } else {
                yihuan_jine_layout.setVisibility(View.VISIBLE);
                setHistoryYihuan(historyYihuanjine + "");
            }
        } else {
            //借款人
            int realsize = mPingtiaoXiangqingResponse.getRepayRecords().size();//还款记录真实条目
            if (realsize > 0) {
                yihuan_jine_layout.setVisibility(View.VISIBLE);
                setHistoryYihuan(historyYihuanjine + "");
            }
        }
    }

    private void setHuankuanHistoryLayout(int size) {
        List<PingtiaoXiangqingResponse.RepayRecords> records = mPingtiaoXiangqingResponse.getRepayRecords();
        jietiao_huankuan_history_layout.removeAllViews();
        for (int i = 0; i < size; i++) {

            PingtiaoXiangqingResponse.RepayRecords tempRecords = records.get(i);
            View view = View.inflate(this, R.layout.layout_huankuan_history_layout, null);
            if (i == 0) {
                view.findViewById(R.id.top_line).setVisibility(View.VISIBLE);
            }
            TextView tv1 = view.findViewById(R.id.tv1);
            TextView tv2 = view.findViewById(R.id.tv2);
            TextView tv3 = view.findViewById(R.id.tv3);
            TextView tv4 = view.findViewById(R.id.tv4);
            tv1.setText("还款");
            tv2.setText(tempRecords.getAmount() + "元");
            tv3.setText(tempRecords.getCreateTime());
            setHuankuanStatus(tempRecords, tv4);
            jietiao_huankuan_history_layout.addView(view);
        }
    }

    private void setHuankuanStatus(PingtiaoXiangqingResponse.RepayRecords repayRecords, TextView tv) {
        //还款记录状态 SUCCESS FAIL APPLY
        String ret = "";
        switch (repayRecords.getStatus()) {
            case "SUCCESS":
                ret = "已成功";
                tv.setText(ret);
                tv.setTextColor(getResources().getColor(R.color.green68AD47));
                break;
            case "REJECTED":
                ret = "已驳回";
                tv.setText(ret);
                tv.setTextColor(getResources().getColor(R.color.orange_color_E5591C));
                break;
            case "APPLY":
                ret = "待审批";
                tv.setText(ret);
                tv.setTextColor(getResources().getColor(R.color.green68AD47));
                break;
            case "REVOKED":
                ret = "已撤销";
                tv.setText(ret);
                tv.setTextColor(getResources().getColor(R.color.black_color_333333));
                break;
        }


    }


    private void shanchujietiao(PingtiaoXiangqingResponse item) {
        ArmsUtils.obtainAppComponentFromContext(this).repositoryManager().obtainRetrofitService(PingtiaoApi.class)
                .closeElectronicNote(new CloseElectronicNoteRequest(
                        AppUtils.getAppVersionName(),
                        Long.valueOf(item.getId()),
                        "ANDRIOD",
                        0L
                )).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<BaseJson<Object>>(ArmsUtils.obtainAppComponentFromContext(this).rxErrorHandler()) {
                    @Override
                    public void onNext(BaseJson<Object> rep) {
                        if (rep.isSuccess()) {
                            finish();
                            ArmsUtils.snackbarText("删除成功");
                        } else {
                            ArmsUtils.snackbarText(rep.getMessage());
                        }
                    }
                });
    }

    DialogChooseNormal mXiaozhangDialog;

    private void hintDialog(final PingtiaoXiangqingResponse item) {
        String title = "提示：销账等同于借款人还款，一经发起，不能撤销！";
        String btnHint = "继续发起";
        if ("1".equals(item.getHasApplyRepayRecord())) {
            //TODO 还款确认
            title = "该借条有1笔还款审批需要您处理，请处理后再操作完结";
            btnHint = "去处理";
            mXiaozhangDialog = new DialogChooseNormal.ChooseBuilder()
                    .setTitle("提示")
                    .setContext(ActivityUtils.getTopActivity())
                    .setContent(title)
                    .setBtn1Content("取消").setOnClickListener1(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mXiaozhangDialog.dismiss();
                        }
                    })
                    .setBtn1Colort(R.color.gray_color_7D7D7D)
                    .setBtn3Content(btnHint)
                    .setOnClickListener3(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mXiaozhangDialog.dismiss();
                            if ("1".equals(item.getHasApplyRepayRecord())) {
                                //TODO 还款审批
                                huankuanshenpi();
                            } else {
                                //TODO 销账
                                xiaozhang(item);
                            }
                        }
                    }).build();
            mXiaozhangDialog.show();
        } else {
            //TODO 销账
            title = "提示：销账等同于借款人还款，一经发起，不能撤销！";
            btnHint = "继续发起";
            xiaozhang(item);
        }

    }

    /**
     * 销账
     *
     * @param item
     */
    private void xiaozhang(PingtiaoXiangqingResponse item) {
        Bundle bundle1 = new Bundle();
        bundle1.putString(XiaoZhangActivity.BORROW, item.getBorrower());
        bundle1.putString(XiaoZhangActivity.LENDER, item.getLender());
        bundle1.putDouble(XiaoZhangActivity.AMOUNT, Double.valueOf(item.getTotalAmount()));
        bundle1.putInt(XiaoZhangActivity.NOTEID, (int) item.getId());
        startNextActivity(bundle1, XiaoZhangActivity.class);
    }

    /**
     * 还款审批
     */
    private void huankuanshenpi() {
        Bundle bundle1 = new Bundle();
        bundle1.putString(BaseWebViewActivity.WEBVIEW_TITLE, "还款审批");
        bundle1.putString(BaseWebViewActivity.WEBVIEW_URL, Api.BASE_H5_URL + "repaymentApproval?id=" + id);
        startNextActivity(bundle1, WebViewActivity.class);
    }


    DialogChooseNormal mDeleteDialog;//删除借条弹框

    private void deleteDialog(final PingtiaoXiangqingResponse item) {
        mDeleteDialog = new DialogChooseNormal.ChooseBuilder()
                .setTitle("提示")
                .setContext(ActivityUtils.getTopActivity())
                .setContent("确定删除该借条？")
                .setBtn1Content("取消").setOnClickListener1(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDeleteDialog.dismiss();
                    }
                })
                .setBtn1Colort(R.color.gray_color_7D7D7D)
                .setBtn3Content("确定删除")
                .setOnClickListener3(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDeleteDialog.dismiss();
                        shanchujietiao(item);
                    }
                }).build();
        mDeleteDialog.show();
    }


    private final int NEXT_REQ = 0x03;

    private void startNextActivity(Bundle bundle, Class clazz) {
        ActivityUtils.startActivityForResult(bundle, this, clazz, NEXT_REQ);
    }



}
