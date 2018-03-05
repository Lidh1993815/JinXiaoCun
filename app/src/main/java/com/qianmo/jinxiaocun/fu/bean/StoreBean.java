package com.qianmo.jinxiaocun.fu.bean;

import java.util.List;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 18-3-5
 * desc : 门店列表的实体类
 * version : 1.0
 */

public class StoreBean {

    /**
     * recordsFiltered : 9
     * data : [{"storeId":21,"storeName":"天猫","contacts":"12","storePhone":"21","storeTelephone":"21","storeDirector":"12","staffNumber":21,"storeClassifyId":12,"storeAddress":"12","longitudeLatitude":"21","storeStatus":1,"brandNumber":21,"repertoryAlarmUpperlimit":21,"repertoryAlarmLowlimit":12,"cTime":1510329600000,"uTime":1510329600000,"regionId":6,"userId":2}]
     * recordsTotal : 9
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
         * storeId : 21
         * storeName : 天猫
         * contacts : 12
         * storePhone : 21
         * storeTelephone : 21
         * storeDirector : 12
         * staffNumber : 21
         * storeClassifyId : 12
         * storeAddress : 12
         * longitudeLatitude : 21
         * storeStatus : 1
         * brandNumber : 21
         * repertoryAlarmUpperlimit : 21
         * repertoryAlarmLowlimit : 12
         * cTime : 1510329600000
         * uTime : 1510329600000
         * regionId : 6
         * userId : 2
         */

        private int storeId;
        private String storeName;
        private String contacts;
        private String storePhone;
        private String storeTelephone;
        private String storeDirector;
        private int staffNumber;
        private int storeClassifyId;
        private String storeAddress;
        private String longitudeLatitude;
        private int storeStatus;
        private int brandNumber;
        private int repertoryAlarmUpperlimit;
        private int repertoryAlarmLowlimit;
        private long cTime;
        private long uTime;
        private int regionId;
        private int userId;

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public String getStorePhone() {
            return storePhone;
        }

        public void setStorePhone(String storePhone) {
            this.storePhone = storePhone;
        }

        public String getStoreTelephone() {
            return storeTelephone;
        }

        public void setStoreTelephone(String storeTelephone) {
            this.storeTelephone = storeTelephone;
        }

        public String getStoreDirector() {
            return storeDirector;
        }

        public void setStoreDirector(String storeDirector) {
            this.storeDirector = storeDirector;
        }

        public int getStaffNumber() {
            return staffNumber;
        }

        public void setStaffNumber(int staffNumber) {
            this.staffNumber = staffNumber;
        }

        public int getStoreClassifyId() {
            return storeClassifyId;
        }

        public void setStoreClassifyId(int storeClassifyId) {
            this.storeClassifyId = storeClassifyId;
        }

        public String getStoreAddress() {
            return storeAddress;
        }

        public void setStoreAddress(String storeAddress) {
            this.storeAddress = storeAddress;
        }

        public String getLongitudeLatitude() {
            return longitudeLatitude;
        }

        public void setLongitudeLatitude(String longitudeLatitude) {
            this.longitudeLatitude = longitudeLatitude;
        }

        public int getStoreStatus() {
            return storeStatus;
        }

        public void setStoreStatus(int storeStatus) {
            this.storeStatus = storeStatus;
        }

        public int getBrandNumber() {
            return brandNumber;
        }

        public void setBrandNumber(int brandNumber) {
            this.brandNumber = brandNumber;
        }

        public int getRepertoryAlarmUpperlimit() {
            return repertoryAlarmUpperlimit;
        }

        public void setRepertoryAlarmUpperlimit(int repertoryAlarmUpperlimit) {
            this.repertoryAlarmUpperlimit = repertoryAlarmUpperlimit;
        }

        public int getRepertoryAlarmLowlimit() {
            return repertoryAlarmLowlimit;
        }

        public void setRepertoryAlarmLowlimit(int repertoryAlarmLowlimit) {
            this.repertoryAlarmLowlimit = repertoryAlarmLowlimit;
        }

        public long getCTime() {
            return cTime;
        }

        public void setCTime(long cTime) {
            this.cTime = cTime;
        }

        public long getUTime() {
            return uTime;
        }

        public void setUTime(long uTime) {
            this.uTime = uTime;
        }

        public int getRegionId() {
            return regionId;
        }

        public void setRegionId(int regionId) {
            this.regionId = regionId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
