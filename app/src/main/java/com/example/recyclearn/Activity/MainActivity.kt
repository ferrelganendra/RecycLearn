package com.example.recyclearn.Activity


import com.example.recyclearn.Activity.Dashboard
import com.example.recyclearn.Activity.LoginActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recyclearn.R

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.SystemClock
import com.example.recyclearn.Notification.NotificationReceiver
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize Firebase Authentication
        auth = Firebase.auth

        // Schedule notifications
        scheduleNotification()

        // Set window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.intro)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find the button by its ID
        val button: Button = findViewById(R.id.introBtn)

        // Set an OnClickListener on the button
        button.setOnClickListener {
            // Check if user is logged in
            val currentUser = auth.currentUser
            if (currentUser != null) {
                // User is signed in, go to Dashboard
                val intent = Intent(this, Dashboard::class.java)
                startActivity(intent)
            } else {
                // No user signed in, go to Login
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly
        val currentUser = auth.currentUser
        if (currentUser == null) {
            // If no user is signed in, redirect to login
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun scheduleNotification() {
        val intent = Intent(this, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val triggerTime = SystemClock.elapsedRealtime() + 60 * 1000 // First trigger in 1 minute
        val interval = 1 * 60 * 1000L // Repeat every 2 minutes

        alarmManager.setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            triggerTime,
            interval,
            pendingIntent
        )
    }
}