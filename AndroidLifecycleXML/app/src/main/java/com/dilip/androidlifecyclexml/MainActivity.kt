package com.dilip.androidlifecyclexml

import android.app.Application
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dilip.androidlifecyclexml.configchanges.ConfigChangeActivity
import com.dilip.androidlifecyclexml.configchanges.ManualConfigChangeActivity
import com.dilip.androidlifecyclexml.fragments.FragmentHostActivity
import com.dilip.androidlifecyclexml.saveandrestore.SaveAndRestoreActivity
import com.dilip.androidlifecyclexml.service.ServiceActivity
import com.dilip.androidlifecyclexml.viewmodel.ViewModelActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var backgroundDetector: BackgroundDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate()")
        Toast.makeText(this, "MainActivity - onCreate() called", Toast.LENGTH_SHORT).show()

        val application = this.applicationContext as Application
        super.onCreate(savedInstanceState)

        backgroundDetector = (application as CustomApplication).backgroundDetector

        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnNextActivity).setOnClickListener {
            SecondActivity.start(this)
        }

        findViewById<Button>(R.id.btnFragmentActivity).setOnClickListener {
            FragmentHostActivity.start(this)
        }

        findViewById<Button>(R.id.btnConfigChangeActivity).setOnClickListener {
            ConfigChangeActivity.start(this)
        }

        findViewById<Button>(R.id.btnManualConfigChangeActivity).setOnClickListener {
            ManualConfigChangeActivity.start(this)
        }

        findViewById<Button>(R.id.btnViewModelActivity).setOnClickListener {
            ViewModelActivity.start(this)
        }

        findViewById<Button>(R.id.btnSaveAndRestoreActivity).setOnClickListener {
            SaveAndRestoreActivity.start(this)
        }

        findViewById<Button>(R.id.btnServiceActivity).setOnClickListener {
            ServiceActivity.start(this)
        }
    }

    override fun onDestroy() {
        Timber.i("onDestroy()")
        Toast.makeText(this, "MainActivity - onDestroy() called", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }

    override fun onStart() {
        Timber.i("onStart()")
        Toast.makeText(this, "MainActivity - onStart() called", Toast.LENGTH_SHORT).show()
        super.onStart()
        backgroundDetector.activityStarted()
    }

    override fun onStop() {
        Timber.i("onStop()")
        Toast.makeText(this, "MainActivity - onStop() called", Toast.LENGTH_SHORT).show()
        super.onStop()
        backgroundDetector.activityStopped()
    }

    override fun onResume() {
        Timber.i("onResume()")
        Toast.makeText(this, "MainActivity - onResume() called", Toast.LENGTH_SHORT).show()
        super.onResume()
    }

    override fun onPause() {
        Timber.i("onPause()")
        Toast.makeText(this, "MainActivity - onPause() called", Toast.LENGTH_SHORT).show()
        super.onPause()
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        Timber.i("onTopResumedActivityChanged(); isTopResumed: $isTopResumedActivity")
        Toast.makeText(this, "MainActivity - onTopResumedActivityChanged(); isTopResumed: $isTopResumedActivity", Toast.LENGTH_SHORT).show()
    }
}
