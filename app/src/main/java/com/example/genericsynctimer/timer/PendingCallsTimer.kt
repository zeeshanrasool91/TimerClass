package com.example.genericsynctimer.timer


import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.genericsynctimer.MyApp
import java.util.concurrent.TimeUnit

/**
 * @param millisInFuture    The number of millis in the future from the call
 * to [.start] until the countdown is done and [.onFinish]
 * is called.
 * @param countDownInterval The interval along the way to receive
 * [.onTick] callbacks.
 */
class PendingCallsTimer(
    private val context: Context, private val action: String,
    private val listener: PendingTransactionCallback,
    private val isRecurrent: Boolean = false,
    private val millisInFuture: Long = TimerConstants.PENDING_TRANSACTION_CALL_TIME,
    private val countDownInterval: Long = TimerConstants.INTERVAL
) : CountDownTimer(millisInFuture, countDownInterval) {


    companion object {
        private const val TAG = "PendingCallsTimer"
        const val IS_SUCCESS = "IS_SUCCESS"
        const val MESSAGE = "message"
    }

    override fun onTick(millisUntilFinished: Long) {
        val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
        Log.d(TAG, "onTick: $seconds $action")
    }

    /**
     * on count down finish
     * call sendPendingTransactions to send pending transactions, messages
     * and restart timer again
     */
    override fun onFinish() {
        Log.d(TAG, "onFinish: $action")
        sendPendingTransactions(action)
        if (isRecurrent) {
            listener.restartItemsSyncTimer(
                context,
                action,
                listener,
                isRecurrent,
                millisInFuture,
                countDownInterval
            )
        } else {
            listener.finishItemsSyncTimer(action)
        }
    }

    /**
     * check internet is connected and user is logged In and then
     * process pending transactions and message
     * call pull_notification if user key exist
     */
    private fun sendPendingTransactions(action: String) {
        Log.d(TAG, "sendPendingTransactions: $action")
        callApi()
    }


    private fun callApi() {

    }

    private fun updateLastUpdatedTime(isSuccess: Boolean, msg: String?) {
        val intent = Intent()
        intent.action = TimerConstants.UPDATE_LABEL_ACTION
        intent.putExtra(IS_SUCCESS, isSuccess)
        intent.putExtra(MESSAGE, msg)
        MyApp.weakContext?.get()?.let {
            LocalBroadcastManager.getInstance(it).sendBroadcast(intent)
        }
    }

}
