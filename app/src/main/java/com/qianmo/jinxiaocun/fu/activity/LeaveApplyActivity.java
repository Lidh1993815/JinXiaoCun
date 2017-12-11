package com.qianmo.jinxiaocun.fu.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.bean.ApplyClockDetailBean;
import com.qianmo.jinxiaocun.fu.bean.ResponseBean;
import com.qianmo.jinxiaocun.fu.utils.DateUtil;
import com.qianmo.jinxiaocun.fu.utils.JsonUitl;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.okhttp.OkhttpUtils;
import com.qianmo.jinxiaocun.main.okhttp.listener.OnActionListener;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 请假申请界面
 */
public class LeaveApplyActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, OnActionListener {

    @BindView(R.id.tv_start_time_choose)
    TextView tvStartTimeChoose;

    @BindView(R.id.tv_leave_days)
    TextView tvLeaveDays;
    @BindView(R.id.tv_end_time_choose)
    TextView tvEndTimeChoose;
    @BindView(R.id.rl_leave_typeLayout)
    RelativeLayout mRlLeaveTypeLayout;
    private Calendar now;
    private String startTime;
    private String endTime;
    private static final String TAG = "LeaveApplyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "请假申请", "提交");
        setContentView(requestView(R.layout.activity_leave));
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void rightTextAction() {
        super.rightTextAction();
        requestData();//提交数据
    }

    @Override
    public void requestData() {
        super.requestData();
        OkhttpParam okhttpParam = new OkhttpParam();
        okhttpParam.putString("startTime","2017-12-01 00:00:00");
        okhttpParam.putString("endTime","2017-12-01 00:00:00");
        okhttpParam.putString("applyDays","1");
        okhttpParam.putString("leaveType","1");
        okhttpParam.putString("leaveContent", "昨日下班比较冲忙忘记打卡");
        okhttpParam.putString("applyClockDetails", JsonUitl.objectToString(new ApplyClockDetailBean(2,1,3)));
        OkhttpUtils.sendRequest(1001, 1, "http://192.168.0.189:8080/app/apply_fill_card/add_applyleave", okhttpParam, this);

    }

    private void initView() {
        //初始化view
        now = Calendar.getInstance();
        String date = DateUtil.getDate(System.currentTimeMillis(), "yyyy-MM-dd");
        startTime = date;
        endTime = date;
        tvEndTimeChoose.setText(date);
        tvStartTimeChoose.setText(date);
        tvLeaveDays.setText("1");

    }



    @OnClick({R.id.tv_start_time_choose, R.id.tv_end_time_choose,R.id.rl_leave_typeLayout})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time_choose:
                createDatePickerDialog("startTime");
                break;
            case R.id.tv_end_time_choose:
                createDatePickerDialog("endTime");
                break;
            case R.id.rl_leave_typeLayout:
               //选择请假类型
                // TODO: 17-12-11
                break;
        }
    }

    //日历选择对话框
    private void createDatePickerDialog(String tag) {

        DatePickerDialog dpd = DatePickerDialog.newInstance(this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setMinDate(now);
        dpd.show(getFragmentManager(), tag);
    }

    @Override
    public void requestInit() {

    }

    //选择日历之后的回调方法
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String tag = view.getTag();
        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        //根据tag的不同，对不同的textView设置文字
        if (tag.equals("startTime")) {
            tvStartTimeChoose.setText(date);
            startTime = date;
        } else {
            tvEndTimeChoose.setText(date);
            endTime = date;
        }

        int betweenDays = 0;
        try {
            betweenDays = DateUtil.daysBetween(startTime, endTime);
            if (betweenDays < 0) {
                betweenDays = 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvLeaveDays.setText(betweenDays + "");

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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
                            ToastUtils.MyToast(LeaveApplyActivity.this,"申请成功！");
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
        Log.i(TAG, "onActionServerFailed: "+httpStatus);
    }

    @Override
    public void onActionException(int actionId, String exception) {
        Log.i(TAG, "onActionException: "+exception);

    }
}
