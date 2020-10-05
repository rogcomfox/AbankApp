package com.nusantarian.abank_gov.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.nusantarian.abank_gov.R
import com.nusantarian.abank_gov.databinding.ActivityMainBinding
import com.nusantarian.abank_gov.fragment.MainFragment

class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.addOnBackStackChangedListener(this)
        initMainFragment()
    }

    private fun initMainFragment(){
        supportFragmentManager.beginTransaction()
            .add(R.id.frame_main, MainFragment())
            .commit()
    }

    override fun onBackStackChanged() {

    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStack()
        else
            super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }
}