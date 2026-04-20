package com.example.hevkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import android.media.MediaPlayer
import android.os.BatteryManager
import android.content.Context
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.*
import com.example.hevkotlin.BatteryService


fun getBatteryLevel(context: Context): Int {
    val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
    return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
}


//            receiver
class BatteryStatusReceiver(
    val onUpdate: (Int) -> Unit
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val level = context?.let { getBatteryLevel(it) }
        if (level != null) {
            onUpdate(level)
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        start Service
        val intent = Intent(this, BatteryService::class.java)
        startForegroundService(intent)


        enableEdgeToEdge()
        setContent {

//          state
            var batteryLevel by remember { mutableIntStateOf(0) }

//          receiver instance
            val receiver = remember {
                BatteryStatusReceiver { newLevel ->
                    batteryLevel = newLevel
                }
            }

//          register receiver
            DisposableEffect(Unit) {
                val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
                registerReceiver(receiver, filter)

                onDispose {
                    unregisterReceiver(receiver)
                }
            }


//          audio file values and files itself mapped via mapOf
            val numberSounds = mapOf(

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

            val wordSounds = mapOf(
                "powerLevelIs" to MediaPlayer.create(this, R.raw.power_level_is),
                "percent" to MediaPlayer.create(this, R.raw.percent),
                "warning" to MediaPlayer.create(this, R.raw.warning)
            )


//            each message dedicated function

            fun playBatteryMessage10() {
                wordSounds["powerLevelIs"]?.setOnCompletionListener {
                    numberSounds[10]?.start()
                }
                numberSounds[10]?.setOnCompletionListener {
                    wordSounds["percent"]?.start()
                }
                wordSounds["powerLevelIs"]?.start()
            }

            fun playBatteryMessage20() {
                wordSounds["powerLevelIs"]?.setOnCompletionListener {
                    numberSounds[20]?.start()
                }
                numberSounds[20]?.setOnCompletionListener {
                    wordSounds["percent"]?.start()
                }
                wordSounds["powerLevelIs"]?.start()
            }

            fun playBatteryMessage30() {
                wordSounds["powerLevelIs"]?.setOnCompletionListener {
                    numberSounds[30]?.start()
                }
                numberSounds[30]?.setOnCompletionListener {
                    wordSounds["percent"]?.start()
                }
                wordSounds["powerLevelIs"]?.start()
            }

            fun playBatteryMessage40() {
                wordSounds["powerLevelIs"]?.setOnCompletionListener {
                    numberSounds[40]?.start()
                }
                numberSounds[40]?.setOnCompletionListener {
                    wordSounds["percent"]?.start()
                }
                wordSounds["powerLevelIs"]?.start()
            }

            fun playBatteryMessage50() {
                wordSounds["powerLevelIs"]?.setOnCompletionListener {
                    numberSounds[50]?.start()
                }
                numberSounds[50]?.setOnCompletionListener {
                    wordSounds["percent"]?.start()
                }
                wordSounds["powerLevelIs"]?.start()
            }

            fun playBatteryMessage60() {
                wordSounds["powerLevelIs"]?.setOnCompletionListener {
                    numberSounds[60]?.start()
                }
                numberSounds[60]?.setOnCompletionListener {
                    wordSounds["percent"]?.start()
                }
                wordSounds["powerLevelIs"]?.start()
            }

            fun playBatteryMessage70() {
                wordSounds["powerLevelIs"]?.setOnCompletionListener {
                    numberSounds[70]?.start()
                }
                numberSounds[70]?.setOnCompletionListener {
                    wordSounds["percent"]?.start()
                }
                wordSounds["powerLevelIs"]?.start()
            }

            fun playBatteryMessage80() {
                wordSounds["powerLevelIs"]?.setOnCompletionListener {
                    numberSounds[80]?.start()
                }
                numberSounds[80]?.setOnCompletionListener {
                    wordSounds["percent"]?.start()
                }
                wordSounds["powerLevelIs"]?.start()
            }

            fun playBatteryMessage90() {
                wordSounds["powerLevelIs"]?.setOnCompletionListener {
                    numberSounds[90]?.start()
                }
                numberSounds[90]?.setOnCompletionListener {
                    wordSounds["percent"]?.start()
                }
                wordSounds["powerLevelIs"]?.start()
            }

            fun playBatteryMessage100() {
                wordSounds["powerLevelIs"]?.setOnCompletionListener {
                    numberSounds[100]?.start()
                }
                numberSounds[100]?.setOnCompletionListener {
                    wordSounds["percent"]?.start()
                }
                wordSounds["powerLevelIs"]?.start()
            }

            fun ifBatteryLevel() {
                if (batteryLevel == 10) {
                    playBatteryMessage10()
                } else if (batteryLevel == 20) {
                    playBatteryMessage20()
                } else if (batteryLevel == 30) {
                    playBatteryMessage30()
                } else if (batteryLevel == 40) {
                    playBatteryMessage40()
                } else if (batteryLevel == 50) {
                    playBatteryMessage50()
                } else if (batteryLevel == 60) {
                    playBatteryMessage60()
                } else if (batteryLevel == 70) {
                    playBatteryMessage70()
                } else if (batteryLevel == 80) {
                    playBatteryMessage80()
                } else if (batteryLevel == 90) {
                    playBatteryMessage90()
                } else if (batteryLevel == 100) {
                    playBatteryMessage100()
                }
            }




            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "power level its: $batteryLevel%",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    "$batteryLevel%",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Button(onClick = {
                    ifBatteryLevel()
                })
                { Text("h.e.v") }
            }
        }
    }
}

