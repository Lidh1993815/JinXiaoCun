package com.qianmo.jinxiaocun.fu.bean;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 17-12-11
 * desc :
 * version : 1.0
 */

public class ApplyLeaveDetailBean {

    /**
     * clockView : {"staffName":"牵陌吴彦祖","cTime":"2017-12-11 03:17","applyStatus":1,"executorName":"南宁陈冠希","aType":1,"aPplyClockId":47,"aPplyAuditorTime":null,"applyOpinion":null}
     * applyLeave : {"leaveId":17,"startTime":"2017-11-30 16:00","endTime":"2017-12-02 16:00","applyDays":1,"leaveContent":"想去挖别人家的祖坟","applyClockId":47,"leaveType":1,"applyClockDetails":null}
     */

    private ClockViewBean clockView;
    private ApplyLeaveBean applyLeave;

    public ClockViewBean getClockView() {
        return clockView;
    }

    public void setClockView(ClockViewBean clockView) {
        this.clockView = clockView;
    }

    public ApplyLeaveBean getApplyLeave() {
        return applyLeave;
    }

    public void setApplyLeave(ApplyLeaveBean applyLeave) {
        this.applyLeave = applyLeave;
    }

    public static class ClockViewBean {
        /**
         * staffName : 牵陌吴彦祖
         * cTime : 2017-12-11 03:17
         * applyStatus : 1
         * executorName : 南宁陈冠希
         * aType : 1
         * aPplyClockId : 47
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

    public static class ApplyLeaveBean {
        /**
         * leaveId : 17
         * startTime : 2017-11-30 16:00
         * endTime : 2017-12-02 16:00
         * applyDays : 1.0
         * leaveContent : 想去挖别人家的祖坟
         * applyClockId : 47
         * leaveType : 1
         * applyClockDetails : null
         */

        private int leaveId;
        private String startTime;
        private String endTime;
        private double applyDays;
        private String leaveContent;
        private int applyClockId;
        private int leaveType;
        private Object applyClockDetails;

        public int getLeaveId() {
            return leaveId;
        }

        public void setLeaveId(int leaveId) {
            this.leaveId = leaveId;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public double getApplyDays() {
            return applyDays;
        }

        public void setApplyDays(double applyDays) {
            this.applyDays = applyDays;
        }

        public String getLeaveContent() {
            return leaveContent;
        }

        public void setLeaveContent(String leaveContent) {
            this.leaveContent = leaveContent;
        }

        public int getApplyClockId() {
            return applyClockId;
        }

        public void setApplyClockId(int applyClockId) {
            this.applyClockId = applyClockId;
        }

        public int getLeaveType() {
            return leaveType;
        }

        public void setLeaveType(int leaveType) {
            this.leaveType = leaveType;
        }

        public Object getApplyClockDetails() {
            return applyClockDetails;
        }

        public void setApplyClockDetails(Object applyClockDetails) {
            this.applyClockDetails = applyClockDetails;
        }
    }
}
