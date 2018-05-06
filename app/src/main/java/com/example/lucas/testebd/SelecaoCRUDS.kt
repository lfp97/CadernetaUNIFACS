package com.example.lucas.testebd

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SelecaoCRUDS : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_selecao_cruds)
    }

    fun onClickProfessor(view: View)
    {
        val intentProf= Intent(this, CRUDProfessor::class.java)
        startActivity(intentProf)
    }
    fun onClickAluno (view: View)
    {
        val intentAluno= Intent(this, CRUDAluno::class.java)
        startActivity(intentAluno)
    }
    fun onClickDisciplina (view: View)
    {
        val intentDisc= Intent(this, CRUDDisciplina::class.java)
        startActivity(intentDisc)
    }
}
