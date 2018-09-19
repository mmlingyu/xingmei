package com.cheyipai.ui.bean;

import com.cheyipai.corec.base.api.ResponseData;

import java.util.List;


public class CarBrand extends ResponseData {
	private List<CarBrandEntity> data;

	public List<CarBrandEntity> getData() {
		return data;
	}

	public void setData(List<CarBrandEntity> data) {
		this.data = data;
	}
	

}
