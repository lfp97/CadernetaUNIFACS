package com.example.lucas.testebd


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.turma_crud.*

class CRUDTurma : AppCompatActivity() {

    lateinit var DBHelperTurma : DBHelperTurma

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.turma_crud)

        DBHelperTurma = DBHelperTurma(this)
    }

    fun addTurma(v: View)
    {
        var idTurma = this.edittext_TurmaidTurma.text.toString()
        var horarioinicio = this.edittext_horarioinicio.text.toString()
        var horariofim = this.edittext_horariofim.text.toString()
        var idDisc = this.edittext_TurmaidDisciplina.text.toString()
        var idProf = this.edittext_TurmaIdProf.text.toString()
        var idSala = this.edittext_TurmaidSala.text.toString()

        var result = DBHelperTurma.insertTurma(Turma(idTurma, horarioinicio, horariofim, idDisc, idProf, idSala))
        //clear all edittext s
        this.edittext_TurmaidDisciplina.setText("")

        this.textview_resultTurma.text= "Added Disc : "+result
        this.ll_entriesTurma.removeAllViews()
    }

    fun deleteTurma(v: View)
    {
        var idTurma = this.edittext_TurmaidTurma.text.toString()
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
            tv_turma.text = it.id + " - " + it.professor + " - " + it.disciplina + " - " + it.horarioinicio.toString() + " - " + it.horariofim.toString()
            this.ll_entriesTurma.addView(tv_turma)
        }
        this.textview_resultTurma.text = "Fetched " + turmas.size + " disciplinas"
    }
}