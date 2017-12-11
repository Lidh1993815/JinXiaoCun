package com.qianmo.jinxiaocun.fu.bean;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 17-12-9
 * desc :
 * version : 1.0
 */

public class ApplyClockDetailBean {

    /**
     * aType : 2
     * staffId : 1
     * aAuditor : 3
     */

    private int aType;
    private int staffId;
    private int aAuditor;

    public ApplyClockDetailBean(int aType, int staffId, int aAuditor) {
        this.aType = aType;
        this.staffId = staffId;
        this.aAuditor = aAuditor;
    }

    public int getAType() {
        return aType;
    }

    public void setAType(int aType) {
        this.aType = aType;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getAAuditor() {
        return aAuditor;
    }

    public void setAAuditor(int aAuditor) {
        this.aAuditor = aAuditor;
    }
}
