package com.example.filmscollection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.filmscollection.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchFragment=SearchFragment()
        val favoritesFragment=FavoritesFragment()
        val settingsFragment=SettingsFragment()

        setCurrentFragment(searchFragment)

        binding.navView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.search->setCurrentFragment(searchFragment)
                R.id.favorites->setCurrentFragment(favoritesFragment)
                R.id.settings->setCurrentFragment(settingsFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }

}