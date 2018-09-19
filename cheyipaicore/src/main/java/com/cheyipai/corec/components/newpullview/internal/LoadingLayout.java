/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.cheyipai.corec.components.newpullview.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.FrameLayout;

import com.cheyipai.corec.components.newpullview.ILoadingLayout;

@SuppressLint("ViewConstructor")
public abstract class LoadingLayout extends FrameLayout implements ILoadingLayout {

    public LoadingLayout(Context context) {
        super(context);
    }

    public abstract void setHeight(int height);

    public abstract void setWidth(int width);

    public abstract int getContentSize();

    public abstract void hideAllViews();

    public abstract void showInvisibleViews();

    public abstract void onPull(float scaleOfLayout);

    public abstract void pullToRefresh();

    public abstract void refreshing();

    public abstract void releaseToRefresh();

    public abstract void reset();
}