package com.qianmo.jinxiaocun.fu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.bean.ResponseBean;
import com.qianmo.jinxiaocun.fu.utils.JsonUitl;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.okhttp.OkhttpUtils;
import com.qianmo.jinxiaocun.main.okhttp.listener.OnActionListener;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 执行意见
 */
public class ExecuteOpinionActivity extends BaseActivity implements OnActionListener {

    @BindView(R.id.tv_advice_content)
    EditText mTvAdviceContent;
    @BindView(R.id.tv_executeOpinion_agree)
    TextView mTvExecuteOpinionAgree;
    @BindView(R.id.tv_executeOpinion_disagree)
    TextView mTvExecuteOpinionDisagree;
    private static final String TAG = "ExecuteOpinionActivity";
    private int mAPplyClockId;
    private String mAdviceContent;
    private int mApplyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mAPplyClockId = intent.getIntExtra("aPplyClockId", 0);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "执行意见", -1);
        setContentView(requestView(R.layout.activity_execute_opinion));
        ButterKnife.bind(this);
    }

    @Override
    public void requestInit() {

    }

    @OnClick({R.id.tv_executeOpinion_agree, R.id.tv_executeOpinion_disagree})
    public void clickAction(View view) {
        mAdviceContent = mTvAdviceContent.getText().toString().trim();

        switch (view.getId()) {

            case R.id.tv_executeOpinion_agree:
                mApplyState = 2;
                //同意
                if (checkParam(mAdviceContent)) {
                    requestData();
                }
                break;
            case R.id.tv_executeOpinion_disagree:
                mApplyState = 3;
                //不同意
                if (checkParam(mAdviceContent)) {
                    requestData();
                }
                break;
        }
    }

    private boolean checkParam(String adviceContent) {
        if (TextUtils.isEmpty(adviceContent)) {
            ToastUtils.MyToast(this,"请输入审核意见！");
            return false;
        }
        return true;
    }

    @Override
    public void requestData() {
        super.requestData();
        /**
         *  "aPplyClockId":"3",
         "aPplyStatus":2,
         "aAuditor":"16",
         "aPplyOpinion":"干的不错噢",
         "applyState":"2"
         */
        OkhttpParam okhttpParam = new OkhttpParam();
        okhttpParam.putString("aPplyClockId", mAPplyClockId + "");
        okhttpParam.putString("aPplyStatus", 2 + "");
        okhttpParam.putString("aAuditor",  "");//换审核人时传(其他时候不用传)
        okhttpParam.putString("aPplyOpinion",  mAdviceContent);
        okhttpParam.putString("applyState",  mApplyState+"");
        OkhttpUtils.sendRequest(1001, 1, ApiConfig.UPADTE_APPLYCLOCK_DETAILS, okhttpParam, this);
    }

    @Override
    public void onActionSuccess(int actionId, String ret) {
        switch (actionId) {
            case 1001:
                Log.i(TAG, "onActionSuccess: " + ret);
                if (!TextUtils.isEmpty(ret)) {
                    ResponseBean responseBean = (ResponseBean) JsonUitl.stringToObject(ret, ResponseBean.class);
                    if (responseBean != null) {
                        String state = responseBean.getState();
                        if (state.equals("00000")) {
                            ToastUtils.MyToast(ExecuteOpinionActivity.this,"申请成功！");
                            finish();
                        } else {
                            ToastUtils.MyToast(this, responseBean.getMsg());
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onActionServerFailed(int actionId, int httpStatus) {

    }

    @Override
    public void onActionException(int actionId, String exception) {

    }
}
