package com.qianmo.jinxiaocun.fu.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.bean.ResponseBean;
import com.qianmo.jinxiaocun.fu.http.LoadingCallback;
import com.qianmo.jinxiaocun.fu.http.OkHttpHelper;
import com.qianmo.jinxiaocun.fu.utils.DateUtil;
import com.qianmo.jinxiaocun.fu.utils.StringUtil;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 填写日报界面
 */
public class NewspaperActivity extends BaseActivity {


    @BindView(R.id.tv_start_time_choose)
    TextView mTvStartTimeChoose;
    @BindView(R.id.et_newspaper_content)
    EditText mEtNewspaperContent;
    @BindView(R.id.tv_newspaper_reportPeople)
    TextView mTvNewspaperReportPeople;
    private int mCarryOutTaskId;
    private String mNewspaperContent;
    private static final String TAG = "NewspaperActivity";
    private String mStaffName;
    private String mCommitReportTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCarryOutTaskId = getIntent().getIntExtra("carryOutTaskId", 0);
        mStaffName = getIntent().getStringExtra("staffName");
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "填写日报", "完成");
        setContentView(requestView(R.layout.activity_newspaper));
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvNewspaperReportPeople.setText(StringUtil.getString(mStaffName));
        mCommitReportTime = DateUtil.getDate(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss");
        mTvStartTimeChoose.setText(mCommitReportTime);
    }


    @Override
    protected void rightTextAction() {
        if (checkParam()) {
            commitData();//提交数据
        }
    }

    private void commitData() {
        /**
         * "cTime":"2017-11-18 12:20:00",
         "carryOutId":"13",
         "dailyContent":"进入任务完成的非常顺利 一铲子下去就挖出了一个夜光杯"
         */
        OkhttpParam okhttpParam = new OkhttpParam();
        okhttpParam.putString("cTime",mCommitReportTime);
        okhttpParam.putString("carryOutId",mCarryOutTaskId);
        okhttpParam.putString("dailyContent",mNewspaperContent);
        OkHttpHelper.getInstance().post(ApiConfig.INSER_DAILY, okhttpParam, new LoadingCallback<ResponseBean>(this) {
            @Override
            public void onSuccess(Response response, ResponseBean responseBean) {
                if (responseBean.getState().equals("00000")) {
                    ToastUtils.MyToast(NewspaperActivity.this, "提交成功");
                    NewspaperActivity.this.finish();
                } else {
                    ToastUtils.MyToast(NewspaperActivity.this, responseBean.getMsg());
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                Log.d(TAG, "onError: "+code);
            }
        });
    }

    /**
     * 判断参数是否合理
     *
     * @return
     */
    private boolean checkParam() {
        mNewspaperContent = mEtNewspaperContent.getText().toString().trim();
        if (TextUtils.isEmpty(mNewspaperContent)) {
            ToastUtils.MyToast(this,"请输入日报的内容！");
            return false;
        }
        return true;
    }

    @Override
    public void requestInit() {

    }

   /* @OnClick(R.id.tv_start_time_choose)
    public void clickAction(View view) {
//        DatePickerDialog d
    }*/

}
