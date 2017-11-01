package com.qianmo.jinxiaocun.fu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.main.MainActivity;
import com.qianmo.jinxiaocun.main.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.btn_login})
    public void cliclAction(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                startActivity(MainActivity.class);
                break;
           /* case R.id.my_release_layout:
                startActivity(MyReleaseActivity.class, false);
                break;*/
        }
    }
    @Override
    public void requestInit() {

    }
}
