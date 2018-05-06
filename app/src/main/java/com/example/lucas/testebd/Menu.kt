package com.example.lucas.testebd

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Menu : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_menu)
    }

    fun onClickLogar (view: View)
    {
        val intentLogar= Intent(this, Login::class.java)
        startActivity(intentLogar)
    }
    fun onClickCRUDS (view: View)
    {
        val inte= Intent(this, SelecaoCRUDS::class.java)
        startActivity(inte)
    }
}
