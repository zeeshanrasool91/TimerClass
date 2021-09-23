package com.example.genericsynctimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.genericsynctimer.timer.TimerConstants

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val timerOne = findViewById<TextView>(R.id.timer_one)
        timerOne.setOnClickListener {
            (applicationContext as MyApp).createPendingItemsSyncTransactionTimer(
                action = TimerConstants.ACTION_FOR_1,
                isRecurrent = false
            )
        }
        val timerTwo = findViewById<TextView>(R.id.timer_two)
        timerTwo.setOnClickListener {
            (applicationContext as MyApp).createPendingItemsSyncTransactionTimer(
                action = TimerConstants.ACTION_FOR_2,
                isRecurrent = false
            )
        }

    }
}