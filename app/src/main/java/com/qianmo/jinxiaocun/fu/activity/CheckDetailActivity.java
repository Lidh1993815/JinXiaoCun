package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.os.Bundle;

import com.github.jdsjlzx.ItemDecoration.LuDividerDecoration;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 库存盘点界面
 */
public class CheckDetailActivity extends BaseActivity {

    @BindView(R.id.rv_check_detail)
    LuRecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter = null;//数据适配器
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;//增强版的Adapter

    private ArrayList<String> datas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "库存盘点", -1);
        setContentView(requestView(R.layout.activity_check_detail));
        ButterKnife.bind(this);
        initDatas();
        initView();
    }

    private void initDatas() {
        datas.add("");
        datas.add("");
        datas.add("");
    }

    private void initView() {

        mTaskAdapter = new TaskAdapter(this);//实例化适配器
        mTaskAdapter.setDataList(datas);
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(mTaskAdapter);
        LuDividerDecoration divider = new LuDividerDecoration.Builder(this, mLuRecyclerViewAdapter)
                .setHeight(R.dimen.line_height_size)
                .setHeaderDivide(true)
                .setColorResource(R.color._eeeeee)
                .build();
        setupRecycleView(mRecyclerView,mLuRecyclerViewAdapter,divider);//创建RecycleView
    }

    @Override
    public void requestInit() {

    }
    //设置RecycleView的适配器
    private class TaskAdapter extends ListBaseAdapter<String> {

        public TaskAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.fu_check_detail_recycler_item;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {

        }

    }

}
