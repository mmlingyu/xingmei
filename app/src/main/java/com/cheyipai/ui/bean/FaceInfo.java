package com.cheyipai.ui.bean;

import java.util.List;

/**
 * Created by gjt on 2016/7/18.
 */
public class FaceInfo {

    private String error_code;
    private String error_msg;
    private Resut result;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public Resut getResult() {
        return result;
    }

    public void setResult(Resut result) {
        this.result = result;
    }

    public class Resut{
        private int face_num;
        private Face[] face_list;

        public int getFace_num() {
            return face_num;
        }

        public void setFace_num(int face_num) {
            this.face_num = face_num;
        }

        public Face[] getFace_list() {
            return face_list;
        }

        public void setFace_list(Face[] face_list) {
            this.face_list = face_list;
        }
    }

    public class Gender{
        private String type;
        private double probability;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getProbability() {
            return probability;
        }

        public void setProbability(double probability) {
            this.probability = probability;
        }
    }
    public class Face{
        private String face_token;
        private Location location;
        private double face_probability;
        private Angle angle;
        private FaceShape face_shape;
        private String gender;
        private Point[] landmark;
        private Point[] landmark72;
        private double beauty;

        public String getFace_token() {
            return face_token;
        }

        public void setFace_token(String face_token) {
            this.face_token = face_token;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public double getFace_probability() {
            return face_probability;
        }

        public void setFace_probability(double face_probability) {
            this.face_probability = face_probability;
        }

        public Angle getAngle() {
            return angle;
        }

        public void setAngle(Angle angle) {
            this.angle = angle;
        }

        public FaceShape getFace_shape() {
            return face_shape;
        }

        public void setFace_shape(FaceShape face_shape) {
            this.face_shape = face_shape;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Point[] getLandmark() {
            return landmark;
        }

        public void setLandmark(Point[] landmark) {
            this.landmark = landmark;
        }

        public Point[] getLandmark72() {
            return landmark72;
        }

        public void setLandmark72(Point[] landmark72) {
            this.landmark72 = landmark72;
        }

        public double getBeauty() {
            return beauty;
        }

        public void setBeauty(double beauty) {
            this.beauty = beauty;
        }
    }
    public  class Point{
        private double x;
        private double y;

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }
    public class FaceShape{
        private String face_shape;
        private double probability;

        public String getFace_shape() {
            return face_shape;
        }

        public void setFace_shape(String face_shape) {
            this.face_shape = face_shape;
        }

        public double getProbability() {
            return probability;
        }

        public void setProbability(double probability) {
            this.probability = probability;
        }
    }
    public  class Angle{
        private double yaw;
        private double pitch;
        private double roll;

        public double getYaw() {
            return yaw;
        }

        public void setYaw(double yaw) {
            this.yaw = yaw;
        }

        public double getPitch() {
            return pitch;
        }

        public void setPitch(double pitch) {
            this.pitch = pitch;
        }

        public double getRoll() {
            return roll;
        }

        public void setRoll(double roll) {
            this.roll = roll;
        }
    }
    public class Location{
        private double left;
        private double top;
        private double width;
        private double height;
        private double rotation;

        public double getLeft() {
            return left;
        }

        public void setLeft(double left) {
            this.left = left;
        }

        public double getTop() {
            return top;
        }

        public void setTop(double top) {
            this.top = top;
        }

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public double getRotation() {
            return rotation;
        }

        public void setRotation(double rotation) {
            this.rotation = rotation;
        }
    }
}

