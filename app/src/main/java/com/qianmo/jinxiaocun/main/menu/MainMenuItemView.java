package com.qianmo.jinxiaocun.main.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;


/**
 * Created by Administrator on 2016/2/26.
 */
public class MainMenuItemView extends LinearLayout {

    ImageView menuImage;
    TextView menuText;
    TextView menuRedNum;
    private int selectedColor;
    private int unselectedColor;
    private int selectedDrawableId = -1;
    private int unselectedDrawableId = -1;
    private View mView;
    private Drawable selectedDrawable;
    private Drawable unselectedDrawable;


    public MainMenuItemView(Context context) {
        this(context, null);
    }

    public MainMenuItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainMenuItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.MainMenuItemView, defStyleAttr, 0);
            selectedDrawable = a.getDrawable(R.styleable.MainMenuItemView_menuSelectedImage);
            unselectedDrawable = a.getDrawable(R.styleable.MainMenuItemView_menuUnselectedImage);
            menuImage.setImageDrawable(unselectedDrawable);
            selectedColor = a.getColor(R.styleable.MainMenuItemView_menuSelectedColor, -1);
            unselectedColor = a.getColor(R.styleable.MainMenuItemView_menuUnselectedColor, -1);
            String s = a.getString(R.styleable.MainMenuItemView_menuText);
            menuText.setTextColor(unselectedColor);
            menuText.setText(s);

            a.recycle();
        }
    }

    public MainMenuItemView(Context context, int selectedColor, int unSelectedColor,
                            int selectedDrawableId, int UnSelectedDrawable, String menuName) {
        super(context);
        this.selectedColor = selectedColor;
        this.unselectedColor = unSelectedColor;
        this.selectedDrawableId = selectedDrawableId;
        this.unselectedDrawableId = UnSelectedDrawable;
        initView();
        menuText.setText(menuName);
        changeState(false);
        hideRed();
    }


    private void initView() {
        if (mView == null) {
            mView = LayoutInflater.from(getContext()).inflate(R.layout.menu_item, this);
            menuImage = (ImageView) mView.findViewById(R.id.main_menuImage);
            menuText = (TextView) mView.findViewById(R.id.main_menuText);
            menuRedNum = (TextView) mView.findViewById(R.id.main_menuRedNum);
        }
    }

    /**
     * 隐藏红点
     */
    public void hideRed() {
        menuRedNum.setVisibility(GONE);
    }

    /**
     * 展示红点
     */
    public void showRed(int num) {
        if (num <= 0) {
            menuRedNum.setVisibility(GONE);
        }
        if (num == -1)
            menuRedNum.setVisibility(VISIBLE);
        if (num > 0 && num <= 99) {
            menuRedNum.setVisibility(VISIBLE);

        }
        if (num > 99) {
            num = 99;
        }
        menuRedNum.setText(num + "");

    }

    public void showRed(){
        menuRedNum.setVisibility(VISIBLE);
    }

    /**
     * 改变状态，true代表选中
     *
     * @param isSelected
     */
    public void changeState(boolean isSelected) {
        if (selectedDrawableId == -1 || unselectedDrawableId == -1) {
            if (isSelected) {
                menuImage.setImageDrawable(selectedDrawable);
                menuText.setTextColor(selectedColor);
            } else {
                menuImage.setImageDrawable(unselectedDrawable);
                menuText.setTextColor(unselectedColor);
            }
        } else {
            if (isSelected) {
                menuImage.setImageResource(selectedDrawableId);
                menuText.setTextColor(selectedColor);
            } else {
                menuImage.setImageResource(unselectedDrawableId);
                menuText.setTextColor(unselectedColor);
            }
        }


    }
}
