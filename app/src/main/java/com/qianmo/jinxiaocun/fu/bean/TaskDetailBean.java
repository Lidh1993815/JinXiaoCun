package com.qianmo.jinxiaocun.fu.bean;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 18-2-1
 * desc :
 * version : 1.0
 */

public class TaskDetailBean {

    /**
     * state : 00000
     * msg : success
     * data : {"taskId":null,"staffName":"穿山甲","taskNumber":"1511600162932","title":"任务指令","content":"蒙古走咯 挖祖坟去咯","executeStatus":2,"bonus":300,"penalty":200,"uptoTime":"2017-12-01 05:51","carryOutTaskId":13}
     */

    private String state;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * taskId : null
         * staffName : 穿山甲
         * taskNumber : 1511600162932
         * title : 任务指令
         * content : 蒙古走咯 挖祖坟去咯
         * executeStatus : 2
         * bonus : 300
         * penalty : 200
         * uptoTime : 2017-12-01 05:51
         * carryOutTaskId : 13
         */

        private Object taskId;
        private String staffName;
        private String taskNumber;
        private String title;
        private String content;
        private int executeStatus;
        private int bonus;
        private int penalty;
        private String uptoTime;
        private int carryOutTaskId;

        public Object getTaskId() {
            return taskId;
        }

        public void setTaskId(Object taskId) {
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

        public int getBonus() {
            return bonus;
        }

        public void setBonus(int bonus) {
            this.bonus = bonus;
        }

        public int getPenalty() {
            return penalty;
        }

        public void setPenalty(int penalty) {
            this.penalty = penalty;
        }

        public String getUptoTime() {
            return uptoTime;
        }

        public void setUptoTime(String uptoTime) {
            this.uptoTime = uptoTime;
        }

        public int getCarryOutTaskId() {
            return carryOutTaskId;
        }

        public void setCarryOutTaskId(int carryOutTaskId) {
            this.carryOutTaskId = carryOutTaskId;
        }
    }
}
