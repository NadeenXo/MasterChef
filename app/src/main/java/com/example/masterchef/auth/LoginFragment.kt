package com.example.masterchef.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import com.example.masterchef.dashboard.Logout
import com.example.masterchef.dashboard.MainActivity
import com.example.masterchef.databinding.FragmentLoginBinding
import com.example.masterchef.onboarding.OnBoardingActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginFragment : Fragment(), Logout {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding: FragmentLoginBinding
    private var isAllFieldsChecked = false
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        auth = Firebase.auth
        auth = FirebaseAuth.getInstance()

        sharedPref =
            activity?.getSharedPreferences(LoginManager.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)!!

        binding.btnLogin.setOnClickListener {
            isAllFieldsChecked = checkAllFields()
            if (isAllFieldsChecked) {
                // if all fields is true login
                login()
            }
        }
        binding.btnCancelLogin.setOnClickListener {
            requireActivity().finish()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(activity, MainActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun login() {
        auth.signInWithEmailAndPassword(binding.etEmailLogin.text.toString(), binding.etPasswordLogin.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //val user = auth.currentUser
                    // loggedin
                    sharedPref.edit {
                        putBoolean(LoginManager.PREFERENCE_IS_LOGGED, true)
                        putString(LoginManager.PREFERENCE_EMAIL, binding.etEmailLogin.text.trim().toString())

                    }
                    startActivity(Intent(activity, MainActivity::class.java))
                    requireActivity().finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        context,
                        "Authentication failed",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    // Function which checks all the text fields when the user clicks on the login button this function is triggered.
    private fun checkAllFields(): Boolean {
        if (binding.etEmailLogin.length() == 0) {
            binding.etEmailLogin.error = "Email is required"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmailLogin.text.toString()).matches()) {
            binding.etEmailLogin.error = "Enter a valid email"
            return false
        }
        if (binding.etPasswordLogin.length() == 0) {
            binding.etPasswordLogin.error = "Password is required"
            return false
        } else if (binding.etPasswordLogin.length() < 8) {
            binding.etPasswordLogin.error = "Password must be a minimum of 8 characters"
            return false
        }
        return true
    }

    override fun onClick() {
        auth.signOut()
        startActivity(Intent(activity, OnBoardingActivity::class.java))
        requireActivity().finish()
    }
}
