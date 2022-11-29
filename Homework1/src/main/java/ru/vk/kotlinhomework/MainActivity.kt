package ru.vk.kotlinhomework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.vk.kotlinhomework.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

                        // Variables declaration

    private lateinit var binding: ActivityMainBinding

                        // Override functions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.activityMainToolbar)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(
                R.id.activity_main_fragmentContainerView, MainFragment.Create(5)
            ).commit()
        }
    }
}