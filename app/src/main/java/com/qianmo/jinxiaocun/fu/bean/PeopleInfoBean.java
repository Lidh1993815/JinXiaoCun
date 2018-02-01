package com.qianmo.jinxiaocun.fu.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 17-12-9
 * desc :
 * version : 1.0
 */

public class PeopleInfoBean {

    /**
     * recordsFiltered : 8
     * data : [{"staffId":1,"staffName":"牵陌吴彦祖","postName":"员工"},{"staffId":2,"staffName":"南宁陈冠希","postName":"店长"},{"staffId":3,"staffName":"小蒙古","postName":"美导"},{"staffId":4,"staffName":"江南七侠","postName":"店长"},{"staffId":17,"staffName":"你是尼","postName":"员工"},{"staffId":19,"staffName":"涨工资","postName":"店长"},{"staffId":20,"staffName":"就试试","postName":"员工"},{"staffId":21,"staffName":"怎么了","postName":"店长"}]
     * recordsTotal : 8
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

    public static class DataBean implements Parcelable{
        /**
         * staffId : 1
         * staffName : 牵陌吴彦祖
         * postName : 员工
         */

        private int staffId;
        private String staffName;
        private String postName;

        public DataBean(Parcel in) {
            staffId = in.readInt();
            staffName = in.readString();
            postName = in.readString();
            isSelect = in.readByte() != 0;
            letters = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public DataBean() {

        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        private boolean isSelect = false;

        public String getLetters() {
            return letters;
        }

        public void setLetters(String letters) {
            this.letters = letters;
        }

        private String letters;//显示拼音的首字母

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

        public String getPostName() {
            return postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(staffId);
            dest.writeString(staffName);
            dest.writeString(postName);
            dest.writeByte((byte) (isSelect ? 1 : 0));
            dest.writeString(letters);
        }
    }
}
