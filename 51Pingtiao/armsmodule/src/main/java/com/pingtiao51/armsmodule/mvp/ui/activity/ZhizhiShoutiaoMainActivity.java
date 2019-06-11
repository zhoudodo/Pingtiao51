package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.pingtiao51.armsmodule.app.utils.OssManager;
import com.pingtiao51.armsmodule.di.component.DaggerZhizhiShoutiaoMainComponent;
import com.pingtiao51.armsmodule.mvp.contract.ZhizhiShoutiaoMainContract;
import com.pingtiao51.armsmodule.mvp.presenter.ZhizhiShoutiaoMainPresenter;

import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.CycleDatePicker;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.CycleDatePickerDialog;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.DatePickerDialog;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.JiekuanyongtuDialog;
import com.pingtiao51.armsmodule.mvp.ui.helper.GlideProxyHelper;
import com.umeng.analytics.MobclickAgent;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/20/2019 14:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class ZhizhiShoutiaoMainActivity extends BaseArmsActivity<ZhizhiShoutiaoMainPresenter> implements ZhizhiShoutiaoMainContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerZhizhiShoutiaoMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_zhizhi_shoutiao_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    public static final int ZHIZHI_JIETIAO = 0x223;
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initTitle();
    }

    /**
     * 检测是否能保存按钮
     *
     * @return
     */
    private boolean checkCanCreate() {
        if (TextUtils.isEmpty(zhizhishoutiao_dijiaoren.getText().toString())) {
            ArmsUtils.snackbarText("请填写递交人");
            return false;
        }
        if (TextUtils.isEmpty(zhizhishoutiao_jingshouren.getText().toString())) {
            ArmsUtils.snackbarText("请填写经手人");
            return false;
        }
        if (TextUtils.isEmpty(zhizhishoutiao_dijiaojine.getText().toString())) {
            ArmsUtils.snackbarText("请填写递交金额");
            return false;
        }


        if (selectList.size() <= 0) {
            ArmsUtils.snackbarText("请上传收凭条照片");
            return false;
        }


        return true;
    }

    private void initTitle() {
        setTitle("纸质收条");
        TextView tvRight = findViewById(R.id.right_tv);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("模板");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 纸质收条 模板
//                startAct(ZhizhiMoban1Activity.class);
                ActivityUtils.startActivity(ShoutiaoMobanVpActivityActivity.class);
            }
        });
    }

    private void toPaizhao() {
        paizhaoQu();
    }


//    DatePickerDialog mDatePickerDialog;
    CycleDatePickerDialog mDatePickerDialog;
    JiekuanyongtuDialog mJiekuanyongtuDialog;


    private String jiekuanyongtuStr = "";
    private String jiekuanyongtuQita = "";//借款用途其他选项

    private Date mDate = new Date();
    Calendar calendar1 = Calendar.getInstance();
    private String zhizhiriqiReq;
    @OnClick({R.id.save_btn, R.id.paizhao, R.id.choice_zhizhijingshouriqi_layout})
    public void onPageClick(View view) {
        switch (view.getId()) {
            case R.id.save_btn:
                if (checkCanCreate()) {
                    //TODO 纸质借条保存
                    MobclickAgent.onEvent(this, "beifenshoutiao_save", "备份收条页\t点击“保存”");
                    findViewById(R.id.save_btn).setEnabled(false);
                    findViewById(R.id.save_btn).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.save_btn).setEnabled(true);
                        }
                    },2000);
                    upLoadFileOss();
                }
                break;
            case R.id.paizhao:
                if (selectList.size() <= 0) {
                    toPaizhao();
                } else {
                    Intent intent = new Intent(ZhizhiShoutiaoMainActivity.this, YulanZhizhiJietiaoActivity.class);
                    intent.putParcelableArrayListExtra(YulanZhizhiJietiaoActivity.PICTURE_DATA, (ArrayList<? extends Parcelable>) selectList);
                    startActivityForResult(intent, ZHIZHI_JIETIAO);
                }
                break;
            case R.id.choice_zhizhijingshouriqi_layout:
                if (mDatePickerDialog == null) {
                    Calendar start = Calendar.getInstance();
                    start.setTime(new Date());//
                    start.add(Calendar.YEAR,-20);

                    Calendar end = Calendar.getInstance();
                    end.setTime(new Date());//
                    mDatePickerDialog = new CycleDatePickerDialog(this,start,end,true);
                    mDatePickerDialog.setTitle("经手日期");
                    mDatePickerDialog.setChoiceSureInterface(new CycleDatePickerDialog.ChoiceSureInterface() {
                        @Override
                        public void sure(Date date) {
                            mDate = date;
                            calendar1.setTime(mDate);
                            zhizhiriqiReq =  calendar1.get(Calendar.YEAR) + "-"
                                    + (calendar1.get(Calendar.MONTH) + 1) + "-"
                                    + calendar1.get(Calendar.DAY_OF_MONTH) ;
                            zhizhijingshouriqi.setText(
                                    calendar1.get(Calendar.YEAR) + "年"
                                            + (calendar1.get(Calendar.MONTH) + 1) + "月"
                                            + calendar1.get(Calendar.DAY_OF_MONTH) + "日");
                        }
                    });
                }
                mDatePickerDialog.show();
                break;
        }
    }


    @BindView(R.id.paizhao)
    ImageView paizhao;

    @BindView(R.id.zhizhishoutiao_dijiaojine)
    EditText zhizhishoutiao_dijiaojine;

    @BindView(R.id.zhizhishoutiao_jingshouren)
    EditText zhizhishoutiao_jingshouren;

    @BindView(R.id.zhizhishoutiao_dijiaoren)
    EditText zhizhishoutiao_dijiaoren;


    @BindView(R.id.zhizhixieshoutiao_beizhu_edit)
    EditText zhizhixieshoutiao_beizhu_edit;

    @BindView(R.id.save_btn)
    TextView save_btn;

    @BindView(R.id.zhizhijingshouriqi)
    TextView zhizhijingshouriqi;


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
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                        GlideProxyHelper.loadImgForLocal(paizhao, media.getPath());
                    }
                    break;
                case ZHIZHI_JIETIAO:

                    List<LocalMedia> tempList = data.getParcelableArrayListExtra(YulanZhizhiJietiaoActivity.PICTURE_DATA);
                    selectList.clear();
                    selectList.addAll(tempList);
                    if (selectList.size() <= 0) {
                        paizhao.setImageDrawable(getResources().getDrawable(R.drawable.zhizhijiejiao_paizhao_bg));
                    } else {
                        GlideProxyHelper.loadImgForLocal(paizhao, selectList.get(0).getPath());
                    }
                    break;
            }
        }
    }

    /**
     * 拍照去 全中文拼音理解更方便
     */
    private void paizhaoQu() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(ZhizhiShoutiaoMainActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_Sina_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(false ?
                        PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
//                    .previewVideo(cb_preview_video.isChecked())// 是否可预览视频
//                    .enablePreviewAudio(cb_preview_audio.isChecked()) // 是否可播放音频
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
//                    .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                    .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
//                    .isGif(cb_isGif.isChecked())// 是否显示gif图片
//                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
//                    .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
//                    .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                    .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
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

    private void upLoadFileOss(){
        for(int i=0;i<selectList.size();i++){
            uploadFile(selectList.get(i),i);
        }
    }
    private void createZhizhiShoutiao(){
        mPresenter.beifenZhizhiJietiao(
                zhizhishoutiao_dijiaojine.getText().toString(),
                zhizhishoutiao_dijiaoren.getText().toString(),
                zhizhixieshoutiao_beizhu_edit.getText().toString(),
                zhizhishoutiao_jingshouren.getText().toString(),
                zhizhiriqiReq,
                urls
        );
    }

    private List<String> urls = new ArrayList<>();
    private void uploadFile(LocalMedia media,int position){
//        File file = new File(media.getCompressPath());
        OssManager.getInstance().upload(this, position, media.getCompressPath(), new OssManager.OnUploadListener() {
            @Override
            public void onProgress(int position, long currentSize, long totalSize) {

            }

            @Override
            public void onSuccess(int position, String uploadPath, String imageUrl) {
                urls.add(imageUrl);
                if(urls.size() >= selectList.size()){
                    createZhizhiShoutiao();
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


    @Override
    public void onSucBeifenShoutiao() {
        ArmsUtils.snackbarText("成功保存纸质收条");
        zhizhixieshoutiao_beizhu_edit.postDelayed(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.ZHI_ZHI_SHOU);
                startActBundle(bundle, MyPingtiaoActivity.class);
                ActivityUtils.finishActivity(SecureCopyActivity.class);
                finish();
            }
        },500);
    }
}
