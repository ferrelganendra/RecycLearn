package com.example.recyclearn.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclearn.Artikel.HorizontalAdapter
import com.example.recyclearn.Artikel.Model
import com.example.recyclearn.Fragment.CourseFragment
import com.example.recyclearn.Fragment.DashboardFragment
import com.example.recyclearn.Fragment.ProfileFragment
import com.example.recyclearn.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HorizontalAdapter
    private val itemList = mutableListOf<Model>()

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.flFragment, DashboardFragment())
                .commit()
        }

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { item ->
            val selectedFragment = when (item.itemId) {
                R.id.home -> DashboardFragment()
                R.id.profile -> ProfileFragment()
                R.id.course -> CourseFragment()
                else -> null
            }

            selectedFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.flFragment, it)
                    .commit()
            }

            true
        }

    }


}