package com.example.recyclearn.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclearn.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        nameTextView = findViewById(R.id.nameTextView)
        emailTextView = findViewById(R.id.emailTextView)
        logoutButton = findViewById(R.id.logoutButton)

        loadUserProfile()

        logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun loadUserProfile() {
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val userDoc = firestore.collection("users").document(user.uid).get().await()
                    val name = userDoc.getString("name") ?: "User"
                    val email = user.email ?: "No email"

                    nameTextView.text = "Name: $name"
                    emailTextView.text = "Email: $email"
                } catch (e: Exception) {
                    Toast.makeText(this@ProfileActivity, "Error loading profile", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}