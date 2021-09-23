package com.example.genericsynctimer

import android.app.Application
import android.content.Context
import com.example.genericsynctimer.timer.PendingCallsTimer
import com.example.genericsynctimer.timer.PendingTransactionCallback
import com.example.genericsynctimer.timer.TimerConstants
import java.lang.ref.WeakReference


class MyApp : Application(), PendingTransactionCallback {

    companion object {
        var weakContext: WeakReference<Context>? = null
    }

    private var pendingCallsTimer: PendingCallsTimer? = null
    private lateinit var timersTrack: HashMap<String, PendingCallsTimer?>
    override fun onCreate() {
        super.onCreate()
        weakContext = WeakReference(this)
        timersTrack = HashMap()
    }


    fun createPendingItemsSyncTransactionTimer(
        context: Context? = null, action: String,
        listener: PendingTransactionCallback? = null,
        isRecurrent: Boolean = false,
        millisInFuture: Long = TimerConstants.PENDING_TRANSACTION_CALL_TIME,
        countDownInterval: Long = TimerConstants.INTERVAL
    ) {
        pendingCallsTimer = timersTrack[action]
        if (pendingCallsTimer == null) {
            pendingCallsTimer = PendingCallsTimer(
                context = context ?: this,
                listener = listener ?: this,
                action = action,
                isRecurrent = isRecurrent,
                millisInFuture = millisInFuture,
                countDownInterval = countDownInterval
            )
            timersTrack[action] = pendingCallsTimer
            pendingCallsTimer!!.start()
        }
    }

    override fun restartItemsSyncTimer(
        context: Context,
        action: String,
        listener: PendingTransactionCallback,
        isRecurrent: Boolean,
        millisInFuture: Long,
        countDownInterval: Long
    ) {
        pendingCallsTimer = timersTrack[action]
        if (pendingCallsTimer != null) {
            pendingCallsTimer!!.start()
        } else {
            createPendingItemsSyncTransactionTimer(context = context,
                listener = listener,
                action = action,
                isRecurrent = isRecurrent,
                millisInFuture = millisInFuture,
                countDownInterval = countDownInterval)
        }
    }

    override fun finishItemsSyncTimer(action: String) {
        pendingCallsTimer = timersTrack[action]
        if (pendingCallsTimer != null) {
            pendingCallsTimer!!.cancel()
            pendingCallsTimer = null
            timersTrack[action] = pendingCallsTimer
        }
    }


}