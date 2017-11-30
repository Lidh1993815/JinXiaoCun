package com.qianmo.jinxiaocun.fu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.activity.NumberManagerActivity;
import com.qianmo.jinxiaocun.fu.activity.PersonalInfoActivity;
import com.qianmo.jinxiaocun.fu.activity.SwitchShopActivity;
import com.qianmo.jinxiaocun.main.base.BaseFragment;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by chengxi on 17/4/26.
 */
public class BasisFragment extends BaseFragment {
    @BindView(R.id.avatar)
    SimpleDraweeView avatar;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.personal_info_layout)
    RelativeLayout personalInfoLayout;
    Unbinder unbinder;
    @BindView(R.id.number_manager)
    RelativeLayout numberManager;
    @BindView(R.id.switchShopLayout)
    RelativeLayout mSwitchShopLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        toolBar = new MyToolBar(getContext(), -1, "个人中心", -1);

        View view = requestView(inflater, R.layout.fragment_basis);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void requestInit() {

    }

    @OnClick({R.id.personal_info_layout, R.id.number_manager,R.id.switchShopLayout})
    public void clickAction(View view) {
        switch (view.getId()) {
            //个人信息
            case R.id.personal_info_layout:
                startActivity(PersonalInfoActivity.class);
                break;
                //会员管理
            case R.id.number_manager:
                startActivity(NumberManagerActivity.class);
                break;
                //切换门店
            case R.id.switchShopLayout:
                startActivity(SwitchShopActivity.class);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
