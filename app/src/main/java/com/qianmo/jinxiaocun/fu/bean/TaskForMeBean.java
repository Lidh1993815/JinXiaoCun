package com.qianmo.jinxiaocun.fu.bean;

import java.util.List;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 18-1-29
 * desc :
 * version : 1.0
 */

public class TaskForMeBean {

    /**
     * recordsFiltered : 3
     * data : [{"taskId":22,"title":"飞到马来西亚去寻找失踪的飞机","cTime":null,"executeStatus":1,"carryOutStatus":null},{"taskId":19,"title":"飞到马来西亚去寻找失踪的飞机","cTime":"2017-12-25 01:30","executeStatus":1,"carryOutStatus":null},{"taskId":15,"title":"赶紧接任务有礼品","cTime":"2017-12-01 05:51","executeStatus":1,"carryOutStatus":null}]
     * recordsTotal : 3
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
         * taskId : 22
         * title : 飞到马来西亚去寻找失踪的飞机
         * cTime : null
         * executeStatus : 1
         * carryOutStatus : null
         */

        private int taskId;
        private String title;
        private String cTime;
        private int executeStatus;
        private Object carryOutStatus;

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCTime() {
            return cTime;
        }

        public void setCTime(String cTime) {
            this.cTime = cTime;
        }

        public int getExecuteStatus() {
            return executeStatus;
        }

        public void setExecuteStatus(int executeStatus) {
            this.executeStatus = executeStatus;
        }

        public Object getCarryOutStatus() {
            return carryOutStatus;
        }

        public void setCarryOutStatus(Object carryOutStatus) {
            this.carryOutStatus = carryOutStatus;
        }
    }
}
