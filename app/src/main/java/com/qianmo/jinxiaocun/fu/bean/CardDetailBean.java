package com.qianmo.jinxiaocun.fu.bean;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 17-12-9
 * desc : 补打开详情实体类
 * version : 1.0
 */

public class CardDetailBean {

    /**
     * clockView : {"staffName":"牵陌吴彦祖","cTime":"2017-12-09 09:07","applyStatus":1,"executorName":"就试试","aType":2,"aPplyClockId":32,"aPplyAuditorTime":null,"applyOpinion":null}
     * applyFillCard : {"fId":14,"fTime":"2017-12-09","fContent":"昨日下班比较冲忙忘记打卡","aPplyClockId":32,"applyClockDetails":null}
     */

    private ClockViewBean clockView;
    private ApplyFillCardBean applyFillCard;

    public ClockViewBean getClockView() {
        return clockView;
    }

    public void setClockView(ClockViewBean clockView) {
        this.clockView = clockView;
    }

    public ApplyFillCardBean getApplyFillCard() {
        return applyFillCard;
    }

    public void setApplyFillCard(ApplyFillCardBean applyFillCard) {
        this.applyFillCard = applyFillCard;
    }

    public static class ClockViewBean {
        /**
         * staffName : 牵陌吴彦祖
         * cTime : 2017-12-09 09:07
         * applyStatus : 1
         * executorName : 就试试
         * aType : 2
         * aPplyClockId : 32
         * aPplyAuditorTime : null
         * applyOpinion : null
         */

        private String staffName;
        private String cTime;
        private int applyStatus;
        private String executorName;
        private int aType;
        private int aPplyClockId;
        private String aPplyAuditorTime;
        private Object applyOpinion;

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

        public String getAPplyAuditorTime() {
            return aPplyAuditorTime;
        }

        public void setAPplyAuditorTime(String aPplyAuditorTime) {
            this.aPplyAuditorTime = aPplyAuditorTime;
        }

        public Object getApplyOpinion() {
            return applyOpinion;
        }

        public void setApplyOpinion(Object applyOpinion) {
            this.applyOpinion = applyOpinion;
        }
    }

    public static class ApplyFillCardBean {
        /**
         * fId : 14
         * fTime : 2017-12-09
         * fContent : 昨日下班比较冲忙忘记打卡
         * aPplyClockId : 32
         * applyClockDetails : null
         */

        private int fId;
        private String fTime;
        private String fContent;
        private int aPplyClockId;
        private Object applyClockDetails;

        public int getFId() {
            return fId;
        }

        public void setFId(int fId) {
            this.fId = fId;
        }

        public String getFTime() {
            return fTime;
        }

        public void setFTime(String fTime) {
            this.fTime = fTime;
        }

        public String getFContent() {
            return fContent;
        }

        public void setFContent(String fContent) {
            this.fContent = fContent;
        }

        public int getAPplyClockId() {
            return aPplyClockId;
        }

        public void setAPplyClockId(int aPplyClockId) {
            this.aPplyClockId = aPplyClockId;
        }

        public Object getApplyClockDetails() {
            return applyClockDetails;
        }

        public void setApplyClockDetails(Object applyClockDetails) {
            this.applyClockDetails = applyClockDetails;
        }
    }
}
