package com.example.lucas.testebd

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main2)
    }
    fun onClickAlunos (view: View)
    {
        val inte= Intent(this, MainActivity::class.java)
        startActivity(inte)
    }
    fun onClickProfessores (view: View)
    {
        val inte2= Intent(this, Main3Activity::class.java)
        startActivity(inte2)
    }
}
