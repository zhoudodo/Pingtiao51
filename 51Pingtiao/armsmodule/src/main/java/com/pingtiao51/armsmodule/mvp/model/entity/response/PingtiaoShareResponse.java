package com.pingtiao51.armsmodule.mvp.model.entity.response;

public class PingtiaoShareResponse {

    /**
     * noteShareSMSVO : {"smsContent":"请您查看我的借条信息"}
     * noteShareUrlVO : {"shareUrl":"http://test.pingtiao.com//borrowSave?id=1"}
     * noteShareWXVO : {"content":"借条信息","picUrl":"http://test.pingtiao.com//borrowSave?id=1","shareUrl":"http://test.pingtiao.com//borrowSave?id=1","title":"51凭条"}
     */

    private NoteShareSMSVOBean noteShareSMSVO;
    private NoteShareUrlVOBean noteShareUrlVO;
    private NoteShareWXVOBean noteShareWXVO;

    public NoteShareSMSVOBean getNoteShareSMSVO() {
        return noteShareSMSVO;
    }

    public void setNoteShareSMSVO(NoteShareSMSVOBean noteShareSMSVO) {
        this.noteShareSMSVO = noteShareSMSVO;
    }

    public NoteShareUrlVOBean getNoteShareUrlVO() {
        return noteShareUrlVO;
    }

    public void setNoteShareUrlVO(NoteShareUrlVOBean noteShareUrlVO) {
        this.noteShareUrlVO = noteShareUrlVO;
    }

    public NoteShareWXVOBean getNoteShareWXVO() {
        return noteShareWXVO;
    }

    public void setNoteShareWXVO(NoteShareWXVOBean noteShareWXVO) {
        this.noteShareWXVO = noteShareWXVO;
    }

    public static class NoteShareSMSVOBean {
        /**
         * smsContent : 请您查看我的借条信息
         */

        private String smsContent;

        public String getSmsContent() {
            return smsContent;
        }

        public void setSmsContent(String smsContent) {
            this.smsContent = smsContent;
        }
    }

    public static class NoteShareUrlVOBean {
        /**
         * shareUrl : http://test.pingtiao.com//borrowSave?id=1
         */

        private String shareUrl;

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }
    }

    public static class NoteShareWXVOBean {
        /**
         * content : 借条信息
         * picUrl : http://test.pingtiao.com//borrowSave?id=1
         * shareUrl : http://test.pingtiao.com//borrowSave?id=1
         * title : 51凭条
         */

        private String content;
        private String picUrl;
        private String shareUrl;
        private String title;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
