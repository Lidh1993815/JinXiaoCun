package com.qianmo.jinxiaocun.main.base;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.github.jdsjlzx.ItemDecoration.LuDividerDecoration;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.utils.DisplayHelper;
import com.qianmo.jinxiaocun.main.application.MyApplication;
import com.qianmo.jinxiaocun.main.utils.DPUtils;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;
import com.qianmo.jinxiaocun.main.utils.ZhUtils;
import com.qianmo.jinxiaocun.main.view.KProgressHUD;

/**
 * 基础BaseActivity
 *
 * @author Administrator
 */
public abstract class BaseActivity extends FragmentActivity {

    public static final int NET_ERROR = 1001;
    public static final int DATA_EMPTY = 1002;
    public static final int SERVER_EXCEPTION = 1003;

    LinearLayout vLayout;
    protected AlertDialog dialog;
    protected View viewLayout;
    protected MyToolBar toolBar;

    protected MyOccupying occupying;

    protected KProgressHUD hud;
    private ImmersionBar mImmersionBar;

    /**
     * 改变占位图和视图的切换
     *
     * @param type 0代表展示占位图，1代表展示视图
     */
    public void changeSimpleLayout(int type) {
        switch (type) {
            case 0:
                viewLayout.setVisibility(View.GONE);
                if (this.occupying != null) {
                    this.occupying.setVisibility(View.VISIBLE);
                } else {
                    viewLayout.setVisibility(View.VISIBLE);
                }
                break;
            case 1:
                viewLayout.setVisibility(View.VISIBLE);
                if (this.occupying != null) {
                    this.occupying.setVisibility(View.GONE);
                }
                break;
        }
    }

    public void changePlaceHolderLayoutByType(int type, int imageResourceId, String tips) {
        changePlaceHolderLayoutByType(type, imageResourceId, tips, "");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 改变占位图
     *
     * @param type 占位图类型 NET_ERROR，DATA_EMPTY
     */
    public void changePlaceHolderLayoutByType(int type, int imageResourceId, String tips, String button) {
        if (occupying != null) {
            changeSimpleLayout(0);
            switch (type) {
                case NET_ERROR:
                    occupying.commit.setVisibility(View.VISIBLE);
                    occupying.img.setImageResource(imageResourceId);
                    occupying.text.setText("网络不给力哦~");
                    occupying.commit.setText("重新连接");
                    occupying.SetCommitListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            requestInit();
                        }
                    });
                    //没有数据占位图
                case DATA_EMPTY:
                    if (imageResourceId != -1)
                        occupying.img.setImageResource(imageResourceId);
                    if (tips != null && tips.length() > 0)
                        occupying.text.setText(tips);
                    if (button != null && button.length() > 0) {
                        occupying.commit.setVisibility(View.VISIBLE);
                        occupying.commit.setText(button);
                        occupying.commit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestInit();
                            }
                        });
                    }
                    break;
                case SERVER_EXCEPTION:
                    if (imageResourceId != -1)
                        occupying.img.setImageResource(imageResourceId);
                    if (tips != null && tips.length() > 0)
                        occupying.text.setText(tips);
                    occupying.commit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestInit();
                        }
                    });
                    break;
            }
        }
    }


    public void requestData() {
        boolean networkConnected = ZhUtils.isNetworkConnected(this);
        if (!networkConnected)
            ToastUtils.MyToast(this, R.string.net_error);
    }

    /**
     * @param layoutId
     * @param type     1代表无占位图，0代表有展位图
     * @return
     */
    public LinearLayout requestView(int layoutId, int type) {
        return requestView(layoutId, toolBar, type);
    }

    public LinearLayout requestView(int layoutId) {
        return requestView(layoutId, toolBar, 0);
    }

    public LinearLayout requestView(int layoutId, MyToolBar toolBar, int type) {
        //将当前activity添加到activity集合中
        MyApplication.addActivity(this);
        //创建占位图
        occupying = new MyOccupying(getBaseContext());
        //占位图按钮点击
        occupying.commit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                requestInit();
            }
        });
        if (vLayout == null) {
            vLayout = new LinearLayout(getBaseContext());
            vLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));//全部填充窗口
            vLayout.setOrientation(LinearLayout.VERTICAL);
        }
        vLayout.removeAllViews();
        viewLayout = LayoutInflater.from(getBaseContext()).inflate(layoutId,
                vLayout, false);
        if (toolBar != null) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayHelper.getStatusBarHeight(this)));
            linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            vLayout.addView(toolBar, 0);
            LinearLayout line = new LinearLayout(this);
            line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayHelper.dp2px(this, 1)));
            line.setBackgroundResource(R.color.line);
            vLayout.addView(line);//标题栏下面的横线
            vLayout.addView(linearLayout, 0);//添加状态栏
            toolBar.setLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        if (type == 0) {
            if (occupying != null) {
                vLayout.addView(this.occupying);
            }
        } else if (type == 1) {

        }
        vLayout.addView(viewLayout);//我们自己设置的视图
//        if (ZhUtils.isNetworkConnected(getBaseContext())) {
        changeSimpleLayout(1);
//        } else {
//            changeSimpleLayout(0);
//        }
        //创建加载
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE);//改变加载进度条的样式
        return vLayout;
    }

    public void showBlackDialog() {
        if (hud != null) {
            hud.show();
        }
    }

    public void dismissBlackDialog() {
        if (hud != null && hud.isShowing()) {
            hud.dismiss();
        }
    }

    //XML布局中有SwipeRefreshLayout，让SwipeRefreshLayout刷新
    public void showSwipeRefresh(final SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    protected void setupSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        //设置刷新时动画的颜色，可以设置4个
        if (swipeRefreshLayout != null) {

            //设置刷新突变距离顶部的距离
            swipeRefreshLayout.setProgressViewOffset(false, 0, DPUtils.dip2px(this, 48));
            //设置刷新圈圈的颜色
            swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        }
    }

    /**
     * 刷新多少秒后关闭
     *
     * @param swipeRefreshLayout
     * @param time
     */
    public void dismissSwipeRefresh(final SwipeRefreshLayout swipeRefreshLayout, long time) {
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, time);
    }

    public void dismissSwipeRefresh(final SwipeRefreshLayout swipeRefreshLayout) {
        setupSwipeRefresh(swipeRefreshLayout);
        if (swipeRefreshLayout != null)
            dismissSwipeRefresh(swipeRefreshLayout, 1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();

    }

    private void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recycle();
        mImmersionBar.destroy();
    }


    private void recycle() {
        recycleBaseDialog(dialog);
        toolBar = null;
        occupying = null;
    }


    public void recycleBaseDialog(AlertDialog dialog1) {
        if (dialog1 != null) {
            if (dialog1.isShowing()) {
                dialog1.dismiss();
            }
            dialog1 = null;
        }

    }

    public abstract void requestInit();

    /**
     * 服务器异常
     */
    public void fuwuqi() {
        occupying.commit.setVisibility(View.VISIBLE);
//        occupying.img.setImageResource(R.drawable.zhang_logo);
        occupying.text.setText("服务器异常哦~");
        changeSimpleLayout(0);
        occupying.commit.setText("重新连接");
        occupying.SetCommitListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                requestInit();
            }
        });
    }

    /**
     * 网络异常
     */
    public void wangluochaoshi() {
        occupying.commit.setVisibility(View.VISIBLE);
        occupying.img.setImageResource(R.drawable.icon_server_error);
        occupying.text.setText("网络不给力哦~");
        occupying.commit.setText("重新连接");
        changeSimpleLayout(0);
        occupying.SetCommitListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                requestInit();
            }
        });
        occupying.commit.setText("重新连接");
    }

    protected void closeRefresh(final SwipeRefreshLayout swipeRefreshLayout, long time) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, time);
    }

    protected void startActivity(Class<?> cls, boolean isFinish) {
        startActivity(new Intent(this, cls));
        if (isFinish) {
            this.finish();
        }
    }

    protected void startActivity(Class<?> cls) {
        startActivity(cls, true);
    }

    //设置RecycleView
    protected void setupRecycleView(LuRecyclerView recyclerView, LuRecyclerViewAdapter luRecyclerViewAdapter) {
        LuDividerDecoration divider = new LuDividerDecoration.Builder(this, luRecyclerViewAdapter)
                .setHeight(R.dimen._6dp)
                //  .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color._eeeeee)
                .build();
        this.setupRecycleView(recyclerView,luRecyclerViewAdapter,divider);
    }

    protected void setupRecycleView(LuRecyclerView recyclerView, LuRecyclerViewAdapter luRecyclerViewAdapter, RecyclerView.ItemDecoration divider) {
        //setLayoutManager放在setAdapter之前配置
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(luRecyclerViewAdapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(divider);//设置RecycleView的分割线
        recyclerView.setLoadMoreEnabled(true);
        //如果使用了自动加载更多，就不要添加FooterView了
        //mLuRecyclerViewAdapter.addFooterView(new SampleFooter(this));
        //设置底部加载颜色
        recyclerView.setFooterViewColor(R.color.colorPrimary, R.color.gray, R.color._eeeeee);
        //设置底部加载文字提示
        recyclerView.setFooterViewHint("拼命加载中", "别扯了，到底了！", "网络不给力啊，点击再试一次吧");

    }
}
