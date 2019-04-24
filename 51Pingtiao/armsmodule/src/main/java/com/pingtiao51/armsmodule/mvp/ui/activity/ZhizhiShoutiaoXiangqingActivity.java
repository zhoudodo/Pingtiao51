package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.pingtiao51.armsmodule.app.utils.OssManager;
import com.pingtiao51.armsmodule.di.component.DaggerZhizhiShoutiaoXiangqingComponent;
import com.pingtiao51.armsmodule.mvp.contract.ZhizhiShoutiaoXiangqingContract;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoXiangqingResponse;
import com.pingtiao51.armsmodule.mvp.presenter.ZhizhiShoutiaoXiangqingPresenter;

import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.ui.adapter.PingtiaoXqImgAdapter;
import com.pingtiao51.armsmodule.mvp.ui.helper.ImagePaizhaoHelper;
import com.zls.baselib.custom.view.dialog.DialogChooseNormal;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.pingtiao51.armsmodule.mvp.ui.activity.DianziJietiaoXiangqingActivity.PING_TIAO_XIANG_QING;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/26/2019 15:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ZhizhiShoutiaoXiangqingActivity extends BaseArmsActivity<ZhizhiShoutiaoXiangqingPresenter> implements ZhizhiShoutiaoXiangqingContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerZhizhiShoutiaoXiangqingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @BindView(R.id.zz_shoutiao_jingshouren)
    TextView zz_shoutiao_jingshouren;
    @BindView(R.id.zz_shoutiao_dijiaoren)
    TextView zz_shoutiao_dijiaoren;
    @BindView(R.id.zz_shoutiao_dijiaojine)
    TextView zz_shoutiao_dijiaojine;
    @BindView(R.id.zz_shoutiao_jingshoushijian)
    TextView zz_shoutiao_jingshoushijian;
    @BindView(R.id.zz_shoutiao_beizhu)
    TextView zz_shoutiao_beizhu;

    @BindView(R.id.pt_zhizhi_shou_hint)
    TextView pt_zhizhi_shou_hint;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_zhizhi_shoutiao_xiangqing; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    private final static int MAX_PIC = 6;//照片最多6张
    private long id;
    PingtiaoXqImgAdapter mPingtiaoXqImgAdapter;
    private List<String> mDatas = new ArrayList<>();
    DialogChooseNormal mDialogChooseNormal;

    private void initDeleteHint() {
        if (mDialogChooseNormal == null) {
            mDialogChooseNormal = new DialogChooseNormal.ChooseBuilder()
                    .setTitle("提示")
                    .setContext(this)
                    .setContent("确定删除该凭条")
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
                            mPresenter.closeElectronicNote(mPingtiaoXiangqingResponse.getId());
                        }
                    }).build();
        }
        mDialogChooseNormal.show();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("纸质收条");
        TextView rightTv = findViewById(R.id.right_tv);
        rightTv.setText("删除");
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDeleteHint();
            }
        });
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
                    ImagePaizhaoHelper.modifyPingzheng(ZhizhiShoutiaoXiangqingActivity.this, overPicNum);
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
                        pt_zhizhi_shou_hint.setVisibility(View.GONE);
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


    @BindView(R.id.zz_shoutiao_rv)
    RecyclerView jietiao_xq_rv;
    PingtiaoXiangqingResponse mPingtiaoXiangqingResponse;

    @Override
    public void onSucJietiaoXq(PingtiaoXiangqingResponse rep) {
        mPingtiaoXiangqingResponse = rep;
        zz_shoutiao_jingshouren.setText(rep.getBorrower());
        zz_shoutiao_dijiaoren.setText(rep.getLender());
        zz_shoutiao_dijiaojine.setText(rep.getAmount() + "元");
        zz_shoutiao_jingshoushijian.setText(rep.getRepaymentDate());
        zz_shoutiao_beizhu.setText(rep.getComment());
        if (rep.getUrls() != null && rep.getUrls().size() > 0) {
            jietiao_xq_rv.setVisibility(View.VISIBLE);
        }
        mDatas.clear();
        if (rep.getUrls() == null || rep.getUrls().size() <= 0) {
            //没有图片
            mDatas.add(PingtiaoXqImgAdapter.ADD_BTN);
        } else if (rep.getUrls().size() >= 6) {
            pt_zhizhi_shou_hint.setVisibility(View.GONE);
            mDatas.addAll(rep.getUrls());
        } else {
            mDatas.add(PingtiaoXqImgAdapter.ADD_BTN);
            mDatas.addAll(rep.getUrls());
        }
        mPingtiaoXqImgAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSucModifyPingtiao() {
        ArmsUtils.snackbarText("添加凭证成功");
    }

    @Override
    public void onSucDownload(String savePath) {
        ArmsUtils.snackbarText("文件保存位置:" + savePath);
    }

    @Override
    public void onSucDelete() {
        finish();
    }
}
