package com.dilip.androidlifecyclexml.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import com.dilip.androidlifecyclexml.CustomApplication
import timber.log.Timber

class MyService: Service() {

    private lateinit var myServiceManager: MyServiceManager

    override fun onBind(intent: Intent?): IBinder? {
        // Show a toast when onBind is called
        Toast.makeText(this, "onBind() called", Toast.LENGTH_SHORT).show()
        return null
    }

    override fun onCreate() {
        Timber.d("onCreate()")
        // Show a toast when onCreate is called
        Toast.makeText(this, "onCreate() called", Toast.LENGTH_SHORT).show()
        super.onCreate()
        myServiceManager = (application as CustomApplication).myServiceManager
    }

    override fun onDestroy() {
        Timber.d("onDestroy()")
        // Show a toast when onDestroy is called
        Toast.makeText(this, "onDestroy() called", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("onStartCommand()")
        // Show a toast when onStartCommand is called
        Toast.makeText(this, "onStartCommand() called", Toast.LENGTH_SHORT).show()

        if (intent?.hasExtra(EXTRA_COMMAND_START) == true) {
            Timber.d("Start service command")
            Toast.makeText(this, "Start service command", Toast.LENGTH_SHORT).show()
            myServiceManager.serviceState.value = MyServiceManager.MyServiceState.STARTED
        }
        if (intent?.hasExtra(EXTRA_COMMAND_STOP) == true) {
            Timber.d("Stop service command")
            Toast.makeText(this, "Stop service command", Toast.LENGTH_SHORT).show()
            stopSelf()
            myServiceManager.serviceState.value = MyServiceManager.MyServiceState.STOPPED
        }

        return super.onStartCommand(intent, flags, startId)
    }

    companion object {

        private val EXTRA_COMMAND_START = "EXTRA_COMMAND_START"
        private val EXTRA_COMMAND_STOP = "EXTRA_COMMAND_STOP"

        fun start(context: Context) {
            val intent = Intent(context, MyService::class.java)
            intent.putExtra(EXTRA_COMMAND_START, true)
            context.startService(intent)
        }

        fun stop(context: Context) {
            val intent = Intent(context, MyService::class.java)
            intent.putExtra(EXTRA_COMMAND_STOP, true)
            context.startService(intent)
        }
    }
}
