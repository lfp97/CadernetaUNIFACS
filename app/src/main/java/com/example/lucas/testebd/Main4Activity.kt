package com.example.lucas.testebd

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import java.util.ArrayList

class Main4Activity : AppCompatActivity()
{
    private lateinit var listView: ListView
    private lateinit var dataBaseHelperChamadaAlunos: DBHelperAluno
    //private lateinit var dataBaseHelperChamadaProfessores: DBHelperProfessor um pra professor, e turma

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        listView= findViewById<View>(R.id.listView) as ListView
        dataBaseHelperChamadaAlunos= DBHelperAluno(this)
        popularListView()
    }

    private fun popularListView()
    {
        var listaAlunos= dataBaseHelperChamadaAlunos.readAllAlunos()
        var adapter= ArrayAdapter(this, android.R.layout.simple_list_item_1, listaAlunos)
        listView.adapter= adapter
    }
}
