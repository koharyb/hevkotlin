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
import android.os.BatteryManager
import android.content.Context
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.*



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




            /*UI*/

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
                    val intent = Intent(this@MainActivity, BatteryService::class.java)
                    startForegroundService(intent)


                })
                { Text("h.e.v") }
            }
        }
    }
}

