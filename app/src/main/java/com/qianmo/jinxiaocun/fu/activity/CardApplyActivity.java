package com.qianmo.jinxiaocun.fu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 补打卡申请
 */
public class CardApplyActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.tv_card_time)
    TextView tvCardTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "补打卡申请", "完成");

        setContentView(requestView(R.layout.activity_card_apply));
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_card_time)
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.tv_card_time:
                createDatePickerDialog();
                break;
        }
    }
    //日历选择对话框
    private void createDatePickerDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void requestInit() {

    }
    //选择日历之后的回调方法
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date =year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
        tvCardTime.setText(date);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
