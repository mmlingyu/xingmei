package com.cheyipai.corec.components.viewpager;

import com.cheyipai.corec.event.IBaseEvent;

/**
 * Created by jincan on 14-11-20.
 */
public class UpdateTitleCount implements IBaseEvent {
	int index;
	String count;

	public UpdateTitleCount(int index, String count) {
		this.index = index;
		this.count = count;
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

	public String getCount() {
		return count;
	}
}
