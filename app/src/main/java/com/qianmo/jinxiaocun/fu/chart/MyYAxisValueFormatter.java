package com.qianmo.jinxiaocun.fu.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * author : wizardev
 * e-mail : wizarddev@163.com
 * time   : 2017/10/12
 * desc   :
 * version: 1.0
 */
public class MyYAxisValueFormatter implements IAxisValueFormatter {

   // private float mFormat;

    public MyYAxisValueFormatter() {

        // format values to 1 decimal digit
       // mFormat = new DecimalFormat("###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        return (int)(value) + " 个";
    }
}
