package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.jdsjlzx.ItemDecoration.LuDividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.PagerAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.fragment.TourShopFragment;
import com.qianmo.jinxiaocun.fu.fragment.WaitTourShopFragment;
import com.qianmo.jinxiaocun.fu.widget.WrapSwipeRefreshLayout;
import com.qianmo.jinxiaocun.main.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 巡店管理界面
 */
public class TourShopManagerActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_task_content)
    TabLayout tabTaskContent;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.vp_approval)
    ViewPager vpApproval;

    private PagerAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_shop_manager);
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
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void setupToolbar() {

        //左箭头事件
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //右边的加号菜单
        mToolbar.inflateMenu(R.menu.task_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(TourTaskAddActivity.class, false);
                return true;
            }
        });

    }

    //为viewPager添加fragment
    public void setupViewPager() {
        mPageAdapter = new PagerAdapter(getSupportFragmentManager());
        mPageAdapter.addFragment(WaitTourShopFragment.newInstance(0), "待巡店");
        mPageAdapter.addFragment(TourShopFragment.newInstance(1), "已巡店");
        vpApproval.setAdapter(mPageAdapter);
    }
}
