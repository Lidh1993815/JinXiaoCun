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
import com.qianmo.jinxiaocun.main.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tab_container)
    TabContainerView tabContainerView;
    private static final String TAG = "MainActivity";
    private long mExitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    
    //监听返回按键
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtils.MyToast(getApplicationContext(), "再按一次退出应用");
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

}
