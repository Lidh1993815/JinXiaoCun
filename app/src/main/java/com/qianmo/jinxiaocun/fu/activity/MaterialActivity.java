package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.Contents;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.bean.AddApplyMaterialBean;
import com.qianmo.jinxiaocun.fu.bean.PeopleInfoBean;
import com.qianmo.jinxiaocun.fu.bean.ResponseBean;
import com.qianmo.jinxiaocun.fu.utils.JsonUitl;
import com.qianmo.jinxiaocun.fu.utils.SPUtil;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.okhttp.OkhttpUtils;
import com.qianmo.jinxiaocun.main.okhttp.listener.OnActionListener;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 物料申请界面
 */
public class MaterialActivity extends BaseActivity implements OnActionListener {

    @BindView(R.id.rv_material_apply)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_avatar_name)
    TextView mTvAvatarName;
    @BindView(R.id.avatar)
    FrameLayout mAvatar;
    @BindView(R.id.img_add_people)
    ImageView mImgAddPeople;
    @BindView(R.id.ll_apply_approval)
    LinearLayout mLlApplyApproval;
    private TaskAdapter mTaskAdapter = null;//数据适配器
    private ArrayList<String> datas = new ArrayList<>();
    private int size;
    private static final String TAG = "MaterialActivity";
    private AddApplyMaterialBean.ApplyMaterielsBean[] mApplyMaterielsData;
    private static final int INTENT_CHOOSE_PEOPLE = 102;
    private String mStaffId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "物料申请", "提交");
        setContentView(requestView(R.layout.activity_material));
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    protected void rightTextAction() {
        super.rightTextAction();
        mApplyMaterielsData = new AddApplyMaterialBean.ApplyMaterielsBean[size];
        //获取填写的数据
        for (int i = 0; i < size; i++) {
            View child = mRecyclerView.getChildAt(i);
            EditText applyMaterialName = child.findViewById(R.id.tv_applyMaterial_name);
            EditText applyMaterialNum = child.findViewById(R.id.tv_applyMaterial_num);
            EditText applyMaterialUse = child.findViewById(R.id.tv_applyMaterial_use);
            String materialName = applyMaterialName.getText().toString().trim();
            if (TextUtils.isEmpty(materialName)) {
                ToastUtils.MyToast(this, "请输入物品名称！");
                return;
            }
            String materialNum = applyMaterialNum.getText().toString().trim();
            if (TextUtils.isEmpty(materialNum)) {
                ToastUtils.MyToast(this, "请输入物品数量！");
                return;
            }
            String materialUse = applyMaterialUse.getText().toString().trim();
            if (TextUtils.isEmpty(materialUse)) {
                ToastUtils.MyToast(this, "请输入物品用途！");
                return;
            }
            mApplyMaterielsData[i] = new AddApplyMaterialBean.ApplyMaterielsBean(materialName, materialNum, materialUse);
        }
        if (TextUtils.isEmpty(mStaffId)) {
            ToastUtils.MyToast(this,"请选择审核人员！");
            return;
        }
        requestData();
    }

    @Override
    public void requestInit() {

    }

    @OnClick({R.id.img_add_people})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.img_add_people:
                Intent intent = new Intent(this, ChoosePeopleActivity.class);
                startActivityForResult(intent, INTENT_CHOOSE_PEOPLE);
                break;
        }
    }

    @Override
    public void requestData() {
        super.requestData();
        OkhttpParam okhttpParam = new OkhttpParam();
        okhttpParam.putString("aType", Contents.MATERIAL);
        okhttpParam.putString("staffId", SPUtil.getInstance().getStaffId());
        okhttpParam.putString("aAuditor", mStaffId);

        // 设置对象数组转JSON串
        String jsonString = JSON.toJSONString(mApplyMaterielsData);
        // JSON串转设置对象列表
        List<AddApplyMaterialBean.ApplyMaterielsBean> applyMaterielBeans = JSON.parseArray(jsonString, AddApplyMaterialBean.ApplyMaterielsBean.class);
        Log.i(TAG, "requestData: " + JSONArray.toJSONString(applyMaterielBeans));
        okhttpParam.putString("applyMateriels", JSONArray.toJSONString(applyMaterielBeans));
        OkhttpUtils.sendRequest(1001, 1, ApiConfig.ADD_APPLY_MATERIEL, okhttpParam, this);

    }

    private void initData() {
        datas.add("");
    }

    private void initView() {
        mTaskAdapter = new TaskAdapter(this);
        mTaskAdapter.addAll(datas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mTaskAdapter);
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onActionSuccess(int actionId, String ret) {
        switch (actionId) {
            case 1001:
                Log.i(TAG, "onActionSuccess: " + ret);
                if (!TextUtils.isEmpty(ret)) {
                    ResponseBean responseBean = (ResponseBean) JsonUitl.stringToObject(ret, ResponseBean.class);
                    if (responseBean != null) {
                        String state = responseBean.getState();
                        if (state.equals("00000")) {
                            ToastUtils.MyToast(MaterialActivity.this, "申请成功！");
                            finish();
                        } else {
                            ToastUtils.MyToast(this, responseBean.getMsg());
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onActionServerFailed(int actionId, int httpStatus) {
        Log.i(TAG, "onActionServerFailed: " + httpStatus);
    }

    @Override
    public void onActionException(int actionId, String exception) {
        Log.i(TAG, "onActionException: " + exception);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                /*case INTENT_CHOOSE_TYPE:
                    mLeaveType = data.getIntExtra("leaveType", 0);
                    String leaveTitle = data.getStringExtra("leaveTitle");
                    if (!TextUtils.isEmpty(leaveTitle)) {
                        mTvLeaveShowType.setText(leaveTitle);
                    }
                    break;*/
                case INTENT_CHOOSE_PEOPLE:
                    ArrayList<PeopleInfoBean.DataBean> parcelableArrayListExtra = data.getParcelableArrayListExtra(("peoplesInfo"));
                    PeopleInfoBean.DataBean dataBean = parcelableArrayListExtra.get(0);
                    String staffName = dataBean.getStaffName();
                    int staffId = dataBean.getStaffId();
                    if (staffId == 0) {
                        mStaffId = "";
                    } else {
                        mStaffId = "" + staffId;
                    }
                    if (!TextUtils.isEmpty(staffName)) {
                        mAvatar.setVisibility(View.VISIBLE);

                        String substring = staffName.substring(staffName.length() - 2, staffName.length());
                        if (!TextUtils.isEmpty(substring)) {
                            mTvAvatarName.setText(substring);
                        }

                    } else {
                        mAvatar.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    }


    //设置RecycleView的适配器
    private class TaskAdapter extends ListBaseAdapter<String> {

        public TaskAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.fu_material_apply_item;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, final int position) {
            holder.getView(R.id.add_material_used).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addItems(datas);
                }
            });
            TextView deleteText = holder.getView(R.id.tv_delete_action);
            if (position == 0) {
                //第一个item不显示删除选项，第一个永远都存在
                deleteText.setVisibility(View.GONE);
            } else {
                deleteText.setVisibility(View.VISIBLE);
            }
            //删除布局
            deleteText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //删除布局
                    removeItem(position);
                }
            });

            //让“添加报销明细”显示隐藏
            LinearLayout layout = holder.getView(R.id.add_material_used);
            if (position == mTaskAdapter.getDataList().size() - 1) {
                layout.setVisibility(View.VISIBLE);
            } else {
                layout.setVisibility(View.GONE);
            }
            TextView positionText = holder.getView(R.id.material_detail_position);
            int num = position + 1;
            positionText.setText("报销明细（" + num + "）");//设置显示的文字
            size = mDataList.size();
        }

    }

    //模拟添加数据，为了增加layout的数量
    private void addItems(ArrayList<String> list) {
        mTaskAdapter.addAll(list);
        mTaskAdapter.notifyDataSetChanged();
    }

    //删除指定位置的布局，并刷新全部的item
    private void removeItem(int position) {
        mTaskAdapter.remove(position);
        mTaskAdapter.notifyDataSetChanged();
    }
}
