package com.qianmo.jinxiaocun.fu.activity;

import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.bean.ResponseBean;
import com.qianmo.jinxiaocun.fu.http.LoadingCallback;
import com.qianmo.jinxiaocun.fu.http.OkHttpHelper;
import com.qianmo.jinxiaocun.fu.utils.SPUtil;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.Contents;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.bean.PeopleInfoBean;
import com.qianmo.jinxiaocun.fu.utils.DateUtil;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 添加任务界面
 */
public class TaskAddActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.tv_select_calendar)
    TextView tvSelectCalendar;
    @BindView(R.id.et_addTask_title)
    EditText mEtAddTaskTitle;
    @BindView(R.id.et_addTask_content)
    EditText mEtAddTaskContent;
    @BindView(R.id.et_addTask_bonus)
    EditText mEtAddTaskBonus;
    @BindView(R.id.et_addTask_fine)
    EditText mEtAddTaskFine;
    @BindView(R.id.tv_condition)
    TextView mTvCondition;

    private static final int INTENT_CHOOSE_PEOPLE = 102;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.img_add_people)
    ImageView mImgAddPeople;
    private ChooseAdapter mChooseAdapter;
    private static final String TAG = "TaskAddActivity";
    private List<PeopleInfoBean.DataBean> mDataList;
    private Calendar mCalendar;
    private String mTaskTitle;
    private String mTaskContent;
    private String mTaskBonus;
    private String mTaskFine;
    private String overTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "添加任务", "完成");
        setContentView(requestView(R.layout.activity_task_add));
        ButterKnife.bind(this);
        initView();
        setupLRecyclerView();//设置recyclerView
    }

    private void setupLRecyclerView() {
        mChooseAdapter = new ChooseAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mChooseAdapter);

        //点击删除对应的人员
        mChooseAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mDataList != null && mDataList.size() > 0) {
                    mDataList.remove(position);
                    mChooseAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    protected void rightTextAction() {
        if (checkParam()) {
            commitData();
        }
    }

    /**
     * 提交数据
     */
    private void commitData() {
        /**
         * "title":"准备出海钓鱼",
         "content":"没钓够500斤不许回来 赶紧去",
         "bonus":"1000",
         "penalty":"800",
         "uptoTime":"2017-11-25 12:30:00",
         "cTime":"2017-11-25 09:30:00",
         "staffId":"2",
         "implementId":"3,6,5,4"
         */
        OkhttpParam okhttpParam = new OkhttpParam();
        okhttpParam.putString("title",mTaskTitle);
        okhttpParam.putString("content",mTaskContent);
        okhttpParam.putString("bonus",mTaskBonus);
        okhttpParam.putString("penalty",mTaskFine);
        okhttpParam.putString("uptoTime",overTimer);
        okhttpParam.putString("staffId", SPUtil.getInstance().getStaffId());
        okhttpParam.putString("implementId",handleID());
        OkHttpHelper.getInstance().post(ApiConfig.INSERT_TASK, okhttpParam, new LoadingCallback<ResponseBean>(this) {

            @Override
            public void onSuccess(Response response, ResponseBean responseBean) {
                if (responseBean.getState().equals("00000")) {
                    ToastUtils.MyToast(TaskAddActivity.this, "添加成功");
                    TaskAddActivity.this.finish();
                } else {
                    ToastUtils.MyToast(TaskAddActivity.this, responseBean.getMsg());
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                Log.i(TAG, "onError: "+code);
            }
        });
    }

    //处理需要传递的数据
    private String handleID() {
        StringBuilder builder = new StringBuilder();
        if (mDataList != null) {
            for (int i = 0; i < mDataList.size(); i++) {
                builder.append(mDataList.get(i).getStaffId());

                if (i != mDataList.size() - 1) {
                    builder.append(",");
                }
            }
        }
        return builder.toString();
    }

    /**
     * 判断参数是否合理
     *
     * @return
     */
    private boolean checkParam() {
        mTaskTitle = mEtAddTaskTitle.getText().toString().trim();
        mTaskContent = mEtAddTaskContent.getText().toString().trim();
        mTaskBonus = mEtAddTaskBonus.getText().toString().trim();
        mTaskFine = mEtAddTaskFine.getText().toString().trim();
        if (TextUtils.isEmpty(mTaskTitle)) {
            ToastUtils.MyToast(this, "请输入标题！");
            return false;
        } else if (TextUtils.isEmpty(mTaskContent)) {
            ToastUtils.MyToast(this, "请输入任务内容！");
            return false;
        } else if (TextUtils.isEmpty(overTimer)) {
            ToastUtils.MyToast(this, "请选择截止时间！");
            return false;
        } else if (TextUtils.isEmpty(mTaskBonus)) {
            ToastUtils.MyToast(this, "请输入奖金！");
            return false;
        } else if (TextUtils.isEmpty(mTaskFine)) {
            ToastUtils.MyToast(this, "请输入罚款！");
            return false;
        } else if (mDataList == null || mDataList.size() == 0) {
            ToastUtils.MyToast(this, "请选择审核人员！");
            return false;
        }
        return true;
    }

    private void initView() {
        mTvCondition.setText("执行人");
    }

    @OnClick({R.id.img_add_people, R.id.tv_select_calendar})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.tv_select_calendar:
                createDatePickerDialog();
                break;
            case R.id.img_add_people:
                Intent intent = new Intent(TaskAddActivity.this, ChoosePeopleActivity.class);
                intent.putExtra("chooseType", Contents.MOST);
                startActivityForResult(intent, INTENT_CHOOSE_PEOPLE);
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
       /* String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        tvSelectCalendar.setText(date);*/
        mCalendar = Calendar.getInstance();
        mCalendar.set(year, monthOfYear, dayOfMonth);
        showTimePicker();//弹出选择时间的dialog
    }



    private void showTimePicker() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance( this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true);
        timePickerDialog.show(getFragmentManager(),"");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case INTENT_CHOOSE_PEOPLE:
                    mDataList = data.getParcelableArrayListExtra("peoplesInfo");
                    if (mDataList != null) {
                        mChooseAdapter.setDataList(mDataList);
                        mChooseAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute,second);
        Date time = calendar.getTime();
        overTimer = DateUtil.getFormateDate(time, "yyyy-MM-dd HH:mm:ss");
        tvSelectCalendar.setText(overTimer);
    }


    class ChooseAdapter extends ListBaseAdapter<PeopleInfoBean.DataBean> {


        public ChooseAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_avatar_name;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {


            PeopleInfoBean.DataBean dataBean = mDataList.get(position);
            Log.d(TAG, "onBindItemHolder: " + dataBean.getStaffName());

            String staffName = dataBean.getStaffName();
            TextView mTvAvatarName = holder.getView(R.id.tv_avatar_name);

            if (!TextUtils.isEmpty(staffName)) {
                if (staffName.length() > 2) {
                    String substring = staffName.substring(staffName.length() - 2, staffName.length());
                    if (!TextUtils.isEmpty(substring)) {
                        mTvAvatarName.setText(substring);
                    }
                }
            }
        }
    }

}
