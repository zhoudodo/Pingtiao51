package com.pingtiao51.armsmodule.mvp.ui.helper.others;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IdCardUtils {

    private IdCardUtils(){

    }
    /**
     * 验证身份证号是否符合规则
     * @param text 身份证号
     * @return
     */
    public static boolean personIdValidation(String text) {
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }


    public static Date getBirthDate(String idCard) {
        String strYear = "";
        String strMonth = "";
        String strDay = "";

        if (idCard.length() == 15) {
            strYear = "19" + idCard.substring(6, 8);
            strMonth = idCard.substring(8, 10);
            strDay = idCard.substring(10, 12);
        }

        if (idCard.length() == 18) {
            strYear = idCard.substring(6, 10);
            strMonth = idCard.substring(10, 12);
            strDay = idCard.substring(12, 14);
        }
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            date = format.parse(strYear + strMonth + strDay);
        }catch (ParseException parse){
            parse.printStackTrace();
        }
        return date;
//        System.out.println("生日日期为：" + strYear + "年" + strMonth + "月" + strDay + "日");
    }

    /**
     * 是否成年
     * @param date 生日时间
     * @return
     */
    public static boolean checkAdult(Date date) {
        return checkAgeStandard(date,18);
    }

    /**
     * 生日时间是否达标 可以用来判断成年 大于20岁等问题
     * @param date 生日时间
     * @param age
     * @return
     */
    public static boolean checkAgeStandard(Date date, int age) {
        Calendar current = Calendar.getInstance();
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTime(date);
        Integer year = current.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        if (year > 18) {
            return true;
        } else if (year < 18) {
            return false;
        }
        // 如果年相等，就比较月份
        Integer month = current.get(Calendar.MONTH) - birthDay.get(Calendar.MONTH);
        if (month > 0) {
            return true;
        } else if (month < 0) {
            return false;
        }
        // 如果月也相等，就比较天
        Integer day = current.get(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH);
        return  day >= 0;
    }




}
