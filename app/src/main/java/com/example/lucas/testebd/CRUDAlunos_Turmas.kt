package com.example.lucas.testebd

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.alunos__turmas_crud.*

class CRUDAlunos_Turmas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alunos__turmas_crud)
    }

    fun onClickAdd(view: View)
    {
        var db= DBHelperAlunos_Turmas(this)
        var EDAlu= findViewById<View>(R.id.editTextATIDALUNO) as EditText
        var EDTur= findViewById<View>(R.id.editTextATIDTURMA) as EditText
        var tvR= findViewById<View>(R.id.textViewATResult) as TextView
        var idA= EDAlu.text.toString()
        var idT= EDTur.text.toString()
        var result= db.insertAlunoEmTurma(idA, idT)
        EDAlu.setText("")
        EDTur.setText("")
        tvR.setText(result.toString())
    }

    fun onClickShowAll(view: View)
    {
        var db= DBHelperAlunos_Turmas(this)
        var lista= db.readAll()
        this.ll_entriesAlunos_Turmas.removeAllViews()
        lista.forEach {
            var tv_alunos_turmas= TextView(this)
            tv_alunos_turmas.textSize= 30F
            tv_alunos_turmas.text= it.idAluno + " - " + it.idTurma
            this.ll_entriesAlunos_Turmas.addView(tv_alunos_turmas)
        }
    }
}
