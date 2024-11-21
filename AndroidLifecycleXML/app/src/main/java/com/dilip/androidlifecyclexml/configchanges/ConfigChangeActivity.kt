package com.dilip.androidlifecyclexml.configchanges

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dilip.androidlifecyclexml.BackgroundDetector
import com.dilip.androidlifecyclexml.CustomApplication
import com.dilip.androidlifecyclexml.MyFragmentLifecycleCallbacks
import com.dilip.androidlifecyclexml.R
import timber.log.Timber

class ConfigChangeActivity : AppCompatActivity(), ConfigChangeFragment.Listener {

    private lateinit var backgroundDetector: BackgroundDetector
    private lateinit var txtUserInput: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        Timber.i("onCreate(); identity: ${System.identityHashCode(this)}")

        // Show a toast message when the activity is created
        Toast.makeText(this, "onCreate() - Activity Created", Toast.LENGTH_SHORT).show()

        val application = this.applicationContext as Application

        super.onCreate(savedInstanceState)

        backgroundDetector = (application as CustomApplication).backgroundDetector

        setContentView(R.layout.activity_config_change)

        txtUserInput = findViewById(R.id.txtUserInput)

        onUserInputChanged("")

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, ConfigChangeFragment.newInstance(), "fragmentTag")
                .commit()
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(
            MyFragmentLifecycleCallbacks(),
            false
        )
    }

    override fun onDestroy() {
        Timber.i("onDestroy(); identity: ${System.identityHashCode(this)}")

        // Show a toast message when the activity is destroyed
        Toast.makeText(this, "onDestroy() - Activity Destroyed", Toast.LENGTH_SHORT).show()

        super.onDestroy()
    }

    override fun onStart() {
        Timber.i("onStart()")
        super.onStart()

        // Show a toast message when the activity is started
        Toast.makeText(this, "onStart() - Activity Started", Toast.LENGTH_SHORT).show()

        backgroundDetector.activityStarted()
    }

    override fun onStop() {
        Timber.i("onStop()")
        super.onStop()

        // Show a toast message when the activity is stopped
        Toast.makeText(this, "onStop() - Activity Stopped", Toast.LENGTH_SHORT).show()

        backgroundDetector.activityStopped()
    }

    override fun onResume() {
        Timber.i("onResume()")
        super.onResume()

        // Show a toast message when the activity is resumed
        Toast.makeText(this, "onResume() - Activity Resumed", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        Timber.i("onPause()")
        super.onPause()

        // Show a toast message when the activity is paused
        Toast.makeText(this, "onPause() - Activity Paused", Toast.LENGTH_SHORT).show()
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        Timber.i("onTopResumedActivityChanged(); isTopResumed: $isTopResumedActivity")

        // Show a toast message when the top resumed activity changes
        Toast.makeText(this, "onTopResumedActivityChanged() - isTopResumed: $isTopResumedActivity", Toast.LENGTH_SHORT).show()
    }

    override fun onUserInputChanged(userInput: String) {
        txtUserInput.text = "User input: $userInput"

        // Show a toast message when the user input changes
        Toast.makeText(this, "onUserInputChanged() - User input changed", Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, ConfigChangeActivity::class.java)
            context.startActivity(intent)
        }
    }
}
