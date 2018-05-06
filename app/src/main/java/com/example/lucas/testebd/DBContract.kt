package com.example.lucas.testebd

import android.provider.BaseColumns

object DBContract
{
    class alunoEntry : BaseColumns
    {
        companion object
        {
            val TABLE_NAME = "Aluno"
            val COLUMN_ID = "ID"
            val COLUMN_NAME = "NOME"
            val COLUMN_MATRICULA = "MATRICULA"
        }
    }
    class professorEntry : BaseColumns
    {
        companion object
        {
            val TABLE_NAME = "Professor"
            val COLUMN_ID = "ID"
            val COLUMN_NAME = "NOME"
            val COLUMN_MATRICULA = "MATRICULA"
            val COLUMN_SENHA = "SENHA"
        }
    }
    class disciplinaEntry: BaseColumns
    {
        companion object
        {
            val TABLE_NAME = "Disciplina"
            val COLUMN_ID = "ID"
            val COLUMN_NAME = "NOME"
        }
    }
}