package com.qianmo.jinxiaocun.fu.bean;

import java.util.List;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 18-2-1
 * desc :
 * version : 1.0
 */

public class NewspaperDetail {

    /**
     * recordsFiltered : 2
     * data : [{"dailyId":6,"cTime":"2017-11-19 05:50","carryOutId":13,"dailyContent":"很快任务就要结束了"},{"dailyId":2,"cTime":"2017-11-18 04:20","carryOutId":13,"dailyContent":"进入任务完成的非常顺利 一铲子下去就挖出了一个夜光杯"}]
     * recordsTotal : 2
     */

    private int recordsFiltered;
    private int recordsTotal;
    private List<DataBean> data;

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * dailyId : 6
         * cTime : 2017-11-19 05:50
         * carryOutId : 13
         * dailyContent : 很快任务就要结束了
         */

        private int dailyId;
        private String cTime;
        private int carryOutId;
        private String dailyContent;

        public int getDailyId() {
            return dailyId;
        }

        public void setDailyId(int dailyId) {
            this.dailyId = dailyId;
        }

        public String getCTime() {
            return cTime;
        }

        public void setCTime(String cTime) {
            this.cTime = cTime;
        }

        public int getCarryOutId() {
            return carryOutId;
        }

        public void setCarryOutId(int carryOutId) {
            this.carryOutId = carryOutId;
        }

        public String getDailyContent() {
            return dailyContent;
        }

        public void setDailyContent(String dailyContent) {
            this.dailyContent = dailyContent;
        }
    }
}
