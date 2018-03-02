package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.bean.CostTypeBean;
import com.qianmo.jinxiaocun.fu.http.LoadingCallback;
import com.qianmo.jinxiaocun.fu.http.OkHttpHelper;
import com.qianmo.jinxiaocun.fu.utils.StringUtil;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

public class ChooseCostProjectActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    LuRecyclerView mRecyclerView;
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter;
    private CostTypeAdapter mAdapter;
    private static final String TAG = "ChooseCostProjectActivi";
    private int mReimburseId;
    private String mTypename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "费用项目", "确认");
        setContentView(requestView(R.layout.activity_choose_cost_project));
        ButterKnife.bind(this);
        initView();
        requestData();
    }

    private void initView() {
        mAdapter = new CostTypeAdapter(this);
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(mAdapter);
        setupRecycleView(mRecyclerView, mLuRecyclerViewAdapter, R.dimen._1dp);
        mLuRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CostTypeBean.DataBean dataBean = mAdapter.getDataList().get(position);
                mReimburseId = dataBean.getReimburseId();
                mTypename = dataBean.getTypename();
                for (int i = 0; i < mAdapter.getDataList().size(); i++) {
                    if (mAdapter.getDataList().get(i).getReimburseId() == mReimburseId) {
                        mAdapter.getDataList().get(i).setSelect(true);
                    } else {
                        mAdapter.getDataList().get(i).setSelect(false);

                    }
                }
                mLuRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void rightTextAction() {
        Intent intent = new Intent();
        intent.putExtra("moneyFlowsType", mReimburseId);
        intent.putExtra("typeName", mTypename);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void requestInit() {
        requestData();
    }

    @Override
    public void requestData() {
        super.requestData();
        OkhttpParam okhttpParam = new OkhttpParam();
        okhttpParam.putString("", "");
        OkHttpHelper.getInstance().post(ApiConfig.APPLY_REIMBURSE_TYPE, okhttpParam, new LoadingCallback<CostTypeBean>(this) {
            @Override
            public void onSuccess(Response response, CostTypeBean costTypeBean) {
                String state = costTypeBean.getState();
                if ("00000".equals(state)) {
                    List<CostTypeBean.DataBean> data = costTypeBean.getData();
                    mAdapter.setDataList(data);
                    mLuRecyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                Log.e("wizardev", "onError: "+code );
            }
        });
    }

    class CostTypeAdapter extends ListBaseAdapter<CostTypeBean.DataBean> {
        public CostTypeAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_cost_type;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {

            CostTypeBean.DataBean dataBean = mDataList.get(position);

            TextView mTvProjectName = holder.getView(R.id.tv_projectName);
            AppCompatCheckBox mCbChooseStatus = holder.getView(R.id.cb_chooseStatus);
            mCbChooseStatus.setChecked(dataBean.isSelect());

            mTvProjectName.setText(StringUtil.getString(dataBean.getTypename()));

        }
    }

}
