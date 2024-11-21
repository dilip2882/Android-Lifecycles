package com.dilip.androidlifecyclexml.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.dilip.androidlifecyclexml.CustomApplication
import com.dilip.androidlifecyclexml.R
import timber.log.Timber

class MyForegroundService: Service() {

    private lateinit var myServiceManager: MyServiceManager
    private lateinit var notificationManager: NotificationManager

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
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onDestroy() {
        Timber.d("onDestroy()")
        // Show a toast when onDestroy is called
        Toast.makeText(this, "onDestroy() called", Toast.LENGTH_SHORT).show()
        super.onDestroy()
        myServiceManager.foregroundServiceState.value = MyServiceManager.MyServiceState.STOPPED
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("onStartCommand()")
        // Show a toast when onStartCommand is called
        Toast.makeText(this, "onStartCommand() called", Toast.LENGTH_SHORT).show()

        if (intent?.hasExtra(EXTRA_COMMAND_START) == true) {
            Timber.d("Start service command")
            Toast.makeText(this, "Start service command", Toast.LENGTH_SHORT).show()
            startForeground(ID, newNotification())
            myServiceManager.foregroundServiceState.value = MyServiceManager.MyServiceState.STARTED
        }

        if (intent?.hasExtra(EXTRA_COMMAND_STOP) == true) {
            Timber.d("Stop service command")
            Toast.makeText(this, "Stop service command", Toast.LENGTH_SHORT).show()
            stopSelf()
        }

        return START_REDELIVER_INTENT
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun newNotification(): Notification {
        val intent = Intent(this, ServiceActivity::class.java)

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        createServiceNotificationChannel()

        return Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("FS notification title")
            .setContentText("FS notification text")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createServiceNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "FS channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        notificationManager.createNotificationChannel(channel)
    }

    companion object {

        private const val EXTRA_COMMAND_START = "EXTRA_COMMAND_START"
        private const val EXTRA_COMMAND_STOP = "EXTRA_COMMAND_STOP"

        private const val ID = 1001
        private const val CHANNEL_ID = "fs_channel_id"

        @RequiresApi(Build.VERSION_CODES.O)
        fun start(context: Context) {
            val intent = Intent(context, MyForegroundService::class.java)
            intent.putExtra(EXTRA_COMMAND_START, true)
            context.startForegroundService(intent)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun stop(context: Context) {
            val intent = Intent(context, MyForegroundService::class.java)
            intent.putExtra(EXTRA_COMMAND_STOP, true)
            context.startForegroundService(intent)
        }
    }
}
