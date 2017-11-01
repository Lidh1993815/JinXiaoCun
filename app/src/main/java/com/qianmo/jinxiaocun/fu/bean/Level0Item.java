package com.qianmo.jinxiaocun.fu.bean;


import com.qianmo.jinxiaocun.fu.adapter.ExpandableItemAdapter;

public class Level0Item extends AbstractExpandableItem<Person> implements MultiItemEntity {
    public String title;
    public String subTitle;

    public Level0Item(String title, String subTitle) {
        this.subTitle = subTitle;
        this.title = title;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_ZERO;
    }

    public int getLevel() {
        return 0;
    }
}
