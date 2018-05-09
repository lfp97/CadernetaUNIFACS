package com.example.lucas.testebd


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

class DBHelperSala(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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
    fun insertSala(sala: Sala): Boolean
    {
      
        val db = writableDatabase

        
        val values = ContentValues()
        values.put(DBContract.salaEntry.COLUMN_ID, sala.id)
        values.put(DBContract.salaEntry.COLUMN_NUMERO, sala.numero)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.salaEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteSala(idSala: String): Boolean {
    
        val db = writableDatabase
    
        val selection = DBContract.salaEntry.COLUMN_ID + " LIKE ?"
      
        val selectionArgs = arrayOf(idSala)
       
        db.delete(DBContract.salaEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readSala(idSala: String): ArrayList<Sala> {
        val salas = ArrayList<Sala>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.salaEntry.TABLE_NAME + " WHERE " + DBContract.salaEntry.COLUMN_ID + "='" + idSala + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var name: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                name = cursor.getString(cursor.getColumnIndex(DBContract.salaEntry.COLUMN_NUMERO))

                salas.add(Sala(idSala, name))
                cursor.moveToNext()
            }
        }
        return salas
    }

    fun readAllSalas(): ArrayList<Sala> {
        val salas = ArrayList<Sala>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.salaEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var idSala: String
        var name: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                idSala = cursor.getString(cursor.getColumnIndex(DBContract.salaEntry.COLUMN_ID))
                name = cursor.getString(cursor.getColumnIndex(DBContract.salaEntry.COLUMN_NUMERO))

                salas.add(Sala(idSala, name))
                cursor.moveToNext()
            }
        }
        return salas
    }

    companion object {

        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Caderneta.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DBContract.salaEntry.TABLE_NAME + " (" +
                        DBContract.salaEntry.COLUMN_ID + " TEXT PRIMARY KEY," +
                        DBContract.salaEntry.COLUMN_NUMERO + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.salaEntry.TABLE_NAME
    }

}