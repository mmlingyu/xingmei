package com.cheyipai.ui.utils;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

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
	
}
