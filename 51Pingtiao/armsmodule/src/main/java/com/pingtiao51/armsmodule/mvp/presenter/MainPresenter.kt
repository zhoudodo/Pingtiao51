package com.pingtiao51.armsmodule.mvp.presenter

import android.app.Application
import android.os.Environment
import android.util.Log

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.pingtiao51.armsmodule.mvp.contract.MainContract
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson
import com.pingtiao51.armsmodule.mvp.model.entity.response.CheckUpdateResponse
import com.pingtiao51.armsmodule.mvp.ui.helper.UrlDecoderHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import okhttp3.ResponseBody
import java.io.*


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/04/2019 11:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class MainPresenter
@Inject
constructor(model: MainContract.Model, rootView: MainContract.View) :
        BasePresenter<MainContract.Model, MainContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager


    override fun onDestroy() {
        super.onDestroy();
    }


    fun checkUpdate(){
        mModel.checkUpdate()
                .subscribeOn(Schedulers.io())
//                .doOnSubscribe { mRootView.showLoading() }
                .observeOn(AndroidSchedulers.mainThread())
//                .doAfterTerminate { mRootView.hideLoading() }
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseJson<CheckUpdateResponse>>(mErrorHandler) {
                    override fun onNext(json: BaseJson<CheckUpdateResponse>) {
                        if (json.isSuccess) {
                            mRootView.checkUpdate(json.data)
                        }
                    }
                })

    }

    fun downLoadFile(url: String, filename: String) {
        mModel.downloadFile(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<ResponseBody>(mErrorHandler) {
                    override fun onNext(body: ResponseBody) {
                        writeFile2Disk(body, filename)
                    }
                })
    }


    //将下载的文件写入本地存储

    private fun writeFile2Disk(response: ResponseBody, fileName: String) {
        val dir = Environment.getExternalStorageDirectory().toString() + File.separator + "pingtiao"
        val file = File(dir, fileName)
        // 如果文件不存在
        if (!file.exists()) {
            // 创建新的空文件
            file.parentFile.mkdirs()
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }


        var currentLength: Long = 0
        var os: OutputStream? = null

        val inputStream = response.byteStream() //获取下载输入流
        val totalLength = response.contentLength()

        try {
            os = FileOutputStream(file) //输出流
            var len: Int
            val buff = ByteArray(1024)

            do {
                len = inputStream.read(buff)
                if(len == -1){
                    break
                }
                os.write(buff, 0, len)
                currentLength += len.toLong()
                Log.e(TAG, "当前进度: $currentLength")
                if ((100 * currentLength / totalLength).toInt() == 100) {
                    mRootView.downApkSuc(file.absolutePath)
                }

                    var percent = (100 * currentLength / (totalLength*1.0f)).toInt();
                    mRootView.downPercent(percent)

            }while (true)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (os != null) {
                try {
                    os.close() //关闭输出流
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            if (inputStream != null) {
                try {
                    inputStream.close() //关闭输入流
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

}
