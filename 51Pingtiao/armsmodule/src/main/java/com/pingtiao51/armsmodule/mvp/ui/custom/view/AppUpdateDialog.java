package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.app.Activity;
import android.view.Gravity;
import android.view.WindowManager;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.pingtiao51.armsmodule.R;
import com.zls.baselib.custom.view.dialog.FrameDialog;

public class AppUpdateDialog extends FrameDialog {

    public AppUpdateDialog(Activity context) {
        super(context);
        setCancelable(false);
    }
    String loadUrl;
    public AppUpdateDialog(Activity context,String url) {
        super(context);
        setCancelable(false);
        loadUrl = url;
    }
    NumberProgressBar numberProgressBar;
    @Override
    protected void initView() {
        super.initView();
        numberProgressBar = findViews(R.id.number_progress_bar);
//        ProgressManager.getInstance().addResponseListener(loadUrl, new ProgressListener() {
//            @Override
//            public void onProgress(ProgressInfo progressInfo) {
//                int progressPercent = (int) ((progressInfo.getCurrentbytes()/(progressInfo.getContentLength()*1.0f))*100f);
//                numberProgressBar.setProgress(progressPercent);
//            }
//
//            @Override
//            public void onError(long id, Exception e) {
//                Log.d("dodot","di =" +id);
//            }
//        });
    }

    @Override
    protected int getViewIds() {
        return R.layout.dialog_check_update;
    }

    @Override
    public void show() {
        super.show();

    }
    protected void initLocation() {
        WindowManager.LayoutParams params = getWindow()
                .getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setGravity(Gravity.CENTER);
    }

    public void setNumberProgressBar(int progressPercent){
        numberProgressBar.setProgress(progressPercent);
    }

}
