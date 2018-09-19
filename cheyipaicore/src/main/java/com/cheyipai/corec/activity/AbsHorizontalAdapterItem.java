package com.cheyipai.corec.activity;

import android.view.View;

/**
 * Created by jinc on 2014/12/13.
 */
public abstract class AbsHorizontalAdapterItem<T> {

    public abstract int getItemLayout();

    public abstract void init(View contentView);

    public abstract void bindData(T t,int position);
}