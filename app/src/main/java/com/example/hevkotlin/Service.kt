package com.example.hevkotlin


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.content.pm.ServiceInfo
import android.media.MediaPlayer
import android.os.BatteryManager
import androidx.compose.runtime.remember
import androidx.core.app.NotificationCompat
import android.os.SystemClock


class BatteryService : Service() {


    private lateinit var numberSounds: Map<Int, MediaPlayer>
    private lateinit var wordSounds: Map<String, MediaPlayer>

    private var lastSoundStartedAt = 0L

    private var lastChargerSoundStartedAt = 0L

    private val soundCooldDown = 60_000L


    private fun canStartNewSound(): Boolean {
        val now = SystemClock.elapsedRealtime()

        if (now - lastSoundStartedAt < soundCooldDown) {
            return false
        }

        if (now - lastChargerSoundStartedAt < soundCooldDown) {
            return false
        }

        return true
    }

    private fun playChargingSound() {
        val now = SystemClock.elapsedRealtime()

        if (now - lastChargerSoundStartedAt < soundCooldDown) {
            return
        }

        lastChargerSoundStartedAt = now

        wordSounds["charge"]?.start()
    }


    private val batteryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)

            if (level != null && level != -1) {
                println("Battery level: $level")
                handleBatteryLevel(level)
            }
        }
    }

    private fun handleBatteryLevel(level: Int) {
        when (level) {
            10 -> playSound(10)
            20 -> playSound(20)
            30 -> playSound(30)
            40 -> playSound(40)
            50 -> playSound(50)
            60 -> playSound(60)
            70 -> playSound(70)
            80 -> playSound(80)
            90 -> playSound(90)
            100 -> playSound(100)
        }
    }


    private fun playSound(level: Int) {

        if (!canStartNewSound()) {

            return
        }
        lastSoundStartedAt = SystemClock.elapsedRealtime()


        wordSounds["powerLevelIs"]?.setOnCompletionListener {
            numberSounds[level]?.start()
        }

        numberSounds[level]?.setOnCompletionListener {
            wordSounds["percent"]?.start()
        }

        wordSounds["powerLevelIs"]?.start()
    }

    @SuppressLint("ForegroundServiceType")

    private fun startForegroundMode() {
        val channelId = "battery_service_channel"

        val channel = NotificationChannel(
            channelId,
            "Battery Service",
            NotificationManager.IMPORTANCE_LOW
        )

        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Battery Service Running")
            .setContentText("Monitoring battery level")
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .build()

        startForeground(1,
            notification)
    }
    // charger plugged receiver
    class ChargerReceiver(
        val onConnected:() -> Unit

    ) : BroadcastReceiver() {
        override fun onReceive(context:Context?, intent: Intent?) {
            if (intent?.action == Intent.ACTION_POWER_CONNECTED) {
                onConnected()
            }
        }
    }

    //            play a sound of charging function
    val chargerReceiver =
        ChargerReceiver {
            playChargingSound()
        }



    override fun onCreate() {
        super.onCreate()



        //          audio file values and files itself mapped via mapOf
        numberSounds = mapOf(

            10 to MediaPlayer.create(this, R.raw.ten),
            20 to MediaPlayer.create(this, R.raw.twenty),
            30 to MediaPlayer.create(this, R.raw.thirty),
            40 to MediaPlayer.create(this, R.raw.fourty),
            50 to MediaPlayer.create(this, R.raw.fifty),
            60 to MediaPlayer.create(this, R.raw.sixty),
            70 to MediaPlayer.create(this, R.raw.seventy),
            80 to MediaPlayer.create(this, R.raw.eighty),
            90 to MediaPlayer.create(this, R.raw.ninety),
            100 to MediaPlayer.create(this, R.raw.onehundred),

            )

        wordSounds = mapOf(
            "powerLevelIs" to MediaPlayer.create(this, R.raw.power_level_is),
            "percent" to MediaPlayer.create(this, R.raw.percent),
            "warning" to MediaPlayer.create(this, R.raw.warning),
            "charge" to MediaPlayer.create(this, R.raw.hevcharg)

        )

        val batteryFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(batteryReceiver, batteryFilter)

        val chargerFilter = IntentFilter(Intent.ACTION_POWER_CONNECTED)
        registerReceiver(chargerReceiver, chargerFilter)


    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundMode()

        return START_STICKY
    }


    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(batteryReceiver)
        unregisterReceiver(chargerReceiver)
        numberSounds.values.forEach { it.release() }
        wordSounds.values.forEach { it.release() }


    }

    override fun onBind(intent: Intent?): IBinder? = null


}