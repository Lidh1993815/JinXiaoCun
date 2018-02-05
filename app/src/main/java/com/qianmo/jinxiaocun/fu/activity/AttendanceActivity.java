package com.qianmo.jinxiaocun.fu.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceClient;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.DPoint;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.widget.AttendanceButton;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 考勤界面
 */
public class AttendanceActivity extends BaseActivity {

    @BindView(R.id.attendance_button)
    AttendanceButton attendanceButton;
    private boolean isWork = false;
    private static final int REQUEST_CODE = 222;
    private static final String TAG = "AttendanceActivity";
    public static final String GEOFENCE_BROADCAST_ACTION = "com.location.apis.geofencedemo.broadcast";
    private double mLatitude;//定位的纬度
    private double mLongitude;//定位的经度

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            Log.d(TAG, "onLocationChanged: 被回调了！");
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //定位成功，可在其中解析amapLocation获取相应内容。
                    //获取纬度
                    mLatitude = aMapLocation.getLatitude();
                    //获取经度
                    mLongitude = aMapLocation.getLongitude();
                    Log.d(TAG, "纬度: " + mLatitude);
                    Log.d(TAG, "经度: " + mLongitude);
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };
    private GeoFenceClient mGeoFenceClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "考勤", "记录");

        setContentView(requestView(R.layout.activity_attendance));
        ButterKnife.bind(this);
        methodRequiresPermission();//申请权限
        initLocation();//初始化定位
        initGeoFence();//初始化地理围栏,用来判断打卡的状态
    }

    private void initGeoFence() {
        mGeoFenceClient = new GeoFenceClient(getApplicationContext());
        //创建一个中心点坐标
        DPoint centerPoint = new DPoint();
        //设置中心点纬度
        centerPoint.setLatitude(mLatitude);
        //设置中心点经度
        centerPoint.setLongitude(mLongitude);
        //执行添加围栏的操作
        mGeoFenceClient.addGeoFence( centerPoint, 500F,  "自有ID");
        //创建回调监听
        GeoFenceListener fenceListenter = new GeoFenceListener() {

            @Override
            public void onGeoFenceCreateFinished(List<GeoFence> list, int i, String s) {
                if(i == GeoFence.ADDGEOFENCE_SUCCESS){//判断围栏是否创建成功
                    Log.d(TAG, "添加围栏成功!!");
                    //geoFenceList就是已经添加的围栏列表，可据此查看创建的围栏
                } else {
                    //geoFenceList就是已经添加的围栏列表
                    Log.d(TAG, "添加围栏失败!!");
                }
            }
        };
        //设置回调监听
        mGeoFenceClient.setGeoFenceListener(fenceListenter);
//创建并设置PendingIntent
        mGeoFenceClient.createPendingIntent(GEOFENCE_BROADCAST_ACTION);

    }

    //新建广播接收者，用来判断用户是否在围栏里
    private BroadcastReceiver mGeoFenceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(GEOFENCE_BROADCAST_ACTION)) {
                //解析广播内容
            }
        }
    };

    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        AMapLocationClientOption option = new AMapLocationClientOption();
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);//设置定位场景为签到
        /*option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//设置定位模式为高精度

        //获取一次定位结果：
        //该方法默认为false。
        option.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        option.setOnceLocationLatest(true);*/

        if (null != mLocationClient) {
            mLocationClient.setLocationOption(option);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
    }


    @AfterPermissionGranted(REQUEST_CODE)//添加注解，是为了首次执行权限申请后，回调该方法
    private void methodRequiresPermission() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            //已经申请过权限，开启定位
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        } else {
            EasyPermissions.requestPermissions(this, "需要获取权限",
                    REQUEST_CODE, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @OnClick({R.id.attendance_button})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.attendance_button:
                isWork = !isWork;
                if (isWork) {
                    attendanceButton.setContentText("下班打卡");
                } else {
                    attendanceButton.setContentText("上班打卡");
                }
                break;
        }
    }

    //toolbar右侧的点击事件
    @Override
    protected void rightTextAction() {
        startActivity(CardRecordActivity.class, false);
    }

    @Override
    public void requestInit() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationListener = null;
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }
}
