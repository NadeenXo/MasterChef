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
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.edit
import com.example.masterchef.R
import com.example.masterchef.dashboard.Logout
import com.example.masterchef.dashboard.MainActivity
import com.example.masterchef.onboarding.OnBoardingActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginFragment : Fragment(), Logout {
    private lateinit var bCancel: Button
    private lateinit var bLogin: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var sharedPref: SharedPreferences

    //check whether all the text fields are filled or not.
    private var isAllFieldsChecked = false
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        auth = Firebase.auth

        bLogin = view.findViewById(R.id.btn_login)
        bCancel = view.findViewById(R.id.btn_cancel_login)
        etEmail = view.findViewById(R.id.et_email_login)
        etPassword = view.findViewById(R.id.et_password_login)

        auth = FirebaseAuth.getInstance()

        sharedPref =
            activity?.getSharedPreferences(LoginManager.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)!!

        bLogin.setOnClickListener {
            isAllFieldsChecked = checkAllFields()
            if (isAllFieldsChecked) {
                // if all fields is true login
                login()
            }
        }
        bCancel.setOnClickListener {
            requireActivity().finish()
        }

        return view
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
        auth.signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //val user = auth.currentUser
                    // loggedin
                    sharedPref.edit {
                        putBoolean(LoginManager.PREFERENCE_IS_LOGGED, true)
                        putString(LoginManager.PREFERENCE_EMAIL, etEmail.text.trim().toString())

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
        if (etEmail.length() == 0) {
            etEmail.error = "Email is required"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
            etEmail.error = "Enter a valid email"
            return false
        }
        if (etPassword.length() == 0) {
            etPassword.error = "Password is required"
            return false
        } else if (etPassword.length() < 8) {
            etPassword.error = "Password must be a minimum of 8 characters"
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
