package com.example.lucas.testebd


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.disciplina_crud.*

class CRUDDisciplina : AppCompatActivity() {

    lateinit var DBHelperDisciplina : DBHelperDisciplina

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.disciplina_crud)

        DBHelperDisciplina = DBHelperDisciplina(this)
    }

    fun addDisc(v: View)
    {
        var idDisciplina = this.edittext_idDisc.text.toString()
        var name = this.edittext_nameDisc.text.toString()
        var result = DBHelperDisciplina.insertDisciplina(Disciplina(idDisciplina, name))
        //clear all edittext s
        this.edittext_nameDisc.setText("")
        this.edittext_idDisc.setText("")
        this.textview_resultDisc.text= "Added Disc : "+result
        this.ll_entriesDisc.removeAllViews()
    }

    fun deleteDisc(v: View)
    {
        var idDisc = this.edittext_idDisc.text.toString()
        val result = DBHelperDisciplina.deletePDisciplina(idDisc)
        this.textview_resultDisc.text = "Deleted user : "+result
        this.ll_entriesDisc.removeAllViews()
    }

    fun showAllDisc(v: View)
    {
        var disciplinas = DBHelperDisciplina.readAllDisciplinas()
        this.ll_entriesDisc.removeAllViews()
        disciplinas.forEach {
            var tv_user = TextView(this)
            tv_user.textSize = 30F
            tv_user.text = it.nome.toString() + " - " + it.nome.toString()
            this.ll_entriesDisc.addView(tv_user)
        }
        this.textview_resultDisc.text = "Fetched " + disciplinas.size + " disciplinas"
    }
}