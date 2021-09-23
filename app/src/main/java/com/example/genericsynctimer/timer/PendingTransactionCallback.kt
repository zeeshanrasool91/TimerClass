package com.example.genericsynctimer.timer

import android.content.Context

interface PendingTransactionCallback {
    fun restartItemsSyncTimer(  context: Context, action: String,
                              listener: PendingTransactionCallback,
                              isRecurrent: Boolean = false,
                              millisInFuture: Long = TimerConstants.PENDING_TRANSACTION_CALL_TIME,
                              countDownInterval: Long = TimerConstants.INTERVAL)
    fun finishItemsSyncTimer(action: String)
}
