package com.cheyipai.ui.vm;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cheyipai.corec.utils.Base64;
import com.cheyipai.ui.api.BaiduOauthApi;
import com.cheyipai.ui.api.FaceApi;
import com.cheyipai.ui.bean.BaseCallBack;
import com.cheyipai.ui.bean.FaceInfo;
import com.cheyipai.ui.bean.FaceSegment;
import com.cheyipai.ui.bean.FaceSegmentBack;
import com.cheyipai.ui.bean.OauthBack;
import com.cheyipai.ui.utils.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HairVM {

    private FaceApi faceApi;
    private BaiduOauthApi baiduOauthApi;
    public HairVM(Context context){
         faceApi = new FaceApi(context);
        baiduOauthApi = new BaiduOauthApi(context);
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

    public void getFaceInfo(File file,OauthBack oauthBack) {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                baiduOauthApi.getAuth(new OauthBack() {
                    @Override
                    public void onOauthSucc(FaceInfo faceInfo) {
                    }

                    @Override
                    public void onTokenSucc(String token) {
                        e.onNext(token);
                        e.onComplete();
                    }
                });


            }
        });
        Consumer<String> onNextConsumer = new Consumer<String>() {
            @Override
            public void accept(String baidu_accessToken) throws Exception {
                baiduOauthApi.uploadUserFace(file, baidu_accessToken, new OauthBack() {
                    @Override
                    public void onOauthSucc(FaceInfo faceInfo) {
                        oauthBack.onOauthSucc(faceInfo);
                        Log.d("BAIDU  succ ||", faceInfo.getResult().getFace_list()[0].getFace_shape().getType());
                    }
                    @Override
                    public void onTokenSucc(String token) {
                    }
                });
            }
        };
        observable.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(onNextConsumer);


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
