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
}