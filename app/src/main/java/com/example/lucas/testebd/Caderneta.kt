package com.example.lucas.testebd

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class Caderneta : AppCompatActivity()
{
    private lateinit var listView: ListView
    private lateinit var dataBaseHelperChamadaAlunos: DBHelperAluno
    private lateinit var dataBaseHelperChamadaDadosProf: DBHelperProfessor

    companion object
    {
        const val nomeProfessor= "Nome"
        const val nomeDisc= "nomeDisc"
        const val idTurma= "idDaTurma"
        const val numeroSala= "numSala"
        const val hora= "Hora"

    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_caderneta)
        listView= findViewById<View>(R.id.listView) as ListView
        dataBaseHelperChamadaAlunos= DBHelperAluno(this)
        popularListView()
    }

    private fun popularListView()
    {
        var listaAlunos = dataBaseHelperChamadaAlunos.readAllAlunos()
        var adapter= AlunoAdapter(this, listaAlunos)
        listView.adapter = adapter

        //mostrarDados()
    }

    private fun mostrarDados()
    {
        var telaDados= findViewById<View>(R.id.textViewDados) as TextView
        dataBaseHelperChamadaDadosProf= DBHelperProfessor(this)

        var listaResults= dataBaseHelperChamadaDadosProf.readProfessor("1")
        if (listaResults.isEmpty())
            telaDados.setText("Banco Vazio")
        else
        {
            var aux= listaResults.get(0)
            telaDados.setText("Professor: " + aux.nome)
        }

    }

    fun onClickSalvar (view: View) //implementar
    {
        //var listaAlunosFaltantes= listView.
        var hora= intent.getStringExtra(hora)
        var nomeProf= intent.getStringExtra(nomeProfessor)
        var telaDados= findViewById<View>(R.id.textViewDados) as TextView
        var nomeDisc= intent.getStringExtra(nomeDisc)
        var idTurma= intent.getStringExtra(idTurma)
        var numSala= intent.getStringExtra(numeroSala)
        telaDados.setText("Hora: " + hora + ", Professor: " + nomeProf + "\nNome disciplina: " + nomeDisc + ", idTurma: " +
        idTurma + ", Número da Sala: " + numSala)
        Toast.makeText(this, "Salvo", Toast.LENGTH_SHORT)
        //mostrarDados()
    }
}
