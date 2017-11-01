package com.qianmo.jinxiaocun.fu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.utils.DateUtil;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 请假申请界面
 */
public class LeaveApplyActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.tv_start_time_choose)
    TextView tvStartTimeChoose;

    @BindView(R.id.tv_leave_days)
    TextView tvLeaveDays;
    @BindView(R.id.tv_end_time_choose)
    TextView tvEndTimeChoose;
    private Calendar now;
    private String startTime;
    private String endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "请假申请", "提交");
        setContentView(requestView(R.layout.activity_leave));
        ButterKnife.bind(this);
        initView();
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

    @OnClick({R.id.tv_start_time_choose,R.id.tv_end_time_choose,})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time_choose:
                createDatePickerDialog("startTime");
                break;
            case R.id.tv_end_time_choose:
                createDatePickerDialog("endTime");
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
            betweenDays = DateUtil.daysBetween(startTime,endTime);
            if (betweenDays < 0) {
                betweenDays = 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvLeaveDays.setText(betweenDays+"");

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
