package com.qianmo.jinxiaocun.fu.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.bean.LoginResponseBean;
import com.qianmo.jinxiaocun.fu.utils.JsonUitl;
import com.qianmo.jinxiaocun.fu.utils.SPUtil;
import com.qianmo.jinxiaocun.fu.widget.LoadingDialog;
import com.qianmo.jinxiaocun.main.MainActivity;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.okhttp.OkhttpUtils;
import com.qianmo.jinxiaocun.main.okhttp.listener.OnActionListener;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class LoginActivity extends BaseActivity implements OnActionListener {

    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.et_login_account)
    MaterialEditText mEtLoginAccount;
    @BindView(R.id.et_login_passwd)
    MaterialEditText mEtLoginPasswd;
    private String mLoginAccount;
    private String mLoginPassWd;
    private LoadingDialog mLoadingDialog;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //todo登录功能好了以后，要把这里的代码去掉
        SPUtil instance = SPUtil.getInstance();
        instance.putString(this, SPUtil.STAFF_ID, "4");//保存员工Id到本地到本地
        initView();
        methodRequiresTwoPermission();
    }

    private void initView() {
        String staffId = SPUtil.getInstance().getStaffId();
        if (!TextUtils.isEmpty(staffId)) {
            startActivity(MainActivity.class,true);
        }
    }

    @AfterPermissionGranted(REQUEST_CODE)//添加注解，是为了首次执行权限申请后，回调该方法
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            //已经申请过权限，直接调用相机
            // openCamera();
        } else {
            EasyPermissions.requestPermissions(this, "需要获取权限",
                    REQUEST_CODE, perms);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @OnClick({R.id.btn_login})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                mLoadingDialog = new LoadingDialog(this, "登录中...");
                mLoginAccount = mEtLoginAccount.getText().toString().trim();
                mLoginPassWd = mEtLoginPasswd.getText().toString().trim();
                if (checkParam(mLoginAccount, mLoginPassWd)) {
                    if (mLoadingDialog != null) {
                        mLoadingDialog.show();
                    }
                    requestLogin();//登录
                }
                break;

        }
    }

    private void requestLogin() {
        OkhttpParam okhttpParam = new OkhttpParam();
        okhttpParam.putString("staffAccount", mLoginAccount);
        okhttpParam.putString("staffPwd", mLoginPassWd);
        OkhttpUtils.sendRequest(1001, 1, ApiConfig.LOGIN_STAFF, okhttpParam, this);
    }

    private boolean checkParam(String phoneString, String pwdString) {
        if (TextUtils.isEmpty(phoneString)) {
            ToastUtils.MyToast(this, "请输入帐号！");
            return false;
        } else if (TextUtils.isEmpty(pwdString)) {
            ToastUtils.MyToast(this, "请输入密码！");
            return false;
        }
        return true;
    }

    @Override
    public void requestInit() {

    }

    @Override
    public void onActionSuccess(int actionId, String ret) {
        switch (actionId) {
            case 1001:
                Log.i(TAG, "onActionSuccess: " + ret);
                if (!TextUtils.isEmpty(ret)) {
                    LoginResponseBean responseBean = (LoginResponseBean) JsonUitl.stringToObject(ret, LoginResponseBean.class);
                    if (responseBean != null) {
                        String state = responseBean.getState();
                        if (state.equals("00000")) {
                            //将信息保存到本地
                            SPUtil instance = SPUtil.getInstance();
                            instance.putString(this, SPUtil.STAFF_ID, responseBean.getData().getStaff().getStaffId()+"");//保存员工Id到本地到本地
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            ToastUtils.MyToast(this, responseBean.getMsg());
                        }
                    }
                }
                break;
        }
        if (mLoadingDialog != null) {
            mLoadingDialog.close();
        }
    }

    @Override
    public void onActionServerFailed(int actionId, int httpStatus) {

    }

    @Override
    public void onActionException(int actionId, String exception) {

    }
}
