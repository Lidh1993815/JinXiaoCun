package com.qianmo.jinxiaocun.fu.bean;

import java.util.List;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 17-12-7
 * desc : 物料申请详情实体类
 * version : 1.0
 */

public class ApplyMaterialDetailBean {

    /**
     * applyMateriels : [{"mId":1,"mName":"洛阳铲","mSum":3,"mRemake":"掘墓太多 铲子明显不够用","aPplyClockId":16},{"mId":2,"mName":"火药","mSum":30,"mRemake":"很多墓门明显炸不开","aPplyClockId":16}]
     * clockView : {"staffName":"牵陌吴彦祖","cTime":"2017-12-06 03:49","applyStatus":2,"executorName":"甲桂林","aType":3,"aPplyClockId":16,"applyOpinion":null}
     */

    private ClockViewBean clockView;
    private List<ApplyMaterielBean> applyMateriels;

    public ClockViewBean getClockView() {
        return clockView;
    }

    public void setClockView(ClockViewBean clockView) {
        this.clockView = clockView;
    }

    public List<ApplyMaterielBean> getApplyMateriels() {
        return applyMateriels;
    }

    public void setApplyMateriels(List<ApplyMaterielBean> applyMateriels) {
        this.applyMateriels = applyMateriels;
    }

    public static class ClockViewBean {
        /**
         * staffName : 牵陌吴彦祖
         * cTime : 2017-12-06 03:49
         * applyStatus : 2
         * executorName : 甲桂林
         * aType : 3
         * aPplyClockId : 16
         * applyOpinion : null
         */

        private String staffName;
        private String cTime;
        private int applyStatus;
        private String executorName;
        private int aType;
        private int aPplyClockId;
        private Object applyOpinion;

        public String getaPplyAuditorTime() {
            return aPplyAuditorTime;
        }

        public void setaPplyAuditorTime(String aPplyAuditorTime) {
            this.aPplyAuditorTime = aPplyAuditorTime;
        }

        private String aPplyAuditorTime;

        public String getStaffName() {
            return staffName;
        }

        public void setStaffName(String staffName) {
            this.staffName = staffName;
        }

        public String getCTime() {
            return cTime;
        }

        public void setCTime(String cTime) {
            this.cTime = cTime;
        }

        public int getApplyStatus() {
            return applyStatus;
        }

        public void setApplyStatus(int applyStatus) {
            this.applyStatus = applyStatus;
        }

        public String getExecutorName() {
            return executorName;
        }

        public void setExecutorName(String executorName) {
            this.executorName = executorName;
        }

        public int getAType() {
            return aType;
        }

        public void setAType(int aType) {
            this.aType = aType;
        }

        public int getAPplyClockId() {
            return aPplyClockId;
        }

        public void setAPplyClockId(int aPplyClockId) {
            this.aPplyClockId = aPplyClockId;
        }

        public Object getApplyOpinion() {
            return applyOpinion;
        }

        public void setApplyOpinion(Object applyOpinion) {
            this.applyOpinion = applyOpinion;
        }
    }

    public static class ApplyMaterielBean {
        /**
         * mId : 1
         * mName : 洛阳铲
         * mSum : 3
         * mRemake : 掘墓太多 铲子明显不够用
         * aPplyClockId : 16
         */

        private int mId;
        private String mName;
        private int mSum;
        private String mRemake;
        private int aPplyClockId;

        public int getMId() {
            return mId;
        }

        public void setMId(int mId) {
            this.mId = mId;
        }

        public String getMName() {
            return mName;
        }

        public void setMName(String mName) {
            this.mName = mName;
        }

        public int getMSum() {
            return mSum;
        }

        public void setMSum(int mSum) {
            this.mSum = mSum;
        }

        public String getMRemake() {
            return mRemake;
        }

        public void setMRemake(String mRemake) {
            this.mRemake = mRemake;
        }

        public int getAPplyClockId() {
            return aPplyClockId;
        }

        public void setAPplyClockId(int aPplyClockId) {
            this.aPplyClockId = aPplyClockId;
        }
    }
}
