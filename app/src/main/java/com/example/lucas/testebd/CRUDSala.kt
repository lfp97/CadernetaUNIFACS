package com.example.lucas.testebd

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.sala_crud.*

class CRUDSala: AppCompatActivity() {

    lateinit var DBHelperSala : DBHelperSala

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sala_crud)

        DBHelperSala = DBHelperSala(this)
    }

    fun addSala(v: View)
    {
        var idSala = this.edittext_TurmaidSala.text.toString()
        var numero = this.edittext_numeroSala.text.toString()
        var result = DBHelperSala.insertSala(Sala(idSala, numero))
        //clear all edittext s
        this.edittext_numeroSala.setText("")
        this.edittext_TurmaidSala.setText("")
        this.textview_resultSala.text= "Added salas : "+result
        this.ll_entriesSala.removeAllViews()
    }

    fun deleteSala(v: View)
    {
        var idSala = this.edittext_TurmaidSala.text.toString()
        val result = DBHelperSala.deleteSala(idSala)
        this.textview_resultSala.text = "Deleted sala : "+result
        this.ll_entriesSala.removeAllViews()
    }

    fun showAllSala(v: View)
    {
        var salas = DBHelperSala.readAllSalas()
        this.ll_entriesSala.removeAllViews()
        salas.forEach {
            var tv_user = TextView(this)
            tv_user.textSize = 30F
            tv_user.text = it.id.toString() + " - " + it.numero.toString()
            this.ll_entriesSala.addView(tv_user)
        }
        this.textview_resultSala.text = "Fetched " + salas.size + " salas"
    }
}