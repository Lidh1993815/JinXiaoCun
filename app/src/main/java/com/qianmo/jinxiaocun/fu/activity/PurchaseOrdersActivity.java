package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.widget.SampleFooter;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 进货单界面
 */
public class PurchaseOrdersActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 已经获取到多少条数据了
     */
    private static int mCurrentCounter = 0;

    @BindView(R.id.addGoodsList)
    LuRecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter = null;//数据适配器
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;//增强版的Adapter

    private ArrayList<String> datas = new ArrayList<>();
    private SampleFooter mSampleFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "进货单", -1);
        setContentView(requestView(R.layout.activity_purchase_orders));
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initEvent() {
        ImageView scanningImg = mSampleFooter.findViewById(R.id.scanning);
        scanningImg.setOnClickListener(this);
        ImageView addImg = mSampleFooter.findViewById(R.id.add_order);
        addImg.setOnClickListener(this);
    }

    //初始化视图
    private void initView() {
        mTaskAdapter = new TaskAdapter(this);//实例化适配器
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(mTaskAdapter);
        mSampleFooter = new SampleFooter(this);
        mLuRecyclerViewAdapter.addFooterView(mSampleFooter);

        setupRecycleView(mRecyclerView, mLuRecyclerViewAdapter);//创建RecycleView
    }

    @Override
    public void requestInit() {

    }

    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //扫描
            case R.id.scanning:
                ToastUtils.MyToast(PurchaseOrdersActivity.this,"扫描");
                break;
            //添加
            case R.id.add_order:
              //  ToastUtils.MyToast(PurchaseOrdersActivity.this,"添加");
                Intent intent = new Intent(this, ChooseProductActivity.class);
                intent.putExtra("type", "purchaseOrder");
                startActivity(intent);
                break;
        }
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

    private void notifyDataSetChanged() {
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    //新增条目
    private void addItems(ArrayList<String> list) {
        mTaskAdapter.addAll(list);
        mCurrentCounter += list.size();
    }
}
