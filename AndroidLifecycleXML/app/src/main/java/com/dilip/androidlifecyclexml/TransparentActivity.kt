package com.dilip.androidlifecyclexml

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class TransparentActivity : AppCompatActivity() {

    private lateinit var backgroundDetector: BackgroundDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate()")
        Toast.makeText(this, "TransparentActivity - onCreate() called", Toast.LENGTH_SHORT).show()

        val application = this.application

        super.onCreate(savedInstanceState)

        backgroundDetector = (application as CustomApplication).backgroundDetector

        setContentView(R.layout.activity_transparent)
    }

    override fun onDestroy() {
        Timber.i("onDestroy()")
        Toast.makeText(this, "TransparentActivity - onDestroy() called", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }

    override fun onStart() {
        Timber.i("onStart()")
        Toast.makeText(this, "TransparentActivity - onStart() called", Toast.LENGTH_SHORT).show()
        super.onStart()
        backgroundDetector.activityStarted()
    }

    override fun onStop() {
        Timber.i("onStop()")
        Toast.makeText(this, "TransparentActivity - onStop() called", Toast.LENGTH_SHORT).show()
        super.onStop()
        backgroundDetector.activityStopped()
    }

    override fun onResume() {
        Timber.i("onResume()")
        Toast.makeText(this, "TransparentActivity - onResume() called", Toast.LENGTH_SHORT).show()
        super.onResume()
    }

    override fun onPause() {
        Timber.i("onPause()")
        Toast.makeText(this, "TransparentActivity - onPause() called", Toast.LENGTH_SHORT).show()
        super.onPause()
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        Timber.i("onTopResumedActivityChanged(); isTopResumed: $isTopResumedActivity")
        Toast.makeText(this, "TransparentActivity - onTopResumedActivityChanged(); isTopResumed: $isTopResumedActivity", Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, TransparentActivity::class.java)
            context.startActivity(intent)
        }
    }
}
