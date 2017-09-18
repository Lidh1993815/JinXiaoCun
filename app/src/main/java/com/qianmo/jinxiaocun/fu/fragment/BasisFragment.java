package com.qianmo.jinxiaocun.fu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.main.base.BaseFragment;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

/**
 * Created by chengxi on 17/4/26.
 */
public class BasisFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        toolBar = new MyToolBar(getContext(), -1, "个人中心", -1);

        View view = requestView(inflater, R.layout.fragment_basis);
        return view;

    }

    @Override
    public void requestInit() {

    }
}
