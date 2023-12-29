package dev.thuan.easysleep.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dev.no.room113.utils.notify.Notify
import kotlinx.coroutines.Job

class TimerTileService : Service() {
    var timerJob: Job? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        //  Create notification here.
        Notify.with(this)
            .header {
            }
            .alerting("channel_key") {
                this.channelName = "channel_name"
                this.channelDescription = "channel_description"
            }
            .progress {
                this.progressPercent = 50
                this.enablePercentage = true
            }
            .show()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }
}
