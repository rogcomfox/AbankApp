package com.nusantarian.abank_gov.fragment

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.nusantarian.abank_gov.R
import com.nusantarian.abank_gov.databinding.FragmentForgotPassBinding

class ForgotPassFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentForgotPassBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForgotPassBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        binding.btnReset.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_reset) {
            binding.progress.visibility = View.VISIBLE
            val email = binding.tilEmail.editText?.text.toString()
            if (!isEmailValid(email)){
                binding.progress.visibility = View.GONE
            } else {
                auth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if (it.isSuccessful){
                        Log.i("Forgot Pass", "Success")
                        Toast.makeText(activity, R.string.text_success_reset, Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("Forgot Pass", "Failed")
                        Toast.makeText(activity, R.string.text_failed_reset, Toast.LENGTH_SHORT).show()
                    }
                    binding.progress.visibility = View.GONE
                }.addOnFailureListener {
                    binding.progress.visibility = View.GONE
                    Log.e("Forgot Pass", it.toString())
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        val empty = activity?.resources?.getString(R.string.text_empty)
        val invalid = activity?.resources?.getString(R.string.text_invalid_email)
        return if(email.isEmpty()){
            binding.tilEmail.error = empty
            false
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.tilEmail.error = invalid
            false
        } else {
            binding.tilEmail.error = null
            binding.tilEmail.isErrorEnabled
            true
        }
    }
}