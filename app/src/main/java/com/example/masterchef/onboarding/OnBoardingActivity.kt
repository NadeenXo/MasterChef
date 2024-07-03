package com.example.masterchef.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.WindowCompat
import com.example.masterchef.R
import com.example.masterchef.auth.AuthActivity
import com.example.masterchef.auth.LoginManager
import com.example.masterchef.dashboard.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.auth.api.signin.GoogleSignInClient


class OnBoardingActivity : AppCompatActivity() {
    companion object Constants {
        const val DESTINATION_FRAGMENT = "initialFragment"
        const val RC_SIGN_IN = 9001
    }

    lateinit var login: TextView
    lateinit var skip: TextView
    lateinit var signup: Button
    lateinit var signupGoogle: Button
    lateinit var sharedPref: SharedPreferences
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        login = findViewById(R.id.tv_login)
        skip = findViewById(R.id.tv_skip)
        signup = findViewById(R.id.btn_signup_onboarding)
        signupGoogle = findViewById(R.id.btn_google)

        //to make the edge to edge screen
        WindowCompat.setDecorFitsSystemWindows(window, false)
        sharedPref =
            this.getSharedPreferences(LoginManager.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)

        login.setOnClickListener {
            startLoginFragment()
        }

        signup.setOnClickListener {
            startSignupFragment()
        }
        skip.setOnClickListener {
            sharedPref.edit {
                putBoolean(LoginManager.PREFERENCE_IS_LOGGED, false)
            }
        }
//        signupGoogle.setOnClickListener {
//            signInWithGoogle()
//        }
        // Configure Google Sign-In
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun startSignupFragment() {
        val intent = Intent(this, AuthActivity::class.java)
        intent.putExtra(DESTINATION_FRAGMENT, R.id.signupFragment)
        startActivity(intent)
    }

//    private fun signInWithGoogle() {
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            handleSignInResult(task)
//        }
//    }

//    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val account = completedTask.getResult(ApiException::class.java)!!
//            Log.d("TAG", "firebaseAuthWithGoogle:" + account.id)
//            firebaseAuthWithGoogle(account.idToken!!)
//        } catch (e: ApiException) {
//            Log.w("TAG", "Google sign in failed", e)
//        }
//    }

//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    Log.d("TAG", "signInWithCredential:success")
//                    val user = auth.currentUser
//                    sharedPref.edit {
//                        putBoolean(LoginManager.PREFERENCE_IS_LOGGED, true)
//                        putString(LoginManager.PREFERENCE_EMAIL, user?.email)
//                    }
//                    startActivity(Intent(this, MainActivity::class.java))
//                    finish()
//                } else {
//                    Log.w("TAG", "signInWithCredential:failure", task.exception)
//                }
//            }
//    }

    private fun startLoginFragment() {
        val intent = Intent(this, AuthActivity::class.java)
        intent.putExtra(DESTINATION_FRAGMENT, R.id.loginFragment)
        startActivity(intent)
    }

    //todo: check this
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}