package com.qianmo.jinxiaocun.fu.http;

import android.content.Context;

import com.qianmo.jinxiaocun.fu.widget.LoadingDialog;

import okhttp3.Request;
import okhttp3.Response;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 18-1-31
 * desc :
 * version : 1.0
 */

public abstract class NoLoadingCallback<T> extends BaseCallback<T> {

    @Override
    public void onBeforeCallback(Request request) {

    }
    @Override
    public void onFailure(Request request, Exception e) {

    }

    @Override
    public void onResponse(Response response) {

    }

}
