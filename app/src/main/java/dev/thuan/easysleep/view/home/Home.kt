package dev.thuan.easysleep.view.home

import android.app.PendingIntent
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.ramcosta.composedestinations.annotation.Destination
import dev.no.room113.utils.notify.Notify
import dev.thuan.easysleep.MainActivity
import dev.thuan.easysleep.R
import dev.thuan.easysleep.receiver.AlarmReceiver
import dev.thuan.easysleep.ui.theme._dp
import dev.thuan.easysleep.view.CircularProgressBarScreen
import dev.thuan.utils.alarm.AlarmUtils

@Composable
@Destination(start = true)
fun Home() {
    val context = LocalContext.current
    var alarmTurnOnAfterHour = remember {
        0.5f
    }
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Timer",
            modifier = Modifier.padding(bottom = 48._dp),
            style = MaterialTheme.typography.headlineSmall,
        )
        Box {
            CircularProgressBarScreen(
                Modifier.wrapContentSize(),
            ) {
                alarmTurnOnAfterHour = it
            }
        }

        Spacer(modifier = Modifier.height(48._dp))
        Image(
            painter = painterResource(id = R.drawable.start_timer),
            contentDescription = null,
            modifier = Modifier
                .size(
                    width = 68._dp,
                    height = 68._dp,
                )
                .clip(RoundedCornerShape(50))
                .clickable {
                    AlarmUtils.instance.setAlarm(
                        System.currentTimeMillis() + alarmTurnOnAfterHour.toLong() * 60 * 60 * 1000,
                        showIntent = PendingIntent.getActivity(
                            context,
                            0,
                            Intent(context, MainActivity::class.java),
                            PendingIntent.FLAG_IMMUTABLE,
                        ),
                        pendingIntent = PendingIntent.getBroadcast(
                            context,
                            0,
                            Intent(context, AlarmReceiver::class.java),
                            PendingIntent.FLAG_IMMUTABLE,
                        ),
                    )
                },
        )
        Spacer(modifier = Modifier.height(48._dp))
        Box(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16._dp)
                .wrapContentHeight(Alignment.CenterVertically).background(color = Color(0xFFC1C4FF))
                .clickable {
                    // TODO: 12/2/2023
                    Notify.with(context)
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
                },
        ) {
            Text(
                text = "Choose music",
                modifier = Modifier.padding(horizontal = 16._dp, vertical = 8._dp),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun DialogChooseMusic() {
    // todo
    Column(
        modifier = Modifier.wrapContentSize(),
    ) {
        Text(
            text = "Choose music",
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}
