package com.pingtiao51.armsmodule.mvp.model.entity.response;

public class WxBindPhoneResponse {


    /**
     * phone : 18538422713
     * tokenVO : {"token":""}
     */

    private String phone;
    private WxLoginResponse.TokenVOBean tokenVO;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public WxLoginResponse.TokenVOBean getTokenVO() {
        return tokenVO;
    }

    public void setTokenVO(WxLoginResponse.TokenVOBean tokenVO) {
        this.tokenVO = tokenVO;
    }

    public static class TokenVOBean {
        /**
         * token :
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

}
