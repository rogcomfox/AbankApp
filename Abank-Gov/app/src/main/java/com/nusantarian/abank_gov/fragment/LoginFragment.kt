package com.nusantarian.abank_gov.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.nusantarian.abank_gov.R
import com.nusantarian.abank_gov.databinding.FragmentLoginBinding

class LoginFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var ft: FragmentTransaction

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.btnLogin.setOnClickListener(this)
        binding.tvForgot.setOnClickListener(this)
        ft = activity?.supportFragmentManager?.beginTransaction()!!
        return binding.root
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.tv_forgot ->
                ft.replace(R.id.frame_auth, ForgotPassFragment())
                    .addToBackStack(null)
                    .commit()
            R.id.btn_login ->
                loginUser()
        }
    }

    private fun loginUser(){
        binding.progress.visibility = View.VISIBLE
        val email = binding.tilEmail.editText?.text.toString()
        val pass = binding.tilPass.editText?.text.toString()

        if (!isValidLogin(email, pass)){
            binding.progress.visibility = View.GONE
        }
    }

    private fun isValidLogin(email: String, pass: String): Boolean {
        return true
    }
}