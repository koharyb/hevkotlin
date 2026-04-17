package com.example.hevkotlin


import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter

class BatteryService : Service() {

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val level = context?.let { getBatteryLevel(it) }

            if (level != null) {
                println("Battery level: $level")

//                sound logic here :

            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(receiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    override fun onBind(intent: Intent?): IBinder? = null


}