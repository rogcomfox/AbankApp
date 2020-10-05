package com.nusantarian.abank_gov.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.nusantarian.abank_gov.R
import com.nusantarian.abank_gov.activity.MainActivity
import com.nusantarian.abank_gov.databinding.FragmentLoginBinding

class LoginFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var ft: FragmentTransaction
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.btnLogin.setOnClickListener(this)
        binding.tvForgot.setOnClickListener(this)
        ft = activity?.supportFragmentManager?.beginTransaction()!!
        auth = FirebaseAuth.getInstance()
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
        } else {
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                if (it.isSuccessful){
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity?.finish()
                    Toast.makeText(activity, R.string.text_success_login, Toast.LENGTH_SHORT).show()
                    Log.i("Login", "Success")
                } else {
                    Toast.makeText(activity, R.string.text_failed_login, Toast.LENGTH_SHORT).show()
                    Log.e("Login", "Failed")
                }
                binding.progress.visibility = View.GONE
            }.addOnFailureListener {
                binding.progress.visibility = View.GONE
                Log.e("Login", it.toString())
                Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidLogin(email: String, pass: String): Boolean {
        val empty = activity?.resources?.getString(R.string.text_empty)
        val invalid = activity?.resources?.getString(R.string.text_invalid_email)
        return if(email.isEmpty()){
            binding.tilEmail.error = empty
            false
        }
        else if (pass.isEmpty()){
            binding.tilPass.error = empty
            false
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.tilEmail.error = invalid
            false
        } else {
            binding.tilEmail.error = null
            binding.tilPass.error = null

            binding.tilEmail.isErrorEnabled
            binding.tilPass.isErrorEnabled
            true
        }
    }
}