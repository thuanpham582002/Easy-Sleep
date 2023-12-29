package dev.thuan.easysleep.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class ScheduleExtractAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.d("onReceive: ")
        // TODO: 12/1/2023
    }
}
