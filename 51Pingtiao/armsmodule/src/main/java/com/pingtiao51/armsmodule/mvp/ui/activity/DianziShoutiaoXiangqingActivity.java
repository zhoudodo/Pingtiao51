package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.app.utils.OssManager;
import com.pingtiao51.armsmodule.di.component.DaggerDianziShoutiaoXiangqingComponent;
import com.pingtiao51.armsmodule.mvp.contract.DianziShoutiaoXiangqingContract;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoXiangqingResponse;
import com.pingtiao51.armsmodule.mvp.presenter.DianziShoutiaoXiangqingPresenter;
import com.pingtiao51.armsmodule.mvp.ui.adapter.PingtiaoXqImgAdapter;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.DownloadPingtiaoDialog;
import com.pingtiao51.armsmodule.mvp.ui.helper.ImagePaizhaoHelper;
import com.pingtiao51.armsmodule.mvp.ui.helper.UrlDecoderHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.pingtiao51.armsmodule.mvp.ui.activity.DianziJietiaoXiangqingActivity.PING_TIAO_XIANG_QING;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/26/2019 15:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class DianziShoutiaoXiangqingActivity extends BaseArmsActivity<DianziShoutiaoXiangqingPresenter> implements DianziShoutiaoXiangqingContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDianziShoutiaoXiangqingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }


    @BindView(R.id.shoutiao_xq_xiazai)
    TextView jietiao_xq_xiazai;



    @BindView(R.id.shoutiao_xq_chakan)
    TextView shoutiao_xq_chakan;
    @BindView(R.id.shoutiao_xq_jingshouren)
    TextView shoutiao_xq_jingshouren;
    @BindView(R.id.shoutiao_xq_dijiaoren)
    TextView shoutiao_xq_dijiaoren;
    @BindView(R.id.shoutiao_xq_dijiaojine)
    TextView shoutiao_xq_dijiaojine;
    @BindView(R.id.shoutiao_xq_jingshoushijian)
    TextView shoutiao_xq_jingshoushijian;
    @BindView(R.id.shoutiao_xq_beizhu)
    TextView shoutiao_xq_beizhu;
    @BindView(R.id.shoutiao_xq_pingtiaobianhao)
    TextView shoutiao_xq_pingtiaobianhao;
    @BindView(R.id.shoutiao_xq_chuangjianshijian)
    TextView shoutiao_xq_chuangjianshijian;

    @BindView(R.id.pingtiao_hint_shou)
    TextView pingtiao_hint_shou;


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
    private void falvbaozheng() {
        if (mPingtiaoXiangqingResponse != null && mPingtiaoXiangqingResponse.getOwnershipRecordDto() != null) {
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
        return R.layout.activity_dianzi_shoutiao_xiangqing; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    private final static int MAX_PIC = 6;//照片最多6张
    private long id;
    PingtiaoXqImgAdapter mPingtiaoXqImgAdapter;
    private List<String> mDatas = new ArrayList<>();

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("电子收条");
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
                    ImagePaizhaoHelper.modifyPingzheng(DianziShoutiaoXiangqingActivity.this, overPicNum);
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
                        pingtiao_hint_shou.setVisibility(View.GONE);
                        mDatas.remove(0);
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


    @BindView(R.id.shoutiao_xq_rv)
    RecyclerView jietiao_xq_rv;
    PingtiaoXiangqingResponse mPingtiaoXiangqingResponse;

    @Override
    public void onSucJietiaoXq(PingtiaoXiangqingResponse rep) {
        mPingtiaoXiangqingResponse = rep;
        goneDown(rep);
        falvbaozheng();
        shoutiao_xq_jingshouren.setText(rep.getBorrower());
        shoutiao_xq_dijiaoren.setText(rep.getLender());
        SpannableStringBuilder ssb = new SpanUtils()
                .append(rep.getAmount()+"").setFontSize(30,true)
                .append("元").setFontSize(15,true)
                .create();
        shoutiao_xq_dijiaojine.setText(ssb);
        shoutiao_xq_jingshoushijian.setText(rep.getRepaymentDate());
        String beizhu = rep.getComment();
        if(TextUtils.isEmpty(beizhu)){
            beizhu = "无";
        }
        shoutiao_xq_beizhu.setText(beizhu);
        shoutiao_xq_pingtiaobianhao.setText(rep.getNoteNo());
        shoutiao_xq_chuangjianshijian.setText(rep.getCreateTime());

        if (rep.getUrls() != null && rep.getUrls().size() > 0) {
            jietiao_xq_rv.setVisibility(View.VISIBLE);
        }
        mDatas.clear();
        if (rep.getUrls() == null || rep.getUrls().size() <= 0) {
            //没有图片
            mDatas.add(PingtiaoXqImgAdapter.ADD_BTN);
        } else if (rep.getUrls().size() >= 6) {
            pingtiao_hint_shou.setVisibility(View.GONE);
            mDatas.addAll(rep.getUrls());
        } else {
            mDatas.add(PingtiaoXqImgAdapter.ADD_BTN);
            mDatas.addAll(rep.getUrls());
        }
        mPingtiaoXqImgAdapter.notifyDataSetChanged();

    }

    private boolean mVisible = false;

    private void goneDown(PingtiaoXiangqingResponse rep) {
        mVisible = false;
        String status = rep.getSignStatus();
        if(TextUtils.isEmpty(status)){
            status = "UNSIGNED";
        }
        switch (status) {
            case "SIGNED":
                // 已签章
                mVisible = true;
                break;
            default:
                //未签章
                mVisible = false;
                break;
        }
        jietiao_xq_xiazai.setVisibility(mVisible ? View.VISIBLE : View.GONE);
        shoutiao_xq_chakan.setText(mVisible ? "查看" : "预览");
    }


    @Override
    public void onSucModifyPingtiao() {
        ArmsUtils.snackbarText("添加凭证成功");
    }

    @Override
    public void onSucDownload(String savePath) {
        ArmsUtils.snackbarText("文件保存位置:" + savePath);
    }


    private DownloadPingtiaoDialog  mDownloadPingtiaoDialog;

    @OnClick({R.id.shoutiao_xq_chakan, R.id.shoutiao_xq_xiazai})
    public void onPageClick(View view) {
        switch (view.getId()) {
            case R.id.shoutiao_xq_chakan:
/*                Bundle bundle = new Bundle();
                ArrayList<String> list = new ArrayList<>();
                list.add(mPingtiaoXiangqingResponse.getViewPdfUrl());
                bundle.putStringArrayList(PhotoViewPagerActivity.TAG, (ArrayList<String>) list);
                bundle.putString(PhotoViewPagerActivity.TITLE,"电子借条");
                startActBundle(bundle, PhotoViewPagerActivity.class);*/
                if (mVisible) {
                    Bundle bundle = new Bundle();
                    bundle.putString(BaseWebViewActivity.WEBVIEW_TITLE, "文件查看");
                    bundle.putString(BaseWebViewActivity.WEBVIEW_URL, mPingtiaoXiangqingResponse.getViewPdfUrl());
                    startActBundle(bundle, WebViewActivity.class);
                } else {
                    yulanshoutiao();
                }
                break;
            case R.id.shoutiao_xq_xiazai:
//                String downUrl = mPingtiaoXiangqingResponse.getDownUrl();
//                downUrl = UrlDecoderHelper.decode(downUrl);
//                String filename = "pingtiao";
//                try {
//                    String[] templist1 = downUrl.split("\\?");
//                    String[] templist2 = templist1[0].split("/");
//                    filename = templist2[templist2.length - 1];
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                if (TextUtils.isEmpty(filename)) {
//                    filename = "pingtiao";
//                }
//                mPresenter.downLoadFile(downUrl, filename);
                if(mDownloadPingtiaoDialog == null){
                    mDownloadPingtiaoDialog = new DownloadPingtiaoDialog(this,"下载电子收条", (int) id);
                }
                mDownloadPingtiaoDialog.show();
                break;
        }
    }


    private void yulanshoutiao() {
        Intent intent = new Intent(this, YulanShoutiaoActivity.class);
        Bundle bundle = new Bundle();
        String hejijine = mPingtiaoXiangqingResponse.getAmount();
        bundle.putString(YulanShoutiaoActivity.hejijine, hejijine);
        String dijiaorenxingming = mPingtiaoXiangqingResponse.getLender();
        bundle.putString(YulanShoutiaoActivity.dijiaorenxingming, dijiaorenxingming);
        String shoutiaochujushijian = mPingtiaoXiangqingResponse.getRepaymentDate();
        bundle.putString(YulanShoutiaoActivity.shoutiaochujushijian, shoutiaochujushijian);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
