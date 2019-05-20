package com.pingtiao51.armsmodule.mvp.ui.interfaces;

public interface SearchPingtiaoListInterface {

    public void getPingtiaoList(
            String enoteType,
            int page,
            String queryName,
            String queryScopeType,
            int size,
            String sortType,
            String userRoleType,
            String loanPeriodType,
            String remainderRepayDaysType
    );

}
