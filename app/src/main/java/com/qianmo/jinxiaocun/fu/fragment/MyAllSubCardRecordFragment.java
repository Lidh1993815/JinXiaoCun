package com.qianmo.jinxiaocun.fu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.ItemDecoration.LuDividerDecoration;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.activity.SubCardRecordDetailActivity;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.decoration.FullyLinearLayoutManager;
import com.qianmo.jinxiaocun.main.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : wizardev
 * e-mail : wizarddev@163.com
 * time   : 2017/09/18
 * desc   : 我下属的考勤界面
 * version: 1.0
 */
public class MyAllSubCardRecordFragment extends BaseFragment {
    /**
     * 服务器端一共多少条数据
     */
    private static final int TOTAL_COUNTER = 34;

    /**
     * 每一页展示多少条数据
     */
    private static final int REQUEST_COUNT = 15;

    /**
     * 已经获取到多少条数据了
     */
    private static int mCurrentCounter = 0;
    @BindView(R.id.rv_my_sub_record)
    RecyclerView mRecyclerView;

    private TaskAdapter mTaskAdapter = null;//数据适配器
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;//增强版的Adapter

    private ArrayList<String> datas = new ArrayList<>();
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = requestView(inflater, R.layout.my_all_sub_card_record_fragment);
        unbinder = ButterKnife.bind(this, view);
        initData();//初始化数据
        initView();
        initEvent();
        return view;
    }

    @Override
    public void requestInit() {

    }

    private void initData() {


        //模拟组装4个数据
        datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            datas.add("");
        }

    }

    private void initEvent() {

    }

    private void initView() {
        mTaskAdapter = new TaskAdapter(getContext());//实例化适配器
        mTaskAdapter.setDataList(datas);

        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(mTaskAdapter);
        LuDividerDecoration divider = new LuDividerDecoration.Builder(getContext(), mLuRecyclerViewAdapter)
                .setHeight(R.dimen.line_height_size)
                //  .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color._eeeeee)
                .setHeaderDivide(false)
                .build();
        //   setupRecycleView(mRecyclerView,mLuRecyclerViewAdapter,divider);//创建RecycleView

        //setLayoutManager放在setAdapter之前配置
        mRecyclerView.setLayoutManager(new FullyLinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(divider);//设置RecycleView的分割线

    }

    //设置RecycleView的适配器
    private class TaskAdapter extends ListBaseAdapter<String> {

        public TaskAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.my_sub_record_item;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(SubCardRecordDetailActivity.class);

                    }
                });
        }

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
