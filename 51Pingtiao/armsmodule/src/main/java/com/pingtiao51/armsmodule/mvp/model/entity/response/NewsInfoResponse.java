package com.pingtiao51.armsmodule.mvp.model.entity.response;

import com.pingtiao51.armsmodule.mvp.model.entity.response.banner.BannerNewsInterface;
import com.pingtiao51.armsmodule.mvp.model.entity.response.pojospeical.NewsListInterface;

import java.util.List;

public class NewsInfoResponse {

    /**
     * total : 1
     * list : [{"id":1,"title":"标题啊","titleImageUrl":"https://blog.csdn.net/tyyking/column/info/19428","category":"-","sort":1,"createdTime":1521184456000,"modifiedTime":1521184456000}]
     */

    private int total;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements NewsListInterface,BannerNewsInterface {
        /**
         * id : 1
         * title : 标题啊
         * titleImageUrl : https://blog.csdn.net/tyyking/column/info/19428
         * category : -
         * sort : 1
         * createdTime : 1521184456000
         * modifiedTime : 1521184456000
         */

        private int id;
        private String title;
        private String titleImageUrl;
        private String category;
        private int sort;
        private long createdTime;
        private long modifiedTime;

        @Override
        public String getTitle1() {
            return title;
        }

        @Override
        public String getTitle2() {
            return category;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public String getImg() {
            return null;
        }

        @Override
        public String getLable1() {
            return category;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitleImageUrl() {
            return titleImageUrl;
        }

        public void setTitleImageUrl(String titleImageUrl) {
            this.titleImageUrl = titleImageUrl;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
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

        @Override
        public String getLoadUrl() {
            return titleImageUrl;
        }

        @Override
        public String getClickIntentUrl() {
            return null;
        }

        @Override
        public int getResId() {
            return 0;
        }
    }
}