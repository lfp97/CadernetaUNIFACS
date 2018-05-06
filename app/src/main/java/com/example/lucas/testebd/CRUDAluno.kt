package com.example.lucas.testebd

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.alunoCRUD.*

class CRUDAluno : AppCompatActivity() {

    lateinit var DBHelperAluno : DBHelperAluno

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alunoCRUD)

        DBHelperAluno = DBHelperAluno(this)
    }

    fun addUser(v:View)
    {
        var idAluno = this.edittext_idAluno.text.toString()
        var name = this.edittext_name.text.toString()
        var matricula = this.edittext_Matricula.text.toString()
        var result = DBHelperAluno.insertAluno(Aluno(idAluno, name, matricula))
        //clear all edittext s
        this.edittext_Matricula.setText("")
        this.edittext_name.setText("")
        this.edittext_idAluno.setText("")
        this.textview_result.text = "Added user : "+result
        this.ll_entries.removeAllViews()
    }

    fun deleteUser(v:View)
    {
        var idAluno = this.edittext_idAluno.text.toString()
        val result = DBHelperAluno.deleteAluno(idAluno)
        this.textview_result.text = "Deleted user : "+result
        this.ll_entries.removeAllViews()
    }

    fun showAllUsers(v:View)
    {
        var alunos = DBHelperAluno.readAllAlunos()
        this.ll_entries.removeAllViews()
        alunos.forEach {
            var tv_user = TextView(this)
            tv_user.textSize = 30F
            tv_user.text = it.nome.toString() + " - " + it.matricula.toString()
            this.ll_entries.addView(tv_user)
        }
        this.textview_result.text = "Fetched " + alunos.size + " users"
    }
}