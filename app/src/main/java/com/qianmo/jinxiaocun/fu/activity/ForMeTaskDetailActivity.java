package com.qianmo.jinxiaocun.fu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

/**
 * 待我执行任务详情界面
 */
public class ForMeTaskDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "任务详情", "填写日报");
        TextView rightText = toolBar.getRightTextBtn();

        setContentView(requestView(R.layout.activity_for_me_task_detail));
        if (rightText != null) {
            rightText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(NewspaperActivity.class,false);
                }
            });
        }
    }

    @Override
    public void requestInit() {

    }
}
