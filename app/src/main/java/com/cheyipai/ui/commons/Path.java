package com.cheyipai.ui.commons;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2018/9/27.
 */

public class Path {

    public static final String HAIR = "/hair/detail";
    public static final String HAIR_LIST= "/hair/list";
    public static final String HAIR_SHOP= "/hair/shop";
    public static final String HAIR_3D= "/hair/3d";


    public static final String MODEL_PATH= Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String WEB_PATH= "web";
    public static final String FBX_DIR = MODEL_PATH+File.separator+WEB_PATH+"/three/models/fbx";
    public static final String USER_FACE_IMAGES=MODEL_PATH+File.separator+"user_images";
    public static final String WEB_FBX_DIR ="three/models/fbx";

    public static final String KEY_HAIR_ID="hair_id";

}
