package com.sridhar.findfuelstations.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sridhar.findfuelstations.R
import com.sridhar.findfuelstations.viewmodel.SplashViewModel


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initView()
    }

    private fun initView() {
        val model = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        model._isActivityLaunched.observe(this, Observer {
            if (it) finish()
        })
    }
}
