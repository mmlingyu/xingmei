package com.cheyipai.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.cheyipai.corec.activity.AbsBaseFragment;
import com.cheyipai.corec.utils.DeviceUtils;
import com.cheyipai.ui.R;

import java.io.File;



/**
 * Created by 景涛 on 2015/9/18.
 */
public class HairShowFragment extends AbsBaseFragment implements GestureDetector.OnGestureListener {

    public ViewFlipper flipper;

    private GestureDetector detector;

    @Override
    public void setFragmentType(int fragmentType) {

    }



    @Override
    protected int getContentLayout() {
        return R.layout.hair_show_fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState, View contentView) {
        detector = new GestureDetector(this.getActivity(),this);
        flipper = contentView.findViewById(R.id.hair_content_vf);
        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                 detector.onTouchEvent(motionEvent);
                return true;
            }
        });
        flipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                detector.onTouchEvent(motionEvent);
                return true;
            }
        });
        addImageView(Environment.getExternalStorageDirectory().getPath()+ File.separator+"test2"+File.separator);
        setFragmentStatus(FRAGMENT_STATUS_SUCCESS);
    }



    private void addImageView(String path) {
        File dir = new File(path);
        if(!dir.isDirectory())return;
        for(String file:dir.list()) {
            ImageView iv = new ImageView(this.getActivity());
            iv.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                     detector.onTouchEvent(motionEvent);
                    return true;
                }
            });
            Bitmap bitmap = BitmapFactory.decodeFile(path+file);
            iv.setImageBitmap(bitmap);
           // iv.setRotation(-90);
            flipper.addView(iv);
            //bitmap.recycle();
        }
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.i(getClass().getName(), "onDown-----");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.i(getClass().getName(), "onSingleTapUp-----");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float v, float v1) {
        Log.i(getClass().getName(), "onScroll-----"+v+"|"+v1+"|"+e1.getX()+"|"+e2.getX());
        if (e2.getX() - e1.getX() > DeviceUtils.getScreenWidth(this.getContext())/3) {
            // this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
            //this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
            this.flipper.showNext();
            return true;
        } else if (e2.getX() - e1.getX() < -(DeviceUtils.getScreenWidth(this.getContext())/3)) {
            // this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
            // this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
            this.flipper.showPrevious();
            return true;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.i(getClass().getName(), "onLongPress-----");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
        Log.i(getClass().getName(), "onFling-----");
       /* if (e1.getX() - e2.getX() > 1) {
           // this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
            //this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
            this.flipper.showNext();
            return true;
        } else if (e1.getX() - e2.getX() < -1) {
           // this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
           // this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
            this.flipper.showPrevious();
            return true;
        }*/
        return false;
    }
}
