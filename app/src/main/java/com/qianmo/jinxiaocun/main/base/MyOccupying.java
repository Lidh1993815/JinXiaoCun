package com.qianmo.jinxiaocun.main.base;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;


public class MyOccupying extends LinearLayout {
    public ImageView img;
    public TextView text;
    public TextView commit;
    public View mView;
    private LayoutInflater mInflater;

    public MyOccupying(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initData();
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.MyOccupy, defStyle, 0);
            Drawable drawable = a.getDrawable(R.styleable.MyOccupy_imgBtnIcon);
            setImage(drawable);
            String te = a.getString(R.styleable.MyOccupy_textText);
            setTextString(te);
            String co = a.getString(R.styleable.MyOccupy_commitText);
            setCommitString(co);
        }
    }

    public MyOccupying(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyOccupying(Context context) {
        this(context, null);
    }

    public MyOccupying(Context context, int imgId, String textString,
                       String commitString) {
        super(context);
        initData();
        if (imgId != -1) {
            setImage(imgId);
        }
        if (!(textString == null || textString.equals("") || textString
                .length() <= 0)) {
            setTextString(textString);

        }
        if (!(commitString == null || commitString.equals("") || commitString
                .length() <= 0)) {
            setCommitString(commitString);
        }

    }

    public void SetCommitListener(OnClickListener listener) {
        if (commit != null) {
            commit.setOnClickListener(listener);
        }
    }

    public void setCommitString(String textString) {
        if (commit != null) {
            commit.setText(textString);
//            commit.setVisibility(ViewInfo.VISIBLE);
        }
    }

    public void setTextString(String textString) {
        if (text != null) {
            text.setText(textString);
//            text.setVisibility(ViewInfo.VISIBLE);

        }
    }

    public void setImage(int imgId) {
        if (img != null) {
            img.setImageResource(imgId);
//            img.setVisibility(ViewInfo.VISIBLE);

        }
    }

    public void setImage(Drawable imgDrawable) {
        if (img != null) {
            img.setImageDrawable(imgDrawable);
//            img.setVisibility(ViewInfo.VISIBLE);

        }
    }

    private void initData() {
        if (mView == null) {
            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.loccupying_layout, this);
            mView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            img = (ImageView) findViewById(R.id.occupy_image);
            text = (TextView) findViewById(R.id.occupy_text);
            commit = (TextView) findViewById(R.id.occupy_commit);
        }
    }

    public void setCommitVisiable(int visi) {
        if (commit != null) {
            commit.setVisibility(visi);
        }
    }

}
