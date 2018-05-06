package com.example.lucas.testebd

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class Caderneta : AppCompatActivity()
{
    private lateinit var listView: ListView
    private lateinit var dataBaseHelperChamadaAlunos: DBHelperAluno
    //private lateinit var dataBaseHelperChamadaProfessores: DBHelperProfessor um pra professor, e turma
    private lateinit var btnSalvar: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.telaCaderneta)
        listView= findViewById<View>(R.id.listView) as ListView
        dataBaseHelperChamadaAlunos= DBHelperAluno(this)
        popularListView()
    }

    private fun popularListView()
    {
        var listaAlunos = dataBaseHelperChamadaAlunos.readAllAlunos()
        var adapter= AlunoAdapter(this, listaAlunos)
        listView.adapter = adapter
    }

    fun onClickSalvar (view: View) //implementar
    {
        //var listaAlunosFaltantes= listView.
        Toast.makeText(this, "Salvo", Toast.LENGTH_SHORT)
    }
}
