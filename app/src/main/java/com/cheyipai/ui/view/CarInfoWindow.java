package com.cheyipai.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cheyipai.ui.R;

/**
 * @date: 2016-2-26 上午9:55:16
 */
public class CarInfoWindow extends PopupWindow {

	private View mMenuView;
	private Animation animation;
	private ImageView back;
	private TextView title;
	public CarInfoWindow(Activity context, String car) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.car_info_window, null);
		back = (ImageView) mMenuView.findViewById(R.id.back_iv);
		title = (TextView) mMenuView.findViewById(R.id.title_tv);
		back.setVisibility(View.GONE);
		this.setContentView(mMenuView);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(400);
		this.setFocusable(true);
		title.setText(car);
		this.update();

	}
}
