package com.qianmo.jinxiaocun.fu.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.main.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chengxi on 17/4/26.
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.main_swipe_refresh)
    SwipeRefreshLayout mainSwipeRefresh;
    Unbinder unbinder;
    private int[] mImages = {R.mipmap.renwu, R.mipmap.xundian, R.mipmap.kaoq, R.mipmap.cuxiao, R.mipmap.shenpi,
            R.mipmap.jinhuo, R.mipmap.xiaoshou, R.mipmap.kucun, R.mipmap.qianliu};
    private String[] title = {"任务","寻店管理","考勤","促销管理","审批","进货管理","销售管理","库存管理","钱流管理"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = requestView(inflater, R.layout.fragment_home);
        unbinder = ButterKnife.bind(this, view);
        initView();//初始化视图
        initEvent();//初始化监听事件
        return view;

    }

    private void initEvent() {
        mainSwipeRefresh.setOnRefreshListener(this);
    }

    private void initView() {
        setupSwipeRefresh(mainSwipeRefresh);
        mainSwipeRefresh.setRefreshing(true);
        dismissSwipeRefresh(mainSwipeRefresh, 2000);
    }

    @Override
    public void requestInit() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        dismissSwipeRefresh(mainSwipeRefresh, 2000);
    }
    class GridAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mImages.length;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.home_grid_item, null, false);
            }
            return null;
        }
    }
}
