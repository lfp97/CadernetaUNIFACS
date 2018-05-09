package com.example.lucas.testebd


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.lucas.testebd.Professor
import android.widget.Toast

import java.util.ArrayList

class DBHelperProfessor(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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
    fun insertProfessor(professor: Professor): Boolean
    {
        val db = writableDatabase

        val values = ContentValues()
        values.put(DBContract.professorEntry.COLUMN_ID, professor.id)
        values.put(DBContract.professorEntry.COLUMN_NAME, professor.nome)
        values.put(DBContract.professorEntry.COLUMN_MATRICULA, professor.matricula)
        values.put(DBContract.professorEntry.COLUMN_SENHA, professor.senha)

        val newRowId = db.insert(DBContract.professorEntry.TABLE_NAME, null, values)

        return true
    }

    fun addProfessor(professor: Professor) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_PROFESSOR_NOME, professor.nome)
        values.put(COLUMN_PROFESSOR_MATRICULA, professor.matricula)
        values.put(COLUMN_PROFESSOR_SENHA, professor.senha)

        db.insert(TABLE_PROFESSOR, null, values)
        db.close()
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteProfessor(idProfessor: String): Boolean {
        val db = writableDatabase
        val selection = DBContract.professorEntry.COLUMN_ID + " LIKE ?"
        val selectionArgs = arrayOf(idProfessor)
        db.delete(DBContract.professorEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readProfessor(matProfessor: String): ArrayList<Professor> {
        val professores = ArrayList<Professor>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.professorEntry.TABLE_NAME + " WHERE " + DBContract.professorEntry.COLUMN_MATRICULA + "='" + matProfessor + "'", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var id: String
        var name: String
        var matricula: String
        var senha: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                id= cursor.getString(cursor.getColumnIndex(DBContract.professorEntry.COLUMN_ID))
                name = cursor.getString(cursor.getColumnIndex(DBContract.professorEntry.COLUMN_NAME))
                matricula = cursor.getString(cursor.getColumnIndex(DBContract.professorEntry.COLUMN_MATRICULA))
                senha= cursor.getString(cursor.getColumnIndex(DBContract.professorEntry.COLUMN_SENHA))

                professores.add(Professor(id, name, matricula, senha))
                cursor.moveToNext()
            }
        }
        return professores
    }

    fun readAllProfessores(): ArrayList<Professor> {
        val professores = ArrayList<Professor>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.professorEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var idProfessor: String
        var name: String
        var matricula: String
        var senha: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                idProfessor = cursor.getString(cursor.getColumnIndex(DBContract.professorEntry.COLUMN_ID))
                name = cursor.getString(cursor.getColumnIndex(DBContract.professorEntry.COLUMN_NAME))
                matricula = cursor.getString(cursor.getColumnIndex(DBContract.professorEntry.COLUMN_MATRICULA))
                senha = cursor.getString(cursor.getColumnIndex(DBContract.professorEntry.COLUMN_SENHA))

                professores.add(Professor(idProfessor, name, matricula, senha))
                cursor.moveToNext()
            }
        }
        return professores
    }

    fun checkProfessor(matricula: String): Boolean {

        val columns = arrayOf(COLUMN_PROFESSOR_ID)
        val db = this.readableDatabase
        val selection = "$COLUMN_PROFESSOR_MATRICULA = ?"
        val selectionArgs = arrayOf(matricula)

        val cursor = db.query(TABLE_PROFESSOR, 
                columns,
                selection,   
                selectionArgs, 
                null,  
                null,  
                null) 

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }

        return false
    }

    fun checkProfessor(matricula: String, senha: String): Boolean {

        val columns = arrayOf(COLUMN_PROFESSOR_ID)
        val db = this.readableDatabase
        val selection = "$COLUMN_PROFESSOR_MATRICULA = ? AND $COLUMN_PROFESSOR_SENHA = ?"
        val selectionArgs = arrayOf(matricula, senha)

        val cursor = db.query(TABLE_PROFESSOR,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null)

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0)
            return true

        return false
    }

    companion object {

        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Caderneta.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DBContract.professorEntry.TABLE_NAME + " (" +
                        DBContract.professorEntry.COLUMN_ID + " TEXT PRIMARY KEY," +
                        DBContract.professorEntry.COLUMN_NAME + " TEXT," +
                        DBContract.professorEntry.COLUMN_MATRICULA + " TEXT," +
                        DBContract.professorEntry.COLUMN_SENHA + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.professorEntry.TABLE_NAME

        private val TABLE_PROFESSOR = "professor"

        private val COLUMN_PROFESSOR_ID = "id"
        private val COLUMN_PROFESSOR_NOME = "nome"
        private val COLUMN_PROFESSOR_MATRICULA = "matricula"
        private val COLUMN_PROFESSOR_SENHA = "senha"
    }

}