package com.dilip.androidlifecyclexml

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class SecondActivity : AppCompatActivity(), BackgroundDetector.Listener {

    private lateinit var backgroundDetector: BackgroundDetector
    private lateinit var progress: View

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate()")
        Toast.makeText(this, "SecondActivity - onCreate() called", Toast.LENGTH_SHORT).show()

        val application = this.application
        super.onCreate(savedInstanceState)

        backgroundDetector = (application as CustomApplication).backgroundDetector

        setContentView(R.layout.activity_second)

        progress = findViewById(R.id.progress)

        findViewById<Button>(R.id.btnNextActivity).setOnClickListener {
            Toast.makeText(this, "SecondActivity - Next Activity Button clicked", Toast.LENGTH_SHORT).show()
            TransparentActivity.start(this)
        }
    }

    override fun onDestroy() {
        Timber.i("onDestroy()")
        Toast.makeText(this, "SecondActivity - onDestroy() called", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }

    override fun onStart() {
        Timber.i("onStart()")
        Toast.makeText(this, "SecondActivity - onStart() called", Toast.LENGTH_SHORT).show()
        super.onStart()

        backgroundDetector.registerListener(this)
        backgroundDetector.activityStarted()
    }

    override fun onStop() {
        Timber.i("onStop()")
        Toast.makeText(this, "SecondActivity - onStop() called", Toast.LENGTH_SHORT).show()
        super.onStop()

        backgroundDetector.activityStopped()
        backgroundDetector.unregisterListener(this)
    }

    override fun onResume() {
        Timber.i("onResume()")
        Toast.makeText(this, "SecondActivity - onResume() called", Toast.LENGTH_SHORT).show()
        super.onResume()

        if (Build.VERSION.SDK_INT < 29) {
            progress.visibility = View.VISIBLE
        }
    }

    override fun onPause() {
        Timber.i("onPause()")
        Toast.makeText(this, "SecondActivity - onPause() called", Toast.LENGTH_SHORT).show()
        super.onPause()

        if (Build.VERSION.SDK_INT < 29) {
            progress.visibility = View.GONE
        }
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        Timber.i("onTopResumedActivityChanged(); isTopResumed: $isTopResumedActivity")
        Toast.makeText(this, "SecondActivity - onTopResumedActivityChanged(); isTopResumed: $isTopResumedActivity", Toast.LENGTH_SHORT).show()

        if (isTopResumedActivity) {
            progress.visibility = View.VISIBLE
        } else {
            progress.visibility = View.GONE
        }
    }

    override fun onBackground() {
    }

    override fun onForeground() {
        Toast.makeText(this, "SecondActivity - Foreground", Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, SecondActivity::class.java)
            context.startActivity(intent)
        }
    }
}
