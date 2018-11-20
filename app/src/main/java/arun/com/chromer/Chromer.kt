/*
 * Lynket
 *
 * Copyright (C) 2018 Arunkumar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package arun.com.chromer

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import arun.com.chromer.data.DataModule
import arun.com.chromer.di.app.AppComponent
import arun.com.chromer.di.app.AppModule
import arun.com.chromer.di.app.DaggerAppComponent
import arun.com.chromer.util.ServiceManager
import com.cheyipai.core.base.modules.app.BaseApplication
import com.cheyipai.ui.BuildConfig
import io.paperdb.Paper

/**
 * Created by Arun on 06/01/2016.
 */
open class Chromer : BaseApplication() {
    override fun initLoation() {
    }

    override fun initAccount() {

    }

    override fun initAppCode() {

    }

    open val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .dataModule(DataModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        initFabric()
        Paper.init(this)

        if (BuildConfig.DEBUG) {
            // Stetho.initializeWithDefaults(this);
            /*StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    //.detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());*/
        } else {

        }
        ServiceManager.takeCareOfServices(applicationContext)
    }

    protected open fun initFabric() {

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


    companion object {

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
