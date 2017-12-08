package com.qianmo.jinxiaocun.fu.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.adapter.SwipeMenuAdapter;
import com.qianmo.jinxiaocun.fu.utils.SPUtil;
import com.qianmo.jinxiaocun.fu.widget.WrapSwipeRefreshLayout;
import com.qianmo.jinxiaocun.main.base.BaseFragment;
import com.qianmo.jinxiaocun.main.okhttp.OkhttpUtils;
import com.qianmo.jinxiaocun.main.okhttp.base.OkhttpBase;
import com.qianmo.jinxiaocun.main.okhttp.listener.OnActionListener;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : wizardev
 * e-mail : wizarddev@163.com
 * time   : 2017/09/18
 * desc   :
 * version: 1.0
 */
public class WaitTourShopFragment extends BaseFragment implements OnActionListener {
    /**
     * 服务器端一共多少条数据
     */
    private static final int TOTAL_COUNTER = 34;

    /**
     * 每一页展示多少条数据
     */
    private static final int REQUEST_COUNT = 10;

    /**
     * 已经获取到多少条数据了
     */
    private static int mCurrentCounter = 0;
    @BindView(R.id.rv_approval_list)
    LRecyclerView mRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.swipe_refresh_layout)
    WrapSwipeRefreshLayout mSwipeRefreshLayout;

    private SwipeMenuAdapter mDataAdapter = null;//可以右划删除的数据适配器
    private ArrayList<String> datas = new ArrayList<>();
    private int mApprovalStatus;
    private int mCurrentPage=1;
    private PreviewHandler mHandler = new PreviewHandler(this);
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private static final String TAG = "WaitTourShopFragment";
    private boolean isRefresh = false;//用来判断是上拉还是下拉刷新

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mApprovalStatus = arguments.getInt("approvalStatus");
        }
    }

    public static WaitTourShopFragment newInstance(int status) {
        WaitTourShopFragment fragment = new WaitTourShopFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("approvalStatus", status);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = requestView(inflater, R.layout.fu_tour_shop_fragment);
        unbinder = ButterKnife.bind(this, view);
      //  requestData();

        initView();
        initEvent();
        return view;
    }

    @Override
    public void requestInit() {

    }
    @Override
    public void requestData() {
        super.requestData();
        OkhttpParam okhttpParam = new OkhttpParam();
        //http://hzq.s1.natapp.cc/app/patrol_store?start=1&length=20&patrolStoreStatus=1&staffId=1
        String uri ="http://hzq.s1.natapp.cc/app/patrol_store?start="+mCurrentPage+"&length="
                +REQUEST_COUNT+"&patrolStoreStatus="+mApprovalStatus+"&staffId="+ SPUtil.getInstance().getStaffId();

        OkhttpUtils.sendRequest(1001, 0, uri, okhttpParam, this);
    }

    private void initEvent() {

        mDataAdapter.setOnDelListener(new SwipeMenuAdapter.onSwipeListener() {
            @Override
            public void onDel(int pos) {
                Toast.makeText(getContext(), "删除:" + pos, Toast.LENGTH_SHORT).show();

                //RecyclerView关于notifyItemRemoved的那点小事 参考：http://blog.csdn.net/jdsjlzx/article/details/52131528
                mDataAdapter.getDataList().remove(pos);
                mDataAdapter.notifyItemRemoved(pos);//推荐用这个

                if(pos != (mDataAdapter.getDataList().size())){ // 如果移除的是最后一个，忽略 注意：这里的mDataAdapter.getDataList()不需要-1，因为上面已经-1了
                    mDataAdapter.notifyItemRangeChanged(pos, mDataAdapter.getDataList().size() - pos);
                }
                //且如果想让侧滑菜单同时关闭，需要同时调用 ((CstSwipeDelMenu) holder.itemView).quickClose();
            }

            @Override
            public void onTop(int pos) {//置顶功能有bug，后续解决
                /*TLog.error("onTop pos = " + pos);
                ItemModel itemModel = mDataAdapter.getDataList().get(pos);

                mDataAdapter.getDataList().remove(pos);
                mDataAdapter.notifyItemRemoved(pos);
                mDataAdapter.getDataList().add(0, itemModel);
                mDataAdapter.notifyItemInserted(0);*/


                if(pos != (mDataAdapter.getDataList().size())){ // 如果移除的是最后一个，忽略
                    mDataAdapter.notifyItemRangeChanged(0, mDataAdapter.getDataList().size() - 1,"jdsjlzx");
                }

                mRecyclerView.scrollToPosition(0);

            }
        });

    }

    private void initView() {

        mDataAdapter = new SwipeMenuAdapter(getContext());
        mDataAdapter.setDataList(datas);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mDataAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);

        mRecyclerView.setLoadMoreEnabled(true);
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
           //     mDataAdapter.clear();
              //  mLRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
                mCurrentCounter = 0;
                isRefresh = true;
                requestDataBak();
            }
        });
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < TOTAL_COUNTER) {
                    // loading more
                    Log.i(TAG, "onLoadMore: ");
                    isRefresh = false;
                    requestDataBak();//从网络获取数据
                } else {
                    //the end
                    mRecyclerView.setNoMore(true);
                }
            }
        });
        mRecyclerView.refresh();

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }





    private void notifyDataSetChanged() {
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void addItems(ArrayList<String> list) {
        mDataAdapter.addAll(list);
        mCurrentCounter += list.size();
    }

    @Override
    public void onActionSuccess(int actionId, String ret) {

    }

    @Override
    public void onActionServerFailed(int actionId, int httpStatus) {

    }

    @Override
    public void onActionException(int actionId, String exception) {

    }

    private static class PreviewHandler extends Handler {

        private WeakReference<WaitTourShopFragment> ref;

        PreviewHandler(WaitTourShopFragment fragment) {
            ref = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            final WaitTourShopFragment fragment = ref.get();
            if (fragment == null) {
                return;
            }
            switch (msg.what) {

                case -1:
                    if(fragment.isRefresh){
                        fragment.mDataAdapter.clear();
                        mCurrentCounter = 0;
                    }

                    int currentSize = fragment.mDataAdapter.getItemCount();

                    //模拟组装10个数据
                    ArrayList<String> newList = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        if (newList.size() + currentSize >= TOTAL_COUNTER) {
                            break;
                        }
                        newList.add("");
                    }

                    fragment.addItems(newList);
                    fragment.mRecyclerView.refreshComplete(REQUEST_COUNT);
                  //  fragment.notifyDataSetChanged();
                    break;
                case -2:
                    fragment.notifyDataSetChanged();
                    break;
                case -3:
                    fragment.mRecyclerView.refreshComplete(REQUEST_COUNT);
                    fragment.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }

    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
         //   RecyclerViewUtils.setFooterViewState(getContext(), mRecyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
         //  RecyclerViewUtils.getLayoutPosition(mRecyclerView,)
            requestDataBak();
        }
    };

    /**
     * 模拟请求网络
     */
    public void requestDataBak() {
        Log.d(TAG, "requestDataBak");
        new Thread() {

            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(-1);
                /*//模拟一下网络请求失败的情况
                if(NetworkUtils.isNetAvailable(SwipeDeleteActivity.this)) {
                    mHandler.sendEmptyMessage(-1);
                } else {
                    mHandler.sendEmptyMessage(-3);
                }*/
            }
        }.start();
    }
}
