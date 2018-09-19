package com.cheyipai.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheyipai.core.base.modules.img.ImageHelper;
import com.cheyipai.ui.bean.Banner;
import com.cheyipai.ui.bean.BannerInfo;
import com.cheyipai.ui.fragment.common.AbsBannerFragment;
import com.cheyipai.ui.ui.car.commons.WebViewShowActivity;
import com.cheyipai.ui.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 景涛 on 2015/9/14.
 */
public class BannerFragment extends AbsBannerFragment {
    private List<BannerInfo> data = new ArrayList<BannerInfo>();//数据
    public static final String BANNER_INDEX = "1", BANNER_HISTORY = "2", BANNER_FUTURE = "3", BANNER_QUERY_BREAK = "4", BANNER_BREAK_DETAIL = "5";//首页

    @Override
    protected ArrayList<View> getPagerViews() {
        ArrayList<View> mViewList = new ArrayList<View>();
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < data.size(); i++) {
            BannerInfo bannerInfo = data.get(i);

                ImageView iv = new ImageView(getActivity());
                iv.setLayoutParams(mParams);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                iv.setTag(bannerInfo);
                ImageHelper.getInstance().load(bannerInfo.getImgUrl(), iv);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BannerInfo bannerInfo = (BannerInfo) view.getTag();
                        if (bannerInfo != null && !StringUtils.isNull(bannerInfo.getLink())) {
                            WebViewShowActivity.startUserCarActivity(BannerFragment.this.getActivity(), bannerInfo.getLink());
                        }
                    }
                });
                mViewList.add(iv);
        }


        return mViewList;
    }


    @Override
    protected void init(Bundle savedInstanceState, View contentView) {
        super.init(savedInstanceState, contentView);
        setFragmentStatus(FRAGMENT_STATUS_SUCCESS);
    }

    /**
     * 设置banner的数据
     *
     * @param banner
     */
    public void setBannerData(Banner banner) {
        data = banner.getData();
        if (data == null) {
            return;
        }
        initData();
    }


}

