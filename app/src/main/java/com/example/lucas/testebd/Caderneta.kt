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
    private lateinit var dataBaseHelperChamdaFalta: DBHelperFalta

    companion object
    {
        const val nomeProfessor= "Nome"
        const val idDisc= "idDisc"
        const val comIdTurma= "idDaTurma"
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
        var comPIdTurma= intent.getStringExtra(comIdTurma)
        var numSala= intent.getStringExtra(numeroSala)
        telaDados.setText("Hora: " + hora + ", Professor: " + nomeProf + "\nidDisc: " + idDisc + ", idTurma: " +
                comPIdTurma + ", Número da Sala: " + numSala)
        popularListView(comPIdTurma)
    }

    private fun popularListView(idTurmaParametro: String)
    {
        var telaDados= findViewById<View>(R.id.textViewDados) as TextView

        var alunoDummy= Alunos_Turmas("999", "888")
        var listaDummy= ArrayList<Alunos_Turmas>()
        var listaAT= dataBaseHelperChamadaAT.readAll()
        //telaDados.setText("ID Aluno: " + listaAT.get(1).idAluno + ", ID Turma: " + listaAT.get(1).idTurma)
        var listaAlunos= ArrayList<Aluno>()
        var it= listaAT.iterator()
        var flag= false

        while (it.hasNext())
        {
            var aux= it.next()
            //telaDados.setText("ID Aluno: " + aux.idAluno + ", ID Turma: " + aux.idTurma)
            var idAlu= aux.idAluno
            var idTur= aux.idTurma
            if (idTur.equals(idTurmaParametro))
            {
                listaAlunos.add(dataBaseHelperChamadaAlunos.readAluno(idAlu))
                flag= true
            }
        }
        //listaAlunos.add(Aluno("999", "dummy", "9875"))
        //telaDados.setText("ID Aluno: " + listaAlunos.get(0).id + ", Nome Aluno: " + listaAlunos.get(0).nome + ", Mat Aluno: " + listaAlunos.get(0).matricula)

        if (flag)
        {
            var adapter= AlunoAdapter(this, listaAlunos)
            listView.adapter = adapter
        }
        else
        {
            listaDummy.add(alunoDummy)
            var adapter= Alunos_TurmasAdapter(this, listaDummy)
            listView.adapter = adapter
        }
    }

    fun onClickSalvar (view: View)
    {

    }
}
