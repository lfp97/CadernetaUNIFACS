package com.example.lucas.testebd

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<< HEAD:app/src/main/java/com/example/lucas/testebd/Main2Activity.kt
        //setContentView(R.layout.activity_main2)
=======
        setContentView(R.layout.tela_menu)
>>>>>>> d69b1342e3fe6ffa04fb0370d48df433586290d1:app/src/main/java/com/example/lucas/testebd/Menu.kt
    }

    fun onClickLogar (view: View)
    {
        val intentLogar= Intent(this, Caderneta::class.java)
        startActivity(intentLogar)
    }
    fun onClickCRUDS (view: View)
    {
        val inte= Intent(this, SelecaoCRUDS::class.java)
        startActivity(inte)
    }
}
