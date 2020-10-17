package com.nusantarian.abank_gov.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.nusantarian.abank_gov.R
import com.nusantarian.abank_gov.activity.LoginActivity
import com.nusantarian.abank_gov.databinding.FragmentMainBinding

class MainFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var ft: FragmentTransaction

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.app_name)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)
        ft = activity?.supportFragmentManager?.beginTransaction()!!
        auth = FirebaseAuth.getInstance()

        //fab on click
        binding.fabAddIot.setOnClickListener{
            ft.replace(R.id.frame_main, AddFragment())
                .addToBackStack(null)
                .commit()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync(this)
        binding.map.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_log_out ->
                raiseDialog()
            R.id.nav_notifications ->
                ft.replace(R.id.frame_main, NotificationsFragment())
                    .addToBackStack(null)
                    .commit()
            R.id.nav_message ->
                Toast.makeText(activity, "Under Construction", Toast.LENGTH_SHORT).show()
            R.id.nav_profile ->
                Toast.makeText(activity, "Under Construction", Toast.LENGTH_SHORT).show()
            R.id.nav_settings ->
                Toast.makeText(activity, "Under Construction", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun raiseDialog() {
        val builder = MaterialAlertDialogBuilder(activity!!)
        builder.setTitle(R.string.nav_log_out)
        builder.setMessage(R.string.text_message_log_out)
        builder.setPositiveButton(R.string.text_yes) { _, _ ->
            auth.signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }
        builder.setNegativeButton(R.string.text_no) { _, _ ->

        }
        builder.show()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}