package com.cheyipai.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class IntentUtil {

	public static void startIntent(Context ctx, Class classObj) {
		if (ctx == null || classObj == null)
			return;
		Intent intent = new Intent();
		intent.setClass(ctx, classObj);
		ctx.startActivity(intent);
	}

	public static void startIntent(Context ctx, Intent intent) {
		if (ctx == null)
			return;
		ctx.startActivity(intent);
	}

	public static void doActivity(Context ctx,
			Class<? extends Activity> activityClazz, Bundle bundle) {
		Intent intent = new Intent(ctx, activityClazz);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		ctx.startActivity(intent);
	}

}
