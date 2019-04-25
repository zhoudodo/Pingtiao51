package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.app.Activity;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.wheelpicker.widgets.WheelDatePicker;
import com.jess.arms.utils.ArmsUtils;
import com.pingtiao51.armsmodule.R;
import com.zls.baselib.custom.view.dialog.FrameDialog;

import java.util.Calendar;
import java.util.Date;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class DatePickerDialog extends FrameDialog {
    public interface clickListener {
        void sure(Date date);
    }

    private clickListener mclickListener;

    public void setClickListener(clickListener cl) {
        this.mclickListener = cl;
    }

    public DatePickerDialog(Activity context) {
        super(context);
    }

    PingtiaoWheelDatePicker mWheelDatePicker;
    private Date mDate;

    @Override
    protected int getViewIds() {
        return R.layout.dialog_date_picker_layout;
    }

    ImageView cancel, sure;
    TextView title;
    private String titleStr;

    Calendar mCalendar = Calendar.getInstance();
    Calendar mCalendar2 = Calendar.getInstance();

    @Override
    protected void initView() {
        super.initView();
        setCanceledOnTouchOutside(false);
        cancel = findViewsId(R.id.cancel, true);
        sure = findViewsId(R.id.sure, true);
        title = findViews(R.id.title);
        mWheelDatePicker = findViews(R.id.wheeldatePicker);
        mWheelDatePicker.findViewById(R.id.wheel_date_picker_year_tv).setVisibility(View.INVISIBLE);
        TextView monthTv = mWheelDatePicker.findViewById(R.id.wheel_date_picker_month_tv);
        monthTv.setTextColor(mActivity.getResources().getColor(R.color.gray_color_666666));
        monthTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        TextView dayTv = mWheelDatePicker.findViewById(R.id.wheel_date_picker_day_tv);
        dayTv.setTextColor(mActivity.getResources().getColor(R.color.gray_color_666666));
        dayTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        mWheelDatePicker.findViewById(R.id.wheel_date_picker_year_tv).setVisibility(View.INVISIBLE);
        mWheelDatePicker.setCurved(true);
        mWheelDatePicker.setAtmospheric(true);
        mWheelDatePicker.setItemTextSize(AutoSizeUtils.sp2px(mActivity, 22));
        mWheelDatePicker.setItemTextColor(mActivity.getResources().getColor(R.color.black_color_333333));
        mDate = mWheelDatePicker.getCurrentDate();
        mWheelDatePicker.setOnDateSelectedListener(new WheelDatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(WheelDatePicker picker, Date date) {
                mDate = date;
            }
        });
    }

    /**
     * 借款日期设置
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title.setText(title);
        titleStr = title;
        int year = mCalendar.get(Calendar.YEAR);
        int dayofmonth = mCalendar.get(Calendar.DAY_OF_MONTH);
        if (titleStr.equals("借款日期")) {
            mWheelDatePicker.setYearStart(year);
            mWheelDatePicker.setYearEnd(year);
        } else if (titleStr.equals("还款日期")) {
            mWheelDatePicker.setYearStart(year);
            mWheelDatePicker.setYearEnd(year + 10);
        }
    }

    /**
     * 借款日期设置
     *
     * @param title
     */
    boolean isZhizhishou = false;
    public void setTitle(String title, int beforeYear, int afterYear) {
        if(beforeYear == 20 && afterYear == 0){
            isZhizhishou = true;
        }
        this.title.setText(title);
        titleStr = title;
        int year = mCalendar.get(Calendar.YEAR);
        int dayofmonth = mCalendar.get(Calendar.DAY_OF_MONTH);
        mWheelDatePicker.setYearStart(year - beforeYear);
        mWheelDatePicker.setYearEnd(year + afterYear);
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
        mCalendar.setTime(date);
        int year = mCalendar.get(Calendar.YEAR);
        int dayofmonth = mCalendar.get(Calendar.DAY_OF_MONTH);
        if (titleStr.equals("借款日期")) {
            mWheelDatePicker.setYearStart(year);
            mWheelDatePicker.setYearEnd(year);
        } else if (titleStr.equals("还款日期")) {
            mWheelDatePicker.setYearStart(year);
            mWheelDatePicker.setYearEnd(year + 10);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.sure:
                mCalendar2.setTime(mDate);


                long time6Day = System.currentTimeMillis() + 6 * 24 * 60 * 60 * 1000;
                Date date = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
                Calendar canlandar = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();
                Calendar calendarZ1 = Calendar.getInstance();
                Calendar calendarZ2 = Calendar.getInstance();
                canlandar.setTime(date);
                canlandar.add(Calendar.YEAR, 10);
                calendar2.setTime(date);
                calendar2.add(Calendar.YEAR, -10);

                calendarZ1.add(Calendar.YEAR, 20);
                calendarZ2.add(Calendar.YEAR, -20);
//                canlandar.getTimeInMillis();

                if (titleStr.equals("借款日期")) {
                    if (!belongCalendar(mDate, new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000), new Date(time6Day))) {
                        ArmsUtils.snackbarText("借款日期限制为当天至今后6天");
                        return;
                    }

                } else if (titleStr.equals("还款日期")) {
                    if (!belongCalendar2(mDate, startDate, new Date(canlandar.getTimeInMillis()))) {
//                        ArmsUtils.snackbarText("还款日期限制为借款日至今后10年");
                        ArmsUtils.snackbarText("还款日期须大于借款日期");
                        return;
                    }
                } else if (titleStr.equals("经手日期")) {
                    if (!belongCalendar(mDate, new Date(calendar2.getTimeInMillis()), new Date(System.currentTimeMillis()))) {
                        ArmsUtils.snackbarText("经手日期须包含当日或小于当天日期");
                        return;
                    }
                } else if (titleStr.equals("约定还款日期")) {
                    if (!belongCalendar(mDate, new Date(calendarZ2.getTimeInMillis()), new Date(calendarZ1.getTimeInMillis()))) {
                        ArmsUtils.snackbarText("约定还款日期限制前后20年");
                        return;
                    }
                }else if(isZhizhishou){
                    if (!belongCalendar(mDate, new Date(calendarZ2.getTimeInMillis()), new Date(System.currentTimeMillis()))) {
                        ArmsUtils.snackbarText("纸质收条经手日期限制前20年");
                        return;
                    }
                }

                if (mclickListener != null && mDate != null) {
                    mclickListener.sure(mDate);
                    dismiss();
                }

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
