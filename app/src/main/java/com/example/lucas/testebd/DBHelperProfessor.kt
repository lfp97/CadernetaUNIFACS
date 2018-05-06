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
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertProfessor(professor: Professor): Boolean
    {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.professorEntry.COLUMN_ID, professor.id)
        values.put(DBContract.professorEntry.COLUMN_NAME, professor.nome)
        values.put(DBContract.professorEntry.COLUMN_MATRICULA, professor.matricula)
        values.put(DBContract.professorEntry.COLUMN_SENHA, professor.senha)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.professorEntry.TABLE_NAME, null, values)

        return true
    }

    fun addProfessor(professor: Professor) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_PROFESSOR_NOME, professor.nome)
        values.put(COLUMN_PROFESSOR_MATRICULA, professor.matricula)
        values.put(COLUMN_PROFESSOR_SENHA, professor.senha)

        // executa inserção
        db.insert(TABLE_PROFESSOR, null, values)
        db.close()
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteProfessor(idProfessor: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.professorEntry.COLUMN_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(idProfessor)
        // Issue SQL statement.
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
            // if table not yet present, create it
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

        val cursor = db.query(TABLE_PROFESSOR, // Tabela
                columns,
                selection,      //columns for the WHERE clause
                selectionArgs,  //The values for the WHERE clause
                null,  //group the rows
                null,   //filter by row groups
                null)  //The sort order

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
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Caderneta.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DBContract.professorEntry.TABLE_NAME + " (" +
                        DBContract.professorEntry.COLUMN_ID + " TEXT PRIMARY KEY," +
                        DBContract.professorEntry.COLUMN_NAME + " TEXT," +
                        DBContract.professorEntry.COLUMN_MATRICULA + " TEXT," +
                        DBContract.professorEntry.COLUMN_SENHA + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.professorEntry.TABLE_NAME

        // Nome da tabela de professor
        private val TABLE_PROFESSOR = "professor"

        // Campos tabela professor
        private val COLUMN_PROFESSOR_ID = "id"
        private val COLUMN_PROFESSOR_NOME = "nome"
        private val COLUMN_PROFESSOR_MATRICULA = "matricula"
        private val COLUMN_PROFESSOR_SENHA = "senha"
    }

}