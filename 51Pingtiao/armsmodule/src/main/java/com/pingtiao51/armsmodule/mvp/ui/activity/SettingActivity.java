package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.makeramen.roundedimageview.RoundedImageView;
import com.pingtiao51.armsmodule.app.utils.OssManager;
import com.pingtiao51.armsmodule.di.component.DaggerSettingComponent;
import com.pingtiao51.armsmodule.mvp.contract.SettingContract;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.ExitAppTag;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.UserAvatarChangeTag;
import com.pingtiao51.armsmodule.mvp.presenter.SettingPresenter;

import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.InputLoginView;
import com.pingtiao51.armsmodule.mvp.ui.helper.GlideProxyHelper;
import com.pingtiao51.armsmodule.mvp.ui.helper.UrlDecoderHelper;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/13/2019 17:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SettingActivity extends BaseArmsActivity<SettingPresenter> implements SettingContract.View {

    public final static String AVATAR = "AVATAR";
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSettingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @BindView(R.id.user_avatar2)
    RoundedImageView user_avatar2;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_setting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
            setTitle("设置");
          String avatarUrl =  getIntent().getStringExtra(AVATAR);
//          if(!TextUtils.isEmpty(avatarUrl)){
              GlideProxyHelper.loadImgByPlaceholder(user_avatar2,R.drawable.wode_touxiang,UrlDecoderHelper.decode(avatarUrl));
//          }
    }

    @OnClick({R.id.xiugaidenglumima,R.id.xiugaiqianyuemima,R.id.exit_app
    ,R.id.touxiang,R.id.wodeyinhangka})
    public void onPageClick(View view){
        switch (view.getId()){
            case R.id.xiugaidenglumima:
                //TODO 修改登录密码
                Intent intent = new Intent(this,LoginActivity.class);
                intent.putExtra(LoginActivity.LOGIN_MODE,InputLoginView.CODE_LOGIN);
                startActivity(intent);
                break;

            case R.id.xiugaiqianyuemima:
                //TODO 修改签约密码
//                Intent intent = new Intent(this,LoginActivity.class);
//                intent.putExtra(LoginActivity.LOGIN_MODE,InputLoginView.CODE_LOGIN);
//                startActivity(intent);
                break;

            case R.id.exit_app:
                Bundle bundle = new Bundle();
                bundle.putInt(LoginActivity.LOGIN_MODE, InputLoginView.CODE_LOGIN);
                ActivityUtils.startActivity(bundle, LoginActivity.class);
                EventBus.getDefault().post(new ExitAppTag());
                finish();
                break;

            case R.id.touxiang:
                //设置头像
                paizhaoQu();
                 break;
            case R.id.wodeyinhangka:
                //我的银行卡
                startAct(MyBankCardsActivity.class);
                break;
        }
    }


    /**
     * 拍照去 全中文拼音理解更方便
     */
    private void paizhaoQu() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(SettingActivity.this)
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
                .enableCrop(true)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
//                    .isGif(cb_isGif.isChecked())// 是否显示gif图片
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .circleDimmedLayer(true)// 是否圆形裁剪
                    .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(true)// 是否开启点击声音
                .selectionMedia(selectList)// 是否传入已选图片
                .isDragFrame(true)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                .rotateEnabled(true) // 裁剪是否可旋转图片
                .scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
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
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                        GlideProxyHelper.loadImgForLocal(user_avatar2, media.getCompressPath());
                        EventBus.getDefault().post(new UserAvatarChangeTag(media.getCompressPath()));
                        uploadFile(media,0);
                    }
                    break;
            }
        }
    }


    private void uploadFile(LocalMedia media, int position) {
//        File file = new File(media.getCompressPath());
        OssManager.getInstance().upload(this, position, media.getCompressPath(), new OssManager.OnUploadListener() {
            @Override
            public void onProgress(int position, long currentSize, long totalSize) {

            }

            @Override
            public void onSuccess(int position, String uploadPath, String imageUrl) {
                //上传头像
                mPresenter.pushAvatar(imageUrl);
            }

            @Override
            public void onFailure(int position) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArmsUtils.snackbarText("上传图片失败，请重试");
                        hideLoading();
                    }
                });
            }
        });

    }

    @Override
    public void onSuccessPushAvatar() {

    }
}
