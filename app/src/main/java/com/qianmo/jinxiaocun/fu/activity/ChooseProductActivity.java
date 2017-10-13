package com.qianmo.jinxiaocun.fu.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jdsjlzx.ItemDecoration.LuDividerDecoration;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择商品界面
 */
public class ChooseProductActivity extends BaseActivity {

    @BindView(R.id.brand_list)
    LuRecyclerView brandList;//品牌列表
    @BindView(R.id.product_list)
    LuRecyclerView productList;//商品列表
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.et_search_content)
    EditText etSearchContent;
    @BindView(R.id.edit_parent_layout)
    RelativeLayout editParentLayout;


    private LeftAdapter mLeftAdapter = null;//数据适配器
    private RightAdapter mRightAdapter = null;//数据适配器
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;//增强版的Adapter

    private ArrayList<String> leftDatas = new ArrayList<>();
    private ArrayList<String> rightDatas = new ArrayList<>();
    private Map<TextView, Integer> textMap = new HashMap<>();
    private boolean isClick = false;
    private boolean isStartAnimate = false;
    private static final String TAG = "ChooseProductActivity";
    private int curValue;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "选择商品", -1);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        setContentView(requestView(R.layout.activity_choose_product));
        ButterKnife.bind(this);
        initData();
        initView();
        initEvent();
    }

    private void initEvent() {

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
                            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                        } else {
                            textView.setTextColor(getResources().getColor(R.color.black));
                        }
                    }
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
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (type != null && type.equals("check")) {
                        //由盘点单进入，点击弹出对话框

                    }
                }
            });

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

    @OnClick({R.id.tv_search})
    public void clickAction(View view) {

        switch (view.getId()) {
            case R.id.tv_search:

                if (!isStartAnimate) {
                    doAnimation();
                }
                break;
        }
    }

    private void doAnimation() {
        final ValueAnimator vm = ValueAnimator.ofInt((int) tvSearch.getX(), (int) editParentLayout.getX());
        vm.setDuration(200);
        vm.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                curValue = (int) valueAnimator.getAnimatedValue();
                Log.i(TAG, "top: " + tvSearch.getTop());
                tvSearch.layout(curValue, 0, curValue + tvSearch.getWidth(), tvSearch.getHeight());
            }
        });
        vm.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isStartAnimate = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                vm.removeAllUpdateListeners();
                tvSearch.setVisibility(View.GONE);
                etSearchContent.setVisibility(View.VISIBLE);

                focusAndShowSoftInput();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        vm.start();

    }

    //弹出软件盘
    private void focusAndShowSoftInput() {
        etSearchContent.setFocusable(true);
        etSearchContent.setFocusableInTouchMode(true);
        etSearchContent.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) etSearchContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(etSearchContent, 0);
    }
}
