package com.pingtiao51.armsmodule.mvp.ui.activity;


import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.model.api.service.PingtiaoApi;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.AppUpdateDialog;
import com.pingtiao51.armsmodule.mvp.ui.helper.UrlDecoderHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.ResponseBody;

/**
 * 确权webview
 */
public class WebViewZXActivity extends FragmentActivity {

    public final static String WEBVIEW_URL = "WEBVIEW_URL";
    public final static String WEBVIEW_TITLE = "WEBVIEW_TITLE";


    PDFView pdfView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_zx_layout);
        pdfView = findViewById(R.id.pdfView);
        initPageInfos();
    }


    AppUpdateDialog mAppUpdateDialog;

    private void initPageInfos() {
        String title = getIntent().getStringExtra(WEBVIEW_TITLE);
        setTitle(title);
        String url = getIntent().getStringExtra(WEBVIEW_URL);
        mAppUpdateDialog = new AppUpdateDialog(this, url);
        mAppUpdateDialog.show();
        downLoadFile(url, "cunzhengzhengming.pdf");
    }


    public void downLoadFile(String url, String filename) {
        ArmsUtils.obtainAppComponentFromContext(this).repositoryManager().obtainRetrofitService(PingtiaoApi.class).downloadFile(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new ErrorHandleSubscriber<ResponseBody>(ArmsUtils.obtainAppComponentFromContext(this).rxErrorHandler()) {
                    @Override
                    public void onNext(ResponseBody body) {
                        File file = writeFile2Disk(body, filename);
                        loadPDF(file);
                    }
                });
    }

    private void loadPDF(File file) {
        mAppUpdateDialog.dismiss();
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        pdfView.fromFile(file)
//                                .pages(0, 1, 1, 1, 1, 1) // all pages are displayed by default
                                .enableSwipe(true) // allows to block changing pages using swipe
                                .swipeHorizontal(false)
                                .enableDoubletap(true)
                                .defaultPage(0)
                                // allows to draw something on the current page, usually visible in the middle of the screen
//                                .onDraw(onDrawListener)
                                // allows to draw something on all pages, separately for every page. Called only for visible pages
//                                .onDrawAll(onDrawListener)
//                                .onLoad(onLoadCompleteListener) // called after document is loaded and starts to be rendered
//                                .onPageChange(onPageChangeListener)
//                                .onPageScroll(onPageScrollListener)
//                                .onError(onErrorListener)
//                                .onPageError(onPageErrorListener)
//                                .onRender(onRenderListener) // called after document is rendered for the first time
                                // called on single tap, return true if handled, false to toggle scroll handle visibility
//                                .onTap(onTapListener)
//                                .onLongPress(onLongPressListener)
                                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                                .password(null)
                                .scrollHandle(null)
                                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                                // spacing between pages in dp. To define spacing color, set view background
                                .spacing(0)
//                                .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
//                                .linkHandler(DefaultLinkHandler)
//                                .pageFitPolicy(FitPolicy.WIDTH)
//                                .pageSnap(true) // snap pages to screen boundaries
//                                .pageFling(false) // make a fling change only a single page like ViewPager
//                                .nightMode(false) // toggle night mode
                                .load();
                    }
                }).isDisposed();
    }

    private void refreshProgress(int progress) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAppUpdateDialog.setNumberProgressBar(progress);
            }
        });

    }

    //将下载的文件写入本地存储
    private File writeFile2Disk(ResponseBody response, String fileName) {
        String dir = Environment.getExternalStorageDirectory() + File.separator + "pingtiao";
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

        InputStream is = response.byteStream(); //获取下载输入流
        long totalLength = response.contentLength();

        try {
            os = new FileOutputStream(file); //输出流
            int len;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
                currentLength += len;
                if ((int) (100 * currentLength / totalLength) == 100) {
//                    loadPDF();
                }
                refreshProgress((int) (100 * currentLength / totalLength));
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
            if (is != null) {
                try {
                    is.close(); //关闭输入流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return file;
        }
    }
}

