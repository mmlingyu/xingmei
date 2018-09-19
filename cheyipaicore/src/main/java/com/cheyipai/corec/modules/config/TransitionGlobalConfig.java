package com.cheyipai.corec.modules.config;

/**
 * 全局配置过渡
 * Created by yangqinghai on 13-12-23.
 */
class TransitionGlobalConfig extends UtilConfig {

    private static final String GLOBAL = "global";

    /**
     * @return
     */
    @Override
    protected String getConfigName() {
        return GLOBAL + Constant.CONFIG_FILENAME;
    }


}
