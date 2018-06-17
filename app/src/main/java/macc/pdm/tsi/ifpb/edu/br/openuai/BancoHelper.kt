package macc.pdm.tsi.ifpb.edu.br.openuai

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val VERSAO = 1

class BancoHelper(context: Context?) :
        SQLiteOpenHelper(context, "projeto.sqlite", null, VERSAO) {

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "create table projeto (" +
                "id integer primary key autoincrement, " +
                "titulo string, " +
                "disciplina string, " +
                "descricao string " +
                ")"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, antes: Int, nova: Int) {

    }

}