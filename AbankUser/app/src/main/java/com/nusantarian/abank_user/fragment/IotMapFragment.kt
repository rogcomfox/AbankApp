package com.nusantarian.abank_user.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nusantarian.abank_user.R
import com.nusantarian.abank_user.databinding.FragmentIotMapBinding

class IotMapFragment : Fragment() {

    private var _binding: FragmentIotMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIotMapBinding.inflate(inflater, container, false)
        return binding.root
    }
}