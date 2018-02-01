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

public abstract class LoadingCallback<T> extends BaseCallback<T> {
    private Context mContext;
    private LoadingDialog mLoadingDialog;

    public LoadingCallback(Context context) {
        mContext = context;
        mLoadingDialog = new LoadingDialog(context, "拼命加载中...");

    }

    public void showDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.show();

        }
    }

    public void dismissDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.close();
        }
    }


    public void setLoadMessage(int resId) {
//        mLoadingDialog.s(mContext.getString(resId));
    }


    @Override
    public void onBeforeCallback(Request request) {
        showDialog();
    }

    @Override
    public void onFailure(Request request, Exception e) {
        dismissDialog();
    }


    @Override
    public void onResponse(Response response) {
        dismissDialog();
    }

}
