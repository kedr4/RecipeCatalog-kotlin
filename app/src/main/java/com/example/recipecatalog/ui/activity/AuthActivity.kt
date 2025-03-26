package com.example.recipecatalog.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recipecatalog.R

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

    }
}

