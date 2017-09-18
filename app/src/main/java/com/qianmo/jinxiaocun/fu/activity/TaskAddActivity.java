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

public class TaskAddActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.tv_select_calendar)
    TextView tvSelectCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "添加任务", "完成");
        setContentView(requestView(R.layout.activity_task_add));
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_select_calendar)
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.tv_select_calendar:
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
        dpd.setMinDate(now);//设置最早可以选择的日期
        //dpd.setCancelColor(R.color.gray);//设置取消按钮字体的颜色
       // dpd.setOkText(R.color.red);//设置确定字体的颜色
        //dpd.setAccentColor(R.color.colorPrimary);//设置日历的主题颜色
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void requestInit() {

    }
    //选择日历之后的回调方法
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date =year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
        tvSelectCalendar.setText(date);
    }
}
