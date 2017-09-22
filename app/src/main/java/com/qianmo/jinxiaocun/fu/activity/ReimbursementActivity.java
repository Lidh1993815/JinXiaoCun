package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 报销界面
 */
public class ReimbursementActivity extends BaseActivity {

    @BindView(R.id.rv_add_reim)
    RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter = null;//数据适配器
    private ArrayList<String> datas = new ArrayList<>();
    private static final String TAG = "ReimbursementActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "报销", "提交");
        setContentView(requestView(R.layout.activity_reimbursement));
        ButterKnife.bind(this);
        initData();
        initView();


    }

    private void initData() {
        datas.add("");
    }

    private void initView() {
        mTaskAdapter = new TaskAdapter(this);
        mTaskAdapter.addAll(datas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mTaskAdapter);
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void requestInit() {

    }

   /* @OnClick({R.id.add_reim_detail_layout})
    public void clickAction(View view) {
        layoutInflater = LayoutInflater.from(this);

       *//* switch (view.getId()) {
            case R.id.add_reim_detail_layout:
                View view1 = layoutInflater.inflate(R.layout.fu_reim_add_item, null, false);
                addViewLayout.addView(view1);
                break;
        }*//*
    }*/

    //设置RecycleView的适配器
    private class TaskAdapter extends ListBaseAdapter<String> {

        public TaskAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.fu_reim_add_item;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, final int position) {
            holder.getView(R.id.add_reim_detail_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addItems(datas);
                }
            });
            TextView deleteText = holder.getView(R.id.tv_delete_action);
            if (position == 0) {
                //第一个item不显示删除选项，第一个永远都存在
                deleteText.setVisibility(View.GONE);
            } else {
                deleteText.setVisibility(View.VISIBLE);
            }
            //删除布局
            deleteText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //删除布局
                    removeItem(position);
                }
            });

            //让“添加报销明细”显示隐藏
            LinearLayout layout = holder.getView(R.id.add_reim_detail_layout);
            if (position == mTaskAdapter.getDataList().size()-1) {
                layout.setVisibility(View.VISIBLE);
            } else {
                layout.setVisibility(View.GONE);
            }
            TextView positionText = holder.getView(R.id.reim_detail_position);
            int num = position + 1;
            positionText.setText("报销明细（"+num+"）");//设置显示的文字
        }

    }
    //模拟添加数据，为了增加layout的数量
    private void addItems(ArrayList<String> list) {
        mTaskAdapter.addAll(list);
        mTaskAdapter.notifyDataSetChanged();
    }

    //删除指定位置的布局，并刷新全部的item
    private void removeItem(int position) {
        mTaskAdapter.remove(position);
        mTaskAdapter.notifyDataSetChanged();
    }
}
