package com.dilip.androidlifecyclexml.fragments

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dilip.androidlifecyclexml.BackgroundDetector
import com.dilip.androidlifecyclexml.CustomApplication
import com.dilip.androidlifecyclexml.MyFragmentLifecycleCallbacks
import com.dilip.androidlifecyclexml.R
import timber.log.Timber

class FragmentHostActivity : AppCompatActivity() {

    private lateinit var backgroundDetector: BackgroundDetector

    private lateinit var btnAddRemoveFragment: Button

    private var isFragmentAdded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate()")
        super.onCreate(savedInstanceState)

        // Show Toast when activity is created
        Toast.makeText(this, "Activity Created", Toast.LENGTH_SHORT).show()

        val application = this.applicationContext as Application
        backgroundDetector = (application as CustomApplication).backgroundDetector

        isFragmentAdded = savedInstanceState?.getBoolean("isFragmentAdded", false) ?: false

        setContentView(R.layout.activity_fragment_host)

        btnAddRemoveFragment = findViewById(R.id.btnAddRemoveFragment)
        btnAddRemoveFragment.setOnClickListener {
            if (isFragmentAdded) {
                Timber.i("Button clicked: remove Fragment")
                removeFragment()
                // Show Toast when the fragment is removed
                Toast.makeText(this, "Fragment Removed", Toast.LENGTH_SHORT).show()
            } else {
                Timber.i("Button clicked: add Fragment")
                addFragment()
                // Show Toast when the fragment is added
                Toast.makeText(this, "Fragment Added", Toast.LENGTH_SHORT).show()
            }
            isFragmentAdded = !isFragmentAdded
            updateButtonText()
        }

        updateButtonText()

        // Register fragment lifecycle callbacks
        supportFragmentManager.registerFragmentLifecycleCallbacks(MyFragmentLifecycleCallbacks(), false)
    }

    private fun updateButtonText() {
        if (isFragmentAdded) {
            btnAddRemoveFragment.text = "Remove fragment"
        } else {
            btnAddRemoveFragment.text = "Add fragment"
        }
    }

    private fun addFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, Fragment1.newInstance(), "fragmentTag")
            .commit()
    }

    private fun removeFragment() {
        supportFragmentManager
            .beginTransaction()
            .remove(supportFragmentManager.findFragmentByTag("fragmentTag")!!)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isFragmentAdded", isFragmentAdded)
    }

    override fun onStart() {
        Timber.i("onStart()")
        super.onStart()
        backgroundDetector.activityStarted()

        // Show Toast when activity is started
        Toast.makeText(this, "Activity Started", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        Timber.i("onStop()")
        super.onStop()
        backgroundDetector.activityStopped()

        // Show Toast when activity is stopped
        Toast.makeText(this, "Activity Stopped", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        Timber.i("onResume()")
        super.onResume()

        // Show Toast when activity is resumed
        Toast.makeText(this, "Activity Resumed", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        Timber.i("onPause()")
        super.onPause()

        // Show Toast when activity is paused
        Toast.makeText(this, "Activity Paused", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        Timber.i("onDestroy()")
        super.onDestroy()

        // Show Toast when activity is destroyed
        Toast.makeText(this, "Activity Destroyed", Toast.LENGTH_SHORT).show()
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        Timber.i("onTopResumedActivityChanged(); isTopResumed: $isTopResumedActivity")
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, FragmentHostActivity::class.java)
            context.startActivity(intent)
        }
    }
}
