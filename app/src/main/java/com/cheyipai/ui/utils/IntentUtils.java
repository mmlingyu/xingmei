package com.cheyipai.ui.utils;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cheyipai.core.base.utils.SharedPrefersUtils;
import com.cheyipai.ui.commons.InterruptCallback;

import java.util.Set;
import java.util.Stack;

public class IntentUtils {
	private static Stack<Class> intents = new Stack<Class>();
	private static NotificationManager mNotificationManager;  
	private static Notification mNotification;  
	private static final int HELLO_ID = 10100;; 
	
	public static void goHome(Context context){
		Intent intent=new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		context.startActivity(intent);
	}
	/**
	 * start intent to the new activity
	 * 
	 * @param ctx
	 *            context
	 * @param classObj
	 *            target activity
	 */
	public static void startIntent(Context ctx, Class classObj) {
		if (ctx == null || classObj == null)
			return;
		Intent intent = new Intent();
		intent.setClass(ctx, classObj);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        ctx.startActivity(intent);
		intents.add(classObj);
	}

	/**
	 * start intent to the new activity
	 * @param ctx
	 *            context
	 * @param classObj
	 *            target activity
	 * @intent intent
	 */
	public static void startIntent(Context ctx, Class classObj, Intent intent) {
		if (ctx == null || classObj == null || intent == null)
			return;
		intent.setClass(ctx, classObj);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		ctx.startActivity(intent);
		intents.add(classObj);
	}

	/**
	 * call last activity
	 * 
	 * @param ctx
	 *            context
	 */
	public static void callback(Context ctx) {
		if (intents.size() < 1) {
			dialog(ctx);
		}
		;
		((Activity) ctx).finish();

		// Intent intent= new Intent();
		// intents.pop();//取出当前�?
		// Class last = intents.peek();
		// intent.setClass(ctx, last);
		// ctx.startActivity(intent);
	}

	/**
	 * 待返回�?的Intent
	 * @param ctx	
	 * @param classObj	
	 * @param requestCode	请求�?
	 * @param intent	
	 */
	public static void starIntentForResult(Context ctx, Class classObj,
			int requestCode, Intent intent) {
		if (ctx == null || classObj == null)
			return;
		if (intent == null)
			intent = new Intent();
		intent.setClass(ctx, classObj);
		((Activity) ctx).startActivityForResult(intent, requestCode);
		intents.add(classObj);
	}

	public static void registCallBackListener(Context ctx) {

	}

	private static void dialog(final Context ctx) {
		Builder builder = new Builder(ctx);
		builder.setMessage("确认�?��吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ctx.getApplicationContext().sendBroadcast(new Intent("finish"));
				dialog.dismiss();
			}
		}).setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).create().show();
	}



	/**
	 * ARouter跳转
	 *
	 * @param build 内容为path /test/activity
	 */
	public static void aRouterIntent(final Context activity, String build) {
		ARouter.getInstance().build(build).navigation(activity, new InterruptCallback() {
			@Override
			public void onArrival(Postcard postcard) {
				super.onArrival(postcard);
				recordPath(activity, postcard);
			}
		});
	}

	/**
	 * ARouter跳转
	 *
	 * @param build  内容为path /test/activity
	 * @param bundle
	 */
	public static void aRouterIntent(final Context activity, String build, final Bundle bundle) {
		ARouter.getInstance().build(build).with(bundle).navigation(activity, new InterruptCallback() {

			@Override
			public void onArrival(Postcard postcard) {
				super.onArrival(postcard);
				recordPath(activity, postcard, bundle);
			}
		});
	}

	/**
	 * ARouter跳转
	 * 这种方式不能传Bundle
	 *
	 * @param activity
	 * @param uri      内容为 cheyipai://m.cheyipai.com/deal/car_list
	 */
	public static void aRouterUriIntent(final Context activity, String uri) {
		Uri mUri = Uri.parse(uri);
		ARouter.getInstance().build(mUri).navigation(activity, new InterruptCallback() {
			@Override
			public void onInterrupt(Postcard postcard) {
				super.onInterrupt(postcard);
			}

			@Override
			public void onArrival(Postcard postcard) {
				super.onArrival(postcard);
				recordPath(activity, postcard);
			}
		});
	}

	/**
	 * ARouter跳转,没有Context
	 *
	 * @param uri 内容为 cheyipai://m.cheyipai.com/deal/car_list
	 */
	public static void aRouterUriIntent(String uri) {
		Uri mUri = Uri.parse(uri);
		ARouter.getInstance().build(mUri).navigation();
	}

	/**
	 * ARouter跳转
	 *
	 * @param uri    内容为 cheyipai://m.cheyipai.com/deal/car_list
	 * @param bundle
	 */
	public static void aRouterUriIntent(final Context activity, String uri, final Bundle bundle) {
		Uri mUri = Uri.parse(uri);
		ARouter.getInstance().build(mUri).with(bundle).navigation(activity, new InterruptCallback() {
			@Override
			public void onArrival(Postcard postcard) {
				super.onArrival(postcard);
				recordPath(activity, postcard,bundle);
			}
		});
	}

	public static void ARouterSetResult(final Activity activity, int resultCode, String key, String value) {
		activity.setResult(resultCode, activity.getIntent().putExtra(key, value));
	}

	private static void recordPath(Context activity, Postcard postcard) {
		recordPath(activity, postcard, null);
	}

	private static void recordPath(Context activity, Postcard postcard, Bundle bundle) {
		if (activity != null && activity instanceof Activity) {
			String bundleInfo = getBundleInfo(bundle);
			//来源页面
			String fromPage = activity.getClass().getName();
			String fromPath = (String) SharedPrefersUtils.get(activity.getApplicationContext(), fromPage, "");

			if (TextUtils.isEmpty(fromPath)) {
				fromPath = fromPage;
			}

			if (!SharedPrefersUtils.contains(activity.getApplicationContext(), postcard.getDestination().getName())) {
				SharedPrefersUtils.put(activity.getApplicationContext(), postcard.getDestination().getName(), postcard.getPath());
			}
		}
	}

	/**
	 * 获取Bundle中的参数，不能获取对象类型
	 *
	 * @param bundle
	 * @return
	 */
	private static String getBundleInfo(Bundle bundle) {

		if (bundle != null) {
			Set<String> keySet = bundle.keySet();  //获取所有的Key,

			String string = "?";
			for (String key : bundle.keySet()) {
				string += key + "=" + bundle.get(key) + "&";
			}
			if (string.endsWith("&")) {
				string = string.substring(0, string.length() - 1);
			}
			return string;
		} else {
			return "";
		}

	}
}
