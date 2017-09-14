package com.qianmo.jinxiaocun.fu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.qianmo.jinxiaocun.R;


/**
 * Created by chengxi on 17/4/26.
 */
public class MainViewAdapter extends BaseAdapter {

    private Fragment[] fragmentArray;
    private FragmentManager fragmentManager;
    private int hasMsgIndex=0;

    public void setHasMsgIndex(int hasMsgIndex) {
        this.hasMsgIndex = hasMsgIndex;
    }

    public MainViewAdapter(FragmentManager fragmentManager, Fragment[] fragmentArray) {
        this.fragmentManager = fragmentManager;
        this.fragmentArray = fragmentArray;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public int hasMsgIndex() {
        return hasMsgIndex;
    }


    @Override
    public String[] getTextArray() {
        return new String[] {"首页", "交易", };
    }

    @Override
    public Fragment[] getFragmentArray() {
        return fragmentArray;
    }

    /**
     * @return 设置选中时的图标
     */
    @Override
    public int[] getIconImageArray() {
        return new int[] {R.mipmap.huazhuan_unselect,R.mipmap.jichu_unselect};
    }

    /**
     * @return设置未选中时的图标
     */
    @Override
    public int[] getSelectedIconImageArray() {
        return new int[] {R.mipmap.huazhuan_select,R.mipmap.jichu_select};
    }

    @Override
    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }
}
