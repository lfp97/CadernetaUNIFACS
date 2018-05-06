package com.example.lucas.testebd

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.professor_crud.*

class CRUDProfessor : AppCompatActivity() {

    lateinit var DBHelperProfessor : DBHelperProfessor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.professor_crud)

        DBHelperProfessor = DBHelperProfessor(this)
    }

    fun addProf(v: View)
    {
        var idProfessor = this.edittext_idProfessor.text.toString()
        var name = this.edittext_nameProf.text.toString()
        var matricula = this.edittext_MatriculaProf.text.toString()
        var senha= this.edittext_SenhaProf.text.toString()
        var result = DBHelperProfessor.insertProfessor(Professor(idProfessor, name, matricula, senha))
        //clear all edittext s
        this.edittext_MatriculaProf.setText("")
        this.edittext_nameProf.setText("")
        this.edittext_idProfessor.setText("")
        this.textview_resultProf.text = "Added user : "+result
        this.ll_entriesProf.removeAllViews()
    }

    fun deleteProf(v: View)
    {
        var idProfessor = this.edittext_idProfessor.text.toString()
        val result = DBHelperProfessor.deleteProfessor(idProfessor)
        this.textview_resultProf.text = "Deleted user : "+result
        this.ll_entriesProf.removeAllViews()
    }

    fun showAllProf(v: View)
    {
        var professores = DBHelperProfessor.readAllProfessores()
        this.ll_entriesProf.removeAllViews()
        professores.forEach {
            var tv_user = TextView(this)
            tv_user.textSize = 30F
            tv_user.text = it.nome.toString() + " - " + it.matricula.toString()
            this.ll_entriesProf.addView(tv_user)
        }
        this.textview_resultProf.text = "Fetched " + professores.size + " users"
    }
}