package com.qianmo.jinxiaocun.fu.bean;

import java.util.List;

/**
 * Created by wizardev on 17-7-14.
 */

public class CityInfoBean {

    /**
     * msg : 成功
     * code : 100
     * resultList : [{"code":110000,"parentCode":100000,"type":1,"name":"北京","fullName":"北京"},{"code":120000,"parentCode":100000,"type":1,"name":"天津","fullName":"天津"},{"code":130000,"parentCode":100000,"type":1,"name":"河北","fullName":"河北省"},{"code":140000,"parentCode":100000,"type":1,"name":"山西","fullName":"山西省"},{"code":150000,"parentCode":100000,"type":1,"name":"内蒙古","fullName":"内蒙古自治区"},{"code":210000,"parentCode":100000,"type":1,"name":"辽宁","fullName":"辽宁省"},{"code":220000,"parentCode":100000,"type":1,"name":"吉林","fullName":"吉林省"},{"code":230000,"parentCode":100000,"type":1,"name":"黑龙江","fullName":"黑龙江省"},{"code":310000,"parentCode":100000,"type":1,"name":"上海","fullName":"上海"},{"code":320000,"parentCode":100000,"type":1,"name":"江苏","fullName":"江苏省"},{"code":330000,"parentCode":100000,"type":1,"name":"浙江","fullName":"浙江省"},{"code":340000,"parentCode":100000,"type":1,"name":"安徽","fullName":"安徽省"},{"code":350000,"parentCode":100000,"type":1,"name":"福建","fullName":"福建省"},{"code":360000,"parentCode":100000,"type":1,"name":"江西","fullName":"江西省"},{"code":370000,"parentCode":100000,"type":1,"name":"山东","fullName":"山东省"},{"code":410000,"parentCode":100000,"type":1,"name":"河南","fullName":"河南省"},{"code":420000,"parentCode":100000,"type":1,"name":"湖北","fullName":"湖北省"},{"code":430000,"parentCode":100000,"type":1,"name":"湖南","fullName":"湖南省"},{"code":440000,"parentCode":100000,"type":1,"name":"广东","fullName":"广东省"},{"code":450000,"parentCode":100000,"type":1,"name":"广西","fullName":"广西壮族自治区"},{"code":460000,"parentCode":100000,"type":1,"name":"海南","fullName":"海南省"},{"code":500000,"parentCode":100000,"type":1,"name":"重庆","fullName":"重庆"},{"code":510000,"parentCode":100000,"type":1,"name":"四川","fullName":"四川省"},{"code":520000,"parentCode":100000,"type":1,"name":"贵州","fullName":"贵州省"},{"code":530000,"parentCode":100000,"type":1,"name":"云南","fullName":"云南省"},{"code":540000,"parentCode":100000,"type":1,"name":"西藏","fullName":"西藏自治区"},{"code":610000,"parentCode":100000,"type":1,"name":"陕西","fullName":"陕西省"},{"code":620000,"parentCode":100000,"type":1,"name":"甘肃","fullName":"甘肃省"},{"code":630000,"parentCode":100000,"type":1,"name":"青海","fullName":"青海省"},{"code":640000,"parentCode":100000,"type":1,"name":"宁夏","fullName":"宁夏回族自治区"},{"code":650000,"parentCode":100000,"type":1,"name":"新疆","fullName":"新疆维吾尔自治区"},{"code":710000,"parentCode":100000,"type":1,"name":"台湾","fullName":"台湾"},{"code":810000,"parentCode":100000,"type":1,"name":"香港","fullName":"香港特别行政区"},{"code":820000,"parentCode":100000,"type":1,"name":"澳门","fullName":"澳门特别行政区"},{"code":900000,"parentCode":100000,"type":1,"name":"钓鱼岛","fullName":"钓鱼岛"}]
     */

    private String msg;
    private int code;
    private List<ResultListBean> resultList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ResultListBean> getResultList() {
        return resultList;
    }

    public void setResultList(List<ResultListBean> resultList) {
        this.resultList = resultList;
    }

    public static class ResultListBean {
        /**
         * code : 110000
         * parentCode : 100000
         * type : 1
         * name : 北京
         * fullName : 北京
         */

        private int code;
        private int parentCode;
        private int type;
        private String name;
        private String fullName;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getParentCode() {
            return parentCode;
        }

        public void setParentCode(int parentCode) {
            this.parentCode = parentCode;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }
}
