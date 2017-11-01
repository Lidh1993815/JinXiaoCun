package com.qianmo.jinxiaocun.fu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.adapter.ExpandableItemAdapter;
import com.qianmo.jinxiaocun.fu.bean.Level0Item;
import com.qianmo.jinxiaocun.fu.bean.MultiItemEntity;
import com.qianmo.jinxiaocun.fu.bean.Person;
import com.qianmo.jinxiaocun.fu.chart.MyValueFormatter;
import com.qianmo.jinxiaocun.fu.chart.MyXAxisValueFormatter;
import com.qianmo.jinxiaocun.main.base.BaseFragment;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : wizardev
 * e-mail : wizarddev@163.com
 * time   : 2017/10/11
 * desc   : 总库存界面
 * version: 1.0
 */
public class MonthDetailFragment extends BaseFragment {
    @BindView(R.id.tv_choose_month)
    TextView tvChooseMonth;
    @BindView(R.id.line_chart)
    LineChart mChart;
    Unbinder unbinder;
    @BindView(R.id.secondary_recycle)
    LRecyclerView mRecyclerView;
    private ExpandableItemAdapter mDataAdapter = null;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = requestView(inflater, R.layout.fu_month_detail_fragment);
        unbinder = ButterKnife.bind(this, view);
        initChart();//初始化折线图
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        mDataAdapter = new ExpandableItemAdapter(getContext());
        mDataAdapter.setDataList(generateData());

        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mDataAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);

        DividerDecoration divider = new DividerDecoration.Builder(getContext())
                .setHeight(R.dimen.line_height_size)
                .setColorResource(R.color._c0c0c0)
                .build();
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(divider);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //禁用下拉刷新功能
        mRecyclerView.setPullRefreshEnabled(false);

        //禁用自动加载更多功能
        mRecyclerView.setLoadMoreEnabled(false);
    }

    private ArrayList<MultiItemEntity> generateData() {
        int lv0Count = 5;//一级菜单显示的条数
        int lv1Count = 7;//二级菜单显示的条数

        String[] nameList = {"Bob", "Andy", "Lily", "Brown", "Bruce","Bob", "Andy", "Lily", "Brown", "Bruce"};
        Random random = new Random();

        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < lv0Count; i++) {
            Level0Item lv0 = new Level0Item("This is " + i + "th item in Level 0", "subtitle of " + i);
            for (int j = 0; j < lv1Count; j++) {
            //    Level1Item lv1 = new Level1Item("Level 1 item: " + j, "(no animation)");
//                for (int k = 0; k < personCount; k++) {
//                  //  lv1.addSubItem(new Person(nameList[k], random.nextInt(40)));
//                }
//                lv0.addSubItem(lv1);
                lv0.addSubItem(new Person(nameList[j], random.nextInt(40)));
            }
            res.add(lv0);
        }
        return res;
    }

    private void initChart() {
        mChart.setDrawGridBackground(false);
        mChart.getDescription().setEnabled(false);
        mChart.setTouchEnabled(false);
        // enable scaling and dragging
        mChart.setDragEnabled(true);//可以拖动
        mChart.setScaleEnabled(true);//可以缩放
        mChart.setPinchZoom(true);
        initXAxis();//初始化X轴
        initYAxis();//初始化Y轴
        mChart.getAxisRight().setEnabled(false);
        // 设置数据
        setData(5, 60);
        mChart.animateX(1000);//设置动画时长
        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();
        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
    }

    private void initYAxis() {
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setGranularity(1f);
        leftAxis.setLabelCount(8, true);
        //   leftAxis.setValueFormatter(new MyYAxisValueFormatter());
        //  leftAxis.enableGridDashedLine(10f, 10f, 0f);
        //  leftAxis.setDrawZeroLine(true);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);
    }

    private void initXAxis() {
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴标题的位置
        xAxis.setDrawAxisLine(true);
        xAxis.setTextSize(15f);
        xAxis.setGranularity(1f);
        String[] week = {"第一周", "第二周", "第三周", "第四周", "第五周"};//设置X轴显示的标题
        xAxis.setValueFormatter(new MyXAxisValueFormatter(week));
    }

    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) + 3;
            values.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "销量");
            set1.setDrawIcons(false);
            set1.setColor(getResources().getColor(R.color._4c94ef));
            set1.setCircleColor(getResources().getColor(R.color._4c94ef));
            set1.setCircleRadius(3f);//设置点的半径
            set1.setLineWidth(2f);//设置线宽
            set1.setCircleHoleRadius(2f);//设置圆环的半径
            set1.setDrawCircleHole(true);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setValueFormatter(new MyValueFormatter());
            set1.setFormSize(15.f);
            set1.setFillAlpha(0);

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets
            // create a data object with the datasets
            LineData data = new LineData(dataSets);
            // set data
            mChart.setData(data);
        }
    }

    @Override
    public void requestInit() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
