package com.pingtiao51.armsmodule.mvp.presenter;

import android.app.Application;
import android.os.Environment;
import android.util.Log;
import android.webkit.DownloadListener;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.ResponseBody;
import retrofit2.Response;

import javax.inject.Inject;

import com.jess.arms.utils.RxLifecycleUtils;
import com.pingtiao51.armsmodule.mvp.contract.DianziJietiaoXiangqingContract;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoDetailListResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoXiangqingResponse;
import com.pingtiao51.armsmodule.mvp.ui.activity.BaseArmsActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


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
@ActivityScope
public class DianziJietiaoXiangqingPresenter extends BasePresenter<DianziJietiaoXiangqingContract.Model, DianziJietiaoXiangqingContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public DianziJietiaoXiangqingPresenter(DianziJietiaoXiangqingContract.Model model, DianziJietiaoXiangqingContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getPingtiaoById(long id){
        mModel.getPingtiaoById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        ((BaseArmsActivity) mRootView).showLoading("正在加载凭条，请等待...");
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        mRootView.hideLoading();
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<PingtiaoXiangqingResponse>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson<PingtiaoXiangqingResponse> rep) {
                        if(rep.isSuccess()) {
                            mRootView.onSucJietiaoXq(rep.getData());
                        }
                    }
                });

    }
    public void modifyPingtiao(long id, List<String> urls){
        mModel.modifyPingtiao(id,urls)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<Object>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson<Object> rep) {
                        if(rep.isSuccess()) {
                            mRootView.onSucModifyPingtiao();
                        }
                    }
                });

    }

    public void downLoadFile(String url,String filename){
        mModel.downloadFile(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<ResponseBody>(mErrorHandler) {
                    @Override
                    public void onNext(ResponseBody body) {
                        writeFile2Disk(body,filename);
                    }
                });
    }




    //将下载的文件写入本地存储
    private void writeFile2Disk(ResponseBody response, String fileName) {
        String dir = Environment.getExternalStorageDirectory()+File.separator+"pingtiao";
        File file = new File(dir, fileName);
        // 如果文件不存在
        if (!file.exists()) {
            // 创建新的空文件
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        long currentLength = 0;
        OutputStream os = null;

        InputStream inputStream = response.byteStream(); //获取下载输入流
        long totalLength = response.contentLength();

        try {
            os = new FileOutputStream(file); //输出流
            int len;
            byte[] buff = new byte[1024];
            while ((len = inputStream.read(buff)) != -1) {
                os.write(buff, 0, len);
                currentLength += len;
                Log.e(TAG, "当前进度: " + currentLength);
                if ((int) (100 * currentLength / totalLength) == 100) {
                    mRootView.onSucDownload(file.getAbsolutePath());
                }

                //计算当前下载百分比，并经由回调传出
                //当百分比为100时下载结束，调用结束回调，并传出下载后的本地路径
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close(); //关闭输出流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close(); //关闭输入流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    }
