package com.dilip.androidlifecyclexml.configchanges

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dilip.androidlifecyclexml.R
import com.dilip.androidlifecyclexml.BackgroundDetector
import com.dilip.androidlifecyclexml.CustomApplication
import com.dilip.androidlifecyclexml.MyFragmentLifecycleCallbacks
import timber.log.Timber

class ManualConfigChangeActivity : AppCompatActivity() {

    private lateinit var backgroundDetector: BackgroundDetector

    override fun onCreate(savedInstanceState: Bundle?) {

        Timber.i("onCreate()")

        val application = this.applicationContext as Application

        super.onCreate(savedInstanceState)

        backgroundDetector = (application as CustomApplication).backgroundDetector

        setContentView(R.layout.activity_manual_config_change)

        // Show a toast when onCreate is called
        Toast.makeText(this, "onCreate() - Activity Created", Toast.LENGTH_SHORT).show()

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, ManualConfigChangeFragment.newInstance(), "fragmentTag")
                .commit()
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(MyFragmentLifecycleCallbacks(), false)
    }

    override fun onDestroy() {
        Timber.i("onDestroy()")
        super.onDestroy()

        // Show a toast when the activity is destroyed
        Toast.makeText(this, "onDestroy() - Activity Destroyed", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        Timber.i("onStart()")
        super.onStart()
        backgroundDetector.activityStarted()

        // Show a toast when the activity is started
        Toast.makeText(this, "onStart() - Activity Started", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        Timber.i("onStop()")
        super.onStop()
        backgroundDetector.activityStopped()

        // Show a toast when the activity is stopped
        Toast.makeText(this, "onStop() - Activity Stopped", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        Timber.i("onResume()")
        super.onResume()

        // Show a toast when the activity is resumed
        Toast.makeText(this, "onResume() - Activity Resumed", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        Timber.i("onPause()")
        super.onPause()

        // Show a toast when the activity is paused
        Toast.makeText(this, "onPause() - Activity Paused", Toast.LENGTH_SHORT).show()
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        Timber.i("onTopResumedActivityChanged(); isTopResumed: $isTopResumedActivity")

        // Show a toast when the top resumed activity changes
        Toast.makeText(this, "onTopResumedActivityChanged() - Top Activity Changed: $isTopResumedActivity", Toast.LENGTH_SHORT).show()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Timber.i("onConfigurationChanged()")
        super.onConfigurationChanged(newConfig)

        // Show a toast when the configuration changes (e.g., orientation change)
        Toast.makeText(this, "onConfigurationChanged() - Configuration Changed: ${newConfig.orientation}", Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, ManualConfigChangeActivity::class.java)
            context.startActivity(intent)
        }
    }
}
