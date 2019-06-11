package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.app.utils.OssManager;
import com.pingtiao51.armsmodule.di.component.DaggerXieShoutiaoComponent;
import com.pingtiao51.armsmodule.mvp.contract.XieShoutiaoContract;
import com.pingtiao51.armsmodule.mvp.model.api.service.PayApi;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.PaySuccessTag;
import com.pingtiao51.armsmodule.mvp.model.entity.request.CreateDingdanRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.ProductPriceRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.CreateDingdanResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.ProductPriceResponse;
import com.pingtiao51.armsmodule.mvp.presenter.XieShoutiaoPresenter;
import com.pingtiao51.armsmodule.mvp.ui.adapter.FullyGridLayoutManager;
import com.pingtiao51.armsmodule.mvp.ui.adapter.GridImageAdapter;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.BankPayDialog;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.CycleDatePickerDialog;
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst;
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference;
import com.umeng.analytics.MobclickAgent;
import com.zls.baselib.custom.view.dialog.DialogChooseNormal;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/19/2019 12:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class XieShoutiaoActivity extends BaseArmsActivity<XieShoutiaoPresenter> implements XieShoutiaoContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerXieShoutiaoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_xie_shoutiao; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initTilte();
        getPrice();
        initPage();
        initRecycler();
        initMultiEditText();
    }

    private void initTilte() {
        setTitle("收条");
        TextView rightTv = findViewById(R.id.right_tv);
        rightTv.setText("预览");
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yulanshoutiao();
            }
        });
    }

    private void initMultiEditText(){
        xieshoutiao_beizhu_edit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
                if ((v.getId() == R.id.xieshoutiao_beizhu_edit && canVerticalScroll(xieshoutiao_beizhu_edit))) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;
            }
        });
    }

    /**
     * EditText竖直方向是否可以滚动
     * @param editText 需要判断的EditText
     * @return true：可以滚动  false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() -editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if(scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }



 /*   private  int getCurrentCursorLine(EditText editText) {
        int selectionStart = Selection.getSelectionStart(editText.getText());
        Layout layout = editText.getLayout();

        if (!(selectionStart == -1)) {
            return layout.getLineForOffset(selectionStart);
        }

        return -1;
    }
*/
    private void yulanshoutiao() {
        MobclickAgent.onEvent(this, "dianzishoutiaoluyan", "写电子收条\t点击“预览”");
        Intent intent = new Intent(XieShoutiaoActivity.this, YulanShoutiaoActivity.class);
        Bundle bundle = new Bundle();
        String hejijine = TextUtils.isEmpty(xieshoutiao_dijiaojine_edit.getText().toString()) ? "0" : xieshoutiao_dijiaojine_edit.getText().toString();
        bundle.putString(YulanShoutiaoActivity.hejijine, hejijine);
        String dijiaorenxingming = TextUtils.isEmpty(xieshouiao_dijiaoren_edit.getText().toString()) ? "- -" : xieshouiao_dijiaoren_edit.getText().toString();
        bundle.putString(YulanShoutiaoActivity.dijiaorenxingming, dijiaorenxingming);
        String shoutiaochujushijian = TextUtils.isEmpty(jingshouriqi) ? "- -" : jingshouriqi;
        bundle.putString(YulanShoutiaoActivity.shoutiaochujushijian, shoutiaochujushijian);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @BindView(R.id.xieshoutiao_jingshouren_tv)
    TextView xieshoutiao_jingshouren_tv;
    @BindView(R.id.xieshoutiao_jingshouriqi_tv)
    TextView xieshoutiao_jingshouriqi_tv;
    @BindView(R.id.xieshouiao_dijiaoren_edit)
    EditText xieshouiao_dijiaoren_edit;//递交人Edit
    @BindView(R.id.xieshoutiao_dijiaojine_edit)
    EditText xieshoutiao_dijiaojine_edit;//递交金额Edit
    @BindView(R.id.xieshoutiao_beizhu_edit)
    EditText xieshoutiao_beizhu_edit;

    private void initPage() {
        //经手人
        xieshoutiao_jingshouren_tv.setText(SavePreference.getStr(this, PingtiaoConst.USER_NAME));
    }

    Calendar calendar = Calendar.getInstance();
    CycleDatePickerDialog mDatePickerDialog;
    String jingshouriqi = "";
    String jingshouriqiReq = "";
    String jingshouriqiVal = "";

    @OnClick({R.id.choice_jingshouriqi_layout, R.id.xiejietiao_btn})
    public void onPageClick(View v) {
        switch (v.getId()) {
            case R.id.choice_jingshouriqi_layout:
                if (mDatePickerDialog == null) {
                    Calendar start = Calendar.getInstance();
                    start.setTime(new Date());//
                    start.add(Calendar.YEAR,-10);

                    Calendar end = Calendar.getInstance();
                    end.setTime(new Date());//
                    mDatePickerDialog = new CycleDatePickerDialog(this,start,end,true);
                    mDatePickerDialog.setChoiceSureInterface(new CycleDatePickerDialog.ChoiceSureInterface() {
                                                           @RequiresApi(api = Build.VERSION_CODES.N)
                                                           @Override
                                                           public void sure(Date date) {
                                                               calendar.setTime(date);
                                                               jingshouriqi = calendar.get(Calendar.YEAR) + "年"
                                                                       + (calendar.get(Calendar.MONTH) + 1) + "月"
                                                                       + calendar.get(Calendar.DAY_OF_MONTH) + "日";
                                                               jingshouriqiVal = calendar.get(Calendar.YEAR) + "/"
                                                                       + (calendar.get(Calendar.MONTH) + 1) + "/"
                                                                       + calendar.get(Calendar.DAY_OF_MONTH)
                                                               ;
                                                               jingshouriqiReq = calendar.get(Calendar.YEAR) + "-"
                                                                       + (calendar.get(Calendar.MONTH) + 1) + "-"
                                                                       + calendar.get(Calendar.DAY_OF_MONTH)
                                                               ;
                                                               xieshoutiao_jingshouriqi_tv.setText(jingshouriqi);
                                                           }
                                                       }
                    );
                    mDatePickerDialog.setTitle(getResources().getString(R.string.jingshouriqi));
                }
                mDatePickerDialog.show();
                break;
            case R.id.xiejietiao_btn:
                if (checkAllPass()) {
//                    showLoading("正在生成收条，请等待...");
                    MobclickAgent.onEvent(this, "shengchengshoutiao", "写电子收条\t点击“生成收条”");
                    findViewById(R.id.xiejietiao_btn).setEnabled(false);
                    findViewById(R.id.xiejietiao_btn).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.xiejietiao_btn).setEnabled(true);
                        }
                    }, 2000);
                    if (selectList.size() > 0) {
                        showLoading();
                        upLoadFilesOss();
                    } else {
                        createShoutiao();
                    }
                }
                break;
        }
    }

    private boolean checkAllPass() {
        if (TextUtils.isEmpty(xieshouiao_dijiaoren_edit.getText().toString())) {
            ArmsUtils.snackbarText("请填写递交人");
            return false;
        }

        if (TextUtils.isEmpty(xieshoutiao_dijiaojine_edit.getText().toString())) {
            ArmsUtils.snackbarText("请填写递交金额");
            return false;
        }
        if (TextUtils.isEmpty(jingshouriqiReq)) {
            ArmsUtils.snackbarText("请填写经手日期");
            return false;
        }
        return true;
    }

    /**
     * 返回是否提醒
     * @return
     */
    private boolean checkNotHint() {
        boolean bo1 = TextUtils.isEmpty(xieshouiao_dijiaoren_edit.getText().toString());
        boolean bo2 = TextUtils.isEmpty(xieshoutiao_dijiaojine_edit.getText().toString());
        boolean bo3 = TextUtils.isEmpty(jingshouriqiReq);

      if(bo1 && bo2 && bo3){
          return true;
      }else{
          return false;
      }
    }

    private void upLoadFilesOss() {
        urls.clear();

        for (int i = 0; i < selectList.size(); i++) {
            uploadFile(selectList.get(i), i);
        }

    }

    private void createShoutiao() {

        mPresenter.addDianziShoutiao(
                xieshoutiao_dijiaojine_edit.getText().toString(),
                SavePreference.getStr(this, PingtiaoConst.USER_NAME),
                xieshoutiao_beizhu_edit.getText().toString(),
                xieshouiao_dijiaoren_edit.getText().toString(),
                jingshouriqiReq,
                urls
        );
    }

    @Override
    public void onBackPressed() {
        if (mDialogChooseNormal == null || !isShow) {
            if(checkNotHint()){
                super.onBackPressed();
            }else {
                backDialog();
            }
        } else {
            super.onBackPressed();
        }
    }

    DialogChooseNormal mDialogChooseNormal;
    private boolean isShow = false;

    private void backDialog() {
        isShow = true;
        if (mDialogChooseNormal == null) {
            mDialogChooseNormal = new DialogChooseNormal.ChooseBuilder()
                    .setTitle("提示")
                    .setContext(this)
                    .setContent("退回将清空当前页面所有内容")
                    .setBtn1Content("清空").setOnClickListener1(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    })
                    .setBtn1Colort(R.color.gray_color_7D7D7D)
                    .setBtn3Content("取消")
                    .setOnClickListener3(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDialogChooseNormal.dismiss();
                            isShow = false;
                        }
                    }).build();
        }
        mDialogChooseNormal.show();
    }

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    private List<LocalMedia> selectList = new ArrayList<>();
    private GridImageAdapter adapter;

    private void initRecycler() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(XieShoutiaoActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(XieShoutiaoActivity.this, onAddPicClickListener);
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
                            PictureSelector.create(XieShoutiaoActivity.this).themeStyle(R.style.picture_Sina_style).openExternalPreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(XieShoutiaoActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(XieShoutiaoActivity.this).externalPictureAudio(media.getPath());
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
            PictureSelector.create(XieShoutiaoActivity.this)
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
                    createShoutiao();
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

    BankPayDialog mBankPayDialog;
    private String mNoteid;

    @Override
    public void onSucAddDianziShoutiao(String noteid) {
        mNoteid = noteid;
        hideLoading();
        mBankPayDialog = new BankPayDialog(this, noteid);
        beforePay();
//        mBankPayDialog.show();

         /*   xieshoutiao_beizhu_edit.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            },500);*/
    }

    @Override
    public void onSucAuthSign(String url) {
        Bundle bundle1 = new Bundle();
        bundle1.putString(BaseWebViewActivity.WEBVIEW_TITLE, "手动签章");
        bundle1.putString(BaseWebViewActivity.WEBVIEW_URL, url);
        bundle1.putString(WebViewSignActivity.NOTE_ID, mNoteid);
        startActBundle(bundle1, WebViewSignActivity.class);
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPaySuc(PaySuccessTag tag) {
        switch (tag.getType()) {
            case PaySuccessTag.PAY_SUCCESS:
                if(mBankPayDialog != null) {
                    mBankPayDialog.dismiss();
                }
//                ArmsUtils.snackbarText("支付成功");
                /*
                Bundle bundleX = new Bundle();
                bundleX.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.DIAN_ZI_SHOU);
                startActBundle(bundleX, MyPingtiaoActivity.class);
                ActivityUtils.finishActivity(CreateDianziJietiaoActivity.class);
                ActivityUtils.finishActivity(CreateJietiaoActivity.class);
                finish();
                */
                //手动签章为了防止 过快后台文件未准备好
                Observable.timer(2000,TimeUnit.MILLISECONDS)
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                showLoading("正在准备签章信息...");
                            }
                        })
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                mPresenter.authSign(mNoteid, SavePreference.getStr(XieShoutiaoActivity.this, PingtiaoConst.USER_NAME), WebViewSignActivity.XIE_SHOUTIAO_RETURNURL);
                            }
                        }).isDisposed();
                break;
        }
    }

    private double mMoney = 0;
    private void getPrice() {
        ArmsUtils.obtainAppComponentFromContext(this).repositoryManager().obtainRetrofitService(PayApi.class).productPrice(new ProductPriceRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseJson<ProductPriceResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseJson<ProductPriceResponse> rep) {
                        if (rep.isSuccess()) {
                            ProductPriceResponse pr = rep.getData();
                            double money = pr.getDiscountPrice();
                            mMoney = money;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    /**
     * 价格大于0 支付弹窗  其他情况 默认调用微信支付创建订单
     */
    private void  beforePay(){
        if(mMoney > 0) {
            mBankPayDialog.show();
        }else{
            ArmsUtils.obtainAppComponentFromContext(this).repositoryManager().obtainRetrofitService(PayApi.class).createDingdan(new CreateDingdanRequest(
                    AppUtils.getAppVersionName(),
                    Integer.valueOf(mNoteid),
                    null,
                    "ANDRIOD",
                    mMoney+"",
                    "WECHAT",//BANKCARD,WECHAT,ALIPAY
                    "APP",
                    null
            ))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseJson<CreateDingdanResponse>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(BaseJson<CreateDingdanResponse> rep) {
                            if (rep.isSuccess()) {
                                EventBus.getDefault().post(new PaySuccessTag(PaySuccessTag.PAY_SUCCESS));
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }
}
