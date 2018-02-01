package com.qianmo.jinxiaocun.fu.http;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.Response;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 18-1-31
 * desc : okhttp的回调接口
 * version : 1.0
 */

public abstract class BaseCallback<T> {
    public   Type mType;

    static Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }


    public BaseCallback()
    {
        mType = getSuperclassTypeParameter(getClass());
    }

    public abstract void onBeforeCallback(Request request);
    public abstract void onFailure(Request request,Exception e);
    public abstract void onSuccess(Response response, T t);
    public abstract void onError(Response response,int code,Exception e);
    public abstract void onResponse(Response response);

//    public abstract void onTokenError(Response response, int code);
}
