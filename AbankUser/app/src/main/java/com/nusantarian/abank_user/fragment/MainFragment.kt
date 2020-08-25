package com.nusantarian.abank_user.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.nusantarian.abank_user.R
import com.nusantarian.abank_user.activity.AuthActivity
import com.nusantarian.abank_user.databinding.FragmentMainBinding

class MainFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var ft: FragmentTransaction
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        ft = activity!!.supportFragmentManager.beginTransaction()
        auth = FirebaseAuth.getInstance()
        setHasOptionsMenu(true)

        binding.cardAccount.setOnClickListener(this)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_chat ->
                ft.replace(R.id.frame_main, ChatFragment())
                    .addToBackStack(null)
                    .commit()
            R.id.nav_notification ->
                ft.replace(R.id.frame_main, NotificationFragment())
                    .addToBackStack(null)
                    .commit()
            R.id.nav_logout ->
                logoutUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logoutUser() {
        val alertDialog = MaterialAlertDialogBuilder(context!!)
        alertDialog.setMessage(resources.getString(R.string.text_logout_verify))
            .setPositiveButton(resources.getString(R.string.text_yes)) { _, _ ->
                auth.signOut()
                startActivity(Intent(activity, AuthActivity::class.java))
                activity!!.finish()
            }
            .setNegativeButton(resources.getString((R.string.text_no))) { _, _ ->

            }
            .show()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.card_account ->
                ft.replace(R.id.frame_main, MyAccountFragment())
                    .addToBackStack(null)
                    .commit()
        }
    }
}