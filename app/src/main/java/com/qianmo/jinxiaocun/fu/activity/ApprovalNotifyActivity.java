package com.qianmo.jinxiaocun.fu.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.adapter.PagerAdapter;
import com.qianmo.jinxiaocun.fu.fragment.ApprovalNotifyFragment;
import com.qianmo.jinxiaocun.main.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApprovalNotifyActivity extends BaseActivity {

    @BindView(R.id.tab_approval_content)
    TabLayout tabApprovalContent;
    @BindView(R.id.vp_approval)
    ViewPager vpApproval;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private PagerAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_notify);
        ImmersionBar.with(this).statusBarColor(R.color.colorPrimary).init();
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void requestInit() {

    }

    private void initView() {
        vpApproval.setOffscreenPageLimit(1);
        if (vpApproval != null) {
            setupViewPager();
        }
        tabApprovalContent.setupWithViewPager(vpApproval);
        //左箭头事件
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //为viewPager添加fragment
    public void setupViewPager() {
        mPageAdapter = new PagerAdapter(getSupportFragmentManager());
        mPageAdapter.addFragment(ApprovalNotifyFragment.newInstance(0), "待我审批的");
        mPageAdapter.addFragment(ApprovalNotifyFragment.newInstance(1), "我已审批的");
        vpApproval.setAdapter(mPageAdapter);
    }
}
