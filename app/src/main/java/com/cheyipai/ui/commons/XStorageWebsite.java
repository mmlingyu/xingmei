package com.cheyipai.ui.commons;

import android.text.TextUtils;

import com.yanzhenjie.andserver.RequestHandler;
import com.yanzhenjie.andserver.handler.StorageRequestHandler;
import com.yanzhenjie.andserver.util.StorageWrapper;
import com.yanzhenjie.andserver.website.BasicWebsite;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class XStorageWebsite extends BasicWebsite {
    private StorageWrapper mStorageWrapper;
    private String mRootPath;

    public XStorageWebsite(String rootPath) {
        super(rootPath);
        if (TextUtils.isEmpty(rootPath)) {
            throw new NullPointerException("The RootPath can not be null.");
        } else {
            this.mRootPath = trimSlash(rootPath);
            this.mStorageWrapper = new StorageWrapper();
        }
    }


    public void onRegister(Map<String, RequestHandler> handlerMap) {
        RequestHandler indexHandler = new StorageRequestHandler(this.INDEX_HTML);
        handlerMap.put("", indexHandler);
        handlerMap.put(this.mRootPath, indexHandler);
        handlerMap.put(this.mRootPath + File.separator, indexHandler);
        handlerMap.put(this.mRootPath + File.separator + this.INDEX_HTML, indexHandler);
        List<String> pathList = this.mStorageWrapper.scanFile(getHttpPath(this.mRootPath));
        Iterator var4 = pathList.iterator();

        while(var4.hasNext()) {
            String path = (String)var4.next();
            RequestHandler requestHandler = new StorageRequestHandler(path);
            handlerMap.put(getPath(path), requestHandler);
        }

    }


    private String getPath(String storagePath){
        if(storagePath.contains("storage")){
            storagePath = storagePath.substring(storagePath.indexOf(Path.WEB_PATH));
        }
        return  storagePath;
    }

}
