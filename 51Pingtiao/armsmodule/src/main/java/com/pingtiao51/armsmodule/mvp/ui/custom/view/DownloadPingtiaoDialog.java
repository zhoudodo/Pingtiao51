package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.model.api.service.PingtiaoApi;
import com.pingtiao51.armsmodule.mvp.model.entity.request.SendEmailRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.ui.helper.others.EmailUtils;
import com.zls.baselib.custom.view.dialog.FrameDialog;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class DownloadPingtiaoDialog extends FrameDialog {

    private String titleStr;
    private int noteId;

    public DownloadPingtiaoDialog(Activity context) {
        super(context);
    }

    public DownloadPingtiaoDialog(Activity context, String title, int noteid) {
        super(context);
        titleStr = title;
        noteId = noteid;
    }

    TextView mTitle;

    EditText mEditText;

    @Override
    protected void initView() {
        mTitle = findViews(R.id.dialog_generic_htv_title);
        setCanceledOnTouchOutside(false);
        mTitle.post(new Runnable() {
            @Override
            public void run() {
                mTitle.setText(titleStr);
            }
        });
        mEditText = findViews(R.id.dianziyouxiang);
        findViewsId(R.id.dialog_generic_btn_button1, true);
        findViewsId(R.id.dialog_generic_btn_button3, true);
    }

    @Override
    protected int getViewIds() {
        return R.layout.dialog_download_dianzijietiao;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_generic_btn_button1:
                dismiss();
                break;
            case R.id.dialog_generic_btn_button3:
                findViews(R.id.dialog_generic_btn_button3).setEnabled(false);
                Observable.timer(1000,TimeUnit.MILLISECONDS)
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                findViews(R.id.dialog_generic_btn_button3).setEnabled(true);
                            }
                        });

                pushEmail();
                break;
        }
    }

    @Override
    protected void initLocation() {
        WindowManager.LayoutParams params = getWindow()
                .getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setGravity(Gravity.CENTER);
    }

    /**
     * 将电子凭条发送至邮箱
     */
    private void pushEmail() {
        if (!EmailUtils.isEmail(mEditText.getText().toString())) {
            ArmsUtils.snackbarText("请输入正确的邮箱地址!");
            return;
        }
        //TODO 网络请求 上传邮箱
        ArmsUtils.obtainAppComponentFromContext(getContext()).repositoryManager().obtainRetrofitService(PingtiaoApi.class)
                .sendEmailDownloadNote(new SendEmailRequest(
                        mEditText.getText().toString(),
                        noteId
                ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<BaseJson<Object>>(ArmsUtils.obtainAppComponentFromContext(getContext()).rxErrorHandler()) {
                    @Override
                    public void onNext(BaseJson<Object> rep) {
                        if(rep.isSuccess()) {
                            ArmsUtils.snackbarText("发送成功");
                            DownloadPingtiaoDialog.this.dismiss();
                        }else{
                            ArmsUtils.snackbarText(rep.getMessage());
                        }
                    }
                });
    }

}
