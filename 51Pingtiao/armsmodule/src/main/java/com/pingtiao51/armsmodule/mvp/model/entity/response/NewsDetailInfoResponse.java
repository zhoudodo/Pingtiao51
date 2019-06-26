package com.pingtiao51.armsmodule.mvp.model.entity.response;

public class NewsDetailInfoResponse {

    /**
     * id : 1
     * title : 三大运营商将加速关闭2G网络 仍有将近3亿用户
     * content : 这是一段富文本内容
     * category :
     * titleImageUrl :
     * createdTime : 1521184456000
     * modifiedTime : 1521184456000
     */

    private int id;
    private String title;
    private String content;
    private String category;
    private String titleImageUrl;
    private long createdTime;
    private long modifiedTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitleImageUrl() {
        return titleImageUrl;
    }

    public void setTitleImageUrl(String titleImageUrl) {
        this.titleImageUrl = titleImageUrl;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
