package com.qianmo.jinxiaocun.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.adapter.MainViewAdapter;
import com.qianmo.jinxiaocun.fu.fragment.BasisFragment;
import com.qianmo.jinxiaocun.fu.fragment.HomeFragment;
import com.qianmo.jinxiaocun.fu.listener.OnTabSelectedListener;
import com.qianmo.jinxiaocun.fu.widget.Tab;
import com.qianmo.jinxiaocun.fu.widget.TabContainerView;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tab_container)
    TabContainerView tabContainerView;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, -1, "首页", "");
        setContentView(requestView(R.layout.activity_main));
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initEvent() {
        //  tabContainerView.setCurrentItem(1);//设置底部哪个菜单被选中，不设置则默认第一个被选中
        //   tabContainerView.setCurrentViewPager(1);//设置显示哪个fragment，不设置则默认第一个被选中
        tabContainerView.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {

            }
        });
    }

    private void initView() {
        MainViewAdapter mainViewAdapter = new MainViewAdapter(getSupportFragmentManager(),
                new Fragment[]{new HomeFragment(), new BasisFragment()});
        //mainViewAdapter.setHasMsgIndex(2);//设置手否有消息提示
        tabContainerView.setAdapter(mainViewAdapter);

    }

    @Override
    public void requestInit() {

    }
}
