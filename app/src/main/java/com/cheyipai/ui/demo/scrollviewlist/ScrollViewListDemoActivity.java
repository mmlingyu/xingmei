package com.cheyipai.ui.demo.scrollviewlist;

import com.cheyipai.corec.activity.AbsBaseActivity;
import com.cheyipai.ui.R;

/**
 * 外层为ScrollView的ListViewFragment Demo
 */
public class ScrollViewListDemoActivity extends AbsBaseActivity {
    @Override
    protected int getLayoutID() {
        return R.layout.activity_scroll_list_demo;
    }

    @Override
    protected boolean isOpenActionBar() {
        return true;
    }

    @Override
    protected String getActionBarTitle() {
        return "ScrollViewListDemo";
    }

    @Override
    protected void init() {

    }
}
