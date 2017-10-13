package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.widget.SampleFooter;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 盘点单界面
 */
public class CheckOrderActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.addGoodsList)
    LuRecyclerView mRecyclerView;
    @BindView(R.id.tv_check_confirm)
    TextView tvCheckConfirm;
    private TaskAdapter mTaskAdapter = null;//数据适配器
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;//增强版的Adapter

    private ArrayList<String> datas = new ArrayList<>();
    private SampleFooter mSampleFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "盘点单", -1);
        setContentView(requestView(R.layout.activity_check_order));
        ButterKnife.bind(this);
        initDatas();
        initView();
        initEvent();
    }

    private void initDatas() {
        datas.add("");
        datas.add("");
    }

    private void initEvent() {
        ImageView scanningImg = mSampleFooter.findViewById(R.id.scanning);
        scanningImg.setOnClickListener(this);
        ImageView addImg = mSampleFooter.findViewById(R.id.add_order);
        addImg.setOnClickListener(this);
    }

    private void initView() {
        mTaskAdapter = new TaskAdapter(this);//实例化适配器
        mTaskAdapter.setDataList(datas);
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(mTaskAdapter);

        mSampleFooter = new SampleFooter(this);
        mLuRecyclerViewAdapter.addFooterView(mSampleFooter);
        setupRecycleView(mRecyclerView, mLuRecyclerViewAdapter);//创建RecycleView
    }

    //设置RecycleView的适配器
    private class TaskAdapter extends ListBaseAdapter<String> {

        public TaskAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.fu_spec_mode_recycler_item;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {

        }

    }

    @Override
    public void requestInit() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //扫描
            case R.id.scanning:
                break;
            //添加
            case R.id.add_order:
                Intent intent = new Intent(this, ChooseProductActivity.class);
                intent.putExtra("type", "check");
                startActivity(intent);
                break;
        }
    }

    @OnClick({R.id.tv_check_confirm})
    public void clickAction(View view) {
        switch (view.getId()) {
            //盘点确认按钮
            case R.id.tv_check_confirm:
                NiceDialog.init()
                        .setLayoutId(R.layout.fu_check_confirm_dialog)     //设置dialog布局文件
                        .setConvertListener(new ViewConvertListener() {     //进行相关View操作的回调
                            @Override
                            public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                                //取消按钮的点击事件
                                    holder.getView(R.id.cancel_action).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog.dismiss();
                                        }
                                    });
                                //确认按钮的点击事件
                                holder.getView(R.id.confirm_action).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                            }
                        })
                        .setDimAmount(0.5f)     //调节灰色背景透明度[0-1]，默认0.5f
                        .setMargin(16)     //dialog左右两边到屏幕边缘的距离（单位：dp），默认0dp
                        .setOutCancel(true)     //点击dialog外是否可取消，默认true
                        .show(getSupportFragmentManager());     //显示dialog
                break;

        }
    }
}
