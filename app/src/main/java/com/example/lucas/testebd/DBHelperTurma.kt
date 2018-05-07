package com.example.lucas.testebd


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

class DBHelperTurma(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertTurma(turma: Turma): Boolean
    {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.turmaEntry.COLUMN_ID, turma.id)
        values.put(DBContract.turmaEntry.COLUMN_HORARIOINICIO, turma.horarioinicio)
        values.put(DBContract.turmaEntry.COLUMN_HORARIOFIM, turma.horariofim)
        values.put(DBContract.turmaEntry.COLUMN_IDDISC, turma.disciplina)
        values.put(DBContract.turmaEntry.COLUMN_IDPROF, turma.professor)
        values.put(DBContract.turmaEntry.COLUMN_IDSALA, turma.sala)


        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.turmaEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteTurma(idTurma: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.turmaEntry.COLUMN_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(idTurma)
        // Issue SQL statement.
        db.delete(DBContract.turmaEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readTurma(idTurma: String): ArrayList<Turma> {
        val turmas = ArrayList<Turma>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.turmaEntry.TABLE_NAME + " WHERE " + DBContract.turmaEntry.COLUMN_ID + "='" + idTurma + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var horarioinicio: String
        var horariofim: String
        var iddisc: String
        var idprof: String
        var idsala: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                horarioinicio = cursor.getString(cursor.getColumnIndex(DBContract.turmaEntry.COLUMN_HORARIOINICIO))
                horariofim = cursor.getString(cursor.getColumnIndex(DBContract.turmaEntry.COLUMN_HORARIOFIM))
                iddisc = cursor.getString(cursor.getColumnIndex(DBContract.turmaEntry.COLUMN_IDDISC))
                idprof = cursor.getString(cursor.getColumnIndex(DBContract.turmaEntry.COLUMN_IDPROF))
                idsala = cursor.getString(cursor.getColumnIndex(DBContract.turmaEntry.COLUMN_IDSALA))

                turmas.add(Turma(idTurma, horarioinicio, horariofim, iddisc, idprof, idsala))
                cursor.moveToNext()
            }
        }
        return turmas
    }

    fun readTurmaProfessorHorario(idProf: String, horario: String): Turma
    {
        val db = writableDatabase
        lateinit var turma: Turma
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.turmaEntry.TABLE_NAME + " WHERE " + DBContract.turmaEntry.COLUMN_IDPROF + "='" + idProf + "' and " +
                    DBContract.turmaEntry.COLUMN_HORARIOINICIO + "='" + horario + "'", null)
                                        //select * from Turma where IDPROF= '1' and HORARIOINICIO= '19:00'
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)

        }

        var horarioinicio: String
        var horariofim: String
        var iddisc: String
        var idprof: String
        var idsala: String

        if (cursor!!.moveToFirst())
        {
                horarioinicio = cursor.getString(cursor.getColumnIndex(DBContract.turmaEntry.COLUMN_HORARIOINICIO))
                horariofim = cursor.getString(cursor.getColumnIndex(DBContract.turmaEntry.COLUMN_HORARIOFIM))
                iddisc = cursor.getString(cursor.getColumnIndex(DBContract.turmaEntry.COLUMN_IDDISC))
                idprof = cursor.getString(cursor.getColumnIndex(DBContract.turmaEntry.COLUMN_IDPROF))
                idsala = cursor.getString(cursor.getColumnIndex(DBContract.turmaEntry.COLUMN_IDSALA))

                turma= Turma(idProf, horarioinicio, horariofim, iddisc, idProf, idsala)
        }
        return turma
    }

    fun readAllTurmas(): ArrayList<Turma> {
        val turmas = ArrayList<Turma>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.turmaEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var idTurma: String
        var horarioinicio: String
        var horariofim: String
        var iddisc: String
        var idprof: String
        var idsala: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                idTurma = cursor.getString(cursor.getColumnIndex(DBContract.turmaEntry.COLUMN_ID))
                horarioinicio = cursor.getString(cursor.getColumnIndex(DBContract.turmaEntry.COLUMN_HORARIOINICIO))
                horariofim = cursor.getString(cursor.getColumnIndex(DBContract.turmaEntry.COLUMN_HORARIOFIM))
                iddisc = cursor.getString(cursor.getColumnIndex(DBContract.turmaEntry.COLUMN_IDDISC))
                idprof = cursor.getString(cursor.getColumnIndex(DBContract.turmaEntry.COLUMN_IDPROF))
                idsala = cursor.getString(cursor.getColumnIndex(DBContract.turmaEntry.COLUMN_IDSALA))

                turmas.add(Turma(idTurma, horarioinicio, horariofim, iddisc, idprof, idsala))
                cursor.moveToNext()
            }
        }
        return turmas
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Caderneta.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DBContract.turmaEntry.TABLE_NAME + " (" +
                        DBContract.turmaEntry.COLUMN_ID + " TEXT PRIMARY KEY," +
                        DBContract.turmaEntry.COLUMN_HORARIOINICIO + " TEXT," +
                        DBContract.turmaEntry.COLUMN_HORARIOFIM + " TEXT," +
                        DBContract.turmaEntry.COLUMN_IDDISC + " TEXT," +
                        DBContract.turmaEntry.COLUMN_IDPROF + " TEXT," +
                        DBContract.turmaEntry.COLUMN_IDSALA + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.salaEntry.TABLE_NAME
    }

}