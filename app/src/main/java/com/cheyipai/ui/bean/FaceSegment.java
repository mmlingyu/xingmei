package com.cheyipai.ui.bean;

public class FaceSegment {
    private String image_id;
    private String result;
    private String body_image;
    private String time_used;
    private String error_message;

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getBody_image() {
        return body_image;
    }

    public void setBody_image(String body_image) {
        this.body_image = body_image;
    }

    public String getTime_used() {
        return time_used;
    }

    public void setTime_used(String time_used) {
        this.time_used = time_used;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}