package com.dilip.androidlifecylexml

import android.app.Application
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var backgroundDetector: BackgroundDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate()")
        Toast.makeText(this, "MainActivity - onCreate", Toast.LENGTH_SHORT).show()

        val application = this.applicationContext as Application

        super.onCreate(savedInstanceState)

        backgroundDetector = (application as CustomApplication).backgroundDetector

        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnNextActivity).setOnClickListener {
            SecondActivity.start(this)
        }
    }

    override fun onDestroy() {
        Timber.i("onDestroy()")
        Toast.makeText(this, "MainActivity - onDestroy", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }

    override fun onStart() {
        Timber.i("onStart()")
        Toast.makeText(this, "MainActivity - onStart", Toast.LENGTH_SHORT).show()
        super.onStart()
        backgroundDetector.activityStarted()
    }

    override fun onStop() {
        Timber.i("onStop()")
        Toast.makeText(this, "MainActivity - onStop", Toast.LENGTH_SHORT).show()
        super.onStop()
        backgroundDetector.activityStopped()
    }

    override fun onResume() {
        Timber.i("onResume()")
        Toast.makeText(this, "MainActivity - onResume", Toast.LENGTH_SHORT).show()
        super.onResume()
    }

    override fun onPause() {
        Timber.i("onPause()")
        Toast.makeText(this, "MainActivity - onPause", Toast.LENGTH_SHORT).show()
        super.onPause()
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        Timber.i("onTopResumedActivityChanged(); isTopResumed: $isTopResumedActivity")
        Toast.makeText(this, "MainActivity - onTopResumedActivityChanged: $isTopResumedActivity", Toast.LENGTH_SHORT).show()
    }
}
