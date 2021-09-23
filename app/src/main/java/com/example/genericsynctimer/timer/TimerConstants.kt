package com.example.genericsynctimer.timer

object TimerConstants {

    const val SYNC_TIME = "sync_default_time"
    const val UPDATE_LABEL_ACTION = "update_label"
    private const val DEFAULT_SYNC_DURATION = 10L
    const val INTERVAL: Long = 1000
    private const val FOR_HALF_MINUTE: Long = DEFAULT_SYNC_DURATION * INTERVAL
    const val PENDING_TRANSACTION_CALL_TIME = FOR_HALF_MINUTE
    const val ACTION_FOR_1 = "action_1"
    const val ACTION_FOR_2 = "action_2"
}