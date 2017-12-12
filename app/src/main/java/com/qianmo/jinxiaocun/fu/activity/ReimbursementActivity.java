package com.qianmo.jinxiaocun.fu.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.utils.FilesUtils;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 报销界面
 */
public class ReimbursementActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.rv_add_reim)
    RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter = null;//数据适配器
    private ArrayList<String> datas = new ArrayList<>();
    private static final String TAG = "ReimbursementActivity";
    private NiceDialog mNiceDialog;
    private Uri imageUri;
    private static final int REQUEST_CODE = 101;
    private static final int REQUEST_CAPTURE = 102;
    private static final int REQUEST_PICTURE = 105;
    private static final int RESULT_CROP = 107;
    private static final int GALLERY_ACTIVITY_CODE = 109;
    private Uri localUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "报销", "提交");
        setContentView(requestView(R.layout.activity_reimbursement));
        ButterKnife.bind(this);
        initData();
        initView();


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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_from_camera:
                //打开相机
                openCamera();
                mNiceDialog.dismiss();
                break;
            case R.id.open_album:
                Intent gallery_Intent = new Intent(Intent.ACTION_PICK, null);
                gallery_Intent.setType("image/*");
                startActivityForResult(gallery_Intent, GALLERY_ACTIVITY_CODE);
                mNiceDialog.dismiss();
                break;
            case R.id.cancel:
                mNiceDialog.dismiss();
                break;
        }
    }

    private void openCamera() {  //调用相机拍照
        Intent intent = new Intent();
        File file = FilesUtils.instance(this).getOutputMediaFile();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {  //针对Android7.0，需要通过FileProvider封装过的路径，提供给外部调用
            imageUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);//通过FileProvider创建一个content类型的Uri，进行封装
        } else { //7.0以下，如果直接拿到相机返回的intent值，拿到的则是拍照的原图大小，很容易发生OOM，所以我们同样将返回的地址，保存到指定路径，返回到Activity时，去指定路径获取，压缩图片

            imageUri = Uri.fromFile(file);
        }
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
        startActivityForResult(intent, REQUEST_CAPTURE);//启动拍照
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
        public void onBindItemHolder(SuperViewHolder holder, final int position) {
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
                    showChooseBottomWindow();
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
            if (position == mTaskAdapter.getDataList().size()-1) {
                layout.setVisibility(View.VISIBLE);
            } else {
                layout.setVisibility(View.GONE);
            }
            TextView positionText = holder.getView(R.id.reim_detail_position);
            int num = position + 1;
            positionText.setText("报销明细（"+num+"）");//设置显示的文字
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

    private void showChooseBottomWindow() {
        mNiceDialog = NiceDialog.init();
        mNiceDialog.setLayoutId(R.layout.bottom_window_choose)     //设置dialog布局文件
                .setConvertListener(new ViewConvertListener() {     //进行相关View操作的回调
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        TextView camera = holder.getView(R.id.open_from_camera);
                        camera.setOnClickListener(ReimbursementActivity.this);
                        TextView album = holder.getView(R.id.open_album);
                        album.setOnClickListener(ReimbursementActivity.this);
                        TextView cancel = holder.getView(R.id.cancel);
                        cancel.setOnClickListener(ReimbursementActivity.this);
                    }
                })
                .setDimAmount(0.5f)     //调节灰色背景透明度[0-1]，默认0.5f-
                .setShowBottom(true)     //是否在底部显示dialog，默认flase
                .setOutCancel(true)     //点击dialog外是否可取消，默认true
                .setAnimStyle(R.style.MyPopWindowAnim)     //设置dialog进入、退出的动画style(底部显示的dialog有默认动画)
                .show(getSupportFragmentManager());     //显示dialog
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CAPTURE:
                    if (null != imageUri) {
                        localUri = imageUri;
                        performCrop(localUri);
                    }
                    break;
                case REQUEST_PICTURE:
                    localUri = data.getData();
                    performCrop(localUri);
                    break;
                case RESULT_CROP:
                    Bundle extras = data.getExtras();
                    Bitmap selectedBitmap = extras.getParcelable("data");
                    //判断返回值extras是否为空，为空则说明用户截图没有保存就返回了，此时应该用上一张图，
                    //否则就用用户保存的图
                    if (extras != null) {

                        FilesUtils.instance(this).storeImage(selectedBitmap);
                        //  storeImage(selectedBitmap);
//                        uploadImg2QiNiu();
                    }
                    break;
                case GALLERY_ACTIVITY_CODE:
                    localUri = data.getData();
                    //  setBitmap(localUri);
                    performCrop(localUri);
                    break;
            }
        }
    }

    //裁剪图片
    private void performCrop(Uri uri) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                grantUriPermission("com.android.camera", uri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 400);
            intent.putExtra("outputY", 300);
            intent.putExtra("return-data", true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FilesUtils.instance(this).getOutputMediaFile().toString());
            startActivityForResult(intent, RESULT_CROP);
        } catch (ActivityNotFoundException anfe) {
            String errorMessage = "你的设备不支持裁剪行为！";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
