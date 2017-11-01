package com.qianmo.jinxiaocun.fu.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.fragment.MonthDetailFragment;
import com.qianmo.jinxiaocun.fu.fragment.TotalInventoryFragment;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品详情界面
 */
public class ProductDetailActivity extends BaseActivity {

    @BindView(R.id.tv_see_detail)
    TextView tvSeeDetail;
    @BindView(R.id.fragment_content)
    FrameLayout fragmentContent;
    @BindView(R.id.tv_month_sales_detail)
    TextView tvMonthSalesDetail;
    @BindView(R.id.tv_total_inventory)
    TextView tvTotalInventory;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "商品详情", R.mipmap.saomiao);
        setContentView(requestView(R.layout.activity_product_detail));
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content, new MonthDetailFragment());
        fragmentTransaction.commit();

    }

    private void initEvent() {
    }

    @Override
    public void requestInit() {

    }

    @OnClick({R.id.tv_month_sales_detail, R.id.tv_total_inventory,R.id.tv_see_detail})
    public void clickAction(View view) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (view.getId()) {
            //30天销售情况
            case R.id.tv_month_sales_detail:
                clickLeft();
                fragmentTransaction.replace(R.id.fragment_content, new MonthDetailFragment());
                fragmentTransaction.commit();
                break;
            //总库存
            case R.id.tv_total_inventory:
                clickRight();
                fragmentTransaction.replace(R.id.fragment_content, new TotalInventoryFragment());
                fragmentTransaction.commit();
                break;
            case R.id.tv_see_detail:
                startActivity(ProductDetail2Activity.class,false);
                break;
        }
    }

    private void clickRight() {
        tvMonthSalesDetail.setTextColor(getResources().getColor(R.color._9f9f9f));
        tvMonthSalesDetail.setBackgroundResource(R.mipmap.anniu);
        tvTotalInventory.setTextColor(getResources().getColor(R.color.black));
        tvTotalInventory.setBackgroundColor(Color.WHITE);
    }

    private void clickLeft() {
        tvTotalInventory.setTextColor(getResources().getColor(R.color._9f9f9f));
        tvTotalInventory.setBackgroundResource(R.mipmap.anniu);
        tvMonthSalesDetail.setTextColor(getResources().getColor(R.color.black));
        tvMonthSalesDetail.setBackgroundColor(Color.WHITE);
    }
}
