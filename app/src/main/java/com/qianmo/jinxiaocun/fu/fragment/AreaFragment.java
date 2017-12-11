package com.qianmo.jinxiaocun.fu.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by wizardev on 17-7-14.
 */
public class AreaFragment extends BaseFragment implements  OnActionListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.refresh_list_view)
    ListView mRefreshListView;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;
    private String mParam;
    private String city;
    private static final String TAG = "AreaFragment";
    private OnFragmentInteractionListener mListener;

    private AreaAdapter adapter;
    private List<CityInfoBean.ResultListBean> resultList;

    public AreaFragment() {
    }

    //新建AreaFragment，并设定需要传递的值
    public static AreaFragment newInstance(String param1, String city) {
        AreaFragment fragment = new AreaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != Bundle.EMPTY) {
            mParam = getArguments().getString(ARG_PARAM1);
            city = getArguments().getString(ARG_PARAM2);
        }
        if (mParam == null || "".equals(mParam)) {
            mParam = "100000";
        }
       /* if (mParam == null || "".equals(mParam)) {
            mParam = "100000";
            toolBar = new MyToolBar(getActivity(), -1, "请选择省", "");
        } else if (mParam != null && !TextUtils.isEmpty(mParam)) {
            if ("市".equals(city)) {
                toolBar = new MyToolBar(getActivity(), -1, "请选择市", "");
            } else {
                toolBar = new MyToolBar(getActivity(), -1, "请选择区/县", "");

            }
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = requestView(inflater, R.layout.fu_fragment_area);
        ButterKnife.bind(this, view);
        requestData();
        return view;
    }

    @Override
    public void requestData() {
        /*OkhttpParam okhttpParam = new OkhttpParam();
        okhttpParam.putString("cityCode", mParam);
        OkhttpUtils.sendRequest(1001, 1, Contents.GET_CITY_LIST, okhttpParam, this);*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void requestInit() {

    }

    @Override
    public void onActionSuccess(int actionId, String ret) {
        Log.i(TAG, "onActionSuccess: "+ret);
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


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(CityInfoBean.ResultListBean areaInfo);
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
                    CityInfoBean.ResultListBean areaInfo = (CityInfoBean.ResultListBean)getItem(position);
                    if (areaInfo == null) return;
                    if (mListener != null) {
                        mListener.onFragmentInteraction(areaInfo);
                    }
                }
            });
            return convertView;
        }


    }
    class ViewHolder {
        TextView addressName;
        TextView subAddress;
    }
}

