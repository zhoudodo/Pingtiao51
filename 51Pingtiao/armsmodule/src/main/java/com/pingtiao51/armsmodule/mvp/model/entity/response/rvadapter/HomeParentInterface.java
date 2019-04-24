package com.pingtiao51.armsmodule.mvp.model.entity.response.rvadapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public interface HomeParentInterface extends MultiItemEntity {

    public final static int SHOU_STATUS = 0;
    public final static int JIE_STATUS = 1;


    /**
     *收借状态 true`收入 false支出
     */
     boolean isReceiveMoney();

    /**
     * 本金利息合
     * @return
     */
    float getMoneyAndRate();
    /**
     * 本金
     * @return
     */
    float getMoney();

    /**
     * 出借人  递交人
     * @return
     */
    String getPersonName();

    /**
     * 还款时间
     * @return
     */
   String getTimes();

    /**
     * 逾期天数
     */
    String getYuQi();
}
