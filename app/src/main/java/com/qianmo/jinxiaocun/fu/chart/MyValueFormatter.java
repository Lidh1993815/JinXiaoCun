package com.qianmo.jinxiaocun.fu.chart;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * author : wizardev
 * e-mail : wizarddev@163.com
 * time   : 2017/10/12
 * desc   :
 * version: 1.0
 */
public class MyValueFormatter implements IValueFormatter {

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return (int) value + "个";
    }
}
