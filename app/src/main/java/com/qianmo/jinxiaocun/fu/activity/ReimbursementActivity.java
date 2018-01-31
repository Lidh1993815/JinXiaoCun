package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.okhttp.OkhttpUtils;
import com.qianmo.jinxiaocun.main.okhttp.base.OkHttpServletUtils;
import com.qianmo.jinxiaocun.main.okhttp.listener.OnActionListener;
import com.qianmo.jinxiaocun.main.okhttp.params.ByteData;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 报销界面
 */
public class ReimbursementActivity extends BaseActivity implements OnItemClickListener, OnActionListener {

    @BindView(R.id.rv_add_reim)
    RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter = null;//数据适配器
    private ArrayList<String> datas = new ArrayList<>();
    private static final String TAG = "ReimbursementActivity";
    private static final int REQUEST_CODE_SELECT = 6666;
    private ArrayList<ImageItem> mImages;
    private ArrayList<String> choosedImageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "报销", "提交");
        setContentView(requestView(R.layout.activity_reimbursement));
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    protected void rightTextAction() {
        String path = choosedImageList.get(0);
        uploadImage(path);//上传图片
    }

    private void uploadImage(String path) {
        OkhttpParam okhttpParam = new OkhttpParam();
//        okhttpParam.putString("file", path);
//        okhttpParam.putFileString("MultipartFile", path);
//        OkhttpUtils.sendRequest(1001,OkhttpUtils.FILE, ApiConfig.UPLOAD,okhttpParam,this);

        //上传
//        OkhttpParam param = new OkhttpParam();
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        okhttpParam.putFileString("file", System.currentTimeMillis() + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        List<ByteData> list = new ArrayList<>();
        ByteData byteData = new ByteData();
        byteData.setData(baos.toByteArray());
        list.add(byteData);
        //字节流（将图片转换成字节流）
//        okhttpParam.setFile(new File(path));
//        okhttpParam.putFileString("file", System.currentTimeMillis() + ".jpg");
        okhttpParam.setByteDatas(list);
        OkhttpUtils.sendRequest(1001, OkhttpUtils.FILE, ApiConfig.UPLOAD, okhttpParam, this);


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
    public void requestInit() {

    }

    //显示选择打开相册还是拍照的对话框
    private void showPhoDialog() {
        new AlertView("上传图片", null, "取消", null,
                new String[]{"拍照", "从相册中选择"},
                this, AlertView.Style.ActionSheet, this).show();
    }

    @Override
    public void onItemClick(Object o, int position) {
        if (position == 0) {
            takePhotos();
        } else if (position == 1) {
            chooseAlbum();
        }
    }


    //拍照
    private void takePhotos() {
        //打开选择,本次允许选择的数量
        ImagePicker.getInstance().setSelectLimit(1);
        Intent intent = new Intent(this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    //相册
    private void chooseAlbum() {
        //打开选择,本次允许选择的数量
        ImagePicker.getInstance().setSelectLimit(1);
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    @Override
    public void onActionSuccess(int actionId, String ret) {
        switch (actionId) {
            case 1001:
                Log.i(TAG, "onActionSuccess: " + ret);
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

    //设置RecycleView的适配器
    private class TaskAdapter extends ListBaseAdapter<String> {

        public TaskAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.fu_reim_add_item;
        }

        @Override
        public void onBindItemHolder(final SuperViewHolder holder, final int position) {
            //点击添加新的提交内容
            holder.getView(R.id.add_reim_detail_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addItems(datas);
                }
            });
            //添加图片
            holder.getView(R.id.iv_reim_addImg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击弹出选择获取图片的方式
//                    showChooseBottomWindow();
                    showPhoDialog();
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
            LinearLayout layout = holder.getView(R.id.add_reim_detail_layout);
            if (position == mTaskAdapter.getDataList().size() - 1) {
                layout.setVisibility(View.VISIBLE);
            } else {
                layout.setVisibility(View.GONE);
            }
            TextView positionText = holder.getView(R.id.reim_detail_position);
            int num = position + 1;
            positionText.setText("报销明细（" + num + "）");//设置显示的文字
            //设置选择图片后的回调。每一个pictureRecyclerView都需要新的对象，否则容易出乱
            setOnRefreshCallback(new RefreshCallback() {
                @Override
                public void onRefresh(ArrayList<ImageItem> imageItems) {
                    //获取显示图片的recyclerView
                    setupInnerRecyclerView(imageItems, holder);
                    choosedImageList.add(imageItems.get(0).path);
                }
            });
        }

    }

    private void setupInnerRecyclerView(ArrayList<ImageItem> imageItems, SuperViewHolder holder) {
        RecyclerView pictureRecyclerView = holder.getView(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ReimbursementActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        pictureRecyclerView.setLayoutManager(linearLayoutManager);
        PictureAdapter pictureAdapter = new PictureAdapter(ReimbursementActivity.this);
        pictureRecyclerView.setAdapter(pictureAdapter);
        pictureAdapter.setDataList(imageItems);
        pictureAdapter.notifyDataSetChanged();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (mRefreshCallback != null) {
                    mRefreshCallback.onRefresh(imageItems);
                }
//                String path = imageItems.get(0).path;//获取文件的路径

            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }


    class PictureAdapter extends ListBaseAdapter<ImageItem> {

        public PictureAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_picture;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {
            ImageItem imageItem = mDataList.get(position);
            ImageView imageView = holder.getView(R.id.iv_show_image);
            Glide.with(ReimbursementActivity.this).load(imageItem.path).into(imageView);
        }
    }

    private RefreshCallback mRefreshCallback;

    interface RefreshCallback {
        void onRefresh(ArrayList<ImageItem> imageItems);
    }

    private void setOnRefreshCallback(RefreshCallback refreshCallback) {
        mRefreshCallback = refreshCallback;
    }

}
