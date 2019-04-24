package com.pingtiao51.armsmodule.mvp.model.entity.response;

import com.pingtiao51.armsmodule.mvp.model.entity.response.rvadapter.PingtiaoParentInterface;

import java.util.Random;

public class TestPingtiaoData implements PingtiaoParentInterface {

    int type = 0;
    public  TestPingtiaoData(int type){
        this.type = type;
    }


    @Override
    public String getImgUrl() {
        return "https://www.baidu.com/img/bd_logo1.png";
    }

    @Override
    public String getMoneyStr() {
        return "300";
    }

    @Override
    public String getCommitMan() {
        return "玛丽";
    }

    @Override
    public String getChujieren() {
        return "张三";
    }

    @Override
    public String getJieHuanMoeny() {
        return "100";
    }

    @Override
    public String getTime() {
        return "2019-12-12";
    }

    @Override
    public boolean hasHuankuan() {
        return new Random().nextBoolean();
    }

    @Override
    public boolean zaicifasong() {
        return new Random().nextBoolean();
    }

    @Override
    public boolean hasYuqi() {
        return new Random().nextBoolean();
    }

    @Override
    public int getItemType() {
        return type;
    }
}
