package com.qianmo.jinxiaocun.fu.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.activity.TaskNotifyActivity;
import com.qianmo.jinxiaocun.main.base.BaseFragment;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by chengxi on 17/4/26.
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.main_swipe_refresh)
    SwipeRefreshLayout mainSwipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.gv_home)
    GridView gvHome;
    @BindView(R.id.task_notify_layout)
    RelativeLayout taskNotifyLayout;
    @BindView(R.id.approval_notify_layout)
    RelativeLayout approvalNotifyLayout;
    @BindView(R.id.alarm_notify_layout)
    RelativeLayout alarmNotifyLayout;
    private int[] mImages = {R.mipmap.renwu, R.mipmap.xundian, R.mipmap.kaoq, R.mipmap.cuxiao,
            R.mipmap.shenpi, R.mipmap.jinhuo, R.mipmap.xiaoshou, R.mipmap.kucun,
            R.mipmap.qianliu};
    private String[] titles = {"任务", "寻店管理", "考勤", "促销管理", "审批", "进货管理", "销售管理", "库存管理", "钱流管理"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        toolBar = new MyToolBar(getContext(), -1, "首页", "");
        View view = requestView(inflater, R.layout.fragment_home);
        unbinder = ButterKnife.bind(this, view);
        initView();//初始化视图
        initEvent();//初始化监听事件
        return view;

    }

    private void initEvent() {
    }

    private void initView() {
        setupSwipeRefresh(mainSwipeRefresh);
        mainSwipeRefresh.setRefreshing(true);
        dismissSwipeRefresh(mainSwipeRefresh, 1000);
        gvHome.setAdapter(new GridAdapter());
    }

    @Override
    public void requestInit() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.task_notify_layout,R.id.approval_notify_layout,R.id.alarm_notify_layout})
    public void clickLayout(View view) {
        switch (view.getId()) {
            //任务通知
            case R.id.task_notify_layout:
                startActivity(TaskNotifyActivity.class);
                break;
            //审批通知
            case R.id.approval_notify_layout:
                break;
            //报警通知
            case R.id.alarm_notify_layout:
                break;
        }
    }




    //GridView的Adapter
    class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mImages.length;
        }

        @Override
        public Object getItem(int i) {
            return mImages[i];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override

        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.home_grid_item, null);
                holder.iv = (convertView.findViewById(R.id.iv_grid_home));
                holder.tv = convertView.findViewById(R.id.tv_grid_home);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.iv.setImageResource(mImages[position]);
            holder.tv.setText(titles[position]);
            return convertView;
        }

        private class ViewHolder {
            ImageView iv;
            TextView tv;
        }

    }
}
