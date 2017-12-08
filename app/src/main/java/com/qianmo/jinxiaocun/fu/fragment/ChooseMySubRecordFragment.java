package com.qianmo.jinxiaocun.fu.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.Contents;
import com.qianmo.jinxiaocun.fu.bean.CityInfoBean;
import com.qianmo.jinxiaocun.fu.utils.JsonUitl;
import com.qianmo.jinxiaocun.main.base.BaseFragment;
import com.qianmo.jinxiaocun.main.okhttp.OkhttpUtils;
import com.qianmo.jinxiaocun.main.okhttp.listener.OnActionListener;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author : wizardev
 * e-mail : wizarddev@163.com
 * time   : 2017/09/18
 * desc   : 选择下属界面
 * version: 1.0
 */
public class ChooseMySubRecordFragment extends BaseFragment implements OnActionListener {
    @BindView(R.id.tv_search)
    TextView mTvSearch;
    @BindView(R.id.et_search_content)
    EditText mEtSearchContent;

    Unbinder unbinder;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    private boolean isStartAnimate = false;
    private int curValue;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.refresh_list_view)
    ListView mRefreshListView;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;
    private String mParam = "100000";
    private String city;
    private static final String TAG = "ChooseMySubRecord";
    private List<CityInfoBean.ResultListBean> citiesInfo = new ArrayList<>();
    private AreaAdapter adapter;
    private List<CityInfoBean.ResultListBean> resultList;
    private CityInfoBean.ResultListBean mSelectCityInfo;
    private int mType;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = requestView(inflater, R.layout.choose_my_sub_record_fragment);
        requestData();//初始化数据
        initEvent();
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void requestInit() {

    }

    @Override
    public void requestData() {
        OkhttpParam okhttpParam = new OkhttpParam();
        okhttpParam.putString("cityCode", mParam);
        OkhttpUtils.sendRequest(1001, 1, Contents.GET_CITY_LIST, okhttpParam, this);
    }

    private void initEvent() {

    }

    private void initView() {
//        mTabLayout.addTab(mTabLayout.newTab().setCustomView(R.layout.directory_tab_view).setText(people.getName()));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(R.layout.directory_tab_view_without_arrow).setText("我下属的考勤"));
    }


    @OnClick({R.id.tv_search})
    public void clickAction(View view) {

        switch (view.getId()) {
            case R.id.tv_search:

                if (!isStartAnimate) {
                    doAnimation();
                }
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActionSuccess(int actionId, String ret) {
        Log.i(TAG, "onActionSuccess: " + ret);
        if (ret != null && ret.length() > 0) {
            CityInfoBean cityInfo = (CityInfoBean) JsonUitl.stringToObject(ret, CityInfoBean.class);
            int status = cityInfo.getCode();
            switch (actionId) {
                //请求数据
                case 1001:
                    if (status == 100) {
                        resultList = cityInfo.getResultList();
                        if (this.resultList.size() > 0) {
                            adapter = new AreaAdapter(getContext(), this.resultList);
                            mRefreshListView.setAdapter(adapter);
                            loadingProgress.setVisibility(View.GONE);
                        } else {
                            ToastUtils.MyToast(getActivity(), "暂无数据");
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void onActionServerFailed(int actionId, int httpStatus) {
    }

    @Override
    public void onActionException(int actionId, String exception) {
    }

    class AreaAdapter extends BaseAdapter {

        private List list;
        private Context context;
        private int lastPosition;

        public AreaAdapter(Context context, List<CityInfoBean.ResultListBean> list) {
            this.list = list;
            this.context = context;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.fu_area_list_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.addressName = (TextView) convertView.findViewById(R.id.addressName);
                viewHolder.subAddress = (TextView) convertView.findViewById(R.id.sub);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            CityInfoBean.ResultListBean item = (CityInfoBean.ResultListBean) list.get(position);
            viewHolder.addressName.setText(item.getFullName());
            if (lastPosition < position && lastPosition != 0) {
                //设置位移动画，从间隔为view的两倍移动到0
                ObjectAnimator.ofFloat(convertView, "translationY", convertView.getHeight() * 2, 0).setDuration(500).start();
            }
            lastPosition = position;
            viewHolder.subAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectCityInfo= (CityInfoBean.ResultListBean) getItem(position);
                    showCityWithNone();
                }
            });
            return convertView;
        }


    }

    private void showCityWithNone() {
        mType = mSelectCityInfo.getType();
        if (mType == 3) {
            return;
        }
        citiesInfo.add(mSelectCityInfo);
        mTabLayout.removeAllTabs();



        int size = citiesInfo.size();
        if (size >= 1) {
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(R.layout.directory_tab_view).setText("我下属的考勤"));
        }
        for (int i = 0; i < size; i++) {
            CityInfoBean.ResultListBean cityInfo = citiesInfo.get(i);
            if (i == size - 1) {
                mTabLayout.addTab(mTabLayout.newTab().setCustomView(R.layout.directory_tab_view_without_arrow)
                        .setText(cityInfo.getName()),true);
            } else {
                mTabLayout.addTab(mTabLayout.newTab().setCustomView(R.layout.directory_tab_view)
                        .setText(cityInfo.getName()), false);
            }

        }
        mParam = mSelectCityInfo.getCode() + "";
        requestData();
    }

    class ViewHolder {
        TextView addressName;
        TextView subAddress;
    }
}
