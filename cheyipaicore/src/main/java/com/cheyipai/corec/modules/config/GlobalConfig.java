package com.cheyipai.corec.modules.config;


import com.cheyipai.corec.threadpool.ThreadPools;

/**
 * GlobalConfig mInstance 设计略显冗余，本可以用类名调用
 * 为贴近HelperFactory架构设计添加
 * <p/>
 * 全局配置文件，保存用户全局性配置信息,跟账号无关联
 * Created by yangqinghai on 13-12-23.
 */
public class GlobalConfig {

    private static UtilConfig mConfig;


    public GlobalConfig() {
        mConfig = new TransitionGlobalConfig();
    }

    void asyncInit() {
        mConfig = new TransitionGlobalConfig();
    }

    public String getString(String key) {
        return mConfig.getString(key);
    }

    public String getString(String key, String defValue) {
        return mConfig.getString(key, defValue);
    }

    public boolean getBoolean(String key) {
        return mConfig.getBoolean(key);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mConfig.getBoolean(key, defValue);
    }

    public int getInt(String key) {
        return mConfig.getInt(key, -1);
    }

    public int getInt(String key, Integer defValue) {
        return mConfig.getInt(key, defValue);
    }

    public float getFloat(String key) {
        return mConfig.getFloat(key);
    }

    public float getFloat(String key, float defValue) {
        return mConfig.getFloat(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return mConfig.getLong(key, defValue);
    }

    public long getLong(String key) {
        return mConfig.getLong(key);
    }

    public void putString(String key, String value) {
        mConfig.putString(key, value);
    }

    public void putBoolean(String key, boolean value) {
        mConfig.putBoolean(key, value);
    }

    public void putInt(String key, Integer value) {
        mConfig.putInt(key, value);
    }

    public void putFloat(String key, Float value) {
        mConfig.putFloat(key, value);
    }

    public void putLong(String key, long value) {
        mConfig.putLong(key, value);
    }


    /**
     * 是否包含该key
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return mConfig.hasKey(key);
    }

    /**
     * 移除键
     */
    public void remove(String key) {
        mConfig.remove(key);
    }

    public void commit() {
        mConfig.commit();
    }

    /**
     * 异步提交保存数据
     */
    private void asyncCommit() {
        ThreadPools.getWorkThreadPool().execute(new Runnable() {

            @Override
            public void run() {
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                commit();
            }
        });
    }

    /**
     * 销毁myconfig对象
     */
    public void destroyMyConfig() {
        mConfig = null;
    }

}
