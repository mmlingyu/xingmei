package com.cheyipai.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cheyipai.corec.utils.DeviceUtils;
import com.cheyipai.ui.CheyipaiApplication;
import com.cheyipai.ui.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SelectPicPopupWindow
 * @Description: 照片选择弹出框
 * @author: SHAOS
 * @date: 2016-2-26 上午9:55:16
 */
public class SelectPicPopupWindow extends PopupWindow {

	private Button btn_cancel;
	private View mMenuView;
	private ViewPager mViewCard;
	private Animation animation;
	private WeakReference<Activity> weakReference;
	private OnClickListener listener;
	private ViewPagerCardAdapter viewPagerCardAdapter;
	public SelectPicPopupWindow(Activity context, OnClickListener itemsOnClick,List<String> pagerList) {
		super(context);
		weakReference = new WeakReference<Activity>(context);
		this.pagerList = pagerList;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.scan_edithead_dialog, null);
		btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		this.setContentView(mMenuView);
		initView();
		this.listener = itemsOnClick;
		this.setWidth(LayoutParams.FILL_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		animation = AnimationUtils.loadAnimation(context,
				R.anim.slide_out_bottom);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		this.setBackgroundDrawable(dw);
		mMenuView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});
	}
	private List<String> pagerList;

	private void initView() {
		mViewCard = (ViewPager)mMenuView.findViewById(R.id.vp_card);
		viewPagerCardAdapter = new ViewPagerCardAdapter();
		mViewCard.setClipChildren(false);
		mViewCard.setAdapter(viewPagerCardAdapter);
		mViewCard.setPageMargin(-DeviceUtils.dpToPx(160,CheyipaiApplication.getInstance()));
		mViewCard.setOffscreenPageLimit(2);//预加载2个

		mViewCard.setPageTransformer(true, new CardTransformer());

	}


	private class ViewPagerCardAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return pagerList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = LayoutInflater.from(weakReference.get()).inflate(R.layout.hair_push_item, null);
			TextView cardNumber = view.findViewById(R.id.tv_task_card_number);
			cardNumber.setText(pagerList.get(position));
			container.addView(view);
			/*view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					SelectPicPopupWindow.this.listener.onClick(v);
				}
			});*/
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);

		}
	}
}
