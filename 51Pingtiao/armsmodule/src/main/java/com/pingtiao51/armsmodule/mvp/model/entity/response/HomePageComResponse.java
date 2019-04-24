package com.pingtiao51.armsmodule.mvp.model.entity.response;

public class HomePageComResponse {
    /**
     * detailUrl : http://wwww.baidu.com
     * iconUrl : http://wwww.baidu.com
     * id : 1
     * name : 收条模板
     * params : {"name":"111"}
     * sort : 1
     * type : H5
     */
    private String detailUrl;
    private String iconUrl;
    private int id;
    private String name;
    private ParamsBean params;
    private int sort;
    private String type;

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class ParamsBean {
        /**
         * name : 111
         */

        private String name;
        private String className;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
