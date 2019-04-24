package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.aigestudio.wheelpicker.WheelPicker;
import com.pingtiao51.armsmodule.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CycleDatePicker extends LinearLayout {

    public interface CycleDatePickerSelect{
         void selectDate(Date date);
    }
    CycleDatePickerSelect mCycleDatePickerSelect;

    public void setCycleDatePickerSelect(CycleDatePickerSelect cycleDatePickerSelect){
        mCycleDatePickerSelect = cycleDatePickerSelect;
    }


    public CycleDatePicker(Context context) {
        this(context, null);
    }

    public CycleDatePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    Unbinder mUnbinder;

    public CycleDatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        addView(LayoutInflater.from(getContext()).inflate(R.layout.include_login_layout, this, false));
        LayoutInflater.from(context).inflate(R.layout.date_picker_cycle_layout, this);
        mUnbinder = ButterKnife.bind(this);
        startCalender.setTime(new Date());
        startCalender.add(Calendar.YEAR,-20);
        endCalender.setTime(new Date());
        endCalender.add(Calendar.YEAR,20);
        initFirst();
        initConfigs();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        mUnbinder.unbind();
    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CycleDatePicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private Calendar startCalender = Calendar.getInstance();
    private Calendar endCalender = Calendar.getInstance();

    @BindView(R.id.wheel_date_picker_year)
    WheelPicker wheel_date_picker_year;
    @BindView(R.id.wheel_date_picker_month)
    WheelPicker wheel_date_picker_month;
    @BindView(R.id.wheel_date_picker_day)
    WheelPicker wheel_date_picker_day;

    private void initConfigs() {
        wheel_date_picker_year.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                yearChange(picker, data, position);
            }
        });

        wheel_date_picker_month.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                    monthChange(picker, data, position);
            }
        });

        wheel_date_picker_day.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                dayChange(picker, data, position);
            }
        });
    }

    private List<Integer> mYearList = new ArrayList<>();
    private List<Integer> mMonthList = new ArrayList<>();
    private List<Integer> mDayList = new ArrayList<>();
    private int startYear, endYear;

    public void setCalenderRange(Calendar startCalender, Calendar endCalender) {
        this.startCalender = startCalender;
        this.endCalender = endCalender;
        startYear = this.startCalender.get(Calendar.YEAR);
        endYear = this.endCalender.get(Calendar.YEAR);
        initFirst();//
    }
    private boolean isFirstLast = false;
    public void setCalenderRange(Calendar startCalender, Calendar endCalender,boolean isFirstLast) {
        this.isFirstLast = isFirstLast;
        this.startCalender = startCalender;
        this.endCalender = endCalender;
        startYear = this.startCalender.get(Calendar.YEAR);
        endYear = this.endCalender.get(Calendar.YEAR);
        initLast();//
    }

    private Calendar localCalendar = Calendar.getInstance();
    private int selectDay;
    private void dayChange(WheelPicker picker, Object data, int position){
        selectDay = (int) data;
        callback();
    }

    private int selectMonth;
    private void monthChange(WheelPicker picker, Object data, int position){
        selectMonth = (int) data;
        mDayList.clear();
        if(selectYear == oneYear && selectMonth == oneMonth){
            mDayList.addAll(oneDays);
        }else if(selectYear == twoYear && selectMonth == twoMonth){
            mDayList.addAll(twoDays);
        }else{
            int startDay = 1;
            int endDay = getDaysByYearMonth(selectYear,selectMonth);
            for(int i=startDay;i<= endDay ;i++){
                mDayList.add(i);
            }
        }
        wheel_date_picker_day.setData(mDayList);
        selectDay = mDayList.get(0);
        wheel_date_picker_day.setSelectedItemPosition(0);
        callback();
    }
    private int selectYear;
    private void yearChange(WheelPicker picker, Object data, int position){
        selectYear = (int) data;
        mMonthList.clear();
        mDayList.clear();
        //能改变年份说明年份大于1
        if(selectYear == mYearList.get(0)){
//            mMonthList.clear();
            int startMonth = this.startCalender.get(Calendar.MONTH) + 1;
            int endMonth = 12;
            for (int i = startMonth; i <= endMonth; i++) {
                mMonthList.add(i);
            }
//            mDayList.clear();
            int startDay = this.startCalender.get(Calendar.DAY_OF_MONTH);
            int endDay = this.endCalender.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = startDay; i <= endDay; i++) {
                mDayList.add(i);
            }


        }else if(selectYear == mYearList.get(mYearList.size() - 1)){
//            mMonthList.clear();
            int startMonth = 1;
            int endMonth = this.endCalender.get(Calendar.MONTH)+1;
            for (int i = startMonth; i <= endMonth; i++) {
                mMonthList.add(i);
            }
//            mDayList.clear();
            if(endMonth > startMonth) {
                int startDay = 1;
                int endDay = 31;
                for (int i = startDay; i <= endDay; i++) {
                    mDayList.add(i);
                }
            }else{
                int startDay = 1;
                int endDay = this.endCalender.get(Calendar.DAY_OF_MONTH);
                for (int i = startDay; i <= endDay; i++) {
                    mDayList.add(i);
                }
            }

        }else{
//            mMonthList.clear();
            for(int i=1; i<=12; i++){
                mMonthList.add(i);
            }
//            wheel_date_picker_month.setData(mMonthList);

            mDayList.clear();
            for(int i=1; i<=31; i++){
                mDayList.add(i);
            }

        }
        wheel_date_picker_month.setData(mMonthList);
        wheel_date_picker_day.setData(mDayList);
        wheel_date_picker_month.setSelectedItemPosition(0);
        selectMonth = mMonthList.get(0);
        wheel_date_picker_day.setSelectedItemPosition(0);
        selectDay = mDayList.get(0);
        callback();
    }


    /**
     * 初始化初始值
     */

    private int oneYear,oneMonth;//第一个年月 对应的天数
    private int twoYear,twoMonth;//最后一个年月 对应的天数
    private List<Integer> oneDays = new ArrayList<>();//第一个年月 对应的天数
    private List<Integer> twoDays = new ArrayList<>();//最后一个年月 对应的天数



    private void initFirst(){
        oneYear = startCalender.get(Calendar.YEAR);
        twoYear = endCalender.get(Calendar.YEAR);
        oneMonth = startCalender.get(Calendar.MONTH)+1;
        twoMonth = endCalender.get(Calendar.MONTH)+1;
        int oneStartDay  = startCalender.get(Calendar.DAY_OF_MONTH);
        int oneEndDay = startCalender.getActualMaximum(Calendar.DAY_OF_MONTH);
        int twoStartDay  = 1;
        int twoEndDay = endCalender.get(Calendar.DAY_OF_MONTH);
            selectYear = oneYear;
            selectMonth = oneMonth;
            selectDay = oneStartDay;

            callback();

        oneDays.clear();
        for(int i=oneStartDay;i<=oneEndDay;i++){
            oneDays.add(i);
        }

        twoDays.clear();
        for(int i=twoStartDay;i<=twoEndDay;i++){
            twoDays.add(i);
        }

        mYearList.clear();
        mMonthList.clear();
        mDayList.clear();
        for (int i = startYear; i <= endYear; i++) {
            mYearList.add(i);
        }

        if (mYearList.size() == 1) {
            int startMonth = this.startCalender.get(Calendar.MONTH) + 1;
            int endMonth = this.endCalender.get(Calendar.MONTH) + 1;
            for (int i = startMonth; i <= endMonth; i++) {
                mMonthList.add(i);
            }
            if (mMonthList.size() == 1) {
                //同一月份
                int startDay = this.startCalender.get(Calendar.DAY_OF_MONTH);
                int endDay = this.endCalender.get(Calendar.DAY_OF_MONTH);
                for (int i = startDay; i <= endDay; i++) {
                    mDayList.add(i);
                }
            } else {
                //不同月份
                int startDay = this.startCalender.get(Calendar.DAY_OF_MONTH);
                int endDay = this.startCalender.getActualMaximum(Calendar.DAY_OF_MONTH);
                for (int i = startDay; i <= endDay; i++) {
                    mDayList.add(i);
                }
            }

        } else {
            //不同年份
            int startMonth = this.startCalender.get(Calendar.MONTH) + 1;
            int endMonth = 12;
            for (int i = startMonth; i <= endMonth; i++) {
                mMonthList.add(i);
            }

            int startDay = this.startCalender.get(Calendar.DAY_OF_MONTH);
            int endDay = this.startCalender.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = startDay; i <= endDay; i++) {
                mDayList.add(i);
            }
        }
        wheel_date_picker_day.setData(mDayList);
        wheel_date_picker_month.setData(mMonthList);
        wheel_date_picker_year.setData(mYearList);
    }

    private void initLast(){
        oneYear = startCalender.get(Calendar.YEAR);
        twoYear = endCalender.get(Calendar.YEAR);
        oneMonth = startCalender.get(Calendar.MONTH)+1;
        twoMonth = endCalender.get(Calendar.MONTH)+1;
        int oneStartDay  = startCalender.get(Calendar.DAY_OF_MONTH);
        int oneEndDay = startCalender.getActualMaximum(Calendar.DAY_OF_MONTH);
        int twoStartDay  = 1;
        int twoEndDay = endCalender.get(Calendar.DAY_OF_MONTH);
            selectYear = twoYear;
            selectMonth = twoMonth;
            selectDay = oneEndDay;

            callback();

        oneDays.clear();
        for(int i=oneStartDay;i<=oneEndDay;i++){
            oneDays.add(i);
        }

        twoDays.clear();
        for(int i=twoStartDay;i<=twoEndDay;i++){
            twoDays.add(i);
        }

        mYearList.clear();
        mMonthList.clear();
        mDayList.clear();
        for (int i = startYear; i <= endYear; i++) {
            mYearList.add(i);
        }

        if (mYearList.size() == 1) {
            int startMonth = this.startCalender.get(Calendar.MONTH) + 1;
            int endMonth = this.endCalender.get(Calendar.MONTH) + 1;
            for (int i = startMonth; i <= endMonth; i++) {
                mMonthList.add(i);
            }
            if (mMonthList.size() == 1) {
                //同一月份
                int startDay = this.startCalender.get(Calendar.DAY_OF_MONTH);
                int endDay = this.endCalender.get(Calendar.DAY_OF_MONTH);
                for (int i = startDay; i <= endDay; i++) {
                    mDayList.add(i);
                }
            } else {
                //不同月份
                int startDay = 1;
                int endDay = this.endCalender.get(Calendar.DAY_OF_MONTH);
                for (int i = startDay; i <= endDay; i++) {
                    mDayList.add(i);
                }
            }

        } else {
            //不同年份
            int startMonth = 1;
            int endMonth = this.endCalender.get(Calendar.MONTH)+1;
            for (int i = startMonth; i <= endMonth; i++) {
                mMonthList.add(i);
            }

            int startDay = 1;
            int endDay = this.endCalender.get(Calendar.DAY_OF_MONTH);
            for (int i = startDay; i <= endDay; i++) {
                mDayList.add(i);
            }
        }
        wheel_date_picker_day.setData(mDayList);
        wheel_date_picker_day.setSelectedItemPosition(mDayList.size()-1);
        wheel_date_picker_month.setData(mMonthList);
        wheel_date_picker_month.setSelectedItemPosition(mMonthList.size()-1);
        wheel_date_picker_year.setData(mYearList);
        wheel_date_picker_year.setSelectedItemPosition(mYearList.size() - 1);
    }
    /**
     * 根据年 月 获取对应的月份 天数
     * */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    private void callback(){
        String formatDate = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
        Date retDate = null;
        try {
             retDate = sdf.parse(selectYear+"-"+selectMonth+"-"+selectDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(mCycleDatePickerSelect != null){
            mCycleDatePickerSelect.selectDate(retDate);
        }
    }

}
