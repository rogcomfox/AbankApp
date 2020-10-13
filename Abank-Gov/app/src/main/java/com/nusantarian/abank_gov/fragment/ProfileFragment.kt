package com.nusantarian.abank_gov.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nusantarian.abank_gov.R
import com.nusantarian.abank_gov.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var userData: CollectionReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.nav_profile)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        auth = FirebaseAuth.getInstance()
        userData = FirebaseFirestore.getInstance().collection("users")
        val uid = auth.currentUser?.uid
        //load data of profile
        loadData(uid)
        setHasOptionsMenu(true)

        //edit profile button
        binding.btnEditProfile.setOnClickListener{

        }

        return binding.root
    }

    private fun loadData(uid: String?) {
        userData.document(uid!!).get().addOnSuccessListener {
            binding.progress.visibility = View.VISIBLE
            binding.tvEmail.text = it.getString("email")
            binding.tvName.text = it.getString("name")
            binding.tvPhone.text = it.getString("phone")
            binding.imgProfile.load(it.getString("imageURL"))
        }.addOnFailureListener {
            Log.e("Failed To Load User Data", it.toString())
            binding.progress.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.profile_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_photo ->
                Toast.makeText(activity, "Under Construction", Toast.LENGTH_SHORT).show()
            R.id.nav_email ->
                Toast.makeText(activity, "Under Construction", Toast.LENGTH_SHORT).show()
            R.id.nav_pass ->
                Toast.makeText(activity, "Under Construction", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}