package com.qianmo.jinxiaocun.fu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.Contents;
import com.qianmo.jinxiaocun.fu.bean.ApplyClockDetailBean;
import com.qianmo.jinxiaocun.fu.bean.LoginResponseBean;
import com.qianmo.jinxiaocun.fu.bean.PeopleInfoBean;
import com.qianmo.jinxiaocun.fu.bean.ResponseBean;
import com.qianmo.jinxiaocun.fu.utils.JsonUitl;
import com.qianmo.jinxiaocun.fu.utils.SPUtil;
import com.qianmo.jinxiaocun.main.MainActivity;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.okhttp.OkhttpUtils;
import com.qianmo.jinxiaocun.main.okhttp.listener.OnActionListener;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 补打卡申请
 */
public class CardApplyActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, OnActionListener {

    @BindView(R.id.tv_card_time)
    EditText tvCardTime;

    @BindView(R.id.img_add_people)
    ImageView mAddPeople;
    public static final int INTENT_CODE = 201;

    private static final String TAG = "CardApplyActivity";
    @BindView(R.id.tv_avatar_name)
    TextView mTvAvatarName;
    @BindView(R.id.avatar)
    FrameLayout mAvatar;
    @BindView(R.id.tv_cardApply_reason)
    EditText mTvCardApplyReason;
    private String mStaffId;//审核人编号
    private StringBuilder mStringBuilder;
    private String mCardTime;
    private String mCardApplyReason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "补打卡申请", "完成");
        setContentView(requestView(R.layout.activity_card_apply));
        ButterKnife.bind(this);
    }


    @Override
    protected void rightTextAction() {
        super.rightTextAction();
        mCardTime = tvCardTime.getText().toString().trim();
        mCardApplyReason = mTvCardApplyReason.getText().toString().trim();
        if (checkParam()) {
            requestData();//提交补卡
        }
    }


    @Override
    public void requestData() {
        super.requestData();
        OkhttpParam okhttpParam = new OkhttpParam();
        okhttpParam.putString("fTime", mCardTime);
        okhttpParam.putString("fContent", mCardApplyReason);
        okhttpParam.putString("applyClockDetails", JsonUitl.objectToString(new ApplyClockDetailBean(Contents.CARD, Integer.parseInt(SPUtil.getInstance().getStaffId()), Integer.parseInt(mStaffId))));
        OkhttpUtils.sendRequest(1001, 1, ApiConfig.ADD_APPLY_FILL_CARD, okhttpParam, this);
    }

    private boolean checkParam() {
        if (TextUtils.isEmpty(mCardTime)) {
            ToastUtils.MyToast(this, "请选择补卡时间！");
            return false;
        } else if (TextUtils.isEmpty(mCardApplyReason)) {
            ToastUtils.MyToast(this, "请输入补卡原因！");
            return false;
        } else if (TextUtils.isEmpty(mStaffId)) {
            ToastUtils.MyToast(this, "请选择审批人！");
            return false;
        }
        return true;
    }

    @OnClick({R.id.tv_card_time, R.id.img_add_people, R.id.avatar})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.tv_card_time:
                mStringBuilder = new StringBuilder();
                createDatePickerDialog();
                break;
            case R.id.img_add_people:
                Intent intent = new Intent(this, ChoosePeopleActivity.class);
                startActivityForResult(intent, INTENT_CODE);
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

    //日期选择对话框
    private void createDatePickerDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    //时间选择对话框
    private void createTimePickerDialog() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE), true);
        timePickerDialog.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void requestInit() {

    }

    //选择日历之后的回调方法
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date;
        if (dayOfMonth < 10) {
            date = year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
            if (monthOfYear + 1 < 10) {
                date = year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
            }
        } else {
            if (monthOfYear + 1 < 10) {
                date = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
            } else {
                date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            }
        }
        mStringBuilder.append(date);
        createTimePickerDialog();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_CODE && resultCode == RESULT_OK && data != null) {
            PeopleInfoBean.DataBean dataBean = (PeopleInfoBean.DataBean) data.getSerializableExtra("peopleInfo");
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
            Log.i(TAG, "onActivityResult: " + staffName);
        }
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = null;
        if (hourOfDay < 10) {
            time = " 0" + hourOfDay + ":" + minute + ":00";
            if (minute < 10) {
                time = " 0" + hourOfDay + ":0" + minute + ":00";
            }
        } else {
            if (minute < 10) {
                time = " " + hourOfDay + ":0" + minute + ":00";
            } else {
                time = " " + hourOfDay + ":" + minute + ":00";
            }
        }
        mStringBuilder.append(time);
        tvCardTime.setText(mStringBuilder.toString());
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
                            ToastUtils.MyToast(CardApplyActivity.this, "申请成功！");
                            finish();
                        } else {
                            ToastUtils.MyToast(this, responseBean.getMsg());
                        }
                    }
                }
                break;
        }
        /*if (mLoadingDialog != null) {
            mLoadingDialog.close();
        }*/
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
