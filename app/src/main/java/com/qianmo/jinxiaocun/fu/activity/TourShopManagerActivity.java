package com.qianmo.jinxiaocun.fu.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.adapter.PagerAdapter;
import com.qianmo.jinxiaocun.fu.fragment.TourShopFragment;
import com.qianmo.jinxiaocun.fu.fragment.WaitTourShopFragment;
import com.qianmo.jinxiaocun.main.base.BaseActivity;

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
    @BindView(R.id.rv_tour_manager)
    RecyclerView mRecyclerView;
    private PagerAdapter mPageAdapter;
    private boolean isShowTab = false;
    private PopupWindow popupWindow;
    private TextView textView;
    private MenuItem menuItem;

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
        //tablayout的显示隐藏
        tablayoutShowOrHiden();
        vpApproval.setOffscreenPageLimit(1);
        if (vpApproval != null) {
            setupViewPager();
        }
        tabTaskContent.setupWithViewPager(vpApproval);
        setupAppbar();
        setupToolbar();
        initPopupWindow();
        menuShowOrHidden(isShowTab);
        pageSwitch(isShowTab);
    }

    private void pageSwitch(boolean showRecyclerOrViewPager) {
        if (showRecyclerOrViewPager) {
            mRecyclerView.setVisibility(View.GONE);
            vpApproval.setVisibility(View.VISIBLE);

        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            vpApproval.setVisibility(View.GONE);
        }
    }

    private void tablayoutShowOrHiden() {
        if (isShowTab) {
            tabTaskContent.setVisibility(View.VISIBLE);

        } else {
            tabTaskContent.setVisibility(View.GONE);
        }
    }

    //菜单的显示隐藏
    private void menuShowOrHidden(boolean isShowTab) {
        //获取toolbar的菜单
        Menu menu = mToolbar.getMenu();
        menu.size();
        if (menu != null) {
            if (menu.size() > 0) {
                menuItem = menu.getItem(0);
            }
        }
        if (menuItem != null) {
            menuItem.setVisible(!isShowTab);

        }
    }

    //初始化popupWindow
    private void initPopupWindow() {
        View content = LayoutInflater.from(this).inflate(R.layout.choose_title_pop, null);
        popupWindow = new PopupWindow(content, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //点击外部消失
        popupWindow.setOutsideTouchable(false);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画
        popupWindow.setAnimationStyle(R.style.MyPopWindowAnim);
        //巡店管理
        content.findViewById(R.id.tv_tour_manager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(R.string.my_tour);
                isShowTab = false;
                menuShowOrHidden(isShowTab);
                tablayoutShowOrHiden();
                dismissPopupWindow();
                setupAppbar();
                pageSwitch(isShowTab);

            }
        });
        //我的巡店
        content.findViewById(R.id.tv_my_tour).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(R.string.tour_shop_inquire);
                isShowTab = true;
                menuShowOrHidden(isShowTab);
                tablayoutShowOrHiden();
                dismissPopupWindow();
                setupAppbar();
                pageSwitch(isShowTab);
            }
        });
    }

    //用来设置toolbar是否跟着滚动折叠
    private void setupAppbar() {

        AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams) appbar.getChildAt(0).getLayoutParams();
        if (isShowTab) {
            mParams.setScrollFlags(5);//的时候AppBarLayout下的toolbar会随着滚动条折叠

        } else {
            mParams.setScrollFlags(0);//的时候AppBarLayout下的toolbar就不会随着滚动条折叠
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
        //右边的加号菜单
        mToolbar.inflateMenu(R.menu.task_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(TourTaskAddActivity.class, false);
                return true;
            }
        });
        //设置title的点击事件
        textView = mToolbar.findViewById(R.id.toolbar_title);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow.isShowing() && popupWindow != null) {
                    popupWindow.dismiss();

                } else {
                    showPopupWindow();
                }
            }
        });
    }

    //显示popupWindou
    private void showPopupWindow() {
        popupWindow.showAsDropDown(textView);

    }

    //显示popupWindou
    private void dismissPopupWindow() {
        if (popupWindow.isShowing() && popupWindow != null) {
            popupWindow.dismiss();

        }
    }

    //为viewPager添加fragment
    public void setupViewPager() {
        mPageAdapter = new PagerAdapter(getSupportFragmentManager());
        mPageAdapter.addFragment(WaitTourShopFragment.newInstance(0), "待巡店");
        mPageAdapter.addFragment(TourShopFragment.newInstance(1), "已巡店");
        vpApproval.setAdapter(mPageAdapter);
    }
}
