package com.cheyipai.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.cheyipai.corec.activity.AbsAdapterItem;
import com.cheyipai.corec.activity.AbsBaseDragListFragment;
import com.cheyipai.corec.activity.AbsBaseDynamicListFragment;
import com.cheyipai.corec.components.newpullview.PullToRefreshBase;
import com.cheyipai.ui.bean.Hair;
import com.cheyipai.ui.commons.Path;
import com.cheyipai.ui.fragment.adapteritem.HairAdapterItem;
import com.cheyipai.ui.ui.car.HairActivity;
import com.cheyipai.ui.utils.IntentUtil;
import com.cheyipai.ui.utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/26.
 */

public class HairListFragment extends AbsBaseDynamicListFragment {

    List<Hair> beans = new ArrayList<Hair>();
    private int pos=9,s;
   /* @Override
    protected void onFresh() {
        beans.clear();
         pos = 9;
        s=0;
        initData();
        setData(beans);
    }

    @Override
    protected void onNextPage() {
        s=pos;
        pos+=pos;
        initData();
        setData(beans);
    }*/

    @Override
    protected int getHeaderLayout() {
        return 0;
    }

    @Override
    protected int getFooterLayout() {
        return 0;
    }

    @Override
    protected AbsAdapterItem getAbsAdapterItem() {
        return new HairAdapterItem();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
        this.setData(beans);
        this.setFragmentStatus(FRAGMENT_STATUS_SUCCESS);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        /*Intent intent = new Intent();
        intent.setClass(this.getActivity(),HairActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        IntentUtil.startIntent(this.getActivity(),intent);*/
        IntentUtils.aRouterIntent(this.getActivity(), Path.HAIR);
    }

    private void initData(){
        for(int i=s;i<pos;i++){
            Hair hair = new Hair();
            hair.setFaceTypes("圆脸|方脸|椭圆脸");
            hair.setName("清爽迷你姑"+i);
            hair.setZan(69880);
            hair.setSex(0);
            hair.setTags("职场|邻家|成熟");
            beans.add(hair);
        }
    }
}
