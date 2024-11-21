package com.dilip.androidlifecyclexml.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.dilip.androidlifecyclexml.CustomApplication
import com.dilip.androidlifecyclexml.R
import timber.log.Timber
import kotlin.math.min

class MyOverlayService: Service() {

    private lateinit var myServiceManager: MyServiceManager
    private lateinit var windowManager: WindowManager
    private lateinit var notificationManager: NotificationManager

    private var overlayView: View? = null

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
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onDestroy() {
        Timber.d("onDestroy()")
        // Show a toast when onDestroy is called
        Toast.makeText(this, "onDestroy() called", Toast.LENGTH_SHORT).show()
        super.onDestroy()
        hideOverlay()
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
            showOverlay()
        }
        if (intent?.hasExtra(EXTRA_COMMAND_STOP) == true) {
            Timber.d("Stop service command")
            Toast.makeText(this, "Stop service command", Toast.LENGTH_SHORT).show()
            stopSelf()
        }

        return START_REDELIVER_INTENT
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showOverlay() {
        myServiceManager.overlayServiceActive.value = true

        if (overlayView != null) {
            Timber.i("overlay already shown - aborting")
            return
        }

        overlayView = LayoutInflater.from(this).inflate(R.layout.element_overlay, null)

        val params = getOverlayLayoutParams()

        windowManager.addView(overlayView, params)

        // Show a toast when the overlay is shown
        Toast.makeText(this, "Overlay shown", Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getOverlayLayoutParams(): ViewGroup.LayoutParams {
        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels

        val size = (min(screenWidth, screenHeight) * 0.25).toInt() // 25% of the smallest dimension

        val params = WindowManager.LayoutParams(
            size,
            size,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.TOP or Gravity.START
        params.x = (screenWidth - size) / 2 // center horizontally
        params.y = screenHeight / 10 // at the top of the screen

        return params
    }

    private fun hideOverlay() {
        myServiceManager.overlayServiceActive.value = false
        if (overlayView == null) {
            Timber.i("overlay not shown - aborting")
            return
        }
        windowManager.removeView(overlayView)
        overlayView = null

        // Show a toast when the overlay is hidden
        Toast.makeText(this, "Overlay hidden", Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Show a toast when configuration changes
        Toast.makeText(this, "onConfigurationChanged() called", Toast.LENGTH_SHORT).show()
        hideOverlay()
        showOverlay()
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

        private const val ID = 1002
        private const val CHANNEL_ID = "overlay_channel_id"

        @RequiresApi(Build.VERSION_CODES.O)
        fun showOverlay(context: Context) {
            val intent = Intent(context, MyOverlayService::class.java)
            intent.putExtra(EXTRA_COMMAND_START, true)
            context.startForegroundService(intent)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun hideOverlay(context: Context) {
            val intent = Intent(context, MyOverlayService::class.java)
            intent.putExtra(EXTRA_COMMAND_STOP, true)
            context.startForegroundService(intent)
        }
    }
}
