package com.qianmo.jinxiaocun.fu.bean;

import java.util.List;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 17-12-7
 * desc :
 * version : 1.0
 */

public class ApprovalListBean {

    /**
     * recordsFiltered : 1
     * data : [{"aPplyClockId":16,"aType":3,"cTime":"2017-12-06 03:49","aPplyStatus":2,"staffName":"甲桂林"}]
     * recordsTotal : 1
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
         * aPplyClockId : 16
         * aType : 3
         * cTime : 2017-12-06 03:49
         * aPplyStatus : 2
         * staffName : 甲桂林
         */

        private int aPplyClockId;
        private int aType;
        private String cTime;
        private int aPplyStatus;
        private String staffName;

        public int getAPplyClockId() {
            return aPplyClockId;
        }

        public void setAPplyClockId(int aPplyClockId) {
            this.aPplyClockId = aPplyClockId;
        }

        public int getAType() {
            return aType;
        }

        public void setAType(int aType) {
            this.aType = aType;
        }

        public String getCTime() {
            return cTime;
        }

        public void setCTime(String cTime) {
            this.cTime = cTime;
        }

        public int getAPplyStatus() {
            return aPplyStatus;
        }

        public void setAPplyStatus(int aPplyStatus) {
            this.aPplyStatus = aPplyStatus;
        }

        public String getStaffName() {
            return staffName;
        }

        public void setStaffName(String staffName) {
            this.staffName = staffName;
        }
    }
}
