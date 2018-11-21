package com.cheyipai.ui.vm;

import android.content.Context;

import com.cheyipai.corec.utils.Base64;
import com.cheyipai.ui.api.FaceApi;
import com.cheyipai.ui.bean.BaseCallBack;
import com.cheyipai.ui.bean.FaceSegment;
import com.cheyipai.ui.bean.FaceSegmentBack;
import com.cheyipai.ui.utils.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class HairVM {

    FaceApi faceApi;
    public HairVM(Context context){
         faceApi = new FaceApi(context);
    }
    public void segmentFace(String file, String newFile, BaseCallBack<String> callBack){
        try {
            faceApi.detect(Base64.encode(FileUtil.readFileByBytes(file)), new FaceSegmentBack() {
                @Override
                public void onFaceSegmentSucc(FaceSegment faceInfo) {

                    try {
                        callBack.onCallBack(decoderBase64File(faceInfo.getBody_image(),newFile));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String decoderBase64File(String base64Code,String savePath) throws Exception {
        //byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        byte[] buffer =Base64.decode(base64Code);
        File dir = new File(savePath);
        if(!dir.exists()){
            dir.mkdir();
        }
        String file = savePath+File.separator+UUID.randomUUID().toString()+".jpg";
        FileOutputStream out = new FileOutputStream(file);
        out.write(buffer);
        out.close();
        return  file;
    }
}
