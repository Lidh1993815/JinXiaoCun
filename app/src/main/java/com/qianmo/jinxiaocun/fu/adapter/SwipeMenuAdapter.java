package com.qianmo.jinxiaocun.fu.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.activity.TourDetailActivity;
import com.qianmo.jinxiaocun.fu.widget.SwipeMenuView;

/**
 * author : wizardev
 * e-mail : wizarddev@163.com
 * time   : 2017/09/23
 * desc   :
 * version: 1.0
 */
public class SwipeMenuAdapter extends ListBaseAdapter<String> {
    private Context mContext;
    public SwipeMenuAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fu_wait_tour_shop_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        View contentView = holder.getView(R.id.swipe_content);
     //   TextView title = holder.getView(R.id.title);
        Button btnDelete = holder.getView(R.id.btnDelete);

        //这句话关掉IOS阻塞式交互效果 并依次打开左滑右滑
       // ((SwipeMenuView)holder.itemView).setIos(false).setLeftSwipe(position % 2 == 0 ? true : false);
        ((SwipeMenuView)holder.itemView).setIos(false).setLeftSwipe(true);

        //title.setText(getDataList().get(position).title + (position % 2 == 0 ? "我只能右滑动" : "我只能左滑动"));


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnSwipeListener) {
                    //如果删除时，不使用mAdapter.notifyItemRemoved(pos)，则删除没有动画效果，
                    //且如果想让侧滑菜单同时关闭，需要同时调用 ((CstSwipeDelMenu) holder.itemView).quickClose();
                    //((CstSwipeDelMenu) holder.itemView).quickClose();
                    mOnSwipeListener.onDel(position);
                }
            }
        });
        //注意事项，设置item点击，不能对整个holder.itemView设置咯，只能对第一个子View，即原来的content设置，这算是局限性吧。
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // AppToast.makeShortToast(mContext, getDataList().get(position).title);
                Log.d("TAG", "onClick() called with: v = [" + v + "]");
            }
        });
    }

    /**
     * 和Activity通信的接口
     */
    public interface onSwipeListener {
        void onDel(int pos);

        void onTop(int pos);
    }

    private onSwipeListener mOnSwipeListener;

    public void setOnDelListener(onSwipeListener mOnDelListener) {
        this.mOnSwipeListener = mOnDelListener;
    }
}
