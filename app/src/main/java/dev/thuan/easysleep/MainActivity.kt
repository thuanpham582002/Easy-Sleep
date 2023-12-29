package dev.thuan.easysleep

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat.startActivity
import com.ramcosta.composedestinations.DestinationsNavHost
import dev.thuan.easysleep.receiver.AlarmReceiver
import dev.thuan.easysleep.ui.theme.EasySleepTheme
import dev.thuan.easysleep.view.home.NavGraphs

class MainActivity : ComponentActivity() {
    var pairedDevices: MutableSet<BluetoothDevice>? = null

    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

    @SuppressLint("MissingPermission")
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted
                val bluetoothManager =
                    this@MainActivity.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

                pairedDevices = bluetoothManager.adapter.bondedDevices
                pairedDevices?.forEach { device ->
                    val deviceName = device.name
                    val deviceHardwareAddress = device.address // MAC address
                    val uuids = device.uuids
                    Log.i(
                        "MainActivity",
                        "deviceName: $deviceName, deviceHardwareAddress: $deviceHardwareAddress uuids: ${uuids.size}",
                    )

//                    device.removeBond()
//                    try {
//                        javaClass.getMethod("removeBond").invoke(device)
//                    } catch (e: Exception) {
//                        Log.e("MainActivity", "Removing bond has been failed. ${e.message}")
//                    }
                }
            } else {
                // Navigate to settings screen
                Intent(
                    android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                ).apply {
                    data = android.net.Uri.fromParts(
                        "package",
                        packageName,
                        null,
                    )
                    startActivity(this)
                }
            }
        }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requestPermissionLauncher.launch(
//            Manifest.permission.BLUETOOTH_CONNECT,
//        )
        alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(this, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(this, 0, intent, FLAG_IMMUTABLE)
        }
        alarmMgr?.setAlarmClock(
            AlarmManager.AlarmClockInfo(
                System.currentTimeMillis() + 60 * 1000,
                alarmIntent,
            ),
            alarmIntent,
        )
        setContent {
            requestSchedulePermission(this@MainActivity)
            EasySleepTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}

fun requestSchedulePermission(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        Intent(
            Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
        ).apply {
            data = android.net.Uri.fromParts(
                "package",
                "dev.thuan.easysleep",
                null,
            )
            startActivity(context, this, null)
        }
    }
}
