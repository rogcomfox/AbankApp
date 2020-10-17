package com.nusantarian.abank_gov.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nusantarian.abank_gov.R
import com.nusantarian.abank_gov.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.nav_notifications)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.notifications_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_trash) {
            raiseDeleteDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun raiseDeleteDialog() {
        val builder = MaterialAlertDialogBuilder(activity!!)
        builder.setMessage(R.string.text_notifications)
        builder.setPositiveButton(R.string.text_yes) { _, _ ->
            Toast.makeText(activity, "Under Construction", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton(R.string.text_no) { _, _ ->

        }
        builder.show()
    }
}