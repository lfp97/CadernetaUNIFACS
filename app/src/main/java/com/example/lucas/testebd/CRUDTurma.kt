package com.example.lucas.testebd


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.disciplina_crud.*
import kotlinx.android.synthetic.main.turma_crud.*

class CRUDTurma : AppCompatActivity() {

    lateinit var DBHelperTurma : DBHelperTurma

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.disciplina_crud)

        DBHelperTurma = DBHelperTurma(this)
    }

    fun addTurma(v: View)
    {
        var idTurma = this.edittext_idTurma.text.toString()
        var horarioinicio = this.edittext_horarioinicio.text.toString()
        var horariofim = this.edittext_horariofim.text.toString()
        var idDisc = this.edittext_idDisciplina.text.toString()
        var idProf = this.edittext_idProfessor.text.toString()
        var idSala = this.edittext_idSala.text.toString()

        var result = DBHelperTurma.insertTurma(Turma(idTurma, horarioinicio, horariofim, idDisc, idProf, idSala))
        //clear all edittext s
        this.edittext_nameDisc.setText("")
        this.edittext_idDisciplina.setText("")
        this.textview_resultDisc.text= "Added Disc : "+result
        this.ll_entriesDisc.removeAllViews()
    }

    fun deleteTurma(v: View)
    {
        var idTurma = this.edittext_idTurma.text.toString()
        val result = DBHelperTurma.deleteTurma(idTurma)
        this.textview_resultTurma.text = "Deleted Turma : "+result
        this.ll_entriesTurma.removeAllViews()
    }

    fun showAllTurma(v: View)
    {
        var turmas = DBHelperTurma.readAllTurmas()
        this.ll_entriesTurma.removeAllViews()
        turmas.forEach {
            var tv_turma = TextView(this)
            tv_turma.textSize = 30F
            tv_turma.text = it.horarioinicio.toString() + " - " + it.horariofim.toString()
            this.ll_entriesDisc.addView(tv_turma)
        }
        this.textview_resultTurma.text = "Fetched " + turmas.size + " disciplinas"
    }
}