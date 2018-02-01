package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.Contents;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.bean.PeopleInfoBean;
import com.qianmo.jinxiaocun.fu.utils.StringUtil;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加任务界面
 */
public class TaskAddActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

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
    LRecyclerView mRecyclerView;
    private String mStaffId;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private ChoosedAdapter mChoosedAdapter;

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
        mChoosedAdapter = new ChoosedAdapter(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mChoosedAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);

        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadMoreEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

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

    }

    /**
     * 判断参数是否合理
     *
     * @return
     */
    private boolean checkParam() {
        String taskTitle = mEtAddTaskTitle.getText().toString().trim();
        String taskContent = mEtAddTaskContent.getText().toString().trim();
        String taskBonus = mEtAddTaskBonus.getText().toString().trim();
        String taskFine = mEtAddTaskFine.getText().toString().trim();
        if (TextUtils.isEmpty(taskTitle)) {
            ToastUtils.MyToast(this, "请输入标题！");
            return false;
        } else if (TextUtils.isEmpty(taskContent)) {
            ToastUtils.MyToast(this, "请输入任务内容！");
            return false;
        } else if (TextUtils.isEmpty(taskBonus)) {
            ToastUtils.MyToast(this, "请输入奖金！");
            return false;
        } else if (TextUtils.isEmpty(taskFine)) {
            ToastUtils.MyToast(this, "请输入罚款！");
            return false;
        } else if (TextUtils.isEmpty(mStaffId)) {
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
                //选择执行人
                Intent intent = new Intent(this, ChoosePeopleActivity.class);
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
        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        tvSelectCalendar.setText(date);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case INTENT_CHOOSE_PEOPLE:
                    ArrayList<PeopleInfoBean.DataBean> dataBeans = (ArrayList<PeopleInfoBean.DataBean>) data.getParcelableExtra("peoplesInfo");
                    mChoosedAdapter.setDataList(dataBeans);
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                   /* String staffName = dataBean.getStaffName();
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
                    }*/
                    break;
            }
        }
    }

    class ChoosedAdapter extends ListBaseAdapter<PeopleInfoBean.DataBean> {


        public ChoosedAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_avatar_name;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {
            PeopleInfoBean.DataBean dataBean = mDataList.get(position);
            String staffName = dataBean.getStaffName();
            TextView mTvAvatarName = holder.getView(R.id.tv_avatar_name);
            mTvAvatarName.setText(StringUtil.getString(staffName));
        }
    }

}
