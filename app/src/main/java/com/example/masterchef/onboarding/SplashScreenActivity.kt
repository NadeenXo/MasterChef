package com.example.masterchef.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.masterchef.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
//        setContentView(R.layout.activity_splash_screen) // causes error

        startActivity(Intent(this@SplashScreenActivity, OnBoardingActivity::class.java))
        finish()
//        val animationView = findViewById<LottieAnimationView>(R.id.lottieAnimationView)
//
//        animationView.addAnimatorListener(object : Animator.AnimatorListener {
//            override fun onAnimationStart(animation: Animator) {
//                animationView.setAnimation(R.raw.food)
//                animationView.playAnimation()
//            }
//
//            override fun onAnimationEnd(animation: Animator) {
//                startActivity(Intent(this@SplashScreenActivity, OnBoardingActivity::class.java))
//                finish()
//            }
//
//            override fun onAnimationCancel(animation: Animator) {}
//
//            override fun onAnimationRepeat(animation: Animator) {}
//        })
    }
}
