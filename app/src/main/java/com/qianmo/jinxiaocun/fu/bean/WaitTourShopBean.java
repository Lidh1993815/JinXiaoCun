package com.qianmo.jinxiaocun.fu.bean;

import java.util.List;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 18-3-3
 * desc :
 * version : 1.0
 */

public class WaitTourShopBean {

    /**
     * recordsFiltered : 1
     * data : [{"patrolStoreId":13,"storeAddress":"杭州市","storeName":"桂林吴彦祖专门店","patrolStoreTime":"2017-12-30 00:00","patrolStoreStatus":1,"longitudeLatitude":"1021,1569"}]
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
         * patrolStoreId : 13
         * storeAddress : 杭州市
         * storeName : 桂林吴彦祖专门店
         * patrolStoreTime : 2017-12-30 00:00
         * patrolStoreStatus : 1
         * longitudeLatitude : 1021,1569
         */

        private int patrolStoreId;
        private String storeAddress;
        private String storeName;
        private String patrolStoreTime;
        private int patrolStoreStatus;
        private String longitudeLatitude;

        public String getStaffName() {
            return staffName;
        }

        public void setStaffName(String staffName) {
            this.staffName = staffName;
        }

        private String staffName;

        public int getPatrolStoreId() {
            return patrolStoreId;
        }

        public void setPatrolStoreId(int patrolStoreId) {
            this.patrolStoreId = patrolStoreId;
        }

        public String getStoreAddress() {
            return storeAddress;
        }

        public void setStoreAddress(String storeAddress) {
            this.storeAddress = storeAddress;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getPatrolStoreTime() {
            return patrolStoreTime;
        }

        public void setPatrolStoreTime(String patrolStoreTime) {
            this.patrolStoreTime = patrolStoreTime;
        }

        public int getPatrolStoreStatus() {
            return patrolStoreStatus;
        }

        public void setPatrolStoreStatus(int patrolStoreStatus) {
            this.patrolStoreStatus = patrolStoreStatus;
        }

        public String getLongitudeLatitude() {
            return longitudeLatitude;
        }

        public void setLongitudeLatitude(String longitudeLatitude) {
            this.longitudeLatitude = longitudeLatitude;
        }
    }
}
