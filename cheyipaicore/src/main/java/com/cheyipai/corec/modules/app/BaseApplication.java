package com.cheyipai.corec.modules.app;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.FragmentActivity;

import com.cheyipai.corec.account.AbsAccount;
import com.cheyipai.corec.log.L;

import java.util.Stack;

/**
 * Created by daincly on 2014/10/26.4
 */
public abstract class BaseApplication extends Application {
	private static final String CLASS_TAG = "BaseApplication";
	public final static int APP_UNINITIALIZED = 0x0;
	public final static int APP_INITIALIZING = 0x1;
	public final static int APP_INITIALIZED = 0x2;
	private static Stack<FragmentActivity> mActivities;
	private static BaseApplication sInstance;
	/**
	 * 账户管理
	 */
	protected static AbsAccount account;

    @Override
	public void onCreate() {
		L.plant(new L.DebugTree());
		sInstance = this;
		super.onCreate();
		mActivities = new Stack<FragmentActivity>();
		initSettings();
	}

	public abstract void initAccount();
	protected void initSettings(){

	}

	public static BaseApplication getApplication() {
		return sInstance;
	}
	
	public void addActivity(FragmentActivity activity) {
		if (activity == null) {
			return;
		}
		mActivities.push(activity);
	}

	private Stack<FragmentActivity> getActivityStack() {
		return mActivities;
	}

	public FragmentActivity getTopActivity() {
		if (mActivities.empty()) {
			return null;
		} else {
			return mActivities.peek();
		}
	}

	public void removeActivity(Activity activity) {
		if (activity == null) {
			return;
		}

		if (mActivities.contains(activity)) {
			mActivities.remove(activity);
		}
	}

	/**
	 * 账户登出信息处理
	 */
	public void logout(){
		if(account != null){
			account.cleanAccount();
		}
	}

	/**
	 * 关闭客户端
	 * 
	 * @param isAppExit
	 *            标示要不要退出app true 标示退出app false 标示清空activity堆栈
	 */
	public void exit(boolean isAppExit) {
		if (!mActivities.empty()) {
			for (Activity activity : mActivities) {
				if (activity != null && !activity.isFinishing())
					activity.finish();
			}
			mActivities.clear();
		}
		if (isAppExit) {
			System.exit(0);
		}
	}

}
