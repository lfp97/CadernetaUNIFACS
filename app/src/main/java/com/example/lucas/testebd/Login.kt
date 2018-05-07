package com.example.lucas.testebd

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.EditText
import android.widget.Toast
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Login : AppCompatActivity()
{
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_login)



            val aux= LocalTime.now() //pega o horario do celular
            val formatador= DateTimeFormatter.ISO_TIME //confirmando que so vai pegar hora
            val horario= aux.format(formatador) //aplicando a formatacao ao valor capturado
            var horaRaw= horario.substring(0, 5)//cortando para ter somente HH:MM, descartando segundos e etc
            lateinit var hora: String
            if (horaRaw.substring(0,2).equals("07") || horaRaw.substring(0,2).equals("08") || horaRaw.substring(0,2).equals("09"))
                hora= "07:00"
            else //1x:00
            {
                if (horaRaw.substring(0,2).equals("10") || horaRaw.substring(0,2).equals("11") || horaRaw.substring(0,2).equals("12"))
                    hora= "10:00"
                else if(horaRaw.substring(0,2).equals("19") || horaRaw.substring(0,2).equals("20") || horaRaw.substring(0,2).equals("21") || horaRaw.substring(0,2).equals("22"))
                        hora= "19:00"
            }
        var ETUsu= findViewById<View>(R.id.editTextUsu) as EditText
        ETUsu.setText(hora)
        var ETSenha= findViewById<View>(R.id.editTextSenha) as EditText
        var auxDBTurma= DBHelperTurma(this)
        var turm: Turma//= Turma("erro", "erro", "erro", "erro", "erro", "erro")
        turm= auxDBTurma.readTurmaProfessorHorario("112001", hora)
        ETSenha.setText("Horario da turma: " + turm.horarioinicio)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onClickLogin (view: View)
    {
        var ETUsu= findViewById<View>(R.id.editTextUsu) as EditText
        val mat= ETUsu.text.toString()
        var ETSenha= findViewById<View>(R.id.editTextSenha) as EditText
        val senha= ETSenha.text.toString()
        var auxDBProf= DBHelperProfessor(this)
        var auxDBTurma= DBHelperTurma(this)

        var arrayProf= auxDBProf.readProfessor(mat)
        if (arrayProf.isEmpty()) //caso nao encontre o usuario
        {
            //Toast.makeText(this, "Usuário não existe!", Toast.LENGTH_LONG)
            ETUsu.setText("")
            ETSenha.setText("")
        }
        else //caso exista usuario
        {
            var professor= arrayProf.get(0)
            if (professor.matricula.equals(mat) && professor.senha.equals(senha)) //caso autentique
            {
                val aux= LocalTime.now() //pega o horario do celular
                val formatador= DateTimeFormatter.ISO_TIME //confirmando que so vai pegar hora
                val horario= aux.format(formatador) //aplicando a formatacao ao valor capturado
                var horaRaw= horario.substring(0, 5)//cortando para ter somente HH:MM, descartando segundos e etc
                lateinit var hora: String
                if (horaRaw.substring(0,2).equals("07") || horaRaw.substring(0,2).equals("08") || horaRaw.substring(0,2).equals("09"))
                    hora= "07:00"
                else //1x:00
                {
                    if (horaRaw.substring(0,2).equals("10") || horaRaw.substring(0,2).equals("11") || horaRaw.substring(0,2).equals("12"))
                        hora= "10:00"
                    else if(horaRaw.substring(0,2).equals("19") || horaRaw.substring(0,2).equals("20") || horaRaw.substring(0,2).equals("21") || horaRaw.substring(0,2).equals("22"))
                        hora= "19:00"
                }


                var turma= auxDBTurma.readTurmaProfessorHorario(professor.matricula, hora)
                //var listaTurmas= auxDBTurma.readAllTurmas()
                //var turma= listaTurmas.get(0)

                if (turma.horarioinicio.equals(hora))
                {
                    val intentLogar= Intent(this, Caderneta::class.java)
                    intentLogar.putExtra(Caderneta.hora, hora)
                    intentLogar.putExtra(Caderneta.nomeProfessor, professor.nome)
                    intentLogar.putExtra(Caderneta.idDisc, turma.disciplina)
                    intentLogar.putExtra(Caderneta.numeroSala, turma.sala)
                    intentLogar.putExtra(Caderneta.idTurma, turma.id)
                    startActivity(intentLogar)
                }
            }
            else//caso tenha algum erro
            {
                ETUsu.setText("")
                ETUsu.setHint("Senha Errada!")
                ETSenha.setText("")
            }
        }
    }
}
