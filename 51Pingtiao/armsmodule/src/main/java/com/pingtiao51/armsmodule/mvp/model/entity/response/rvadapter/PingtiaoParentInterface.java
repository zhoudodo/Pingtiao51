package com.pingtiao51.armsmodule.mvp.model.entity.response.rvadapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public interface PingtiaoParentInterface extends MultiItemEntity {
    public final static int DIANZI_JIETIAO = 0;
    public final static int DIANZI_SHOUTIAO = 1;
    public final static int ZHIZHI_JIETIAO = 2;
    public final static int ZHIZHI_SHOUTIAO = 3;
    public String getImgUrl();//借条加载url
    public String getMoneyStr();//借款 还款 金额 和文字描述
    public String getCommitMan();// 出借人 递交人 描述+名字
    public String getChujieren();// 出借人名字
    public String getJieHuanMoeny();//借款金额 100元
    public String getTime();//还款时间

    public boolean hasHuankuan();//已还款 true 显示图片 false隐藏
    public boolean zaicifasong();//再次发送
    public boolean hasYuqi();//已经逾期

}
