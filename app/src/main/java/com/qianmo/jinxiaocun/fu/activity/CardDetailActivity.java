package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.os.Bundle;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.decoration.TaskProgressDecoration;
import com.qianmo.jinxiaocun.fu.widget.ForbiddenSwipeRefreshLayout;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CardDetailActivity extends BaseActivity {
 /*   @BindView(R.id.rv_task_progress_list)
    RecyclerView mRecyclerView;*/
    Unbinder unbinder;
    @BindView(R.id.swipe_refresh_layout)
    ForbiddenSwipeRefreshLayout mSwipeRefreshLayout;
    private TaskAdapter mTaskAdapter = null;//数据适配器
    private ArrayList<String> datas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "补打卡详情", -1);
        setContentView(requestView(R.layout.activity_card_detail));
        ButterKnife.bind(this);
       // initData();
       // initView();
       // initEvent();
    }

    @Override
    public void requestInit() {

    }

    private void initData() {

        //模拟组装10个数据
        datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            datas.add("");
        }

    }

    private void initEvent() {
        onRefresh();
    }

    private void initView() {
      //  setupSwipeRefresh(mSwipeRefreshLayout);
        mTaskAdapter = new TaskAdapter(this);//实例化适配器
        //设置decoration
        TaskProgressDecoration decoration = new TaskProgressDecoration(this);

        //setLayoutManager放在setAdapter之前配置
       /* mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mTaskAdapter);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(decoration);*/
    }

    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mTaskAdapter.setDataList(datas);
        dismissSwipeRefresh(mSwipeRefreshLayout, 1000);
        //requestDataFromNet();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //设置RecycleView的适配器
    private class TaskAdapter extends ListBaseAdapter<String> {

        public TaskAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.fu_task_recycle_item;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {

        }

    }
}
