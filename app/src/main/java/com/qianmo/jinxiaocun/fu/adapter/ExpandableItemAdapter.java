package com.qianmo.jinxiaocun.fu.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.bean.Level0Item;
import com.qianmo.jinxiaocun.fu.bean.MultiItemEntity;
import com.qianmo.jinxiaocun.fu.bean.Person;

/**
 * 分组可点击展开的adapter
 * @author lizhixian
 * @time 2017/1/12 22:36
 */

public class ExpandableItemAdapter extends BaseMultiAdapter<MultiItemEntity> {
    private static final String TAG = ExpandableItemAdapter.class.getSimpleName();
    public static final int TYPE_LEVEL_ZERO = 0;
   // public static final int TYPE_LEVEL_ONE = 1;
    public static final int TYPE_ENTITY = 1;

    public ExpandableItemAdapter(Context context) {
        super(context);
        addItemType(TYPE_LEVEL_ZERO, R.layout.sales_expandable_item_lv0);
    //    addItemType(TYPE_LEVEL_ONE, R.layout.sales_expandable_item_lv1);
        addItemType(TYPE_ENTITY, R.layout.sales_expandable_item_lv1);
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        MultiItemEntity item = getDataList().get(position);
        switch (item.getItemType()) {
            case TYPE_LEVEL_ZERO:
                bindLevel0Item(holder,position, (Level0Item)item);
                break;
           /* case TYPE_LEVEL_ONE:
                bindLevel1Item(holder,position, (Level1Item)item);
                break;*/
            case TYPE_ENTITY:
                bindEntityItem(holder,position, (Person) item);
                break;
            default:
                break;
        }

    }

    private void bindLevel0Item(final SuperViewHolder holder, final int position, final Level0Item item) {
       /* TextView title = holder.getView(R.id.title);
        TextView subTitle = holder.getView(R.id.sub_title);
        TextView expandState = holder.getView(R.id.expand_state);
        title.setText(item.title);
        subTitle.setText(item.subTitle);
        expandState.setText(item.isExpanded() ? R.string.expanded : R.string.collapsed);
       */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Level 0 item pos: " + position);
                if (item.isExpanded()) {
                    //当展开时。将展开的数据收起
                    collapse(position);
                } else {
                    if (position % 3 == 0) {
                        expandAll(position, false);
                    } else {
                        expand(position);
                    }
                }

            }
        });
    }

   // private void bindLevel1Item(final SuperViewHolder holder, final int position, final Level1Item item) {
        /*TextView title = holder.getView(R.id.title);
        TextView subTitle = holder.getView(R.id.sub_title);
        TextView expandState = holder.getView(R.id.expand_state);
        title.setText(item.title);
        subTitle.setText(item.subTitle);
        expandState.setText(item.isExpanded() ? R.string.expanded : R.string.collapsed);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Level 1 item pos: " + position);
                if (item.isExpanded()) {
                    collapse(position, false);
                } else {
                    expand(position, false);
                }
            }
        });*/
   // }

    private void bindEntityItem(SuperViewHolder holder, final int position, final Person person) {
//        TextView textView = holder.getView(R.id.info_text);
//        textView.setText(person.name + " parent pos: " + position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // AppToast.showShortText(mContext,"person: " + person.name + " age: " + person.age);
            }
        });

    }


}
