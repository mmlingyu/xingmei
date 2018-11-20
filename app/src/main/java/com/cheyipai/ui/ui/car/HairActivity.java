package com.cheyipai.ui.ui.car;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cheyipai.core.base.bean.LoginUserBean;
import com.cheyipai.core.base.common.CypGlobalBaseInfo;
import com.cheyipai.corec.activity.AbsBaseActivity;
import com.cheyipai.corec.modules.config.GlobalConfigHelper;
import com.cheyipai.corec.utils.EncryptUtil;
import com.cheyipai.ui.CheyipaiApplication;
import com.cheyipai.ui.HomeActivity;
import com.cheyipai.ui.R;
import com.cheyipai.ui.api.D3OauthApi;
import com.cheyipai.ui.bean.DownF3d;
import com.cheyipai.ui.bean.DownObj;
import com.cheyipai.ui.bean.F3d;
import com.cheyipai.ui.bean.F3dCallback;
import com.cheyipai.ui.bean.F3dStatus;
import com.cheyipai.ui.bean.F3dUploadCallback;
import com.cheyipai.ui.bean.Oauth;
import com.cheyipai.ui.bean.OauthCallback;
import com.cheyipai.ui.commons.EventCode;
import com.cheyipai.ui.commons.Path;
import com.cheyipai.ui.fragment.BannerFragment;
import com.cheyipai.ui.fragment.common.WebFragment;
import com.cheyipai.ui.service.ServerService;
import com.cheyipai.ui.utils.DialogUtils;
import com.cheyipai.ui.utils.IntentUtils;
import com.cheyipai.ui.view.ScrollListView;
import com.cheyipai.ui.view.SelectPicPopupWindow;
import com.ypy.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import arun.com.chromer.appdetect.AppDetectionManager;
import arun.com.chromer.data.website.DefaultWebsiteRepository_Factory;
import arun.com.chromer.data.website.WebsiteRepository;
import arun.com.chromer.data.website.model.Website;
import arun.com.chromer.settings.Preferences;
import arun.com.chromer.tabs.DefaultTabsManager;
import arun.com.chromer.tabs.TabsManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 首页
 */
@Route(path = Path.HAIR)
public class HairActivity extends AbsBaseActivity {
    private FragmentManager fragmentManager;
    private Dialog faceDialog;
    @BindView(R.id.hair_ll)
    protected RelativeLayout hair_ll;
    private int PICK_IMAGE_REQUEST = 1;
    private SelectPicPopupWindow picPopupWindow;
    public static WebFragment[] fragments;
    private int modelId;
    private Oauth oauth;
    private F3dStatus f3dStatus;
    private D3OauthApi d3OauthApi;
    private AssetManager assetManager;
    private static final String intro="https://mp.weixin.qq.com/s?__biz=MzIyNzM2ODIyMQ==&mid=2247483695&idx=1&sn=ace240e37f3d9be9e485364791e2635e&mpshare=1&scene=1&srcid=0928dmYEHuGEZcOa12GvEdgA&pass_ticket=4SbZtPID2TQWhKPSJp3MPlQ3GaENP%2F%2Br2foKqDmc6JVLkaJC%2BZ2qi5GhZuxT8eYi#rd";
    private static final String service="https://mp.weixin.qq.com/s?__biz=MzIxMDkzNjU3Mw==&mid=2247483751&idx=1&sn=d6f72d65e56a8c7e44e8f691c0f3ebcb&chksm=975db6f5a02a3fe3633bcaee03833c79af19e307f3b3191efda75569d2434c616c9bffd63f27&mpshare=1&scene=1&srcid=09280lYNuqyeSVcZSBHvv78D&pass_ticket=4SbZtPID2TQWhKPSJp3MPlQ3GaENP%2F%2Br2foKqDmc6JVLkaJC%2BZ2qi5GhZuxT8eYi#rd";
    /**
     * 设置布局文件
     * @return
     */
    @Override
    protected int getLayoutID() {

        return R.layout.hair_activity;
    }

    @OnClick(R.id.back_iv)
    public void back(View view){
        this.finish();
    }

    /**初始化fragment*/
    public void setfragment()
    {
        if(fragments==null) {
            fragments = new WebFragment[2];
            fragments[0] = WebFragment.newInstance(EventCode.WEB_100010);
            fragments[1] = WebFragment.newInstance(EventCode.WEB_100011);
            fragmentManager.beginTransaction().replace(R.id.ll_tab, fragments[0]).commit();
            fragmentManager.executePendingTransactions();
        }

    }

    public void initAuthor(){
        try {
            LoginUserBean.DataBean dataBean = CypGlobalBaseInfo.getLoginUserDataBean();
            if(!TextUtils.isEmpty(dataBean.getAreaIDs())){
                oauth= new Oauth();
                oauth.setAccess_token(dataBean.getAreaIDs());
                oauth.setToken_type(dataBean.getAreaNames());
                oauth.setCode(dataBean.getBusinessId());
                oauth.setPlayer(dataBean.getBusId());
                return;
            }
            d3OauthApi = new D3OauthApi(this);
            d3OauthApi.getAuthor("gjt825", new OauthCallback() {
                @Override
                public void onOauthSucc(Oauth oauth) {
                    HairActivity.this.oauth = oauth;
                    LoginUserBean.DataBean dataBean = new LoginUserBean.DataBean();
                    dataBean.setAreaIDs(oauth.getAccess_token());
                    dataBean.setAreaNames(oauth.getToken_type());
                    dataBean.setBusinessId(oauth.getCode());
                    dataBean.setBusId(oauth.getPlayer());
                    CypGlobalBaseInfo.saveLoginInfo(HairActivity.this,dataBean);
                    Toast.makeText(HairActivity.this,"初始化成功！",Toast.LENGTH_LONG).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.hair_mote_iv)
    public void onMoteClick(View view){
        IntentUtils.aRouterIntent(this, Path.HAIR_SHOP);
        IntentUtils.aRouterIntent(HairActivity.this, Path.HAIR_3D);
       /* CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse("http://192.168.96.254:8080/f3dthree/main.html"))*/;
        //createAssetManager(Path.MODEL_PATH+"/model.fbx");

    }

    @OnClick(R.id.camera_face_tv)
    public void onCameraClick(View view){
        selectImage();
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"选择图像..."), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setfragment();

    }



    @Override
    protected void init() {
        fragmentManager = this.getSupportFragmentManager();
       // startLocation();
        openEventBus();
       // initDialog();
        ButterKnife.bind(this);
        initView();
        initAuthor();
        modelId = getIntent().getExtras().getInt(Path.KEY_HAIR_ID);
        assetManager = this.getAssets();
        openAppStatitcs();
        Intent intent2 = new Intent(HairActivity.this, ServerService.class);
        startService(intent2);
       //TextViewh all_tv.setText(getRadiusGradientSpan("全部",0xFFec4ce6,0xfffa4a6f));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                File file =new File(getPathByUri4kitkat(HairActivity.this,uri));
               /* baiduOauthApi.uploadUserFace(file, baidu_accessToken, new OauthBack() {
                    @Override
                    public void onOauthSucc(FaceInfo faceInfo) {
                        Toast.makeText(D3FaceActivity.this,"BAIDU  succ ||"+faceInfo.getResult().getFace_list()[0].getFace_shape().getFace_shape(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onTokenSucc(String token) {

                    }


                });*/
                String dirPath= Path.FBX_DIR+File.separator+modelId;
                String webDirPath =Path.WEB_FBX_DIR+File.separator+modelId+File.separator;
                d3OauthApi.uploadUserFace("gjtFace", HairActivity.this.oauth, file, new F3dUploadCallback() {
                    @Override
                    public void onUploadSucc(final F3dStatus f3dStatus) {
                        HairActivity.this.f3dStatus = f3dStatus;
                        Toast.makeText(HairActivity.this,"upload succ",Toast.LENGTH_LONG).show();
                        d3OauthApi.getFace(HairActivity.this.oauth, f3dStatus, new F3dCallback() {
                            @Override
                            public void onF3dSucc(F3d f3d) {

                                Toast.makeText(HairActivity.this," finish",Toast.LENGTH_LONG).show();
                             /*   d3OauthApi.get(HairActivity.this.oauth, f3d.getMesh(), new DownF3d() {
                                    @Override
                                    public void onDown(DownObj downObj) {
                                        Toast.makeText(HairActivity.this," ply - down ply succ",Toast.LENGTH_LONG).show();
                                    }
                                }, Environment.getExternalStorageDirectory().getAbsolutePath()+"/git825"+ UUID.randomUUID().toString()+".zip");*/
                                d3OauthApi.get(HairActivity.this.oauth, f3d.getTexture(), new DownF3d() {
                                    @Override
                                    public void onDown(DownObj downObj) {
                                        Toast.makeText(HairActivity.this," ply - down jpg succ",Toast.LENGTH_LONG).show();
                                    }
                                }, dirPath,"model.jpg");

                                d3OauthApi.getObjOfBlendshapes(HairActivity.this.oauth, f3dStatus.getCode(), new DownF3d() {
                                    @Override
                                    public void onDown(DownObj downObj) {
                                        Toast.makeText(HairActivity.this," ply - down obj succ",Toast.LENGTH_LONG).show();
                                        //IntentUtils.aRouterIntent(HairActivity.this, Path.HAIR_3D);
                                        Intent intent2 = new Intent(HairActivity.this, ServerService.class);
                                        startService(intent2);
                                        CheyipaiApplication.getInstance().getDefaultTabsManager()
                                                .openUrl(HairActivity.this,
                                                        new Website("http://localhost:8001/web/ply.jsp?p="+EncryptUtil.encryptData(webDirPath)),
                                                        false,false,false,false,false);
                                    }
                                },dirPath,"model.fbx");
                            }
                        });


                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void initView(){
        DialogUtils.setShapeDrawable(hair_ll);
    }
    private void openAppStatitcs(){

    }



    @OnClick(R.id.use_ll)
    public void onUserClick(View view){
        if(picPopupWindow==null)
        picPopupWindow = new SelectPicPopupWindow(this,null, Arrays.asList("清新自然风","甜美淑女风","职场女神风","清新自然风","甜美淑女风","职场女神风"));
        picPopupWindow.showAtLocation(this.findViewById(R.id.title), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //popupView.startAnimation(animation);
    }

    public void onEvent(Integer code){
        if(code == EventCode.WEB_100010){
            fragmentManager.beginTransaction().replace(R.id.ll_tab,fragments[0]).commit();
            //fragments[0].loadWebview(intro);
        }else if(code == EventCode.WEB_100011){
            fragmentManager.beginTransaction().replace(R.id.ll_tab,fragments[1]).commit();
            //fragments[1].loadWebview(service);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Intent intent2 = new Intent(this, ServerService.class);
        stopService(intent2);
    }

    /**
     * 点击事件
     * @param
     */
    @OnClick(R.id.rb_tab_hair_intro)
    public void onHairIntro(View view) {

        fragmentManager.beginTransaction().replace(R.id.ll_tab,fragments[0]).commit();
        fragmentManager.executePendingTransactions();
        if(!fragments[0].isLoad())
        fragments[0].loadWebview(intro);

    }
    @OnClick(R.id.rb_tab_hair_service)
    public void onHairService(View view) {

        fragmentManager.beginTransaction().replace(R.id.ll_tab,fragments[1]).commit();
        fragmentManager.executePendingTransactions();
        if(!fragments[1].isLoad())
        fragments[1].loadWebview(service);
    }

    protected void openEventBus() {
        EventBus.getDefault().register(this);
    }






    @SuppressLint("NewApi")
    public static String getPathByUri4kitkat(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {// ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {// DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {// MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore
            // (and
            // general)
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {// File
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context
     *            The context.
     * @param uri
     *            The Uri to query.
     * @param selection
     *            (Optional) Filter used in the query.
     * @param selectionArgs
     *            (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


}
