package com.cheyipai.ui.fragment.adapteritem;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cheyipai.corec.activity.AbsAdapterItem;
import com.cheyipai.ui.CheyipaiApplication;
import com.cheyipai.ui.R;
import com.cheyipai.ui.bean.CarPhoto;
import com.cheyipai.ui.bean.Hair;
import com.cheyipai.ui.fragment.HairListFragment;
import com.cheyipai.ui.utils.DialogUtils;
import com.cheyipai.ui.view.ShadowDrawable;
import com.cheyipai.ui.view.XCFlowLayout;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gjt on 2016/7/18.
 */
public class HairAdapterItem extends AbsAdapterItem<Hair> {

    @BindView(R.id.face_name_tv)
    protected TextView face_name;

    @BindView(R.id.hair_tag_flowlayout)
    protected XCFlowLayout tagFlowLatout;

    public HairAdapterItem() {;
    }

    @Override
    public int getItemLayout() {
        return R.layout.hair_item_layout;
    }

    @Override
    public void init(View contentView) {
        ButterKnife.bind(this, contentView);
       // DialogUtils.setShapeDrawable(contentView);

    }


    @Override
    public void bindData(Hair carPhoto) {
        face_name.setText(carPhoto.getName());
        setFlowLayout(Arrays.asList(carPhoto.getTags().split("\\|")));
    }

    private void setFlowLayout(final List<String> data) {
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.leftMargin = 0;
        lp.rightMargin = 10;
        lp.topMargin = 10;
        lp.bottomMargin = 10;

        if(data==null||data.size()<1)return;
        LayoutInflater inflater = LayoutInflater.from(CheyipaiApplication.getInstance());
        tagFlowLatout.removeAllViews();
        for (int i = 0; i < data.size(); i++) {
            final String brand = data.get(i);
            TextView view = (TextView) inflater.inflate(R.layout.hair_tag_text,null);
            view.setText(brand);
            view.setFocusable(false);
            view.setTextSize(14);
            if (Build.VERSION.SDK_INT >= 16) {
                view.setBackground(CheyipaiApplication.getInstance().getResources().getDrawable(R.drawable.hair_item_textview_nofoucs));
            } else {
                view.setBackgroundDrawable(CheyipaiApplication.getInstance().getResources().getDrawable(R.drawable.hair_item_textview_nofoucs));
            }
            tagFlowLatout.addView(view, lp);
        }

    }
}
