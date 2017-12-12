package com.qianmo.jinxiaocun.fu.bean;

import java.util.List;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 17-12-12
 * desc :
 * version : 1.0
 */

public class AddApplyMaterialBean {

    private List<ApplyMaterielsBean> applyMateriels;

    public List<ApplyMaterielsBean> getApplyMateriels() {
        return applyMateriels;
    }

    public void setApplyMateriels(List<ApplyMaterielsBean> applyMateriels) {
        this.applyMateriels = applyMateriels;
    }

    public static class ApplyMaterielsBean {
        /**
         * mName : 洛阳铲
         * mSum : 3
         * mRemake : 掘墓太多 铲子明显不够用
         */

        private String mName;
        private String mSum;
        private String mRemake;

        public ApplyMaterielsBean() {

        }

        public ApplyMaterielsBean(String name, String sum, String remake) {
            mName = name;
            mSum = sum;
            mRemake = remake;
        }

        public String getMName() {
            return mName;
        }

        public void setMName(String mName) {
            this.mName = mName;
        }

        public String getMSum() {
            return mSum;
        }

        public void setMSum(String mSum) {
            this.mSum = mSum;
        }

        public String getMRemake() {
            return mRemake;
        }

        public void setMRemake(String mRemake) {
            this.mRemake = mRemake;
        }
    }
}
