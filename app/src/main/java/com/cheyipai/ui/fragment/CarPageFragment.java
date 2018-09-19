package com.cheyipai.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.cheyipai.corec.activity.AbsBaseFragment;
import com.cheyipai.ui.R;

/**
 * Created by gjt on 2016/10/12.
 */

public class CarPageFragment extends AbsBaseFragment {

    private WebFragment webFragment;

    @Override
    protected void init(Bundle savedInstanceState, View contentView) {
        webFragment = (WebFragment) this.getActivity().getSupportFragmentManager().findFragmentById(R.id.web_fragment);
        setFragmentStatus(FRAGMENT_STATUS_SUCCESS);

    }

    public WebFragment getWebFragment(){
        return  webFragment;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.car_page;
    }


}
