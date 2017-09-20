package com.qianmo.jinxiaocun.fu.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;

/**
 * author : wizardev
 * e-mail : wizarddev@163.com
 * time   : 2017/09/20
 * desc   : 图文混排
 * version: 1.0
 */
public class ImageAndText extends LinearLayout{

    private int textSize;
    private String text;
    private int color;
    private Drawable drawable;
    private int imageMarginTop;
    private int textMarginTop;
    private int textMarginBottom;

    public ImageAndText(Context context) {
        this(context,null);
    }

    public ImageAndText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ImageAndText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ImageAndText, defStyleAttr, 0);
        textSize = array.getDimensionPixelSize(R.styleable.ImageAndText_android_textSize, 0);
        text = array.getString(R.styleable.ImageAndText_android_text);
        color = array.getColor(R.styleable.ImageAndText_android_textColor, getResources().getColor(R.color.white));
        drawable = array.getDrawable(R.styleable.ImageAndText_android_src);
        imageMarginTop = array.getDimensionPixelSize(R.styleable.ImageAndText_imageMarginTop, 0);
        textMarginTop = array.getDimensionPixelSize(R.styleable.ImageAndText_textMarginTop, 0);
        textMarginBottom = array.getDimensionPixelSize(R.styleable.ImageAndText_textMarginBottom, 0);
        array.recycle();
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.fu_pic_text_layout, null, false);
        ImageView imageView = view.findViewById(R.id.pic);

        TextView textView = view.findViewById(R.id.text_pic);

        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        }
        textView.setTextColor(color);
       // textView.setTextSize(textSize);
        textView.setText(text);
        textView.setPadding(0,textMarginTop,0,textMarginBottom);
        imageView.setPadding(0,imageMarginTop,0,0);
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.gravity = Gravity.CENTER;
        addView(view,layoutParams);
    }
}
