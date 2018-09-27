package com.cheyipai.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 聚类筛选标签流式布局
 */
public class FlowLayout extends ViewGroup {
	private Context context;
	/**
	 * ViewGroup的绘制过程：onMeasure --> onLayout
	 * onMeasure、onLayout和MeasureSpec解析参考：http://blog.csdn.net/yuliyige/article/details/12656751
	 * onMeasure有两方面的额作用：①获得ViewGroup和子View的宽和高 ②设置子ViewGroup的宽和高（只是获得宽高并且存储在它各自的View中，这时ViewGroup根本就不知道子View的大小）
	 * onLayout负责设置子View的大小和位置（告诉ViewGroup子View在它里面中的大小和应该放在哪里）
	 */
//	private List<List<View>> allViews = new ArrayList<List<View>>(); // 一行一行的存储所有的View
//	private List<Integer> lineHeights = new ArrayList<Integer>(); // 存储每一行的高度

	// 我们让一个参数的构造方法调用两个参数的构造方法，两个参数的构造方法调用三个参数的构造方法，这样，不管我们用哪个构造方法产生实例，最终的调用代码都是一致的
	// 当我们在布局文件中书写了控件的属性，并且使用了自定义属性时，就可以使用这个构造方法
	public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	// 当我们在布局文件中书写了控件的属性，但没有使用自定义属性时，就可以调用这个构造方法
	public FlowLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		this.context = context;
	}

	// 当我们只需要通过一个上下文来NEW一个控件时，就可以创建这个控件
	public FlowLayout(Context context) {
		this(context, null);
		this.context = context;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// widthMeasureSpec和heightMeasureSpec中包括了控件的宽和高的测量模式和测量值（测量=测量模式+测量值）
		// 获取容器的宽和高的测量模式和测量值（模式包括EXACTLY、AT_MOST、UNSPECIFIED）
		int width_size = MeasureSpec.getSize(widthMeasureSpec); // 一个MeasureSpec封装了父布局传递给子布局的布局要求，每个MeasureSpec代表了一组宽度和高度的要求
		int width_mode = MeasureSpec.getMode(widthMeasureSpec);
		int height_size = MeasureSpec.getSize(heightMeasureSpec);
		int height_mode = MeasureSpec.getMode(heightMeasureSpec);
		// 如果是wrap_content，那么我们想要得到的值是当前行宽度和历史最长宽度（不超出屏幕）中比较大的一个值
		// 分别用来存储如果FlowLayout的宽和高是wrap_content时容器的行宽和行高
		int width = 0; // 容器中当前最宽的宽度值
		int height = 0; // 容器中的控件所占的高度
		// 分别用来存储当前行所占的宽度和高度
		int lineWidth = 0;
		int lineHeight = 0;
		// 布局中元素的个数
		int itemCount = getChildCount();
		// 循环迭代布局中的每一个子View，为上面的几个变量赋值
		for (int i = 0; i < itemCount; i++) {
			View child = getChildAt(i);
			measureChild(child, widthMeasureSpec, heightMeasureSpec); // 测量子View的宽和高
			MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams(); // 得到LayoutParams
			// 获取子View占据的宽度和高度（子View本身的宽度/高度 加上子View与左右边/上下边之间的间距）
			int childWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
			int childHeight = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
			// 如果新加入的字View超出本行剩余的宽度，则换行，同时记录到现在为止容器中最宽的行的宽度
			if (childWidth + lineWidth > width_size - getPaddingLeft() - getPaddingRight()) {
//				width = Math.max(width, lineWidth); // 记录最宽的行的宽度
////				lineWidth = childWidth; // 换到下一行，当前行宽就是行中第一个子View的宽度
////				height += lineHeight;
////				lineHeight = childHeight; // 换到下一行，当前行高就是行中第一个子View的高度
			} else { // 未换行的情况
				lineWidth += childWidth;
				lineHeight = Math.max(lineHeight, childHeight);
			}
			// （当容器的高度是wrap_content时）到达最后一个控件
			// 如果没有下面这段代码，则在容器的高度是wrap_content时最后一个子View所在的一行都不能显示
			if (i == itemCount - 1) {
				width = Math.max(width, lineWidth);
				height += lineHeight;
			}
		}
		// 设置容器所占的宽和高（如果容器的宽和高是精确值，我们就使用父控件传入的宽度和高度，否则我们就使用我们自己测量的宽度和高度）
		setMeasuredDimension(width_mode == MeasureSpec.EXACTLY ? width_size : width + getPaddingLeft() + getPaddingRight(),
				height_mode == MeasureSpec.EXACTLY ? height_size : height + getPaddingTop() + getPaddingBottom());
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
//		allViews.clear();
//		lineHeights.clear();
		int width = getWidth(); // 当前ViewGroup的宽度
		// 当前行的宽度和高度
		int lineWidth = 0;
		int lineHeight = 0;
		List<View> lineViews = new ArrayList<View>(); // 存储当前行中的子View
		int viewCount = getChildCount();
		boolean isFullLine = false;
		TextView tv = null;
		for (int i = 0; i < viewCount; i++) {
			View child = getChildAt(i);
			MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
			// 当前子View的宽度和高度
			int childWidth = child.getMeasuredWidth();
			int childHeight = child.getMeasuredHeight();

			TextView textView = (TextView)child;
			if("...".equals(textView.getText())){
				tv = textView;
			}
			// 如果需要换行
			if (childWidth + lineWidth + layoutParams.leftMargin + layoutParams.rightMargin > width - getPaddingLeft()
					- getPaddingRight()) {
				isFullLine = true;
				if(i==viewCount-1){
					lineViews.remove(lineViews.size()-1);
					tv.setVisibility(View.VISIBLE);
					lineViews.add(child);
				}

			}else{

				if(tv!=null){
					if(isFullLine){
						tv.setVisibility(View.VISIBLE);
					}else {
						tv.setVisibility(View.GONE);
					}
				}
				lineWidth += childWidth + layoutParams.leftMargin + layoutParams.rightMargin;
				lineHeight = Math.max(lineHeight, childHeight + layoutParams.topMargin + layoutParams.bottomMargin);
				lineViews.add(child);
			}

		}
		// 处理最后一行
//		lineHeights.add(lineHeight);
//		allViews.add(lineViews);
		// 设置子View的位置
		int left = getPaddingLeft();
		int top = getPaddingTop();
//		int lineCount = allViews.size();

//		lineViews = allViews.get(0); // 当前行中的子View
//		lineHeight = lineHeights.get(0);
		for (int j = 0; j < lineViews.size(); j++) {
			View child = lineViews.get(j);
			// 不处理不可见的子View
			if (child.getVisibility() == View.GONE) {
				continue;
			}
			MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
			int childLeft = left + layoutParams.leftMargin;
			int childTop = top + layoutParams.topMargin;
			int childRight = childLeft + child.getMeasuredWidth();
			int childBottom = childTop + child.getMeasuredHeight();
			child.layout(childLeft, childTop, childRight, childBottom); // 为子View进行布局
			left += child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
		}
//		left = getPaddingLeft();
//		top += lineHeight;

//		for (int i = 0; i < lineCount; i++) {
//			lineViews = allViews.get(i); // 当前行中的子View
//			lineHeight = lineHeights.get(i);
//			for (int j = 0; j < lineViews.size(); j++) {
//				View child = lineViews.get(j);
//				// 不处理不可见的子View
//				if (child.getVisibility() == View.GONE) {
//					continue;
//				}
//				MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
//				int childLeft = left + layoutParams.leftMargin;
//				int childTop = top + layoutParams.topMargin;
//				int childRight = childLeft + child.getMeasuredWidth();
//				int childBottom = childTop + child.getMeasuredHeight();
//				child.layout(childLeft, childTop, childRight, childBottom); // 为子View进行布局
//				left += child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
//			}
//			left = getPaddingLeft();
//			top += lineHeight;
//		}
	}
}