package com.nusantarian.abank_user.fragment

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.nusantarian.abank_user.R
import com.nusantarian.abank_user.databinding.FragmentForgotPassBinding

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
        binding.btnReset.setOnClickListener(this)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_forgot){
            binding.progress.visibility = View.VISIBLE
            val email = binding.tilEmail.editText?.text.toString()

            if (!isEmailValid(email)){
                binding.progress.visibility = View.GONE
            } else {
                auth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(activity, R.string.text_success_reset, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(activity, R.string.text_failed_reset, Toast.LENGTH_LONG).show()
                        Log.e("ForgotPass", "Failed reset password")
                    }
                    binding.progress.visibility = View.GONE
                }.addOnFailureListener {
                    Log.e("ForgotPass", it.toString())
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    binding.progress.visibility = View.GONE
                }
            }
        }
    }

    private fun isEmailValid(email: String) : Boolean{
        val empty = activity!!.resources.getString(R.string.text_empty_field)
        val invalid = activity!!.resources.getString(R.string.text_invalid_email)

        return if (email.isEmpty()){
            binding.tilEmail.error = empty
            false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.tilEmail.error = invalid
            false
        } else {
            binding.tilEmail.error = null
            binding.tilEmail.isErrorEnabled
            true
        }
    }
}