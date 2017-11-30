package com.qianmo.jinxiaocun.fu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.bean.CityInfoBean;
import com.qianmo.jinxiaocun.fu.fragment.AreaFragment;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 切换门店界面
 */
public class SwitchShopActivity extends BaseActivity implements AreaFragment.OnFragmentInteractionListener{
    private Fragment oneFragment;
    private Fragment twoFragment;
    private int isLeaf=3;
    private static final String TAG = "AreaSelectActivity";
    private Map<String,Integer> cityCode = new HashMap<>();
    private Map<String,String> cityName = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "切换门店", -1);
        setContentView(requestView(R.layout.activity_switch_shop));
        oneFragment = AreaFragment.newInstance("","");
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content,oneFragment).commit();
    }

    @Override
    public void requestInit() {

    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount()>0){
                    fragmentManager.popBackStack();
                }else{
                    finish();
                }
                break;
        }
        return true;
    }*/

    @Override
    public void onFragmentInteraction(CityInfoBean.ResultListBean areaInfo) {
        if (areaInfo==null){
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int level = areaInfo.getType();
        switch (level){
            case 1:
                cityCode.put("provCode",areaInfo.getCode());
                cityName.put("provName",areaInfo.getFullName());
                if (areaInfo.getType()==isLeaf){
                    Intent intent = new Intent();
                    intent.putExtra("addressCode", (Serializable) cityCode);
                    intent.putExtra("addressName", (Serializable) cityName);
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    transaction.hide(oneFragment);
                    transaction.add(R.id.content,twoFragment=AreaFragment.newInstance(areaInfo.getCode()+"","市")).addToBackStack(null).commit();
                }
                break;
            case 2:
                cityCode.put("cityCode",areaInfo.getCode());
                cityName.put("cityName",areaInfo.getFullName());
                if (areaInfo.getType()==isLeaf){
                    Intent intent = new Intent();
                    intent.putExtra("addressCode", (Serializable) cityCode);
                    intent.putExtra("addressName", (Serializable) cityName);
                    setResult(RESULT_OK,intent);
                    finish();
                }else {
                    transaction.hide(twoFragment);
                    transaction.add (R.id.content, AreaFragment.newInstance(areaInfo.getCode()+"","")).addToBackStack(null).commit();
                }
                break;
            case 3:
                cityCode.put("countryCode",areaInfo.getCode());
                cityName.put("countryName",areaInfo.getFullName());
                Intent intent = new Intent();
                intent.putExtra("addressCode", (Serializable) cityCode);
                intent.putExtra("addressName", (Serializable) cityName);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }
}
