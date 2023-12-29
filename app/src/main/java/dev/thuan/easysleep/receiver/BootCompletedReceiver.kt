package dev.thuan.easysleep.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // TODO: 12/2/2023 reset up timer
        Timber.d("BootCompletedReceiver onReceive")
    }
}
