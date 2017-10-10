package com.qianmo.jinxiaocun.fu.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.adapter.PagerAdapter;
import com.qianmo.jinxiaocun.fu.fragment.MoneyManagerFragment;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoneyManagerActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_income)
    TextView tvIncome;
    @BindView(R.id.tv_expenditure)
    TextView tvExpenditure;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    private PagerAdapter mPageAdapter;
    private BottomSheetDialog mBottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "钱流管理", R.mipmap.add);
        //toolbar右边菜单的点击事件
        ImageView rightImageBtn = toolBar.getRightImageBtn();
        if (rightImageBtn != null) {
            rightImageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //显示底部弹窗
                    showBottomDialog();
                }
            });
        }
        setContentView(requestView(R.layout.activity_money_manager));
        ButterKnife.bind(this);
        setupViewPager();
    }

    private void showBottomDialog() {
        mBottomSheetDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.fu_money_bottom_dialog, null, false);
        mBottomSheetDialog.setContentView(view);
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        view.findViewById(R.id.tv_new_income_order).setOnClickListener(this);
        view.findViewById(R.id.tv_new_expenditure_order).setOnClickListener(this);
        mBottomSheetDialog.show();
    }

    private void dismisDialog() {
        if (mBottomSheetDialog != null && mBottomSheetDialog.isShowing()) {
            mBottomSheetDialog.dismiss();
        }
    }

    @Override
    public void requestInit() {

    }

    @OnClick({R.id.tv_income, R.id.tv_expenditure})
    public void clickAction(View view) {
        switch (view.getId()) {
            //收入
            case R.id.tv_income:
                tvIncome.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvExpenditure.setTextColor(Color.BLACK);
                vpContent.setCurrentItem(0);
                break;
            //支出
            case R.id.tv_expenditure:
                tvIncome.setTextColor(Color.BLACK);
                tvExpenditure.setTextColor(getResources().getColor(R.color.colorPrimary));
                vpContent.setCurrentItem(1);
                break;
        }
    }

    //为viewPager添加fragment
    public void setupViewPager() {
        mPageAdapter = new PagerAdapter(getSupportFragmentManager());
        mPageAdapter.addFragment(MoneyManagerFragment.newInstance(0), "");
        mPageAdapter.addFragment(MoneyManagerFragment.newInstance(1), "");
        vpContent.setAdapter(mPageAdapter);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MoneyManagerActivity.this, NewMoneyOrderActivity.class);

        switch (view.getId()) {
            //取消弹出框
            case R.id.tv_cancel:
                dismisDialog();
                break;
            //新建收入单
            case R.id.tv_new_income_order:
                intent.putExtra("type", "income");
                dismisDialog();
                startActivity(intent);
                break;
            //新建费用单
            case R.id.tv_new_expenditure_order:
                intent.putExtra("type", "expenditure");
                dismisDialog();

                startActivity(intent);
                break;
        }
    }
}
