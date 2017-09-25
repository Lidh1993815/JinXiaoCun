package com.qianmo.jinxiaocun.fu.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.adapter.PagerAdapter;
import com.qianmo.jinxiaocun.fu.fragment.ApprovalNotifyFragment;
import com.qianmo.jinxiaocun.fu.fragment.MyCardRecordFragment;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 打卡记录页面
 */
public class CardRecordActivity extends BaseActivity {

    @BindView(R.id.vp_approval)
    ViewPager mViewPager;
    @BindView(R.id.toolbar_leftText)
    TextView toolbar_left;
    @BindView(R.id.toolbar_rightText)
    TextView toolbar_right;
    private PagerAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "打卡记录", "记录");
        setContentView(requestView(R.layout.activity_card_record));
        ButterKnife.bind(this);
        initView();
        initEvent();
    }
    private void initView() {

        if (mViewPager != null){
            setupViewPager();
        }
        selectLeft();

    }
    @Override
    public void requestInit() {

    }

    @OnClick({R.id.toolbar_leftText,R.id.toolbar_rightText})
    public void toolbarTextClick(View v){
        switch (v.getId()){
            case R.id.toolbar_leftText:
                selectLeft();

                mViewPager.setCurrentItem(0);
                break;
            case R.id.toolbar_rightText:
                selectRight();
                //设置不同的viewPager
                mViewPager.setCurrentItem(1);
                break;
        }
    }

    private void initEvent() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    selectLeft();
                }else {
                    selectRight();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //为viewPager添加fragment
    public void setupViewPager() {
        mPageAdapter = new PagerAdapter(getSupportFragmentManager());
        mPageAdapter.addFragment(MyCardRecordFragment.newInstance(0), "");
        mPageAdapter.addFragment(MyCardRecordFragment.newInstance(1), "");
        mViewPager.setAdapter(mPageAdapter);
        /*mPageAdapter = new PagerAdapter(getSupportFragmentManager());
        TransactionDetailFragment fragment = new TransactionDetailFragment();
        fragment.setArguments(bundle);
        pageAdapter.addFragment(fragment,"");
        StatisticalAnalysisFragment analysisFragment = new StatisticalAnalysisFragment();
        analysisFragment.setArguments(bundle);
        pageAdapter.addFragment(analysisFragment,"");
        mViewPager.setAdapter(pageAdapter);*/
    }

    private void selectRight() {
        toolbar_right.setBackground(getResources().getDrawable(R.drawable.tv_right_select_bg));
        toolbar_left.setTextColor(getResources().getColor(R.color.colorPrimary));
        toolbar_right.setTextColor(Color.WHITE);
        toolbar_left.setBackground(getResources().getDrawable(R.drawable.tv_left_unselect_bg));
    }

    private void selectLeft() {
        toolbar_left.setBackground(getResources().getDrawable(R.drawable.tv_left_select_bg));
        toolbar_right.setTextColor(getResources().getColor(R.color.colorPrimary));
        toolbar_left.setTextColor(Color.WHITE);
        toolbar_right.setBackground(getResources().getDrawable(R.drawable.tv_right_unselect_bg));

    }
}
