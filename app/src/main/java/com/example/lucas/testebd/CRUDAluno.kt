package com.example.lucas.testebd

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.aluno_crud.*

<<<<<<< HEAD:app/src/main/java/com/example/lucas/testebd/MainActivity.kt
class  MainActivity : AppCompatActivity() {
=======
class CRUDAluno : AppCompatActivity() {
>>>>>>> d69b1342e3fe6ffa04fb0370d48df433586290d1:app/src/main/java/com/example/lucas/testebd/CRUDAluno.kt

    lateinit var DBHelperAluno : DBHelperAluno

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<< HEAD:app/src/main/java/com/example/lucas/testebd/MainActivity.kt
        //setContentView(R.layout.activity_main)
=======
        setContentView(R.layout.aluno_crud)
>>>>>>> d69b1342e3fe6ffa04fb0370d48df433586290d1:app/src/main/java/com/example/lucas/testebd/CRUDAluno.kt

        DBHelperAluno = DBHelperAluno(this)
    }

    fun addUser(v:View)
    {
        var idAluno = this.edittext_idAluno.text.toString()
        var name = this.edittext_nameAluno.text.toString()
        var matricula = this.edittext_MatriculaAluno.text.toString()
        var result = DBHelperAluno.insertAluno(Aluno(idAluno, name, matricula))
        //clear all edittext s
        this.edittext_MatriculaAluno.setText("")
        this.edittext_nameAluno.setText("")
        this.edittext_idAluno.setText("")
        this.textview_resultAluno.text = "Added user : "+result
        this.ll_entriesAluno.removeAllViews()
    }

    fun deleteUser(v:View)
    {
        var idAluno = this.edittext_idAluno.text.toString()
        val result = DBHelperAluno.deleteAluno(idAluno)
        this.textview_resultAluno.text = "Deleted user : "+result
        this.ll_entriesAluno.removeAllViews()
    }

    fun showAllUsers(v:View)
    {
        var alunos = DBHelperAluno.readAllAlunos()
        this.ll_entriesAluno.removeAllViews()
        alunos.forEach {
            var tv_user = TextView(this)
            tv_user.textSize = 30F
            tv_user.text = it.nome.toString() + " - " + it.matricula.toString()
            this.ll_entriesAluno.addView(tv_user)
        }
        this.textview_resultAluno.text = "Fetched " + alunos.size + " users"
    }
}