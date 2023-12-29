package dev.thuan.easysleep.utils

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothHeadset
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.util.Log

fun BluetoothDevice.removeBond() {
    try {
        javaClass.getMethod("removeBond").invoke(this)
    } catch (e: Exception) {
        Log.e("MainActivity", "Removing bond has been failed. ${e.message}")
    }
}

private fun disconnect(context: Context, device: BluetoothDevice) {
    val serviceListener: BluetoothProfile.ServiceListener = object :
        BluetoothProfile.ServiceListener {
        override fun onServiceDisconnected(profile: Int) {
            Log.i("MainActivity", "onServiceDisconnected: $profile")
        }

        @SuppressLint("DiscouragedPrivateApi")
        override fun onServiceConnected(profile: Int, proxy: BluetoothProfile) {
            Log.i("MainActivity", "onServiceConnected: $profile $proxy")
            val disconnect = BluetoothHeadset::class.java.getDeclaredMethod(
                "disconnect",
                BluetoothDevice::class.java,
            )
            disconnect.isAccessible = true
            disconnect.invoke(proxy, device)
            BluetoothAdapter.getDefaultAdapter().closeProfileProxy(profile, proxy)
        }
    }
    BluetoothAdapter.getDefaultAdapter()
        .getProfileProxy(context, serviceListener, BluetoothProfile.HEADSET)
}

@SuppressLint("MissingPermission")
fun isBluetoothHeadsetConnected(context: Context): Boolean {
    val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    val isBluetoothHeadsetConnected =
        mBluetoothAdapter != null && mBluetoothAdapter.isEnabled &&
            mBluetoothAdapter.getProfileConnectionState(BluetoothHeadset.HEADSET) == BluetoothHeadset.STATE_CONNECTED
    return isBluetoothHeadsetConnected
}
