package com.example.masterchef.auth

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.masterchef.R
import com.example.masterchef.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private var isAllFieldsChecked = false
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        binding.btnSignup.setOnClickListener {
            isAllFieldsChecked = checkAllFields()
            if (isAllFieldsChecked) {
                // if all fields is true signup
                signUp()
            }
        }
        binding.btnCancelSignup.setOnClickListener {
            requireActivity().finish()
        }
        return binding.root
    }

    private fun signUp() {
        auth.createUserWithEmailAndPassword(
            binding.etEmailSignup.text.toString(),
            binding.etPasswordSignup.text.toString()
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
                    "Authentication failed",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    // Function which checks all the text fields
    // When the user clicks on the SIGNUP button this function is triggered.
    private fun checkAllFields(): Boolean {
        if ( binding.etEmailSignup.length() == 0) {
             binding.etEmailSignup.error = "Email is required"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher( binding.etEmailSignup.text.toString()).matches()) {
             binding.etEmailSignup.error = "Enter a valid email"
            return false
        }
        if (binding.etPasswordSignup.length() == 0) {
            binding.etPasswordSignup.error = "Password is required"
            return false
        } else if (binding.etPasswordSignup.length() < 8) {
            binding.etPasswordSignup.error = "Password must be a minimum of 8 characters"
            return false
        }
        if (binding.etConfirmPasswordSignup.length() == 0) {
            binding.etConfirmPasswordSignup.error = "Confirm Password is required"
            return false
        } else if (binding.etPasswordSignup.text.toString() != binding.etConfirmPasswordSignup.text.toString()) {
            binding.etConfirmPasswordSignup.error = "Passwords do not match"
            return false
        }
        return true
    }
}
