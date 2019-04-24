package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pingtiao51.armsmodule.R;
import com.zls.baselib.custom.view.dialog.FrameDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CycleDatePickerDialog extends FrameDialog {
    public interface ChoiceSureInterface {
        void sure(Date date);
    }

    private ChoiceSureInterface  mChoiceSureInterface;

    public void setChoiceSureInterface(ChoiceSureInterface csi) {
        this.mChoiceSureInterface = csi;
    }

    public CycleDatePickerDialog(Activity context, Calendar startCalender, Calendar endCalender) {
        super(context);
        this.startCalender = startCalender;
        this.endCalender = endCalender;
        mWheelDatePicker.setCalenderRange(startCalender, endCalender);
    }
    public CycleDatePickerDialog(Activity context, Calendar startCalender, Calendar endCalender,boolean isLast) {
        super(context);
        this.startCalender = startCalender;
        this.endCalender = endCalender;
        mWheelDatePicker.setCalenderRange(startCalender, endCalender,isLast);
    }

    @Override
    protected int getViewIds() {
        return R.layout.dialog_date_picker_cycle_layout;
    }

    ImageView cancel, sure;
    TextView title;
    private String titleStr;

    private Calendar startCalender;
    private Calendar endCalender;

    CycleDatePicker mWheelDatePicker;
    private Date mSelectDate = new Date();

    @Override
    protected void initView() {
        super.initView();
        setCanceledOnTouchOutside(false);
        cancel = findViewsId(R.id.cancel, true);
        sure = findViewsId(R.id.sure, true);
        title = findViews(R.id.title);
        mWheelDatePicker = findViews(R.id.wheeldatePicker);
//        mWheelDatePicker.setCalenderRange(startCalender, endCalender);
        mWheelDatePicker.setCycleDatePickerSelect(new CycleDatePicker.CycleDatePickerSelect() {
            @Override
            public void selectDate(Date date) {
                mSelectDate = date;
                Log.d("dodo", "date = " + sdf.format(date));
            }
        });
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分ss秒");
    /**
     * 借款日期设置
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title.setText(title);
        titleStr = title;

    }


    /**
     * 还款日期设置
     *
     * @param title
     * @param date
     */
    private Date startDate;

    public void setTitle(String title, Date date) {
        startDate = date;
        this.title.setText(title);
        titleStr = title;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.sure:
                if(mChoiceSureInterface != null){
                    mChoiceSureInterface.sure(mSelectDate);
                }
                dismiss();
                break;
        }
    }

    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else if (nowTime.compareTo(beginTime) == 0 || nowTime.compareTo(endTime) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean belongCalendar2(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else if (nowTime.compareTo(beginTime) == 0 || nowTime.compareTo(endTime) == 0) {
            return false;
        } else {
            return false;
        }
    }
}
