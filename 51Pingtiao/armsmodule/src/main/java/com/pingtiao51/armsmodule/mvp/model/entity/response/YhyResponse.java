package com.pingtiao51.armsmodule.mvp.model.entity.response;

//人脸识别 云慧眼
public class YhyResponse {

    /**
     * addr_card : 浙江省杭州市滨江区越达巷
     * be_idcard : 0.9805
     * branch_issued : 滨江公安局
     * date_birthday : 1990.04.12
     * flag_sex : 男
     * id_name : 周伯通
     * id_no : 320421199011120054
     * result_auth : T
     * result_status : 01
     * url_frontcard : https://idsafe-auth.udcredit.com/front/4.0/api/file_download/....
     * url_backcard : https://idsafe-auth.udcredit.com/front/4.0/api/file_download/....
     * url_photoget : https://idsafe-auth.udcredit.com/front/4.0/api/file_download/....
     * url_photoliving : https://idsafe-auth.udcredit.com/front/4.0/api/file_download/....
     * sdk_idcard_front_photo : Bitmap
     * sdk_idcard_portrait_photo : Bitmap
     * sdk_idcard_back_photo : Bitmap
     * risk_tag : {"living_attack":"0"}
     * state_id : 汉
     * start_card : 2017.02.03-2037.02.03
     * ret_msg : 操作成功
     * ret_code : 000000
     */

    private String addr_card;
    private String be_idcard;
    private String branch_issued;
    private String date_birthday;
    private String flag_sex;
    private String id_name;
    private String id_no;
    private String result_auth;
    private String result_status;
    private String url_frontcard;
    private String url_backcard;
    private String url_photoget;
    private String url_photoliving;
    private String sdk_idcard_front_photo;
    private String sdk_idcard_portrait_photo;
    private String sdk_idcard_back_photo;
    private RiskTagBean risk_tag;
    private String state_id;
    private String start_card;
    private String ret_msg;
    private String ret_code;

    public String getAddr_card() {
        return addr_card;
    }

    public void setAddr_card(String addr_card) {
        this.addr_card = addr_card;
    }

    public String getBe_idcard() {
        return be_idcard;
    }

    public void setBe_idcard(String be_idcard) {
        this.be_idcard = be_idcard;
    }

    public String getBranch_issued() {
        return branch_issued;
    }

    public void setBranch_issued(String branch_issued) {
        this.branch_issued = branch_issued;
    }

    public String getDate_birthday() {
        return date_birthday;
    }

    public void setDate_birthday(String date_birthday) {
        this.date_birthday = date_birthday;
    }

    public String getFlag_sex() {
        return flag_sex;
    }

    public void setFlag_sex(String flag_sex) {
        this.flag_sex = flag_sex;
    }

    public String getId_name() {
        return id_name;
    }

    public void setId_name(String id_name) {
        this.id_name = id_name;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public String getResult_auth() {
        return result_auth;
    }

    public void setResult_auth(String result_auth) {
        this.result_auth = result_auth;
    }

    public String getResult_status() {
        return result_status;
    }

    public void setResult_status(String result_status) {
        this.result_status = result_status;
    }

    public String getUrl_frontcard() {
        return url_frontcard;
    }

    public void setUrl_frontcard(String url_frontcard) {
        this.url_frontcard = url_frontcard;
    }

    public String getUrl_backcard() {
        return url_backcard;
    }

    public void setUrl_backcard(String url_backcard) {
        this.url_backcard = url_backcard;
    }

    public String getUrl_photoget() {
        return url_photoget;
    }

    public void setUrl_photoget(String url_photoget) {
        this.url_photoget = url_photoget;
    }

    public String getUrl_photoliving() {
        return url_photoliving;
    }

    public void setUrl_photoliving(String url_photoliving) {
        this.url_photoliving = url_photoliving;
    }

    public String getSdk_idcard_front_photo() {
        return sdk_idcard_front_photo;
    }

    public void setSdk_idcard_front_photo(String sdk_idcard_front_photo) {
        this.sdk_idcard_front_photo = sdk_idcard_front_photo;
    }

    public String getSdk_idcard_portrait_photo() {
        return sdk_idcard_portrait_photo;
    }

    public void setSdk_idcard_portrait_photo(String sdk_idcard_portrait_photo) {
        this.sdk_idcard_portrait_photo = sdk_idcard_portrait_photo;
    }

    public String getSdk_idcard_back_photo() {
        return sdk_idcard_back_photo;
    }

    public void setSdk_idcard_back_photo(String sdk_idcard_back_photo) {
        this.sdk_idcard_back_photo = sdk_idcard_back_photo;
    }

    public RiskTagBean getRisk_tag() {
        return risk_tag;
    }

    public void setRisk_tag(RiskTagBean risk_tag) {
        this.risk_tag = risk_tag;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getStart_card() {
        return start_card;
    }

    public void setStart_card(String start_card) {
        this.start_card = start_card;
    }

    public String getRet_msg() {
        return ret_msg;
    }

    public void setRet_msg(String ret_msg) {
        this.ret_msg = ret_msg;
    }

    public String getRet_code() {
        return ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    public static class RiskTagBean {
        /**
         * living_attack : 0
         */

        private String living_attack;

        public String getLiving_attack() {
            return living_attack;
        }

        public void setLiving_attack(String living_attack) {
            this.living_attack = living_attack;
        }
    }
}
