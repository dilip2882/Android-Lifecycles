package com.dilip.androidlifecyclexml

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.dilip.androidlifecyclexml.service.MyServiceManager
import com.dilip.androidlifecyclexml.sharedpreferences.SharedPrefsHelper
import timber.log.Timber

class CustomApplication : Application() {

    private lateinit var sharedPrefsHelper: SharedPrefsHelper

    lateinit var backgroundDetector: BackgroundDetector

    val myServiceManager = MyServiceManager()

    override fun onCreate() {
        super.onCreate()

        sharedPrefsHelper = SharedPrefsHelper(this)
        backgroundDetector = BackgroundDetector(sharedPrefsHelper)

        Timber.plant(Timber.DebugTree())

        Timber.i("onCreate()")

        ProcessLifecycleOwner.get().lifecycle.addObserver(MyAppLifecycleObserver())

        registerActivityLifecycleCallbacks(MyActivityCallbacks())
    }

    inner class MyActivityCallbacks: ActivityLifecycleCallbacks {

        private var firstActivityCreated = false

        override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
            super.onActivityPreCreated(activity, savedInstanceState)
            if (!firstActivityCreated && savedInstanceState == null) {
                sharedPrefsHelper.foregroundCount().set(0)
            }
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            firstActivityCreated = true
        }

        override fun onActivityStarted(activity: Activity) {}

        override fun onActivityResumed(activity: Activity) {}

        override fun onActivityPaused(activity: Activity) {}

        override fun onActivityStopped(activity: Activity) {}

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

        override fun onActivityDestroyed(activity: Activity) {}
    }
}

class MyAppLifecycleObserver: DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Timber.i("application is in foreground")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Timber.i("application is in background")
    }
}
