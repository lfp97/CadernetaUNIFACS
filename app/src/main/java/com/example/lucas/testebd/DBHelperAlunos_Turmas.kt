package com.example.lucas.testebd

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBHelperAlunos_Turmas(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    override fun onCreate(db: SQLiteDatabase)
    {
        db.execSQL(SQL_CREATE_ENTRIES)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun readAll (): ArrayList<Alunos_Turmas>
    {
        val alunos_turmas = ArrayList<Alunos_Turmas>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.alunos_turmasEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var idAluno: String
        var idTurma: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                idAluno= cursor.getString(cursor.getColumnIndex(DBContract.alunos_turmasEntry.COLUMN_IDALUNO))
                idTurma = cursor.getString(cursor.getColumnIndex(DBContract.alunos_turmasEntry.COLUMN_IDTURMA))

                alunos_turmas.add(Alunos_Turmas(idAluno, idTurma))
                cursor.moveToNext()
            }
        }
        return alunos_turmas
    }

    fun insertAlunoEmTurma (idAluno: String, idTurma: String): Boolean
    {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.alunos_turmasEntry.COLUMN_IDALUNO, idAluno)
        values.put(DBContract.alunos_turmasEntry.COLUMN_IDTURMA, idTurma)


        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.alunos_turmasEntry.TABLE_NAME, null, values)

        return true
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Caderneta.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DBContract.turmaEntry.TABLE_NAME + " (" +
                        DBContract.alunos_turmasEntry.COLUMN_IDALUNO + " TEXT PRIMARY KEY," +
                        DBContract.alunos_turmasEntry.COLUMN_IDTURMA + " TEXT, FOREIGN KEY (IDALUNO) REFERENCES ALUNO(ID), FOREIGN KEY (IDTURMA) REFERENCES TURMA(ID))"
        /*
        create table ALUNOS_TURMAS (IDALUNO text primary key, IDTURMA text, foreign key (IDALUNO) references Aluno(ID), foreign key (IDTURMA) references Turma(ID));
        */

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.salaEntry.TABLE_NAME
    }
}