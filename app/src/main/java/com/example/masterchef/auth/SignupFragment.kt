package com.example.masterchef.auth

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.masterchef.R
import com.google.firebase.auth.FirebaseAuth

class SignupFragment : Fragment() {
    private lateinit var bCancel: Button
    private lateinit var bSignup: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText

    //check whether all the text fields are filled or not.
    private var isAllFieldsChecked = false
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        bSignup = view.findViewById(R.id.btn_signup)
        bCancel = view.findViewById(R.id.btn_cancel_signup)
        etEmail = view.findViewById(R.id.et_email_signup)
        etPassword = view.findViewById(R.id.et_password_signup)
        etConfirmPassword = view.findViewById(R.id.et_confirm_password_signup)

        auth = FirebaseAuth.getInstance()

        bSignup.setOnClickListener {
            isAllFieldsChecked = checkAllFields()
            if (isAllFieldsChecked) {
                // if all fields is true signup
                signUp()
            }
        }
        bCancel.setOnClickListener {
            requireActivity().finish()
        }
        return view
    }

    private fun signUp() {
        auth.createUserWithEmailAndPassword(
            etEmail.text.toString(),
            etPassword.text.toString()
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information

                Toast.makeText(
                    context,
                    "Signup Successfully",
                    Toast.LENGTH_SHORT,
                ).show()

                // Sign out the user to require login
                auth.signOut()
                //go to login
                findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(
                    context,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    // Function which checks all the text fields
    // When the user clicks on the SIGNUP button this function is triggered.
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
        if (etConfirmPassword.length() == 0) {
            etConfirmPassword.error = "Confirm Password is required"
            return false
        } else if (etPassword.text.toString() != etConfirmPassword.text.toString()) {
            etConfirmPassword.error = "Passwords do not match"
            return false
        }
        return true
    }
}
