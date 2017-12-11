package com.qianmo.jinxiaocun.fu.utils;

import com.qianmo.jinxiaocun.fu.bean.PeopleInfoBean;

import java.util.Comparator;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 17-12-8
 * desc :
 * version : 1.0
 */

public class PinyinComparator implements Comparator<PeopleInfoBean.DataBean> {

    @Override
    public int compare(PeopleInfoBean.DataBean o1, PeopleInfoBean.DataBean o2) {
        if (o1.getLetters().equals("@")
                || o2.getLetters().equals("#")) {
            return 1;
        } else if (o1.getLetters().equals("#")
                || o2.getLetters().equals("@")) {
            return -1;
        } else {
            return o1.getLetters().compareTo(o2.getLetters());
        }
    }
}

