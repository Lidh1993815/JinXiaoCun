package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.decoration.FullyLinearLayoutManager;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 报销详情界面
 */
public class ReimbursementDetailActivity extends BaseActivity {

    @BindView(R.id.rv_add_reim)
    RecyclerView mRecyclerView;

    private TaskAdapter mTaskAdapter = null;//数据适配器
    private ArrayList<String> datas = new ArrayList<>();
    private LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "报销详情", -1);
        setContentView(requestView(R.layout.activity_reimbursement_detail));
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    public void requestInit() {

    }

    private void initData() {
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
    }

    private void initView() {
        mTaskAdapter = new TaskAdapter(this);
        mTaskAdapter.addAll(datas);
        mRecyclerView.setLayoutManager(new FullyLinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mTaskAdapter);
        mRecyclerView.setHasFixedSize(true);
    }

    //设置RecycleView的适配器
    private class TaskAdapter extends ListBaseAdapter<String> {

        public TaskAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.fu_reim_detail_item;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, final int position) {
            TextView positionText = holder.getView(R.id.reim_detail_position);
            int num = position + 1;
            positionText.setText("报销明细（"+num+"）");//设置显示的文字
        }

    }
}
