package com.qianmo.jinxiaocun.main.base;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.main.utils.DPUtils;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;
import com.qianmo.jinxiaocun.main.utils.ZhUtils;
import com.qianmo.jinxiaocun.main.view.KProgressHUD;


public abstract class BaseFragment extends Fragment {

    public static final int NET_ERROR = 1001;
    public static final int DATA_EMPTY = 1002;
    public static final int SERVER_EXCEPTION = 1003;

    protected AlertDialog dialog;
    protected MyOccupying occupying;
    protected MyToolBar toolBar;
    protected View viewLayout;
    private LinearLayout vLayout;
    protected KProgressHUD hud;

    public void recycleBaseDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        }
    }

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

    /**
     * 改变占位图
     *
     * @param type 占位图类型 NET_ERROR，DATA_EMPTY
     */
    public void changePlaceHolderLayoutByType(int type, int imageResourceId, String tips) {
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
                        occupying.commit.setVisibility(View.GONE);
                        changeSimpleLayout(1);
                    }
                });
                //没有数据占位图
            case DATA_EMPTY:
                if (imageResourceId != -1)
                    occupying.img.setImageResource(imageResourceId);
                if (tips != null && tips.length() > 0)
                    occupying.text.setText(tips);
                break;
            //服务器异常的错误
            case SERVER_EXCEPTION:
                if (imageResourceId != -1)
                    occupying.img.setImageResource(imageResourceId);
                if (tips != null && tips.length() > 0)
                    occupying.text.setText(tips);
                occupying.commit.setVisibility(View.VISIBLE);
                occupying.commit.setText("重新连接");
                occupying.commit.setBackgroundResource(R.drawable.zhang_selector_video_getto_shopcart);
                occupying.commit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestInit();
                        occupying.commit.setVisibility(View.GONE);
                        changeSimpleLayout(1);
                    }
                });
                break;
        }
    }

    public void changePlaceHolderLayoutNetError(int type) {
        changePlaceHolderLayoutByType(NET_ERROR, R.drawable.icon_server_error, "");
    }

    public void requestData() {
        boolean networkConnected = ZhUtils.isNetworkConnected(getActivity());
        if (!networkConnected)
            ToastUtils.MyToast(getActivity(), R.string.net_error);
    }

    @Override
    public void onDestroyView() {
        recycleBaseDialog();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        recycleBaseDialog();
        super.onDestroy();
    }

    /**
     * @param inflater
     * @param layoutId
     * @param type     1代表无需占位图，0代表需要占位图
     * @return
     */
    public LinearLayout requestView(LayoutInflater inflater, int layoutId, int type) {
        occupying = new MyOccupying(getActivity());
        occupying.commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestInit();
            }
        });
        if (vLayout == null) {
            vLayout = new LinearLayout(getActivity().getBaseContext());
            vLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
            vLayout.setOrientation(LinearLayout.VERTICAL);
        }
        vLayout.removeAllViews();
        viewLayout = LayoutInflater.from(getActivity().getBaseContext())
                .inflate(layoutId, vLayout, false);
        if (toolBar != null) {
            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ZhUtils.getStatusBarHeight(getActivity())));
            linearLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
            vLayout.addView(toolBar, 0);
            LinearLayout line = new LinearLayout(getActivity());
            line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ZhUtils.DimenTrans.dip2px(getActivity(), 1)));
            line.setBackgroundResource(R.color.line);
            vLayout.addView(line);
            vLayout.addView(linearLayout, 0);
        }
        if (occupying != null) {
            vLayout.addView(occupying);
        }
        vLayout.addView(viewLayout);
        changeSimpleLayout(1);

        //创建加载
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
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
    //设置SwipeRefreshLayout
    protected void setupSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        //设置刷新时动画的颜色，可以设置4个
        if (swipeRefreshLayout != null) {

            //设置刷新突变距离顶部的距离
            swipeRefreshLayout.setProgressViewOffset(false, 0, DPUtils.dip2px(getContext(),48));
            //设置刷新圈圈的颜色
            swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        }
    }
    public void showSwipeRefresh(final SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
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
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing())
            dismissSwipeRefresh(swipeRefreshLayout, 1000);
    }

    public LinearLayout requestView(LayoutInflater inflater, int layoutId) {
        return requestView(inflater, layoutId, 0);
    }

    protected void startActivity(Class<?> cls, boolean isFinish) {
        startActivity(new Intent(getContext(),cls));
        if (isFinish) {
            getActivity().finish();
        }
    }
    protected void startActivity(Class<?> cls) {
        startActivity(cls,false);
    }

    public abstract void requestInit();

}
