package com.example.construction_app

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main. activity_main.*
import android.widget.Toast
import android.os.CountDownTimer
import java.util.*
import java.util.concurrent.TimeUnit
import android.content.Context

class MainActivity : AppCompatActivity() {
    private var isCancelled = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val minute: Long = 1000 * 60 // 1000 milliseconds = 1 second
        // 10
        val millisInFuture: Long = (minute * 100)
        // Count down interval 1 second
        val countDownInterval: Long = 1000
        button.setOnClickListener {
            timer(millisInFuture,countDownInterval).start()
        }
    }
    private fun timer(millisInFuture: Long, countDownInterval: Long): CountDownTimer {
        return object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                val timeRemaining = timeString(millisUntilFinished)
                if (isCancelled) {
                    textView.text = textView.text.toString() + "\nStopped.(Cancelled)"
                    cancel()
                } else {
                    textView.text = timeRemaining
                }
            }
            override fun onFinish() {
                toast("Timer complete.")
            }
        }
    }
    private fun timeString(millisUntilFinished: Long): String {
        var millisUntilFinished: Long = millisUntilFinished
        val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
        millisUntilFinished -= TimeUnit.DAYS.toMillis(days)
        val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
        millisUntilFinished -= TimeUnit.MILLISECONDS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
        millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
        return String.format(
            Locale.getDefault(),
            "%02d:%02d:%02d:%02d",
            days, hours, minutes, seconds
        )
    }
    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}