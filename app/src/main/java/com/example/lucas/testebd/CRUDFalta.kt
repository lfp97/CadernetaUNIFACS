package com.example.lucas.testebd

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.falta_crud.*

class CRUDFalta : AppCompatActivity() {

    lateinit var DBHelperFalta: DBHelperFalta
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.falta_crud)
        DBHelperFalta= DBHelperFalta(this)
    }

    fun addFalta (view: View)
    {
        var idFalta= this.editTextFaltaID.text.toString()
        var idAluno= this.editTextFaltaIDAluno.text.toString()
        var idTurma= this.editTextFaltaIDTURMA.text.toString()

        var result= DBHelperFalta.insertFalta(idFalta, idAluno, idTurma)
        this.textViewFaltaResult.setText(result.toString())
        this.editTextFaltaID.setText("")
        this.editTextFaltaIDAluno.setText("")
        this.editTextFaltaIDTURMA.setText("")
    }

    fun showAllFaltas (view: View)
    {
        var faltas= DBHelperFalta.readAll()
        this.ll_entriesFaltas.removeAllViews()
        faltas.forEach {
            var tv_falta= TextView(this)
            tv_falta.textSize= 30F
            tv_falta.text= it.idFalta + " - " + it.idAluno + " - " + it.idTurma
            this.ll_entriesFaltas.addView(tv_falta)
        }
        this.textViewFaltaResult.text= "Fetched " + faltas.size + " faltas"
    }
}
