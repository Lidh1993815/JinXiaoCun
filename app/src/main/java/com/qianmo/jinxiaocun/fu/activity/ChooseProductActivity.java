package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.ItemDecoration.LuDividerDecoration;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.widget.SampleFooter;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择商品界面
 */
public class ChooseProductActivity extends BaseActivity {

    @BindView(R.id.brand_list)
    LuRecyclerView brandList;//品牌列表
    @BindView(R.id.product_list)
    LuRecyclerView productList;//商品列表
    private LeftAdapter mLeftAdapter = null;//数据适配器
    private RightAdapter mRightAdapter = null;//数据适配器
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;//增强版的Adapter

    private ArrayList<String> leftDatas = new ArrayList<>();
    private ArrayList<String> rightDatas = new ArrayList<>();
    private SampleFooter mSampleFooter;
    private Map<TextView, Integer> textMap = new HashMap<>();
    private boolean isClick = false;
    private static final String TAG = "ChooseProductActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "选择商品", -1);
        setContentView(requestView(R.layout.activity_choose_product));
        ButterKnife.bind(this);
        initData();
        initView();
    }

    //初始化数据
    private void initData() {
        leftDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            leftDatas.add("");
        }

        rightDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            rightDatas.add("");
        }
    }

    //初始化视图
    private void initView() {
        mLeftAdapter = new LeftAdapter(this);//实例化适配器
        setupRecycleViewList(brandList, mLeftAdapter, leftDatas);//设置列表
        mRightAdapter = new RightAdapter(this);//实例化适配器
        setupRecycleViewList(productList, mRightAdapter, rightDatas);//设置列表
    }

    private void setupRecycleViewList(LuRecyclerView recyclerView, ListBaseAdapter adapter, Collection list) {
        adapter.setDataList(list);
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(adapter);
        LuDividerDecoration divider = new LuDividerDecoration.Builder(this, mLuRecyclerViewAdapter)
                .setHeight(R.dimen.line_height_size)
                .setColorResource(R.color._eeeeee)
                .build();
        setupRecycleView(recyclerView, mLuRecyclerViewAdapter, divider);//创建RecycleView
        recyclerView.setLoadMoreEnabled(false);
    }


    @Override
    public void requestInit() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //设置品牌列表的适配器
    private class LeftAdapter extends ListBaseAdapter<String> {

        public LeftAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.fu_brand_recycle_item;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, final int position) {
            final TextView brand = holder.getView(R.id.tv_brand);
            brand.setText("品牌" + position);
            if (!isClick) {
                if (position == 0) {
                    brand.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            }
            textMap.put(brand, position);
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isClick = true;
                    for (TextView textView : textMap.keySet()) {
                        int i = textMap.get(textView);
                        if (i == position) {
                            Log.i(TAG, "i= " + i + " and position= " + position);
                            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                        } else {
                            Log.i(TAG, "onClick: ");
                            textView.setTextColor(getResources().getColor(R.color.black));
                        }
                    }
                    /*for (Map.Entry<TextView, Integer> entry : textMap.entrySet()) {
                        //System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());

                        if (entry.getValue() == position) {
                            brand.setTextColor(getResources().getColor(R.color.colorPrimary));
                            Log.i(TAG, "key= " + entry.getKey() + " and value= " + entry.getValue());
                        } else {
                            brand.setTextColor(getResources().getColor(R.color.black));
                        }
                    }*/
                }
            });
        }

    }

    //设置商品列表的适配器
    private class RightAdapter extends ListBaseAdapter<String> {

        public RightAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.fu_product_recycle_item;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {

        }

    }

    private void notifyDataSetChanged() {
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    //新增条目
   /* private void addItems(ArrayList<String> list) {
        mTaskAdapter.addAll(list);
        mCurrentCounter += list.size();
    }*/
}
