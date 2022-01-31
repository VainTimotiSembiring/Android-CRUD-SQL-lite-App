package com.example.mysqlite

import MyDB.userDB
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.semester6.uas_crudsqlite.User

class myDBHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME,
    null, DATABASE_VERSION
) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "uas2021phonebookdb.db"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        var CREATE_USER_TABLE = ("CREATE TABLE ${userDB.userTable.TABLE_USER} " +
                "(${userDB.userTable.COLUMN_ID} INTEGER PRIMARY KEY," +
                "${userDB.userTable.COLUMN_NAME} TEXT," +
                "${userDB.userTable.COLUMN_PHONE} TEXT)")
        db!!.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(
            "DROP TABLE IF EXISTS " +
                    "${userDB.userTable.TABLE_USER}"
        )
        onCreate(db)
    }

    fun addUser(user: User): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
           put(userDB.userTable.COLUMN_NAME, user.nama)
           put(userDB.userTable.COLUMN_PHONE, user.no_hp)
        }
        val success = db.insert(
            userDB.userTable.TABLE_USER,
            null, contentValues
        )
        db.close()
        return success
    }

    fun viewAllName(): List<String> {
        val nameList = ArrayList<String>()
        val SELECT_NAME = "SELECT ${userDB.userTable.COLUMN_NAME} " +
                "FROM ${userDB.userTable.TABLE_USER}"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(SELECT_NAME, null)
        } catch (e: SQLException) {
            //db.execSQL(SELECT_NAME)
            return ArrayList()
        }
        var userNama: String = ""
        if (cursor.moveToFirst()) {
            do {
                userNama = cursor.getString(
                    cursor.getColumnIndex(userDB.userTable.COLUMN_NAME)
                )
                nameList.add(userNama)
            } while (cursor.moveToNext())
        }
        return nameList
    }

    fun deleteData(nama: String){
        val db = this.writableDatabase
        val selection = "${userDB.userTable.COLUMN_NAME} = ?"
        val selectionArgs = arrayOf(nama)
        db.delete(userDB.userTable.TABLE_USER,selection,selectionArgs)
    }

}
