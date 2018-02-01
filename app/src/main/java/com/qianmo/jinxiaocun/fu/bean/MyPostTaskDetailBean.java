package com.qianmo.jinxiaocun.fu.bean;

import java.util.List;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 18-2-1
 * desc :
 * version : 1.0
 */

public class MyPostTaskDetailBean {

    /**
     * data : {"taskId":14,"staffName":"穿山甲","taskNumber":"1511598737178","title":"去西藏跑3圈","content":"准备好洛阳铲随时准备出发准备蒙古","executeStatus":1,"bonus":300,"penalty":200,"uptoTime":"2017-12-01 05:39","carryOutTaskId":null}
     * carryOutViews : [{"staffName":"江南铁拐李","carryOutTaskId":5,"carryOutStatus":2},{"staffName":"甲桂林","carryOutTaskId":6,"carryOutStatus":2},{"staffName":"江南七侠","carryOutTaskId":7,"carryOutStatus":1}]
     */

    private DataBean data;
    private List<CarryOutViewsBean> carryOutViews;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public List<CarryOutViewsBean> getCarryOutViews() {
        return carryOutViews;
    }

    public void setCarryOutViews(List<CarryOutViewsBean> carryOutViews) {
        this.carryOutViews = carryOutViews;
    }

    public static class DataBean {
        /**
         * taskId : 14
         * staffName : 穿山甲
         * taskNumber : 1511598737178
         * title : 去西藏跑3圈
         * content : 准备好洛阳铲随时准备出发准备蒙古
         * executeStatus : 1
         * bonus : 300.0
         * penalty : 200.0
         * uptoTime : 2017-12-01 05:39
         * carryOutTaskId : null
         */

        private int taskId;
        private String staffName;
        private String taskNumber;
        private String title;
        private String content;
        private int executeStatus;
        private double bonus;
        private double penalty;
        private String uptoTime;
        private Object carryOutTaskId;

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public String getStaffName() {
            return staffName;
        }

        public void setStaffName(String staffName) {
            this.staffName = staffName;
        }

        public String getTaskNumber() {
            return taskNumber;
        }

        public void setTaskNumber(String taskNumber) {
            this.taskNumber = taskNumber;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getExecuteStatus() {
            return executeStatus;
        }

        public void setExecuteStatus(int executeStatus) {
            this.executeStatus = executeStatus;
        }

        public double getBonus() {
            return bonus;
        }

        public void setBonus(double bonus) {
            this.bonus = bonus;
        }

        public double getPenalty() {
            return penalty;
        }

        public void setPenalty(double penalty) {
            this.penalty = penalty;
        }

        public String getUptoTime() {
            return uptoTime;
        }

        public void setUptoTime(String uptoTime) {
            this.uptoTime = uptoTime;
        }

        public Object getCarryOutTaskId() {
            return carryOutTaskId;
        }

        public void setCarryOutTaskId(Object carryOutTaskId) {
            this.carryOutTaskId = carryOutTaskId;
        }
    }

    public static class CarryOutViewsBean {
        /**
         * staffName : 江南铁拐李
         * carryOutTaskId : 5
         * carryOutStatus : 2
         */

        private String staffName;
        private int carryOutTaskId;
        private int carryOutStatus;

        public String getStaffName() {
            return staffName;
        }

        public void setStaffName(String staffName) {
            this.staffName = staffName;
        }

        public int getCarryOutTaskId() {
            return carryOutTaskId;
        }

        public void setCarryOutTaskId(int carryOutTaskId) {
            this.carryOutTaskId = carryOutTaskId;
        }

        public int getCarryOutStatus() {
            return carryOutStatus;
        }

        public void setCarryOutStatus(int carryOutStatus) {
            this.carryOutStatus = carryOutStatus;
        }
    }
}
