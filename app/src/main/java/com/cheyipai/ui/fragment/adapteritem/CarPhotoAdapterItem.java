package com.cheyipai.ui.fragment.adapteritem;

import android.view.View;

import com.cheyipai.corec.activity.AbsAdapterItem;
import com.cheyipai.ui.bean.CarPhoto;

/**
 * Created by gjt on 2016/7/18.
 */
public class CarPhotoAdapterItem extends AbsAdapterItem<CarPhoto> {

    public CarPhotoAdapterItem(View itemView) {
        super(itemView);
    }



    @Override
    public int getItemLayout() {
        return 0;
    }

    @Override
    public void init(View contentView) {

    }

    @Override
    public void bindData(CarPhoto carPhoto) {

    }
}
