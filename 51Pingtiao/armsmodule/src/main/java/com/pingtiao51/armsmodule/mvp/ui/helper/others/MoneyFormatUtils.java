package com.pingtiao51.armsmodule.mvp.ui.helper.others;

import java.text.DecimalFormat;

public class MoneyFormatUtils {

    public static String coverTenThousand(String inputMoney){
        String outMoney = "";
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        double inputD = 0;
        try {
           inputD =  Double.valueOf(inputMoney);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        if(inputD > 10000){
           outMoney =  decimalFormat.format(inputD/10000f)+"万";
        }else{
            outMoney = decimalFormat.format(inputD);
        }
        return outMoney;
    }

}
