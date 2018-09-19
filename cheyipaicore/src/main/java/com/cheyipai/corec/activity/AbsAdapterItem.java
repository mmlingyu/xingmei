package com.cheyipai.corec.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * LietViewItem基类，item布局文件设置和数据绑定
 * @param <T>：item数据类型
 */
public abstract class AbsAdapterItem<T> extends  RecyclerView.ViewHolder {
    public AbsAdapterItem(View itemView) {
        super(itemView);
    }

    public abstract int getItemLayout();

    public abstract void init(View contentView);

    public abstract void bindData(T t);

    public int getLayout(){
        return 0;
    };

}
