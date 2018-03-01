package com.qianmo.jinxiaocun.fu.bean;

import java.util.List;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 18-3-1
 * desc :
 * version : 1.0
 */

public class NumberBean {

    /**
     * recordsFiltered : 1
     * data : [{"memberPhone":"15669963627","memberName":"黄金会员","integral":null}]
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
         * memberPhone : 15669963627
         * memberName : 黄金会员
         * integral : null
         */

        private String memberPhone;
        private String memberName;
        private long integral;

        public String getMemberPhone() {
            return memberPhone;
        }

        public void setMemberPhone(String memberPhone) {
            this.memberPhone = memberPhone;
        }

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public long getIntegral() {
            return integral;
        }

        public void setIntegral(long integral) {
            this.integral = integral;
        }
    }
}
