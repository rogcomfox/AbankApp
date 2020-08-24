package com.nusantarian.abank_user.fragment

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nusantarian.abank_user.R
import com.nusantarian.abank_user.databinding.FragmentRegisterBinding
import com.nusantarian.abank_user.model.User

class RegisterFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var ft: FragmentTransaction
    private lateinit var user: CollectionReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.btnSignup.setOnClickListener(this)
        auth = FirebaseAuth.getInstance()
        user = FirebaseFirestore.getInstance().collection("users")
        ft = activity!!.supportFragmentManager.beginTransaction()
        return binding.root
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_signup) {
            binding.progress.visibility = View.VISIBLE
            val email = binding.tilEmail.editText?.text.toString()
            val name = binding.tilName.editText?.text.toString()
            val phone = binding.tilPhone.editText?.text.toString()
            val number = binding.tilId.editText?.text.toString()
            val pass = binding.tilPass.editText?.text.toString()
            val confirm = binding.tilConfirmPass.editText?.text.toString()

            if (!isValid(email, name, phone, number, pass, confirm)){
                binding.progress.visibility = View.GONE
            } else {
                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val uid = auth.currentUser?.uid!!
                        saveUserData(uid, email, name, phone, number)
                        Log.i("TAG", "Success Register")
                    } else {
                        Toast.makeText(activity, R.string.text_failed_register, Toast.LENGTH_LONG)
                            .show()
                        Log.e("TAG", "Failed Register")
                    }
                    binding.progress.visibility = View.GONE
                }.addOnFailureListener {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    Log.e("TAG", it.toString())
                }
            }
        }
    }

    private fun saveUserData(uid : String, email: String, name: String, phone: String, number: String) {
        val data = User(number, email, name, phone)
        user.document(uid).set(data).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(activity, R.string.text_success_register, Toast.LENGTH_SHORT).show()
                ft.replace(R.id.frame_auth, LoginFragment())
                    .commit()
            } else {
                auth.currentUser!!.delete()
                Toast.makeText(activity, R.string.text_failed_register, Toast.LENGTH_LONG)
                    .show()
                Log.e("TAG", "Failed Push Data")
                activity!!.onBackPressed()
            }
        }.addOnFailureListener {
            Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
            Log.e("TAG", it.toString())
        }
    }

    private fun isValid(
        email: String,
        name: String,
        phone: String,
        number: String,
        pass: String,
        confirm: String
    ): Boolean {
        val empty = activity!!.resources.getString(R.string.text_invalid_email)
        val invalid = activity!!.resources.getString(R.string.text_invalid_email)
        val below = activity!!.resources.getString(R.string.text_pass_below)
        val notSame = activity!!.resources.getString(R.string.text_pass_not_match)

        return if (email.isEmpty()){
            binding.tilEmail.error = empty
            false
        }
        else if (name.isEmpty()){
            binding.tilName.error = empty
            false
        }
        else if (phone.isEmpty()){
            binding.tilPhone.error = empty
            false
        }
        else if (number.isEmpty()){
            binding.tilId.error = empty
            false
        }
        else if (pass.isEmpty()){
            binding.tilPass.error = empty
            false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.tilEmail.error = invalid
            false
        }
        else if (pass != confirm){
            binding.tilPass.error = notSame
            binding.tilConfirmPass.error = notSame
            false
        }
        else if (pass.length < 8){
            binding.tilPass.error = below
            false
        } else {
            binding.tilId.error = null
            binding.tilName.error = null
            binding.tilPhone.error = null
            binding.tilEmail.error = null
            binding.tilPass.error = null
            binding.tilConfirmPass.error = null

            binding.tilId.isErrorEnabled
            binding.tilName.isErrorEnabled
            binding.tilPhone.isErrorEnabled
            binding.tilEmail.isErrorEnabled
            binding.tilPass.isErrorEnabled
            binding.tilConfirmPass.isErrorEnabled
            true
        }
    }

}