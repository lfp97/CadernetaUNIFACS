package com.example.lucas.testebd

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main3.*

class Main3Activity : AppCompatActivity() {

    lateinit var DBHelperProfessor : DBHelperProfessor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DBHelperProfessor = DBHelperProfessor(this)
    }

    fun addUser(v: View)
    {
        var idProfessor = this.edittext_idProfessor.text.toString()
        var name = this.edittext_name.text.toString()
        var matricula = this.edittext_Matricula.text.toString()
        var senha= this.edittext_Senha.text.toString()
        var result = DBHelperProfessor.insertProfessor(Professor(idProfessor, name, matricula, senha))
        //clear all edittext s
        this.edittext_Matricula.setText("")
        this.edittext_name.setText("")
        this.edittext_idProfessor.setText("")
        this.textview_result.text = "Added user : "+result
        this.ll_entries.removeAllViews()
    }

    fun deleteUser(v: View)
    {
        var idProfessor = this.edittext_idProfessor.text.toString()
        val result = DBHelperProfessor.deleteProfessor(idProfessor)
        this.textview_result.text = "Deleted user : "+result
        this.ll_entries.removeAllViews()
    }

    fun showAllUsers(v: View)
    {
        var professores = DBHelperProfessor.readAllProfessores()
        this.ll_entries.removeAllViews()
        professores.forEach {
            var tv_user = TextView(this)
            tv_user.textSize = 30F
            tv_user.text = it.nome.toString() + " - " + it.matricula.toString()
            this.ll_entries.addView(tv_user)
        }
        this.textview_result.text = "Fetched " + professores.size + " users"
    }
}