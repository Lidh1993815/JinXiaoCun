package com.qianmo.jinxiaocun.fu.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.qianmo.jinxiaocun.R;

/**
 * author : wizardev
 * e-mail : wizarddev@163.com
 * time   : 2017/09/21
 * desc   :
 * version: 1.0
 */
public class TaskProgressDecoration extends RecyclerView.ItemDecoration{

    private Paint mPaint;
    //ItemView左边的间距
    private float mOffsetLeft;
    //ItemView右边的间距
    private float mOffsetTop;
    //时间轴结点的半径
    private float mNodeRadius;
    private float mLineWidth;
    private float mTextSize;
    private Paint mLinePaint;//画线的画笔
    private Paint mTextPaint;//画字符的画笔
    private static final String TAG = "TaskProgressDecoration";
    public TaskProgressDecoration(Context context) {
        //画圆的画笔初始化
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#ffd868"));



        mOffsetLeft = context.getResources().getDimension(R.dimen.timeline_item_offset_left);
        mNodeRadius = context.getResources().getDimension(R.dimen.timeline_item_node_radius);
        mLineWidth = context.getResources().getDimension(R.dimen._1dp);
        mTextSize = context.getResources().getDimension(R.dimen._12sp);

        //画线的画笔初始化
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(mLineWidth);
        mLinePaint.setColor(Color.parseColor("#c5cacf"));

        //画文字的画笔初始化
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = (int) mOffsetLeft;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int childCount = parent.getChildCount();

        Log.i(TAG, "childCount: "+childCount);
        for ( int i = 0; i < childCount; i++ ) {
            View view = parent.getChildAt(i);

            int index = parent.getChildAdapterPosition(view);
            float dividerTop = view.getTop() - mOffsetTop;
            //第一个ItemView 没有向上方向的间隔
            if ( index == 0 ) {
                dividerTop = view.getTop();
            }

            float dividerLeft = parent.getPaddingLeft();
            float dividerBottom = view.getBottom();
            float dividerRight = parent.getWidth() - parent.getPaddingRight();

            float centerX = dividerLeft + mOffsetLeft / 2;
            float centerY = dividerTop + (dividerBottom - dividerTop) / 2;//加上以前的iitem的高度

            float upLineTopX = centerX;
            float upLineTopY = dividerTop;
            float upLineBottomX = centerX;
            float upLineBottomY = centerY - mNodeRadius;
            if (index == 0) {
                //是第一个item的时候不绘制上方的线
                upLineTopY = centerY - mNodeRadius;
            }

            //绘制上半部轴线
            c.drawLine(upLineTopX,upLineTopY,upLineBottomX,upLineBottomY,mLinePaint);

            //绘制时间轴结点
            c.drawCircle(centerX,centerY,mNodeRadius,mPaint);

            float downLineTopX = centerX;
            float downLineTopY = centerY + mNodeRadius;
            float downLineBottomX = centerX;
            float downLineBottomY = dividerBottom;


            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            //判断是当前layoutManager是否为LinearLayoutManager
            // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                //获取最后一个可见view的位置
                int lastItemPosition = linearManager.findLastVisibleItemPosition();
                //获取第一个可见view的位置
                int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                if (lastItemPosition == index) {
                    //是最后一个item时不绘制下方的线条
                    downLineBottomY = centerY + mNodeRadius;
                }
            }

            //绘制上半部轴线
            c.drawLine(downLineTopX,downLineTopY,downLineBottomX,downLineBottomY,mLinePaint);
            //绘制文字
            Paint.FontMetricsInt fm = mTextPaint.getFontMetricsInt();
            //计算文字基线的位置
            float baseline = centerY + (fm.bottom - fm.top)/2 - fm.bottom;
            //绘制文字
            c.drawText("流",centerX,baseline,mTextPaint);
        }
    }
}
