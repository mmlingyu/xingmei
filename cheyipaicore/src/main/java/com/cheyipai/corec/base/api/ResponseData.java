package com.cheyipai.corec.base.api;

/**
 * Created by gjt
 */
public class ResponseData {

    /**
     * 接口状态返回码
     */
    public int resultCode;
    /**
     * 接口状态描述语
     */
    public String StateDescription;

    public String getStateDescription() {
        return StateDescription;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public void setStateDescription(String stateDescription) {
        StateDescription = stateDescription;
    }



}
