package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.lifecycle.Lifecycleable;
import com.jess.arms.utils.ArmsUtils;

import com.jess.arms.utils.RxLifecycleUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.pingtiao51.armsmodule.app.utils.OssManager;
import com.pingtiao51.armsmodule.di.component.DaggerHuankuanFangshiComponent;
import com.pingtiao51.armsmodule.mvp.contract.HuankuanFangshiContract;
import com.pingtiao51.armsmodule.mvp.model.api.service.PingtiaoApi;
import com.pingtiao51.armsmodule.mvp.model.entity.request.AddRepaymentRecordRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.presenter.HuankuanFangshiPresenter;

import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.ui.adapter.FullyGridLayoutManager;
import com.pingtiao51.armsmodule.mvp.ui.adapter.GridImageAdapter;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.ComSingleWheelDialog;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/29/2019 18:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HuankuanFangshiActivity extends BaseArmsActivity<HuankuanFangshiPresenter> implements HuankuanFangshiContract.View {
    public final static String BORROW = "borrow";
    public final static String LENDER = "lender";
    public final static String AMOUNT = "amount";
    public final static String NOTEID = "noteid";
    public final static String USER_TYPE = "user_type";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHuankuanFangshiComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @BindView(R.id.huankuan_huankuanjine)
    TextView huankuan_huankuanjine;
    @BindView(R.id.huankuan_huankuanfangshi_layout)
    RelativeLayout huankuan_huankuanfangshi_layout;
    @BindView(R.id.qingxuanze)
    TextView qingxuanze;

    ComSingleWheelDialog mComSingleWheelDialog;

    private String mHuankuanfangshi = "";
    private String borrow = "";
    private String lender = "";
    private int noteid;
    private double amount;
    private int mUserType = 0;

    @OnClick({R.id.huankuan_huankuanfangshi_layout, R.id.huankuan_sure_btn})
    public void onPageClick(View view) {
        switch (view.getId()) {
            case R.id.huankuan_huankuanfangshi_layout:
                if (mComSingleWheelDialog == null) {
                    List<String> datas = Arrays.asList(getResources().getStringArray(R.array.huankuan_fangshi));
                    List<String> dataSend = Arrays.asList(getResources().getStringArray(R.array.huankuan_fangshi_send));
                    mComSingleWheelDialog = new ComSingleWheelDialog(this, datas,"还款方式");
                    mComSingleWheelDialog.setComSingleWheelInterface(new ComSingleWheelDialog.ComSingleWheelInterface() {
                        @Override
                        public void getChoiceStr(String str) {
                            qingxuanze.setText(str);
                            for (int i = 0; i < datas.size(); i++) {
                                if (datas.get(i).equals(str)) {
                                    mHuankuanfangshi = dataSend.get(i);
                                }
                            }
                        }
                    });
                }
                mComSingleWheelDialog.show();
                break;

            case R.id.huankuan_sure_btn:
                upAll();
                break;
        }
    }

    private void upAll(){
        if(TextUtils.isEmpty(mHuankuanfangshi)){
            ArmsUtils.snackbarText("请选择还款方式");
            return;
        }
        if(selectList.size() > 0){
            upLoadFilesOss();
        }else{
            addHistoryHuankuan();
        }
    }


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_huankuan_fangshi; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    DecimalFormat decimalFormat = new DecimalFormat("0.00");
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        borrow = getIntent().getStringExtra(BORROW);
        lender = getIntent().getStringExtra(LENDER);
        amount = getIntent().getDoubleExtra(AMOUNT,0);
        noteid = getIntent().getIntExtra(NOTEID, 0);
        String money = decimalFormat.format(amount);
        huankuan_huankuanjine.setText(money);
        setTitle("还款");
        initRecycler();
    }


    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    private List<LocalMedia> selectList = new ArrayList<>();
    private GridImageAdapter adapter;

    private void initRecycler() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(HuankuanFangshiActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(HuankuanFangshiActivity.this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(3);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = adapter.getDatas().get(position);
//                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(HuankuanFangshiActivity.this).themeStyle(R.style.picture_Sina_style).openExternalPreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(HuankuanFangshiActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(HuankuanFangshiActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            // 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(HuankuanFangshiActivity.this)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(R.style.picture_Sina_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(3)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(true ?
                            PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
                    .previewImage(true)// 是否可预览图片
                    .previewVideo(false)// 是否可预览视频
                    .enablePreviewAudio(false) // 是否可播放音频
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                    .enableCrop(false)// 是否裁剪
                    .compress(true)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    //.compressSavePath(getPath())//压缩图片保存地址
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                        .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                        .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
//                        .isGif(cb_isGif.isChecked())// 是否显示gif图片
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
//                        .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
//                        .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                        .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .openClickSound(true)// 是否开启点击声音
                    .selectionMedia(selectList)// 是否传入已选图片
                    //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                    //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                    //.rotateEnabled(true) // 裁剪是否可旋转图片
                    //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                    //.videoQuality()// 视频录制质量 0 or 1
                    //.videoSecond()//显示多少秒以内的视频or音频也可适用
                    //.recordVideoSecond()//录制视频秒数 默认60s
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

        }

    };


    private void upLoadFilesOss() {
        urls.clear();
        showLoading("正在提交信息");
        for (int i = 0; i < selectList.size(); i++) {
            uploadFile(selectList.get(i), i);
        }

    }

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
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                    }
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    private List<String> urls = new ArrayList<>();

    private void uploadFile(LocalMedia media, int position) {
//        File file = new File(media.getCompressPath());
        OssManager.getInstance().upload(this, position, media.getCompressPath(), new OssManager.OnUploadListener() {
            @Override
            public void onProgress(int position, long currentSize, long totalSize) {

            }

            @Override
            public void onSuccess(int position, String uploadPath, String imageUrl) {
                urls.add(imageUrl);
                if (urls.size() >= selectList.size()) {
                    addHistoryHuankuan();
                }
            }

            @Override
            public void onFailure(int position) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArmsUtils.snackbarText("上传图片失败，请重试");
                    }
                });
                hideLoading();
            }
        });

    }

    private void addHistoryHuankuan() {
        ArmsUtils.obtainAppComponentFromContext(this).repositoryManager().obtainRetrofitService(PingtiaoApi.class)
                .addRepaymentRecord(new AddRepaymentRecordRequest(
                        amount,
                        AppUtils.getAppVersionName(),
                        noteid,
                        "ANDRIOD",
                        lender,
                        borrow,
                        mHuankuanfangshi,
                        urls
                ))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        hideLoading();
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle((Lifecycleable) this))
                .subscribe(new ErrorHandleSubscriber<BaseJson<Object>>(ArmsUtils.obtainAppComponentFromContext(this).rxErrorHandler()) {
                    @Override
                    public void onNext(BaseJson<Object> objectBaseJson) {
                        if (objectBaseJson.isSuccess()) {
                            toHuankuanstatus();
                            finish();
                        } else {
                            ArmsUtils.snackbarText(objectBaseJson.getMessage());
                        }
                    }
                });
    }

    private void toHuankuanstatus(){
        mUserType = getIntent().getIntExtra(USER_TYPE, 0);
        Bundle bundle1 = new Bundle();
        bundle1.putString(HuankuanStatusActivity.BORROW, borrow);
        bundle1.putString(HuankuanStatusActivity.LENDER, lender);
        bundle1.putDouble(HuankuanStatusActivity.AMOUNT, amount);
        bundle1.putInt(HuankuanStatusActivity.NOTE_ID, noteid);
        bundle1.putInt(HuankuanStatusActivity.USER_TYPE, mUserType);
        ActivityUtils.startActivity(bundle1, HuankuanStatusActivity.class);
    }
}
