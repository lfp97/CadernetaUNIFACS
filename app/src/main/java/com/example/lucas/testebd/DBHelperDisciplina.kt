package com.example.lucas.testebd


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

class DBHelperDisciplina(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertDisciplina(disc: Disciplina): Boolean
    {

        val db = writableDatabase

        val values = ContentValues()
        values.put(DBContract.disciplinaEntry.COLUMN_ID, disc.id)
        values.put(DBContract.disciplinaEntry.COLUMN_NAME, disc.nome)

        val newRowId = db.insert(DBContract.disciplinaEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deletePDisciplina(idDisc: String): Boolean {

        val db = writableDatabase
        val selection = DBContract.disciplinaEntry.COLUMN_ID + " LIKE ?"
        val selectionArgs = arrayOf(idDisc)
        db.delete(DBContract.disciplinaEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readDisciplina(idDisc: String): ArrayList<Disciplina> {
        val disciplinas = ArrayList<Disciplina>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.disciplinaEntry.TABLE_NAME + " WHERE " + DBContract.disciplinaEntry.COLUMN_ID + "='" + idDisc + "'", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var name: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                name = cursor.getString(cursor.getColumnIndex(DBContract.disciplinaEntry.COLUMN_NAME))

                disciplinas.add(Disciplina(idDisc, name))
                cursor.moveToNext()
            }
        }
        return disciplinas
    }

    fun readAllDisciplinas(): ArrayList<Disciplina> {
        val disciplinas = ArrayList<Disciplina>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.disciplinaEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var idDisc: String
        var name: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                idDisc = cursor.getString(cursor.getColumnIndex(DBContract.disciplinaEntry.COLUMN_ID))
                name = cursor.getString(cursor.getColumnIndex(DBContract.disciplinaEntry.COLUMN_NAME))

                disciplinas.add(Disciplina(idDisc, name))
                cursor.moveToNext()
            }
        }
        return disciplinas
    }

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Caderneta.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DBContract.disciplinaEntry.TABLE_NAME + " (" +
                        DBContract.disciplinaEntry.COLUMN_ID + " TEXT PRIMARY KEY," +
                        DBContract.disciplinaEntry.COLUMN_NAME + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.disciplinaEntry.TABLE_NAME
    }

}