package com.qianmo.jinxiaocun.fu.bean;

import java.util.List;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 17-12-7
 * desc :
 * version : 1.0
 */

public class LoginResponseBean {

    /**
     * state : 00000
     * msg : success
     * data : {"staff":{"staffId":1,"staffName":"牵陌吴彦祖","staffPostName":"员工","staffNumber":9527,"staffAccount":"18329194501","storeId":null,"userId":null,"staffPwd":null},"staffId":[{"postId":1,"storeId":23,"staffId":1},{"postId":2,"storeId":24,"staffId":1},{"postId":3,"storeId":17,"staffId":1}]}
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
         * staff : {"staffId":1,"staffName":"牵陌吴彦祖","staffPostName":"员工","staffNumber":9527,"staffAccount":"18329194501","storeId":null,"userId":null,"staffPwd":null}
         * staffId : [{"postId":1,"storeId":23,"staffId":1},{"postId":2,"storeId":24,"staffId":1},{"postId":3,"storeId":17,"staffId":1}]
         */

        private StaffBean staff;
        private List<StaffIdBean> staffId;

        public StaffBean getStaff() {
            return staff;
        }

        public void setStaff(StaffBean staff) {
            this.staff = staff;
        }

        public List<StaffIdBean> getStaffId() {
            return staffId;
        }

        public void setStaffId(List<StaffIdBean> staffId) {
            this.staffId = staffId;
        }

        public static class StaffBean {
            /**
             * staffId : 1
             * staffName : 牵陌吴彦祖
             * staffPostName : 员工
             * staffNumber : 9527
             * staffAccount : 18329194501
             * storeId : null
             * userId : null
             * staffPwd : null
             */

            private int staffId;
            private String staffName;
            private String staffPostName;
            private int staffNumber;
            private String staffAccount;
            private Object storeId;
            private Object userId;
            private Object staffPwd;

            public int getStaffId() {
                return staffId;
            }

            public void setStaffId(int staffId) {
                this.staffId = staffId;
            }

            public String getStaffName() {
                return staffName;
            }

            public void setStaffName(String staffName) {
                this.staffName = staffName;
            }

            public String getStaffPostName() {
                return staffPostName;
            }

            public void setStaffPostName(String staffPostName) {
                this.staffPostName = staffPostName;
            }

            public int getStaffNumber() {
                return staffNumber;
            }

            public void setStaffNumber(int staffNumber) {
                this.staffNumber = staffNumber;
            }

            public String getStaffAccount() {
                return staffAccount;
            }

            public void setStaffAccount(String staffAccount) {
                this.staffAccount = staffAccount;
            }

            public Object getStoreId() {
                return storeId;
            }

            public void setStoreId(Object storeId) {
                this.storeId = storeId;
            }

            public Object getUserId() {
                return userId;
            }

            public void setUserId(Object userId) {
                this.userId = userId;
            }

            public Object getStaffPwd() {
                return staffPwd;
            }

            public void setStaffPwd(Object staffPwd) {
                this.staffPwd = staffPwd;
            }
        }

        public static class StaffIdBean {
            /**
             * postId : 1
             * storeId : 23
             * staffId : 1
             */

            private int postId;
            private int storeId;
            private int staffId;

            public int getPostId() {
                return postId;
            }

            public void setPostId(int postId) {
                this.postId = postId;
            }

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
            }

            public int getStaffId() {
                return staffId;
            }

            public void setStaffId(int staffId) {
                this.staffId = staffId;
            }
        }
    }
}
