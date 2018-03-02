package com.qianmo.jinxiaocun.fu.bean;

import java.util.List;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 18-3-2
 * desc :
 * version : 1.0
 */

public class CostTypeBean {


    /**
     * state : 00000
     * msg : success
     * data : [{"reimburseId":1,"typename":"出差","cTime":"2017-12-11 04:00","isDel":1},{"reimburseId":2,"typename":"吃饭","cTime":"2017-11-26 02:30","isDel":1},{"reimburseId":3,"typename":"机票","cTime":"2017-11-23 02:20","isDel":1},{"reimburseId":4,"typename":"打车","cTime":"2017-12-21 02:30","isDel":1},{"reimburseId":5,"typename":"旅游","cTime":"2017-12-28 06:09","isDel":1},{"reimburseId":6,"typename":"去玩","cTime":"2017-12-28 07:08","isDel":1}]
     */

    private String state;
    private String msg;
    private List<DataBean> data;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * reimburseId : 1
         * typename : 出差
         * cTime : 2017-12-11 04:00
         * isDel : 1
         */

        private int reimburseId;
        private String typename;
        private String cTime;
        private int isDel;
        private boolean isSelect;

        public int getReimburseId() {
            return reimburseId;
        }

        public void setReimburseId(int reimburseId) {
            this.reimburseId = reimburseId;
        }

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }

        public String getCTime() {
            return cTime;
        }

        public void setCTime(String cTime) {
            this.cTime = cTime;
        }

        public int getIsDel() {
            return isDel;
        }

        public void setIsDel(int isDel) {
            this.isDel = isDel;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }
    }
}
