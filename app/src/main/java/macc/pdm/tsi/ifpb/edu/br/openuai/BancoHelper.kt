package macc.pdm.tsi.ifpb.edu.br.openuai

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val VERSAO = 1

class BancoHelper(context: Context?) :
        SQLiteOpenHelper(context, "openuai.sqlite", null, VERSAO) {

    override fun onCreate(db: SQLiteDatabase?) {
        val sql_user = "create table user (" +
                "id integer primary key autoincrement, " +
                "email string, " +
                "password string " +
                ")"
        val sql_project = "create table projeto (" +
                "id integer primary key autoincrement, " +
                "titulo string, " +
                "disciplina string, " +
                "descricao string " +
                ")"
        db?.execSQL(sql_user)
        db?.execSQL(sql_project)
    }

    override fun onUpgrade(db: SQLiteDatabase?, antes: Int, nova: Int) {

    }

}