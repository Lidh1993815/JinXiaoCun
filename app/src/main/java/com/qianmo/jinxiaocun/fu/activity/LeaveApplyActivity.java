package com.qianmo.jinxiaocun.fu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.Contents;
import com.qianmo.jinxiaocun.fu.bean.ApplyClockDetailBean;
import com.qianmo.jinxiaocun.fu.bean.PeopleInfoBean;
import com.qianmo.jinxiaocun.fu.bean.ResponseBean;
import com.qianmo.jinxiaocun.fu.utils.DateUtil;
import com.qianmo.jinxiaocun.fu.utils.JsonUitl;
import com.qianmo.jinxiaocun.fu.utils.SPUtil;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.okhttp.OkhttpUtils;
import com.qianmo.jinxiaocun.main.okhttp.listener.OnActionListener;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 请假申请界面
 */
public class LeaveApplyActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, OnActionListener, TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.et_choose_store_name)
    TextView tvStartTimeChoose;

    @BindView(R.id.tv_leave_days)
    TextView tvLeaveDays;
    @BindView(R.id.tv_end_time_choose)
    TextView tvEndTimeChoose;
    @BindView(R.id.rl_leave_typeLayout)
    RelativeLayout mRlLeaveTypeLayout;
    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;
    @BindView(R.id.tv_end_time)
    TextView mTvEndTime;
    @BindView(R.id.tv_leave_type)
    TextView mTvLeaveType;
    @BindView(R.id.tv_leave_showType)
    TextView mTvLeaveShowType;
    @BindView(R.id.tv_avatar_name)
    TextView mTvAvatarName;
    @BindView(R.id.img_add_people)
    ImageView mImgAddPeople;
    @BindView(R.id.avatar)
    FrameLayout mAvatar;
    @BindView(R.id.ll_apply_approval)
    LinearLayout mLlApplyApproval;
    @BindView(R.id.et_leave_content)
    EditText mEtLeaveContent;

    private Calendar now;
    private String startTime;
    private String endTime;
    private static final String TAG = "LeaveApplyActivity";
    private Calendar mCalendarDate;
    private Calendar mCalendarTime;
    private int mBetweenDays;
    private static final int INTENT_CHOOSE_TYPE = 101;
    private static final int INTENT_CHOOSE_PEOPLE = 102;

    private int mLeaveType;
    private String mStaffId;
    private String mLeaveContent;

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
        mLeaveContent = mEtLeaveContent.getText().toString().trim();
        if (checkParam()) {
            requestData();//提交数据

        }
    }

    private boolean checkParam() {
        if (TextUtils.isEmpty(startTime)) {
            ToastUtils.MyToast(this, "请选择开始请假日期！");
            return false;
        } else if (TextUtils.isEmpty(endTime)) {
            ToastUtils.MyToast(this, "请选择结束请假日期！");
            return false;
        } else if (mLeaveType == 0) {
            ToastUtils.MyToast(this, "请选择请假类型！");
            return false;
        } else if (TextUtils.isEmpty(mLeaveContent)) {
            ToastUtils.MyToast(this, "请输入请假原因！");
            return false;
        }else if (TextUtils.isEmpty(mStaffId)) {
            ToastUtils.MyToast(this, "请选择审核人员！");
            return false;
        }
        return true;
    }

    @Override
    public void requestData() {
        super.requestData();
        OkhttpParam okhttpParam = new OkhttpParam();
        okhttpParam.putString("startTime", startTime);
        okhttpParam.putString("endTime", endTime);
        okhttpParam.putString("applyDays", mBetweenDays + "");
        okhttpParam.putString("leaveType", mLeaveType + "");
        okhttpParam.putString("leaveContent", mLeaveContent);
        okhttpParam.putString("applyClockDetails", JsonUitl.objectToString(
                new ApplyClockDetailBean(Contents.LEAVE, Integer.valueOf(SPUtil.getInstance().getStaffId()), Integer.valueOf(mStaffId))));
        OkhttpUtils.sendRequest(1001, 1, ApiConfig.ADD_APPLYLEAVE, okhttpParam, this);

    }

    private void initView() {
        //初始化view
        now = Calendar.getInstance();
        String date = DateUtil.getDate(System.currentTimeMillis(), "yyyy-MM-dd HH:mm");
        startTime = DateUtil.getDate(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
        endTime = DateUtil.getDate(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
        tvEndTimeChoose.setText(date);
        tvStartTimeChoose.setText(date);
        try {
            mBetweenDays = DateUtil.daysBetween(startTime, endTime);
            if (mBetweenDays < 0) {
                mBetweenDays = 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvLeaveDays.setText(mBetweenDays + "");
    }


    @OnClick({R.id.et_choose_store_name, R.id.tv_end_time_choose, R.id.rl_leave_typeLayout,
            R.id.img_add_people,R.id.avatar})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.et_choose_store_name:
                createDatePickerDialog("startDate");
                break;
            case R.id.tv_end_time_choose:
                createDatePickerDialog("endDate");
                break;
            case R.id.rl_leave_typeLayout: {
                //选择请假类型
                Intent intent = new Intent(LeaveApplyActivity.this, ChooseLeaveTypeActivity.class);
                startActivityForResult(intent, INTENT_CHOOSE_TYPE);
            }
            break;
            case R.id.img_add_people:
                Intent intent = new Intent(this, ChoosePeopleActivity.class);
                startActivityForResult(intent, INTENT_CHOOSE_PEOPLE);
                break;
            case R.id.avatar:
                int visibility = mAvatar.getVisibility();
                if (visibility == View.VISIBLE) {
                    mAvatar.setVisibility(View.GONE);
                    mStaffId = "";
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case INTENT_CHOOSE_TYPE:
                    mLeaveType = data.getIntExtra("leaveType", 0);
                    String leaveTitle = data.getStringExtra("leaveTitle");
                    if (!TextUtils.isEmpty(leaveTitle)) {
                        mTvLeaveShowType.setText(leaveTitle);
                    }
                    break;
                case INTENT_CHOOSE_PEOPLE:
                    ArrayList<PeopleInfoBean.DataBean> parcelableArrayListExtra = data.getParcelableArrayListExtra(("peoplesInfo"));
                    PeopleInfoBean.DataBean dataBean = parcelableArrayListExtra.get(0);
                    String staffName = dataBean.getStaffName();
                    int staffId = dataBean.getStaffId();
                    if (staffId == 0) {
                        mStaffId = "";
                    } else {
                        mStaffId = "" + staffId;
                    }
                    if (!TextUtils.isEmpty(staffName)) {
                        mAvatar.setVisibility(View.VISIBLE);

                        String substring = staffName.substring(staffName.length() - 2, staffName.length());
                        if (!TextUtils.isEmpty(substring)) {
                            mTvAvatarName.setText(substring);
                        }

                    } else {
                        mAvatar.setVisibility(View.GONE);
                    }
                    break;
            }
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

    //日历选择对话框
    private void createTimePickerDialog(String tag) {

        TimePickerDialog tpd = TimePickerDialog.newInstance(this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND), true
        );
        tpd.show(getFragmentManager(), tag);
    }

    @Override
    public void requestInit() {

    }

    //选择日历之后的回调方法
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String tag = view.getTag();
        mCalendarDate = Calendar.getInstance();
        mCalendarDate.set(year, monthOfYear, dayOfMonth);
        //根据tag的不同，对不同的textView设置文字
        if (tag.equals("startDate")) {
            createTimePickerDialog("startTime");
        } else {
            createTimePickerDialog("endTime");
        }
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String tag = view.getTag();
        if (mCalendarDate != null) {
            mCalendarTime = Calendar.getInstance();
            mCalendarTime.set(mCalendarDate.get(Calendar.YEAR), mCalendarDate.get(Calendar.MONTH),
                    mCalendarDate.get(Calendar.DAY_OF_MONTH), hourOfDay, minute, second);
        }

        if (mCalendarTime != null) {
            if (tag.equals("startTime")) {
                tvStartTimeChoose.setText(DateUtil.getFormateDate(mCalendarTime.getTime(), "yyyy-MM-dd HH:mm"));
                startTime = DateUtil.getFormateDate(mCalendarTime.getTime(), "yyyy-MM-dd HH:mm:ss");
            } else {
                tvEndTimeChoose.setText(DateUtil.getFormateDate(mCalendarTime.getTime(), "yyyy-MM-dd HH:mm"));
                endTime = DateUtil.getFormateDate(mCalendarTime.getTime(), "yyyy-MM-dd HH:mm:ss");
                ;
            }
        }
        //计算日期之间相隔的天数
        try {
            mBetweenDays = DateUtil.daysBetween(startTime, endTime);
            if (mBetweenDays < 0) {
                mBetweenDays = 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvLeaveDays.setText(mBetweenDays + "");
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
                            ToastUtils.MyToast(LeaveApplyActivity.this, "申请成功！");
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
        Log.i(TAG, "onActionServerFailed: " + httpStatus);
    }

    @Override
    public void onActionException(int actionId, String exception) {
        Log.i(TAG, "onActionException: " + exception);

    }


}
