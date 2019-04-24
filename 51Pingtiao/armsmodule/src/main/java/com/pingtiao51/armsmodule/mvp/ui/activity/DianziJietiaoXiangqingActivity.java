package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
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
import com.pingtiao51.armsmodule.mvp.model.entity.request.FinishElectronicNoteRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoDetailResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoXiangqingResponse;
import com.pingtiao51.armsmodule.mvp.presenter.DianziJietiaoXiangqingPresenter;
import com.pingtiao51.armsmodule.mvp.ui.adapter.PingtiaoXqImgAdapter;
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
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


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

    @BindView(R.id.jietiao_xq_chakan)
    TextView jietiao_xq_chakan;
    @BindView(R.id.jietiao_xq_xiazai)
    TextView jietiao_xq_xiazai;
    @BindView(R.id.shu_line)
    View shu_line;
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

    /**
     * 法律保证
     */
    private void falvbaozheng(){
        if(mPingtiaoXiangqingResponse != null && mPingtiaoXiangqingResponse.getOwnershipRecordDto() != null){
            falv_layout.setVisibility(View.VISIBLE);
            gongzhengchu.setText(mPingtiaoXiangqingResponse.getOwnershipRecordDto().getCertificateStructure());
            gongzhengbianhao.setText(mPingtiaoXiangqingResponse.getOwnershipRecordDto().getOwnershipOrderNo());
            cunzhengshijian.setText(mPingtiaoXiangqingResponse.getOwnershipRecordDto().getCertificateDate());
        }else{
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

    private List<LocalMedia> selectList = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
    }

    @BindView(R.id.jietiao_xq_zaicifasong)
    TextView jietiao_xq_zaicifasong;
    @BindView(R.id.jietiao_xq_btn)
    TextView jietiao_xq_btn;

    private void setBtnType(PingtiaoXiangqingResponse item) {
        String status = item.getStatus();
        if (!"UNPAY_REJECT".equals(status) &&
                !"PAY_REJECT".equals(status) &&
                !"LENDER_FINISHED".equals(status) &&
                !"BORROWER_FINISHED".equals(status)
                ) {
            if ("UNCLOUD_STORE".equals(item.getHasCloudStoreOrConfirm()) ||
                    "UNCONFIRMED".equals(item.getHasCloudStoreOrConfirm())||
                    "UNSIGNED".equals(item.getStatus())) {
                jietiao_xq_zaicifasong.setVisibility(View.VISIBLE);
            } else {
                jietiao_xq_zaicifasong.setVisibility(View.GONE);
            }
            if("UNSIGNED".equals(item.getStatus())) {
                jietiao_xq_btn.setVisibility(View.GONE);
            }else{
                jietiao_xq_btn.setVisibility(View.VISIBLE);
            }
        } else {
            jietiao_xq_zaicifasong.setVisibility(View.GONE);
            jietiao_xq_btn.setVisibility(View.GONE);
        }
        if ("DRAFT".equals(status) || "UNPAY_UNHANDLE".equals(status) || "PAY_UNHANDLE".equals(status)) {
            jietiao_xq_zaicifasong.setVisibility(View.VISIBLE);
            jietiao_xq_btn.setVisibility(View.GONE);
        }
    }

    private boolean mVisible = false;

    private void goneDown(PingtiaoXiangqingResponse rep) {
        mVisible = false;
        String status = rep.getStatus();
        switch (status) {
            case "SIGNED":
            case "OVERDUE":
            case "BORROWER_FINISHED":
            case "LENDER_FINISHED":
                // 已签章
                mVisible = true;
                break;
            default:
                //未签章
                mVisible = false;
                break;
        }

        shu_line.setVisibility(mVisible ? View.VISIBLE : View.GONE);
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

    @OnClick({R.id.jietiao_xq_chakan, R.id.jietiao_xq_xiazai, R.id.jietiao_xq_btn, R.id.jietiao_xq_zaicifasong})
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
                String downUrl = mPingtiaoXiangqingResponse.getDownUrl();
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
                mPresenter.downLoadFile(downUrl, filename);
                break;
            case R.id.jietiao_xq_btn://已经还款
                showDialog(findViewById(R.id.jietiao_xq_btn), mPingtiaoXiangqingResponse.getId());
                break;
            case R.id.jietiao_xq_zaicifasong:
                Bundle bundle1 = new Bundle();
                bundle1.putString(BaseWebViewActivity.WEBVIEW_TITLE, "二维码分享");
                bundle1.putInt(WebViewShareActivity.NOTE_ID, mPingtiaoXiangqingResponse.getId());
                bundle1.putInt(WebViewShareActivity.USER_TYPE, Integer.parseInt(mPingtiaoXiangqingResponse.getInitiator()));
                bundle1.putString(BaseWebViewActivity.WEBVIEW_URL, Api.BASE_H5_URL + "borrowShare?id=" + mPingtiaoXiangqingResponse.getId() + "&userType=" + mPingtiaoXiangqingResponse.getInitiator());
                startActBundle(bundle1, WebViewShareActivity.class);
                break;
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

    public static String getStatus(PingtiaoXiangqingResponse item) {
        String value = item.getStatus();
        switch (value) {
            case "DRAFT":
                return "未生效";
            case "UNPAY_REJECT":
                return "被驳回";
            case "PAY_REJECT":
                return "被驳回";
            case "UNPAY_UNHANDLE":
                return "未生效";
            case "PAY_UNHANDLE":
                return "未生效";
            case "UNSIGNED":
                return "未生效";
            case "SIGNED":
                return "";
            case "OVERDUE":
                return "已逾期";
            case "BORROWER_FINISHED":
                return "借款人完结";
            case "LENDER_FINISHED":
                return "出借人完结";
            case "CLOSED":
                return "作废";
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
}
