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
    class salaEntry: BaseColumns
    {
        companion object
        {
            val TABLE_NAME = "Sala"
            val COLUMN_ID = "ID"
            val COLUMN_NUMERO = "NUMERO"
        }
    }
    class turmaEntry: BaseColumns
    {
        companion object
        {
            val TABLE_NAME = "Turma"
            val COLUMN_ID = "ID"
            val COLUMN_HORARIOINICIO = "HORARIOINICIO"
            val COLUMN_HORARIOFIM = "HORARIOFIM"
            val COLUMN_IDDISC = "IDDISC"
            val COLUMN_IDPROF = "IDPROF"
            val COLUMN_IDSALA = "IDSALA"

        }
    }
    class alunos_turmasEntry: BaseColumns
    {
        companion object
        {
            val TABLE_NAME = "Alunos_Turmas"
            val COLUMN_IDALUNO = "IDALUNO"
            val COLUMN_IDTURMA = "IDTURMA"
        }
    }
    class faltaEntry: BaseColumns
    {
        companion object
        {
            val TABLE_NAME = "Falta"
            val COLUMN_ID = "ID"
            val COLUMN_IDALUNO = "IDALUNO"
            val COLUMN_IDTURMA = "IDTURMA"
        }
    }
}