package dev.thuan.easysleep

import android.app.Application
import android.os.Looper
import android.widget.Toast
import dev.no.room113.utils.UtilsCenter
import timber.log.Timber

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        instance = this
        UtilsCenter.init(this)
    }

    companion object {
        lateinit var instance: MainApplication
            private set

        fun toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
            Looper.getMainLooper().run {
                Toast.makeText(
                    instance,
                    message,
                    duration,
                ).show()
            }
        }
    }
}
