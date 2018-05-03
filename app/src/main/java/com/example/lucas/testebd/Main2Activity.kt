package com.example.lucas.testebd

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }
    fun onClick (view: View)
    {
        val inte= Intent(this, MainActivity::class.java)
        startActivity(inte)
    }
}
