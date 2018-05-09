package com.example.lucas.testebd

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBHelperFalta(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun readAll(): ArrayList<Falta>
    {
        val faltas = ArrayList<Falta>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.faltaEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var idFalta: String
        var idAluno: String
        var idTurma: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                idFalta= cursor.getString(cursor.getColumnIndex(DBContract.faltaEntry.COLUMN_ID))
                idAluno= cursor.getString(cursor.getColumnIndex(DBContract.faltaEntry.COLUMN_IDALUNO))
                idTurma = cursor.getString(cursor.getColumnIndex(DBContract.faltaEntry.COLUMN_IDTURMA))

                faltas.add(Falta(idFalta, idAluno, idTurma))
                cursor.moveToNext()
            }
        }
        return faltas
    }

    fun insertFalta (idFalta: String, idAluno: String, idTurma: String): Boolean
    {
        val db = writableDatabase

        val values = ContentValues()
        values.put(DBContract.faltaEntry.COLUMN_ID, idFalta)
        values.put(DBContract.faltaEntry.COLUMN_IDALUNO, idAluno)
        values.put(DBContract.faltaEntry.COLUMN_IDTURMA, idTurma)

        val newRowId = db.insert(DBContract.faltaEntry.TABLE_NAME, null, values)

        return true
    }

    companion object {

        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Caderneta.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DBContract.faltaEntry.TABLE_NAME + " (" +
                        DBContract.faltaEntry.COLUMN_ID + " TEXT PRIMARY KEY, " +
                        DBContract.faltaEntry.COLUMN_IDALUNO + " TEXT, " +
                        DBContract.faltaEntry.COLUMN_IDTURMA + " TEXT, FOREIGN KEY (IDALUNO) REFERENCES ALUNO(ID), FOREIGN KEY (IDTURMA) REFERENCES TURMA(ID))"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.faltaEntry.TABLE_NAME
    }
}