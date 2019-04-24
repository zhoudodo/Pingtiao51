package com.pingtiao51.armsmodule.mvp.ui.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    public static long bettweenDays(String startDay,String endDay){
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=new GregorianCalendar();
        Date d1= null;
        Date d2= null;
        try {
            d1 = df.parse(endDay);
            d2 = df.parse(startDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(d1 != null && d2 != null){
            return  ((d1.getTime()-d2.getTime())/(60*60*1000*24));
        }
        return 0;
    }
}
