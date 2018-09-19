package com.cheyipai.ui.bean;

public class CarBrandEntity {
	private String brand;
	private String model;
	private String caryear;
	private String volume;
	private String transmission;
	private String type;
	private double score;
	public String getBrand() {
		return brand;
	}

	public String getTransmission() {
		return transmission;
	}

	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public CarBrandEntity(String brand, String model) {
		super();
		this.brand = brand;
		this.model = model;
	}
	public CarBrandEntity(String brand, String model,double score) {
		super();
		this.brand = brand;
		this.model = model;
		this.score = score;
	}
	public CarBrandEntity(String brand, String model,double score,String caryear,String carkind,String carvolume,String transmission) {
		super();
		this.brand = brand;
		this.model = model;
		this.score = score;
		this.caryear = caryear;
		this.type = carkind;
		this.volume=carvolume;
		this.transmission = transmission;
	}

	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getCaryear() {
		return caryear;
	}
	public void setCaryear(String caryear) {
		this.caryear = caryear;
	}

}
