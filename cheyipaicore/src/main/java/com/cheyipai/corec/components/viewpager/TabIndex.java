package com.cheyipai.corec.components.viewpager;

import com.cheyipai.corec.event.IBaseEvent;

/**
 * Created by jinc on 2014/12/15.
 */
public class TabIndex implements IBaseEvent {
	int index;

	public TabIndex(int index) {
		this.index = index;
	}

	@Override
	public Object getData() {
		return null;
	}

    @Override
    public void setData(Object o) {

    }

    public int getIndex() {
		return index;
	}
}