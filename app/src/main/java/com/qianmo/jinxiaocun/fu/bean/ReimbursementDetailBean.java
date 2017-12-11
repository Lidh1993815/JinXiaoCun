package com.qianmo.jinxiaocun.fu.bean;

import java.util.List;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 17-12-11
 * desc : 报销的实体类
 * version : 1.0
 */

public class ReimbursementDetailBean {

    /**
     * applyReimburses : [{"rId":5,"rMoney":2000,"rType":"出差 买洛阳铲","rContent":"出差买了洛阳铲 挖了长城 挖故宫","rImgUrl":"xxxx.jpg","aPplyClockId":49},{"rId":6,"rMoney":1800,"rType":"报销车费","rContent":"打了辆飞的到欧洲去帮老板买巧克力","rImgUrl":"xxxx.jpg","aPplyClockId":49}]
     * clockView : {"staffName":"牵陌吴彦祖","cTime":"2017-12-11 05:09","applyStatus":1,"executorName":"江南铁拐李","aType":4,"aPplyClockId":49,"aPplyAuditorTime":null,"applyOpinion":null}
     */

    private ClockViewBean clockView;
    private List<ApplyReimbursesBean> applyReimburses;

    public ClockViewBean getClockView() {
        return clockView;
    }

    public void setClockView(ClockViewBean clockView) {
        this.clockView = clockView;
    }

    public List<ApplyReimbursesBean> getApplyReimburses() {
        return applyReimburses;
    }

    public void setApplyReimburses(List<ApplyReimbursesBean> applyReimburses) {
        this.applyReimburses = applyReimburses;
    }

    public static class ClockViewBean {
        /**
         * staffName : 牵陌吴彦祖
         * cTime : 2017-12-11 05:09
         * applyStatus : 1
         * executorName : 江南铁拐李
         * aType : 4
         * aPplyClockId : 49
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

    public static class ApplyReimbursesBean {
        /**
         * rId : 5
         * rMoney : 2000
         * rType : 出差 买洛阳铲
         * rContent : 出差买了洛阳铲 挖了长城 挖故宫
         * rImgUrl : xxxx.jpg
         * aPplyClockId : 49
         */

        private int rId;
        private int rMoney;
        private String rType;
        private String rContent;
        private String rImgUrl;
        private int aPplyClockId;

        public int getRId() {
            return rId;
        }

        public void setRId(int rId) {
            this.rId = rId;
        }

        public int getRMoney() {
            return rMoney;
        }

        public void setRMoney(int rMoney) {
            this.rMoney = rMoney;
        }

        public String getRType() {
            return rType;
        }

        public void setRType(String rType) {
            this.rType = rType;
        }

        public String getRContent() {
            return rContent;
        }

        public void setRContent(String rContent) {
            this.rContent = rContent;
        }

        public String getRImgUrl() {
            return rImgUrl;
        }

        public void setRImgUrl(String rImgUrl) {
            this.rImgUrl = rImgUrl;
        }

        public int getAPplyClockId() {
            return aPplyClockId;
        }

        public void setAPplyClockId(int aPplyClockId) {
            this.aPplyClockId = aPplyClockId;
        }
    }
}
