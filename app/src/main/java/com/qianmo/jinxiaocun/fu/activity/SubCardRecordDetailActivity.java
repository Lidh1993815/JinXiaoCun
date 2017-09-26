package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.os.Bundle;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

public class SubCardRecordDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "黄克山的考勤", -1);
        setContentView(requestView(R.layout.activity_sub));
        initData();//初始化数据
        initView();
        initEvent();
    }

    @Override
    public void requestInit() {

    }

    private void initData() {

    }

    private void initEvent() {

    }

    private void initView() {

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
