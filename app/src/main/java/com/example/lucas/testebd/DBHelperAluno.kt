package com.example.lucas.testebd

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

class DBHelperAluno(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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
    fun insertAluno(aluno: Aluno): Boolean
    {

        val db = writableDatabase

        val values = ContentValues()
        values.put(DBContract.alunoEntry.COLUMN_ID, aluno.id)
        values.put(DBContract.alunoEntry.COLUMN_NAME, aluno.nome)
        values.put(DBContract.alunoEntry.COLUMN_MATRICULA, aluno.matricula)

        val newRowId = db.insert(DBContract.alunoEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteAluno(idAluno: String): Boolean {

        val db = writableDatabase

        val selection = DBContract.alunoEntry.COLUMN_ID + " LIKE ?"

        val selectionArgs = arrayOf(idAluno)

        db.delete(DBContract.alunoEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readAluno(idAluno: String): Aluno {
        val padrao= Aluno("-1", "ERRO", "9999")
        var flag= false
        lateinit var aluno: Aluno
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.alunoEntry.TABLE_NAME + " WHERE " + DBContract.alunoEntry.COLUMN_ID + "='" + idAluno + "'", null)
        } catch (e: SQLiteException) {

            db.execSQL(SQL_CREATE_ENTRIES)
            return padrao
        }

        var idAluno: String
        var name: String
        var matricula: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                idAluno = cursor.getString(cursor.getColumnIndex(DBContract.alunoEntry.COLUMN_ID))
                name = cursor.getString(cursor.getColumnIndex(DBContract.alunoEntry.COLUMN_NAME))
                matricula = cursor.getString(cursor.getColumnIndex(DBContract.alunoEntry.COLUMN_MATRICULA))

                aluno= (Aluno(idAluno, name, matricula))
                flag= true
                cursor.moveToNext()
            }
        }
        if (flag)
            return aluno
        else
            return padrao
    }

    fun readAllAlunos(): ArrayList<Aluno> {
        val alunos = ArrayList<Aluno>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.alunoEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var idAluno: String
        var name: String
        var matricula: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                idAluno = cursor.getString(cursor.getColumnIndex(DBContract.alunoEntry.COLUMN_ID))
                name = cursor.getString(cursor.getColumnIndex(DBContract.alunoEntry.COLUMN_NAME))
                matricula = cursor.getString(cursor.getColumnIndex(DBContract.alunoEntry.COLUMN_MATRICULA))

                alunos.add(Aluno(idAluno, name, matricula))
                cursor.moveToNext()
            }
        }
        return alunos
    }

    fun readAllAlunosToListView(): Cursor
    {
        val db = writableDatabase
        lateinit var cursor: Cursor
        try
        {
            cursor = db.rawQuery("select NOME, MATRICULA from " + DBContract.alunoEntry.TABLE_NAME, null)
        }
        catch (e: SQLiteException)
        {
            db.execSQL(SQL_CREATE_ENTRIES)
        }
        return cursor
    }

    companion object {

        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Caderneta.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DBContract.alunoEntry.TABLE_NAME + " (" +
                        DBContract.alunoEntry.COLUMN_ID + " TEXT PRIMARY KEY," +
                        DBContract.alunoEntry.COLUMN_NAME + " TEXT," +
                        DBContract.alunoEntry.COLUMN_MATRICULA + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.alunoEntry.TABLE_NAME
    }

}