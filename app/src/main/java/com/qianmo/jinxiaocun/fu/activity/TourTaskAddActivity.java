package com.qianmo.jinxiaocun.fu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.Contents;
import com.qianmo.jinxiaocun.fu.bean.PeopleInfoBean;
import com.qianmo.jinxiaocun.fu.bean.ResponseBean;
import com.qianmo.jinxiaocun.fu.http.LoadingCallback;
import com.qianmo.jinxiaocun.fu.http.OkHttpHelper;
import com.qianmo.jinxiaocun.fu.utils.DateUtil;
import com.qianmo.jinxiaocun.fu.utils.SPUtil;
import com.qianmo.jinxiaocun.fu.utils.StringUtil;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 添加巡店任务界面
 */
public class TourTaskAddActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.et_choose_store_name)
    EditText mEtChooseStoreName;
    @BindView(R.id.tv_end_time_choose)
    TextView mTvTourTime;
    @BindView(R.id.et_tourTask_content)
    EditText mEtTourTaskContent;
    @BindView(R.id.et_choose_people)
    EditText mEtChoosePeople;
    private String mStoreName;
    private static final int INTENT_STORE_CODE = 222;
    private static final int INTENT_PEOPLE_CODE = 333;
    private int mStoreId;
    private String mStaffId;
    private Calendar mCalendar;
    private String overTimer;
    private String mTaskContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "添加巡店任务", "完成");

        setContentView(requestView(R.layout.activity_tour_task_add));
        ButterKnife.bind(this);
    }


    @Override
    protected void rightTextAction() {
        mStoreName = mEtChooseStoreName.getText().toString().trim();
        if (checkParam()) {
            commitData();//提交数据到服务器
        }
    }

    private void commitData() {
/**
 * {
 "storeId":"门店ID * ",
 "staffId":"巡店人员ID * ",
 "patrolStoreTime":"巡店时间 *" ,
 "patrolStoreContent":"巡店内容 *"
 }
 */
        OkhttpParam okhttpParam = new OkhttpParam();
        okhttpParam.putString("storeId", mStoreId+"");
        okhttpParam.putString("staffId", mStaffId);
        okhttpParam.putString("patrolStoreTime", overTimer);
        okhttpParam.putString("patrolStoreContent", mTaskContent);
        OkHttpHelper.getInstance().post(ApiConfig.ADD, okhttpParam, new LoadingCallback<ResponseBean>(this) {

            @Override
            public void onSuccess(Response response, ResponseBean responseBean) {
                if ("00000".equals(responseBean.getState())) {
                    ToastUtils.MyToast(TourTaskAddActivity.this, "添加成功");
                    finish();
                } else {
                    ToastUtils.MyToast(TourTaskAddActivity.this, responseBean.getMsg());

                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private boolean checkParam() {
        mTaskContent = mEtTourTaskContent.getText().toString().trim();
        if (TextUtils.isEmpty(mStoreName)) {
            ToastUtils.MyToast(this, "请选择门店！");
            return false;
        } else  if (TextUtils.isEmpty(mStaffId)) {
            ToastUtils.MyToast(this, "请选择巡店人员！");
            return false;
        }else  if (TextUtils.isEmpty(overTimer)) {
            ToastUtils.MyToast(this, "请选择巡店时间！");
            return false;
        }else  if (TextUtils.isEmpty(mTaskContent)) {
            ToastUtils.MyToast(this, getResources().getString(R.string.input_tour_content));
            return false;
        }
        return true;
    }

    @Override
    public void requestInit() {

    }

    @OnClick({R.id.et_choose_store_name, R.id.tv_end_time_choose, R.id.et_choose_people})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_choose_store_name:{
                Intent intent = new Intent(this, ChooseStoreActivity.class);
                startActivityForResult(intent, INTENT_STORE_CODE);
            }

                break;
            case R.id.tv_end_time_choose:
                createDatePickerDialog();

                break;
            case R.id.et_choose_people:
                Intent intent = new Intent(this, ChoosePeopleActivity.class);
                intent.putExtra("chooseType", Contents.ONLY_ONE);
                startActivityForResult(intent, INTENT_PEOPLE_CODE);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case INTENT_PEOPLE_CODE:
                    ArrayList<PeopleInfoBean.DataBean> parcelableArrayListExtra = data.getParcelableArrayListExtra(("peoplesInfo"));
                    PeopleInfoBean.DataBean dataBean = parcelableArrayListExtra.get(0);
                    String staffName = dataBean.getStaffName();
                    int staffId = dataBean.getStaffId();
                    if (staffId == 0) {
                        mStaffId = "";
                    } else {
                        mStaffId = "" + staffId;
                    }
                    mEtChoosePeople.setText(StringUtil.getString(staffName));
                    break;
                case INTENT_STORE_CODE:
                    mStoreId = data.getIntExtra("storeId", 0);
                    String storeName = data.getStringExtra("storeName");
                    mEtChooseStoreName.setText(StringUtil.getString(storeName));
                    break;
            }
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        mCalendar = Calendar.getInstance();
        mCalendar.set(year, monthOfYear, dayOfMonth);

        Date time = mCalendar.getTime();
        overTimer = DateUtil.getFormateDate(time, "yyyy-MM-dd");
        mTvTourTime.setText(overTimer);

    }
}
