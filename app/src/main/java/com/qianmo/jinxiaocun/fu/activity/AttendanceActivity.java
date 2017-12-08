package com.qianmo.jinxiaocun.fu.activity;

import android.os.Bundle;
import android.view.View;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.widget.AttendanceButton;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 考勤界面
 */
public class AttendanceActivity extends BaseActivity {

    @BindView(R.id.attendance_button)
    AttendanceButton attendanceButton;
    private boolean isWork = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "考勤", "记录");

        setContentView(requestView(R.layout.activity_attendance));
        ButterKnife.bind(this);
    }

    @OnClick({R.id.attendance_button})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.attendance_button:
                isWork = !isWork;
                if (isWork) {
                    attendanceButton.setContentText("下班打卡");
                } else {
                    attendanceButton.setContentText("上班打卡");
                }
                break;
        }
    }
    //toolbar右侧的点击事件
    @Override
    protected void rightTextAction() {
        startActivity(CardRecordActivity.class,false);
    }

    @Override
    public void requestInit() {

    }
}
