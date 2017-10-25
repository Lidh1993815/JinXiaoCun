package com.qianmo.jinxiaocun.fu.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.adapter.PagerAdapter;
import com.qianmo.jinxiaocun.fu.fragment.BirthdayNotifyFragment;
import com.qianmo.jinxiaocun.main.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 通知界面
 */
public class NotifyActivity extends BaseActivity {

    @BindView(R.id.vp_approval)
    ViewPager vpApproval;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_task_content)
    TabLayout tabTaskContent;
    private PagerAdapter mPageAdapter;
    private static final String TAG = "NotifyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        ButterKnife.bind(this);
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
        tabTaskContent.setupWithViewPager(vpApproval);
        setupToolbar();
        int childCount = mPageAdapter.getCount();
        Log.i(TAG, "childCount: "+childCount);
        if (childCount > 2) {
            tabTaskContent.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tabTaskContent.setTabMode(TabLayout.MODE_FIXED);
        }
    }

    private void setupToolbar() {
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
        mPageAdapter.addFragment(BirthdayNotifyFragment.newInstance(0), "员工生日");
        mPageAdapter.addFragment(BirthdayNotifyFragment.newInstance(1), "会员生日");
        vpApproval.setAdapter(mPageAdapter);
    }
}
