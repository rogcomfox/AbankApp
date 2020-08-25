package com.nusantarian.abank_user.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nusantarian.abank_user.R
import com.nusantarian.abank_user.databinding.FragmentMyAccountBinding

class MyAccountFragment : Fragment() {

    private var _binding: FragmentMyAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var ft: FragmentTransaction

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        ft = activity!!.supportFragmentManager.beginTransaction()
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.my_account_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_profile_picture ->
                raiseAlertDialog()
            R.id.nav_change_email ->
                ft.replace(R.id.frame_main, ChangeEmailFragment())
                    .addToBackStack(null)
                    .commit()
            R.id.nav_change_pass ->
                ft.replace(R.id.frame_main, ChangePassFragment())
                    .addToBackStack(null)
                    .commit()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun raiseAlertDialog(){
        val alert = MaterialAlertDialogBuilder(context!!)
        alert.setItems(R.array.profile_picture) { _, which ->
            when (which) {
                0 -> takePhoto()
                1 -> chooseGallery()
                2 -> deletePicture()
            }
        }
            .setTitle(resources.getString(R.string.title_change_picture))
            .show()
    }

    private fun takePhoto(){

    }

    private fun chooseGallery(){

    }

    private fun deletePicture(){

    }
}