package com.pingtiao51.armsmodule.mvp.model.entity.response;

import com.pingtiao51.armsmodule.mvp.model.entity.response.rvadapter.HomeParentInterface;

import java.util.Random;

public class TestDianZiPingTiao implements HomeParentInterface {


    @Override
    public int getItemType() {
        return new Random().nextInt(2);
    }

    @Override
    public boolean isReceiveMoney() {
        return false;
    }

    @Override
    public float getMoneyAndRate() {
        return 0;
    }

    @Override
    public float getMoney() {
        return 0;
    }

    @Override
    public String getPersonName() {
        return "玛丽";
    }

    @Override
    public String getTimes() {
        return "2019-12-12";
    }

    @Override
    public String getYuQi() {
        return "2";
    }
}
