package com.cheyipai.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cheyipai.corec.activity.AbsBaseActivity;
import com.cheyipai.corec.modules.config.GlobalConfigHelper;
import com.cheyipai.ui.commons.Path;
import com.cheyipai.ui.fragment.BannerFragment;
import com.cheyipai.ui.view.ScrollListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页
 */
@Route(path = Path.HAIR_LIST)
public class HairListActivity extends AbsBaseActivity {

    private long mExitTime;
    public static final int INDEX_HOT_BRAND_TYPE = 1;//热门品牌
    public static final int INDEX_DREAM_TYPE = 2;//心愿单
    private static String mCity = "北京";
    private static String mCityShort = "";
    private static double mCurrentLat,mCurrentLng;
    private FragmentManager fragmentManager;

    @BindView(R.id.man_tv)
    protected TextView all_tv;


    @BindView(R.id.face_tv)
    protected TextView face_tv;
    protected TextView cancel_tv;
    protected TextView sure_tv;
    @BindView(R.id.face_ll)
    protected LinearLayout face_ll;
    @BindView(R.id.title)
    protected TextView title;
    private Dialog faceDialog;
    private ScrollListView faceListView;


    private List<String> faceDataList;
    /**
     * 设置布局文件
     * @return
     */
    @Override
    protected int getLayoutID() {

        return R.layout.hair_list_activity;
    }

    private void initDialog() {
        if (faceDialog == null) {
            faceDialog = new Dialog(this, R.style.time_dialog);
            faceDialog.setCancelable(false);
            faceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            faceDialog.setContentView(R.layout.index_face_window);
            Window window = faceDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            faceDialog.setCanceledOnTouchOutside(true);
            WindowManager manager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(dm);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = dm.widthPixels;
            window.setAttributes(lp);
            faceDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    face_tv.setText(faceListView.getSelect());
                }
            });
        }
    }

    @OnClick(R.id.back_iv)
    public void back(View view){
        this.finish();
    }

    @Override
    protected void init() {

        fragmentManager = this.getSupportFragmentManager();

       // startLocation();
        openEventBus();
        checkVersion();
        initDialog();
        ButterKnife.bind(this);
        initView();

        openAppStatitcs();
       //TextViewh all_tv.setText(getRadiusGradientSpan("全部",0xFFec4ce6,0xfffa4a6f));
    }

    private void initView(){
        faceDataList = new ArrayList<String>();
        faceDataList.add("圆脸");
        faceDataList.add("瓜子脸");
        faceDataList.add("方脸");
        faceDataList.add("椭圆脸");
        //back_iv.setVisibility(View.GONE);
        title.setText("发型库");
        faceListView = faceDialog.findViewById(R.id.face_lv);
        cancel_tv = faceDialog.findViewById(R.id.cancel_tv);
        sure_tv = faceDialog.findViewById(R.id.tv_sure);
        sure_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faceDialog.dismiss();
                face_tv.setText(faceListView.getSelect());
            }
        });
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faceDialog.dismiss();
            }
        });
        faceListView.setData(faceDataList);
        faceListView.setOnSelectListener(new ScrollListView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                Log.d("----","face = "+text);
               // faceDialog.dismiss();
                //face_tv.setText(text);
            }
        });
    }
    private void openAppStatitcs(){

    }
    @OnClick(R.id.face_ll)
    public void onFaceClick(View view){
        faceDialog.show();
    }
    /**
     * check app version
     */
    private void checkVersion(){
        String version = GlobalConfigHelper.getInstance().getCurrentAppVersionTime();
      /*  if(!StringUtils.getDay().equalsIgnoreCase(version)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    appVersionFragment.requestUpdateApi();
                }
            }, 3000);
        }*/
    }




    protected void openEventBus() {
        //EventBus.getDefault().register(this);
    }



    /**
     * set location city
     * @param city
     * @param cityShort
     * @param latlng
     * @param cityCode
     */
    public void setLocationCity(String city,String cityShort, String latlng,String cityCode){
    }


    public void startMap(View view){
        Intent intent = new Intent();
        intent.putExtra("city",mCity);
        intent.putExtra("lat",mCurrentLat);
        intent.putExtra("lng",mCurrentLng);
       // IntentUtils.startIntent(this,CarMapActivity.class,intent);
    }


    /**
     * 有网络时候自动请求
     * @param networkConnected
     */
    private void reflush(boolean networkConnected){
        if(networkConnected){

        }
    }








    /**
     * 两次点击back退出appp
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

                this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public static boolean locationStatus = true;
    @Override
    protected void onResume() {
       /* if(window!=null){
            window.requestGetCityApi();
        }*/


        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();

    }


}
