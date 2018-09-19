package com.cheyipai.corec.event;

/**
 * Created by daincly on 2014/11/14.
 */
public interface IBaseEvent<T> {
    T getData();

    void setData(T t);
}
