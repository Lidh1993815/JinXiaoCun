package com.qianmo.jinxiaocun.fu.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.Contents;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.bean.PeopleInfoBean;
import com.qianmo.jinxiaocun.fu.decoration.TitleItemDecoration;
import com.qianmo.jinxiaocun.fu.utils.JsonUitl;
import com.qianmo.jinxiaocun.fu.utils.PinyinComparator;
import com.qianmo.jinxiaocun.fu.utils.SPUtil;
import com.qianmo.jinxiaocun.fu.widget.LoadingDialog;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.okhttp.OkhttpUtils;
import com.qianmo.jinxiaocun.main.okhttp.listener.OnActionListener;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择人员界面
 */
public class ChoosePeopleActivity extends BaseActivity implements OnActionListener {

    @BindView(R.id.tv_search)
    TextView mTvSearch;
    @BindView(R.id.et_search_content)
    EditText mEtSearchContent;
    @BindView(R.id.edit_parent_layout)
    RelativeLayout mEditParentLayout;
    @BindView(R.id.rv_choosePeople)
    RecyclerView mRecyclerView;
    @BindView(R.id.rv_choosePeople_bottom)
    RecyclerView mRvChoosePeopleBottom;
    @BindView(R.id.tv_total_num)
    TextView mTvTotalNum;
    private boolean isStartAnimate = false;
    private int curValue;
    PeopleNameAdapter mDataAdapter;
    LuRecyclerViewAdapter mLuRecyclerViewAdapter;
    private PinyinComparator mComparator;
    private List<PeopleInfoBean.DataBean> mDateList;
    private int mCurrentPage = 1;
    private static final int REQUEST_COUNT = 100;
    private static final String TAG = "ChoosePeopleActivity";
    private LoadingDialog mLoadingDialog;
    private ChoosePeopleAdapter mPeopleAdapter;
    private List<PeopleInfoBean.DataBean> mSelectPeopleList = new ArrayList<>();
    private int mChooseType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChooseType = getIntent().getIntExtra("chooseType", 0);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "选择人员", -1);
        setContentView(requestView(R.layout.activity_choose_people));
        ButterKnife.bind(this);
        initView();
        requestData();//获取可以选择的人员
    }

    private void initView() {
        mLoadingDialog = new LoadingDialog(this, "数据加载中...");

        mComparator = new PinyinComparator();//实例化比较器
        mDataAdapter = new PeopleNameAdapter(this);
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(mDataAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(ChoosePeopleActivity.this, DividerItemDecoration.VERTICAL));
        mLuRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //  isSureChoose = !isSureChoose;
                //  Log.i(TAG, "onItemClick: " + isSureChoose);
                PeopleInfoBean.DataBean dataBean = mDataAdapter.getDataList().get(position);
                if (dataBean.isSelect()) {
                    Log.i(TAG, "点击的id: " + dataBean.getStaffId());
                    mSelectPeopleList.remove(dataBean);//从已经选择人员列表中移除
                    dataBean.setSelect(false);
                } else {
                    mSelectPeopleList.add(dataBean);
                    dataBean.setSelect(true);
                }
                Log.i(TAG, "列表的长度: " + mSelectPeopleList.size());
                mTvTotalNum.setText("确定（" + mSelectPeopleList.size() + "）");
                mPeopleAdapter.setDataList(mSelectPeopleList);
                mPeopleAdapter.notifyDataSetChanged();
                mLuRecyclerViewAdapter.notifyDataSetChanged();

            }
        });


        mPeopleAdapter = new ChoosePeopleAdapter(this);
//        mChoosePeopleAdapter = new LuRecyclerViewAdapter(mPeopleAdapter);
        mRvChoosePeopleBottom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvChoosePeopleBottom.setAdapter(mPeopleAdapter);
    }


    @Override
    public void requestData() {
        super.requestData();
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }
        OkhttpParam okhttpParam = new OkhttpParam();
        okhttpParam.putString(SPUtil.STAFF_ID, SPUtil.getInstance().getStaffId());
        okhttpParam.putString("start", mCurrentPage + "");
        okhttpParam.putString("length", REQUEST_COUNT + "");
        OkhttpUtils.sendRequest(1001, 0, ApiConfig.TASK_STAFF, okhttpParam, this);
    }

    private List<PeopleInfoBean.DataBean> filledData(List<PeopleInfoBean.DataBean> date) {
        List<PeopleInfoBean.DataBean> mSortList = new ArrayList<>();

        for (int i = 0; i < date.size(); i++) {
            PeopleInfoBean.DataBean sortModel = new PeopleInfoBean.DataBean();
            sortModel.setStaffName(date.get(i).getStaffName());
            sortModel.setPostName(date.get(i).getPostName());
            sortModel.setStaffId(date.get(i).getStaffId());
            //汉字转换成拼音
            try {
                String pinyin = PinyinHelper.getShortPinyin(date.get(i).getStaffName());
                String sortString = pinyin.substring(0, 1).toUpperCase();
                // 正则表达式，判断首字母是否是英文字母
                if (sortString.matches("[A-Z]")) {
                    sortModel.setLetters(sortString.toUpperCase());
                } else {
                    sortModel.setLetters("#");
                }
            } catch (PinyinException e) {
                e.printStackTrace();
            }
            mSortList.add(sortModel);
        }
        return mSortList;

    }


    @Override
    public void requestInit() {

    }

    @OnClick({R.id.tv_search, R.id.tv_total_num})
    public void clickAction(View view) {

        switch (view.getId()) {
            case R.id.tv_search:

                if (!isStartAnimate) {
                    doAnimation();
                }
                break;
            case R.id.tv_total_num:
                Intent intent = new Intent();
                if (mChooseType == Contents.ONLY_ONE) {
                    if (mSelectPeopleList.size() > 1) {
                        ToastUtils.MyToast(this, "只能选择一个审批人！");
                        return;
                    }
                }
                intent.putParcelableArrayListExtra("peoplesInfo", (ArrayList<? extends Parcelable>) mSelectPeopleList);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    private void doAnimation() {
        final ValueAnimator vm = ValueAnimator.ofInt((int) mTvSearch.getX(), (int) mEtSearchContent.getX());
        vm.setDuration(200);
        vm.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                curValue = (int) valueAnimator.getAnimatedValue();
                mTvSearch.layout(curValue, 0, curValue + mTvSearch.getWidth(), mTvSearch.getHeight());
            }
        });
        vm.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isStartAnimate = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                vm.removeAllUpdateListeners();
                mTvSearch.setVisibility(View.GONE);
                mEtSearchContent.setVisibility(View.VISIBLE);

                focusAndShowSoftInput();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        vm.start();
    }

    //弹出软件盘
    private void focusAndShowSoftInput() {
        mEtSearchContent.setFocusable(true);
        mEtSearchContent.setFocusableInTouchMode(true);
        mEtSearchContent.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) mEtSearchContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(mEtSearchContent, 0);
    }

    @Override
    public void onActionSuccess(int actionId, String ret) {
        switch (actionId) {
            case 1001:
                Log.i(TAG, "onActionSuccess: " + ret);
                if (!TextUtils.isEmpty(ret)) {
                    PeopleInfoBean peopleInfoBean = (PeopleInfoBean) JsonUitl.stringToObject(ret, PeopleInfoBean.class);
                    if (peopleInfoBean != null) {
                        List<PeopleInfoBean.DataBean> data = peopleInfoBean.getData();
                        mDateList = filledData(data);
                        Collections.sort(mDateList, mComparator);//对服务端获取的数据进行重新排序
                        mDataAdapter.setDataList(mDateList);
                        mRecyclerView.addItemDecoration(new TitleItemDecoration(this, mDateList));
                        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);
//                        notifyDataSetChanged();
                    }
                }
                break;
        }
        if (mLoadingDialog != null) {
            mLoadingDialog.close();
        }

    }

    @Override
    public void onActionServerFailed(int actionId, int httpStatus) {

    }

    @Override
    public void onActionException(int actionId, String exception) {

    }

    private void notifyDataSetChanged() {
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    //已经选择的人员的列表Adapter
    class ChoosePeopleAdapter extends ListBaseAdapter<PeopleInfoBean.DataBean> {
        public ChoosePeopleAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.bottom_choose_people_item;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {
            PeopleInfoBean.DataBean dataBean = mDataList.get(position);

            TextView tvChooseName = holder.getView(R.id.tv_choose_name);
            TextView tvAvatarName = holder.getView(R.id.tv_avatar_name);

            String staffName = dataBean.getStaffName();
            if (!TextUtils.isEmpty(staffName)) {
                tvChooseName.setText(staffName);
                if (staffName.length() > 2) {
                    String substring = staffName.substring(staffName.length() - 2, staffName.length());
                    if (!TextUtils.isEmpty(substring)) {
                        tvAvatarName.setText(substring);
                    }
                }
            }
        }
    }

    //可以选择的人员
    class PeopleNameAdapter extends ListBaseAdapter<PeopleInfoBean.DataBean> {
        public PeopleNameAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.recycle_choose_people_item;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {
            PeopleInfoBean.DataBean dataBean = mDataList.get(position);

            TextView tvName = holder.getView(R.id.tv_name);
            TextView tvAvatarName = holder.getView(R.id.tv_avatar_name);
            TextView positionName = holder.getView(R.id.tv_position);//职位

            String staffName = mDataList.get(position).getStaffName();
            String postName = mDataList.get(position).getPostName();

            ImageView imageView = holder.getView(R.id.iv_chooseStatus);

            if (dataBean.isSelect()) {
                imageView.setImageResource(R.drawable.dagou02);
            } else {
                imageView.setImageResource(R.drawable.dagou);
            }


            if (!TextUtils.isEmpty(staffName)) {
                tvName.setText(staffName);
                String substring = staffName.substring(staffName.length() - 2, staffName.length());
                if (!TextUtils.isEmpty(substring)) {
                    tvAvatarName.setText(substring);
                }
            }
            if (!TextUtils.isEmpty(postName)) {
                positionName.setText(postName);
            }

        }
    }
}
