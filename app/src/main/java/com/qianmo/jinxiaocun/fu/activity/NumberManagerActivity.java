package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.bean.NumberBean;
import com.qianmo.jinxiaocun.fu.utils.JsonUitl;
import com.qianmo.jinxiaocun.fu.utils.SPUtil;
import com.qianmo.jinxiaocun.fu.utils.StringUtil;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.okhttp.OkhttpUtils;
import com.qianmo.jinxiaocun.main.okhttp.listener.OnActionListener;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 会员管理界面
 */
public class NumberManagerActivity extends BaseActivity implements OnActionListener {

    @BindView(R.id.recyclerView)
    LuRecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter;
    private NumberAdapter mNumberAdapter;
    private static final String TAG = "NumberManagerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "会员管理", R.mipmap.add);
        setContentView(requestView(R.layout.activity_number_manager));
        ButterKnife.bind(this);
        initView();
        mSwipeRefreshLayout.setRefreshing(true);
        requestData();
    }

    private void initView() {
        mNumberAdapter = new NumberAdapter(this);
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(mNumberAdapter);
        setupRecycleView(mRecyclerView, mLuRecyclerViewAdapter, R.dimen._1dp);
    }

    @Override
    public void requestInit() {
        requestData();
    }

    @Override
    public void requestData() {
        super.requestData();
        OkhttpParam okhttpParam = new OkhttpParam();
        okhttpParam.putString("staffId", SPUtil.getInstance().getStaffId());
        OkhttpUtils.sendRequest(1001, OkhttpUtils.GET, ApiConfig.STAFF_MEMBER, okhttpParam, this);
    }

    @Override
    protected void rightImageAction() {
        startActivity(AddNumberActivity.class, false);
    }

    @Override
    public void onActionSuccess(int actionId, String ret) {
        dismissRefreshLayout();
        Log.d(TAG, "onActionSuccess: " + ret);
        if (!TextUtils.isEmpty(ret)) {
            NumberBean numberBean = (NumberBean) JsonUitl.stringToObject(ret, NumberBean.class);
            List<NumberBean.DataBean> data = numberBean.getData();
            mNumberAdapter.setDataList(data);
            mLuRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    private void dismissRefreshLayout() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onActionServerFailed(int actionId, int httpStatus) {
        dismissRefreshLayout();
    }

    @Override
    public void onActionException(int actionId, String exception) {
        dismissRefreshLayout();
    }

    class NumberAdapter extends ListBaseAdapter<NumberBean.DataBean> {


        public NumberAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_number_info;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {

            NumberBean.DataBean dataBean = mDataList.get(position);

            TextView mTvNumberManagerNumberName = holder.getView(R.id.tv_numberManager_numberName);
            TextView mTvNumberManagerNumberIntegral = holder.getView(R.id.tv_numberManager_numberIntegral);
            TextView mTvNumberManagerNumberMobile = holder.getView(R.id.tv_numberManager_numberMobile);

            mTvNumberManagerNumberName.setText(dataBean.getMemberName());
            mTvNumberManagerNumberIntegral.setText("积分："+dataBean.getIntegral());
            mTvNumberManagerNumberMobile.setText("联系方式："+dataBean.getMemberPhone());
        }
    }
}
