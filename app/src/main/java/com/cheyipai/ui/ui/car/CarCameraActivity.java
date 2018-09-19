package com.cheyipai.ui.ui.car;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cheyipai.corec.activity.AbsBaseActivity;
import com.cheyipai.corec.utils.ToastHelper;
import com.cheyipai.ui.R;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CarCameraActivity extends AbsBaseActivity implements View.OnClickListener {
    // camera 类
    private Camera c = null;
    // 继承surfaceView的自定义view 用于存放照相的图片
    private CameraView cv = null;

    @Override
    protected int getLayoutID() {
        return R.layout.car_camera_activity;
    }

    @Override
    protected void init() {
        ButterKnife.inject(this);
        btn2.setOnClickListener(this);
        btn1.setOnClickListener(this);
    }

    // 按钮 布局等定义，不作赘述
    @InjectView(R.id.open)
    Button btn1;
    @InjectView(R.id.take)
    Button btn2;

    @InjectView(R.id.cameraView)
    LinearLayout l ;
    // 回调用的picture，实现里边的onPictureTaken方法，其中byte[]数组即为照相后获取到的图片信息
    private Camera.PictureCallback picture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // 主要就是将图片转化成drawable，设置为固定区域的背景（展示图片），当然也可以直接在布局文件里放一个surfaceView供使用。
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            Drawable d = BitmapDrawable.createFromStream(bais, Environment
                    .getExternalStorageDirectory().getAbsolutePath()
                    + "/img.jpeg");
            l.setBackgroundDrawable(d);
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };


    @Override
    public void onClick(View view) {
        if (view == btn1) {
            l.removeAllViews();
            cv = new CameraView(CarCameraActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.FILL_PARENT);
            l.addView(cv, params);
        } else if (view == btn2) {
            c.takePicture(new Camera.ShutterCallback() {
                @Override
                public void onShutter() {
                    ToastHelper.getInstance().showToast(" 拍照成功");

                }
            }, null, picture);
        }
    }


    //主要的surfaceView，负责展示预览图片，camera的开关
    class CameraView extends SurfaceView {

        //
        private SurfaceHolder holder = null;

        public CameraView(Context context) {
            super(context);
            holder = this.getHolder();

            holder.addCallback(new SurfaceHolder.Callback() {

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format,
                                           int width, int height) {
                    Camera.Parameters parameters = c.getParameters();
                    //以下注释掉的是设置预览时的图像以及拍照的一些参数
                    // parameters.setPictureFormat(PixelFormat.JPEG);
                    // parameters.setPreviewSize(parameters.getPictureSize().width,
                    // parameters.getPictureSize().height);
                    // parameters.setFocusMode("auto");
                    // parameters.setPictureSize(width, height);
                    c.setParameters(parameters);
                    c.startPreview();
                }

                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    c = Camera.open();

                    try {
                        //设置camera预览的角度，因为默认图片是倾斜90度的
                        c.setDisplayOrientation(90);
                        //设置holder主要是用于surfaceView的图片的实时预览，以及获取图片等功能，可以理解为控制camera的操作..
                        c.setPreviewDisplay(holder);
                    } catch (IOException e) {
                        c.release();
                        c = null;
                        e.printStackTrace();
                    }

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    //顾名思义可以看懂
                    c.stopPreview();
                    c.release();
                    c = null;
                }
            });
//          holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
    }
}
