package com.qianmo.jinxiaocun.fu.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.bean.ResponseBean;
import com.qianmo.jinxiaocun.fu.http.LoadingCallback;
import com.qianmo.jinxiaocun.fu.http.OkHttpHelper;
import com.qianmo.jinxiaocun.fu.utils.SPUtil;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 添加会员界面
 */
public class AddNumberActivity extends BaseActivity {

    @BindView(R.id.et_addNum_vipName)
    EditText mEtAddNumVipName;
    @BindView(R.id.et_addNum_contactName)
    EditText mEtAddNumContactName;
    @BindView(R.id.et_addNum_mobile)
    EditText mEtAddNumMobile;
    @BindView(R.id.et_addNum_sex)
    EditText mEtAddNumSex;
    @BindView(R.id.et_addNum_age)
    EditText mEtAddNumAge;
    @BindView(R.id.et_addNum_profession)
    EditText mEtAddNumProfession;
    @BindView(R.id.et_addNum_monthIncome)
    EditText mEtAddNumMonthIncome;
    @BindView(R.id.rl_addNum_chooseSex)
    RelativeLayout mRlAddNumChooseSex;
    private String mVipName;
    private String mContactName;
    private String mMobile;
    private String mAge;
    private String mProfession;
    private String mMonthIncome;
    private AlertDialog.Builder builder;
    private RadioButton mSex_rb_nan;
    private int mChooseSex = -1;
    private RadioButton mSex_rb_nv;
    private AlertDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "添加会员", "添加");
        setContentView(requestView(R.layout.activity_add_number));
        ButterKnife.bind(this);

    }


    @OnClick(R.id.rl_addNum_chooseSex)
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.rl_addNum_chooseSex:
                showSexPickerDialog();
                break;

        }
    }

    @Override
    public void requestInit() {

    }

    @Override
    protected void rightTextAction() {
        mVipName = mEtAddNumVipName.getText().toString().trim();//会员名称
        //联系人
        mContactName = mEtAddNumContactName.getText().toString().trim();
        //手机号
        mMobile = mEtAddNumMobile.getText().toString().trim();
        //年龄
        mAge = mEtAddNumAge.getText().toString().trim();
        //职位
        mProfession = mEtAddNumProfession.getText().toString().trim();
        //月收入
        mMonthIncome = mEtAddNumMonthIncome.getText().toString().trim();

        if (checkParam()) {
            //提交数据
            commitData();
        }
    }

    private void commitData() {
        /**
         * {
         "staffId":"1",
         "memberName":"黄金会员",
         "memberPhone":"18329184501",
         "sex":"1",
         "age":"18",
         "profession":"程序员鼓励师",
         "income":"58000",
         "pwd":"12356"
         }
         */
        OkhttpParam okhttpParam = new OkhttpParam();
        okhttpParam.putString("staffId", SPUtil.getInstance().getStaffId());
        okhttpParam.putString("memberName", mVipName);
        okhttpParam.putString("memberPhone", mMobile);
        okhttpParam.putString("sex", mChooseSex + "");
        okhttpParam.putString("age", mAge);
        okhttpParam.putString("profession", mProfession);
        okhttpParam.putString("income", mMonthIncome);
        OkHttpHelper.getInstance().post(ApiConfig.INCREASE_MEMBER, okhttpParam, new LoadingCallback<ResponseBean>(this) {
            @Override
            public void onSuccess(Response response, ResponseBean responseBean) {
                String state = responseBean.getState();
                if (state.equals("00000")) {
                    ToastUtils.MyToast(AddNumberActivity.this, "添加会员成功！");
                    AddNumberActivity.this.finish();
                } else {
                    ToastUtils.MyToast(AddNumberActivity.this, responseBean.getMsg());
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }


    //选择性别的弹出框
    private void showSexPickerDialog() {

        if (builder == null) {
            builder = new AlertDialog.Builder(this);
            builder.setTitle("请您选择性别");
            View view = View.inflate(this, R.layout.update_sex_dialog, null);

            mSex_rb_nan = view.findViewById(R.id.sex_rb_nan);
            mSex_rb_nan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        mChooseSex = 0;
                    }

                    sexCheckedChange(mChooseSex);
                }
            });

            mSex_rb_nv = view.findViewById(R.id.sex_rb_nv);
            mSex_rb_nv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        mChooseSex = 1;
                    }
                    sexCheckedChange(mChooseSex);
                }
            });


            builder.setView(view);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (mChooseSex == 1) {
                        mEtAddNumSex.setText(getResources().getString(R.string.woman));
                    } else {
                        mEtAddNumSex.setText(getResources().getString(R.string.man));
                    }
                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                }
            });
            mDialog = builder.create();
        }
        sexCheckedChange(mChooseSex);
        mDialog.show();
    }

    private void sexCheckedChange(int state) {
        if (state == 0) {
            mSex_rb_nan.setChecked(true);
            mSex_rb_nv.setChecked(false);
        } else {
            mSex_rb_nan.setChecked(false);
            mSex_rb_nv.setChecked(true);
        }
    }


    private boolean checkParam() {
        if (TextUtils.isEmpty(mVipName)) {
            ToastUtils.MyToast(this, getResources().getString(R.string.please_input_member_name));
            return false;
        } else if (TextUtils.isEmpty(mContactName)) {
            ToastUtils.MyToast(this, getResources().getString(R.string.please_input_contact_person));
            return false;
        } else if (TextUtils.isEmpty(mMobile)) {
            ToastUtils.MyToast(this, getResources().getString(R.string.please_input_phone_num));
            return false;
        }  else if (mMobile.length()!=11) {
            ToastUtils.MyToast(this, "输入的手机号格式有误！");
            return false;
        } else if (mChooseSex == -1) {
            ToastUtils.MyToast(this, getResources().getString(R.string.please_choose_sex));
            return false;
        } else if (TextUtils.isEmpty(mAge)) {
            ToastUtils.MyToast(this, getResources().getString(R.string.please_input_age));
            return false;
        } else if (TextUtils.isEmpty(mProfession)) {
            ToastUtils.MyToast(this, getResources().getString(R.string.please_input_profession));
            return false;
        } else if (TextUtils.isEmpty(mMonthIncome)) {
            ToastUtils.MyToast(this, getResources().getString(R.string.please_input_month_income));
            return false;
        }
        return true;
    }
}
