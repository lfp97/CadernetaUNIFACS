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
    private lateinit var dataBaseHelperChamadaTurma: DBHelperTurma
    private lateinit var dataBaseHelperChamadaAT: DBHelperAlunos_Turmas

    companion object
    {
        const val nomeProfessor= "Nome"
        const val idDisc= "idDisc"
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
        dataBaseHelperChamadaAT= DBHelperAlunos_Turmas(this)

        var hora= intent.getStringExtra(hora)
        var nomeProf= intent.getStringExtra(nomeProfessor)
        var telaDados= findViewById<View>(R.id.textViewDados) as TextView
        var idDisc= intent.getStringExtra(idDisc)
        var idTurma= intent.getStringExtra(idTurma)
        var numSala= intent.getStringExtra(numeroSala)
        telaDados.setText("Hora: " + hora + ", Professor: " + nomeProf + "\nidDisc: " + idDisc + ", idTurma: " +
                idTurma + ", Número da Sala: " + numSala)
        popularListView()
    }

    /*fun getListaAlunos(): ArrayList<Aluno>
    {
        var listaAT = dataBaseHelperChamadaAT.readAll()
        return
    }*/

    private fun popularListView()
    {
        var listaAT = dataBaseHelperChamadaAT.readAll()
        //var alunoDummy= Aluno("99", "dummy", "99999")
        val listaAlunos= ArrayList<Aluno>()
        //listaAlunos.add(alunoDummy)
        var flag= false
        for (i in 0..listaAT.size-1)
        {
            var at= listaAT.get(i)
            var idAlunoAT= at.idAluno
            //var aluno= dataBaseHelperChamadaAlunos.readAluno(listaAT.get(i).idAluno)
            var aluno= dataBaseHelperChamadaAlunos.readAluno(idAlunoAT)
            listaAlunos.add(aluno)
            flag= true
        }
        if (flag)
        {
            var adapter= AlunoAdapter(this, listaAlunos)
            listView.adapter = adapter
        }
    }

    fun onClickSalvar (view: View) //implementar
    {
        var listaAT = dataBaseHelperChamadaAT.readAll()
        //var alunoDummy= Aluno("99", "dummy", "99999")
        val listaAlunos= ArrayList<Aluno>()
        //listaAlunos.add(alunoDummy)
        var flag= false
        var cont: Int
        cont=1
        while (cont<=listaAT.size)
        {
            var aluno= dataBaseHelperChamadaAlunos.readAluno(listaAT.get(cont).toString())
            listaAlunos.add(aluno)
            cont++
            flag= true
        }
        if (flag)
        {
            var adapter= AlunoAdapter(this, listaAlunos)
            listView.adapter = adapter
        }
    }
}
