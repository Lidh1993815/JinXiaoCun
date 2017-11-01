package com.qianmo.jinxiaocun.fu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.main.base.BaseFragment;

/**
 * author : wizardev
 * e-mail : wizarddev@163.com
 * time   : 2017/10/11
 * desc   : 总库存界面
 * version: 1.0
 */
public class TotalInventoryFragment extends BaseFragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = requestView(inflater, R.layout.fu_total_inventory_fragment);
        return view;
    }

    @Override
    public void requestInit() {

    }
}
