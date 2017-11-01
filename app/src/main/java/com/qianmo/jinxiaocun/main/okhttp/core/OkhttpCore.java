package com.qianmo.jinxiaocun.main.okhttp.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.qianmo.jinxiaocun.main.okhttp.OkhttpUtils;
import com.qianmo.jinxiaocun.main.okhttp.listener.OnActionListener;
import com.qianmo.jinxiaocun.main.okhttp.params.ByteData;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;

import org.json.JSONObject;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Administrator on 2016/8/15.
 * 请求的主要代码
 */
public class OkhttpCore implements Runnable {

    /**
     * 成功获得网络数据 ID
     */
    private final static int MSG_TYPE_SUCCESS = 0x10;
    /**
     * 获取网络数据失败 ID
     */
    private final static int MSG_TYPE_SERVER_FILED = 0x20;
    /**
     * 获取网络数据异常 ID
     */
    private final static int MSG_TYPE_EXCEPTION = 0x30;
    /**
     * 请求成功返回参数名
     */
    private final static String MSG_KEY_PARAM = "param";
    /**
     * 请求异常返回参数名
     */
    private final static String MSG_KEY_EXCEPTION = "exception";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private int id;

    private String url;

    private OnActionListener listener;

    private OkhttpParam param;

    private Handler handler;

    private Context context;

    int httpCode;
    /**
     * 请求方式，0：get请求；1：post请求；2：上传文件
     */
    private int requestMethod = 1;

    @SuppressLint("HandlerLeak")
    public OkhttpCore(int actionId, String url, Context context) {
        this.context = context;
        this.url = url;
        this.id = actionId;
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {

                if (msg.what == id) {
                    switch (msg.arg1) {
                        case MSG_TYPE_SUCCESS:
                            // 成功获得消息
                            if (listener != null) {
                                listener.onActionSuccess(id, msg
                                        .getData().getString(MSG_KEY_PARAM));
                            }
                            break;
                        case MSG_TYPE_SERVER_FILED:
                            if (listener != null) {
                                listener.onActionServerFailed(id, msg.arg2);
                            }
                            break;
                        case MSG_TYPE_EXCEPTION:
                            if (listener != null) {
                                listener.onActionException(id, msg.getData()
                                        .getString(MSG_KEY_EXCEPTION));
                            }
                            break;
                    }
                }
                super.handleMessage(msg);
            }
        };
    }

    public void setRequestMethod(int requestMethod) {
        this.requestMethod = requestMethod;
    }

    public void setParam(OkhttpParam param) {
        this.param = param;
    }

    public void setOnActionListener(OnActionListener l) {
        listener = l;
    }

    /**
     * 开启线程
     */
    public void startAction() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
//            OkHttpClient okHttpClient = new OkHttpClient.Builder().sslSocketFactory(MySSLSocketFactory.getSocketFactory(context)).build();
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = null;
            if (requestMethod == OkhttpUtils.POST) {
                //POST request
                if (param != null) {
//                    FormBody.Builder builder = new FormBody.Builder();//构建参数
                    JSONObject jsonObject = new JSONObject();
                    Map<String, String> stringParams = param.getStringParams();
                    if (stringParams != null && stringParams.size() > 0) {
                        //在字符串集合中有数据
                        Iterator<Map.Entry<String, String>> iterator = stringParams.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, String> next = iterator.next();
                            jsonObject.put(next.getKey(), next.getValue());
                        }
                    }
                    RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
                    request = new Request.Builder().url(url).post(requestBody).build();
                    Log.d("Debug POST-->", url + param != null ? url + param.getRequestParam() : url);
                } else {
                    //GET请求,
                    request = new Request.Builder().url(url).build();
                    Log.d("Debug GET1-->", url);
                }
            } else if (requestMethod == OkhttpUtils.GET) {
                //GET request
                if (param != null) {
                    request = new Request.Builder().url(url + param.getRequestParam()).build();
                    Log.d("Debug GET2-->", url + param.getRequestParam());
                } else {
                    request = new Request.Builder().url(url).build();
                    Log.d("Debug GET2-->", url);
                }
            } else if (requestMethod == OkhttpUtils.FILE) {
                // upload file
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                Map<String, String> fileParams = param.getFileParams();
                if (fileParams != null && fileParams.size() > 0) {
                    // byte array
                    Iterator<Map.Entry<String, String>> iterator = fileParams.entrySet().iterator();
                    while (iterator.hasNext()) {
                        int i = 0;//定义一个变量，用于标记二进制流是哪个
                        List<ByteData> byteDatas = param.getByteDatas();
                        RequestBody requestBody = null;
                        if (byteDatas != null && byteDatas.size() > 0) {//暂未规定字节是图片
                            requestBody = RequestBody.create(MediaType.parse(/*"application/octet-stream"*/"image/jpg"), byteDatas.get(i++).getData());
                            Log.d("Debug", "传图片");
                        } else {
                            File file = param.getFile();
                            if (file != null && file.exists()) {//暂未规定字节是图片
                                requestBody = RequestBody.create(MediaType.parse("application/octet-stream"/*"image/jpg"*/), file);
                                Log.d("Debug", "传文件");
                            }
                        }
                        Map.Entry<String, String> next = iterator.next();
                        builder.addPart(Headers.of("Content-Disposition", "form-data;name=\"" + next.getKey() + "\";filename=\"" + next.getValue() + "\"")
                                , requestBody);
                    }

                    Map<String, String> stringParams = param.getStringParams();
                    if (stringParams != null && stringParams.size() > 0) {
                        //说明在传图片的时候还带了参数
                        Iterator<Map.Entry<String, String>> iterator1 = stringParams.entrySet().iterator();
                        while (iterator1.hasNext()) {
                            Map.Entry<String, String> next = iterator1.next();
                            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + next.getKey() + "\""), RequestBody.create(null, next.getValue() + ""));
                        }
                    }
                    request = new Request.Builder().url(url).post(builder.build()).build();
                    Log.d("Debug", "执行有图片的请求,url:" + url);
                } else {
                    //do not have byte array
                    Map<String, String> stringParams = param.getStringParams();
                    if (stringParams != null && stringParams.size() > 0) {
                        FormBody.Builder byteBuilder = new FormBody.Builder();//构建参数
                        //在字符串集合中有数据
                        Iterator<Map.Entry<String, String>> iterator = stringParams.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, String> next = iterator.next();
                            byteBuilder.add(next.getKey(), next.getValue());
                        }
                        request = new Request.Builder().url(url).post(byteBuilder.build()).build();
                        Log.d("Debug", "执行没有图片的请求，但有参数");
                    }
                }

                Log.d("Debug", "upload file execute");
            }

            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                //请求成功
                Message msg = Message.obtain();
                msg.what = id;
                msg.arg1 = MSG_TYPE_SUCCESS;
                Bundle data = new Bundle();
                String string = response.body().string();
                Log.d("Debug", "Result:" + string);
                data.putString(MSG_KEY_PARAM, string);
                msg.setData(data);
                handler.sendMessage(msg);
            } else {
                Message msg = Message.obtain();
                msg.what = id;
                msg.arg1 = MSG_TYPE_SERVER_FILED;
                msg.arg2 = response.code();
                handler.sendMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Message msg = Message.obtain();
            msg.what = id;
            msg.arg1 = MSG_TYPE_EXCEPTION;
            Bundle data = new Bundle();
            data.putString(MSG_KEY_EXCEPTION, e.getMessage());
            msg.setData(data);
            handler.sendMessage(msg);
        }
    }
}
