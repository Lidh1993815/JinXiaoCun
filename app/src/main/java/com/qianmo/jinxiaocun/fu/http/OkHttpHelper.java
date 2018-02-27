package com.qianmo.jinxiaocun.fu.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 18-1-31
 * desc :
 * version : 1.0
 */

public class OkHttpHelper {
    private static OkHttpHelper mOkHttpHelper;
    private OkHttpClient mClient;
    private Gson mGson;
    private Handler mHandler;
    public static final int TOKEN_MISSING = 401;// token 丢失
    public static final int TOKEN_ERROR = 402; // token 错误
    public static final int TOKEN_EXPIRE = 403; // token 过期
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpHelper() {
        mClient = new OkHttpClient();
        mClient.newBuilder().readTimeout(10, TimeUnit.SECONDS);
        mClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        mClient.newBuilder().writeTimeout(10, TimeUnit.SECONDS);
        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static synchronized OkHttpHelper getInstance() {
        if (mOkHttpHelper == null) {
            mOkHttpHelper = new OkHttpHelper();
            return mOkHttpHelper;
        } else {
            return mOkHttpHelper;
        }
    }

    private Request buildRequestGet(String url, Map<String, String> params) {
        return buildRequest(url, HttpMethodType.GET, null);
    }


    private Request buildRequestPost(String url, OkhttpParam params) {
        return buildRequest(url, HttpMethodType.POST, params);
    }

    public void get(String url, Map<String, String> params, BaseCallback callback) {


        Request request = buildRequestGet(url, params);

        request(request, callback);

    }

    public void get(String url, BaseCallback callback) {


        this.get(url, null, callback);

    }

    private static String buildUrlParams(String url, OkhttpParam params) {

        if (params != null) {


      /*  String token = SPUtil.getInstance().getUserToken();
        if (!TextUtils.isEmpty(token))
            params.put("token", token);*/

            Map<String, Object> stringParams = params.getStringParams();

            if (stringParams != null && stringParams.size() > 0) {
                StringBuffer sb = new StringBuffer();
                for (Map.Entry<String, Object> entry : stringParams.entrySet()) {
                    sb.append(entry.getKey() + "=" + (entry.getValue() == null ? "" : entry.getValue().toString()));
                    sb.append("&");
                }
                String s = sb.toString();
                if (s.endsWith("&")) {
                    s = s.substring(0, s.length() - 1);
                }

                if (url.indexOf("?") > 0) {
                    url = url + "&" + s;
                } else {
                    url = url + "?" + s;
                }
            }
        }
        return url;
    }

    public void post(String url, OkhttpParam param, BaseCallback callback) {

        Request request = buildRequestPost(url, param);
        request(request, callback);
    }


    private static Request buildRequest(String url, HttpMethodType type, OkhttpParam params) {
        Request.Builder builder = new Request.Builder();
        if (type == HttpMethodType.GET) {
            url = buildUrlParams(url, params);
            builder.url(url);

            builder.get();
        } else if (type == HttpMethodType.POST) {
            RequestBody requestBody = builderFormData(params);
            builder.url(url).post(requestBody);
        }
        return builder.build();
    }

    public void request(final Request request, final BaseCallback callback) {
        callback.onBeforeCallback(request);
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("wizardev", "onFailure: ");
                callbackFailure(callback, request, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callbackResponse(callback, response);
                if (response.isSuccessful()) {
                    String resultStr = response.body().string();
                    Log.i("wizardev", "onResponse: " + resultStr);
                    if (callback.mType == String.class) {
                        callbackSuccess(callback, response, resultStr);
                    } else {
                        try {
                            Object object = mGson.fromJson(resultStr, callback.mType);
                            callbackSuccess(callback, response, object);
                        } catch (JsonParseException e) {
                            callback.onError(response, response.code(), e);
                            Log.d("wizardev", "error: "+ response.code());
                        }
                    }
                    // mGson.fromJson(resultStr,)
                } else if (response.code() == TOKEN_ERROR || response.code() == TOKEN_EXPIRE || response.code() == TOKEN_MISSING) {

                    callbackTokenError(callback, response);
                } else {
                    Log.d("wizardev", "error: "+ response.code());

                    callback.onError(response, response.code(), null);
                }
            }
        });
    }

    private void callbackTokenError(final BaseCallback callback, final Response response) {

       /* mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onTokenError(response,response.code());
            }
        });*/
    }

    private void callbackSuccess(final BaseCallback callback, final Response response, final Object object) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response, object);
            }
        });
    }

    private void callbackFailure(final BaseCallback callback, final Request request, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(request, e);
            }
        });
    }

    private void callbackError(final BaseCallback callback, final Response response, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response, response.code(), e);
            }
        });
    }

    private void callbackResponse(final BaseCallback callback, final Response response) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(response);
            }
        });
    }


    private static RequestBody builderFormData(OkhttpParam params) {
       /* FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }

//            builder.add(SPUtil.USER_TOKEN, SPUtil.getInstance().getUserToken());
        }*/

        if (params != null) {
            JSONObject jsonObject = new JSONObject();
            Map<String, Object> stringParams = params.getStringParams();

            if (stringParams != null && stringParams.size() > 0) {
                //在字符串集合中有数据
                Iterator<Map.Entry<String, Object>> iterator = stringParams.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> next = iterator.next();
                    jsonObject.put(next.getKey(), next.getValue());
                }
            }
            Log.d("wizardev", "请求的数据: "+jsonObject.toJSONString());
            return RequestBody.create(JSON, jsonObject.toString());
        }
        return null;
    }

    enum HttpMethodType {
        GET,
        POST
    }
}
