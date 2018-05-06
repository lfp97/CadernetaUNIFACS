package com.example.lucas.testebd

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_login)
    }

    fun onClickLogin (view: View)
    {
        var ETUsu= findViewById<View>(R.id.editTextUsu) as EditText
        val mat= ETUsu.text.toString()
        var ETSenha= findViewById<View>(R.id.editTextSenha) as EditText
        val senha= ETSenha.text.toString()
        var auxDB= DBHelperProfessor(this)

        var arrayProf= auxDB.readProfessor(mat)
        if (arrayProf.isEmpty())
        {
            Toast.makeText(this, "Usuário não existe!", Toast.LENGTH_LONG)
            ETUsu.setText("")
            ETSenha.setText("")
        }
        else
        {
            var professor= arrayProf.get(0)
            if (professor.matricula.equals(mat) && professor.senha.equals(senha))
            {
                val intentLogar= Intent(this, Caderneta::class.java)
                startActivity(intentLogar)
            }
            else
            {
                Toast.makeText(this, "Usuário ou senha errados!", Toast.LENGTH_LONG)
                ETUsu.setText("")
                ETSenha.setText("")
            }
        }
    }
}
