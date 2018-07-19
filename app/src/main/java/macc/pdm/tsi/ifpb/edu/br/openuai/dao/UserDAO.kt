package macc.pdm.tsi.ifpb.edu.br.openuai.dao

import android.content.ContentValues
import android.content.Context
import macc.pdm.tsi.ifpb.edu.br.openuai.utils.BancoHelper
import macc.pdm.tsi.ifpb.edu.br.openuai.model.User

/**
 * Created by ifpb on 18/05/18.
 */

class UserDAO (var context: Context){
    private lateinit var banco: BancoHelper

    init {
        this.banco = BancoHelper(context)
    }

    fun insert(user: User){
        val cv = ContentValues()
        cv.put("email", user.email)
        cv.put("password", user.password)
        this.banco.writableDatabase.insert("user", null, cv)
    }

    fun select() : ArrayList<String>{
        val lista = ArrayList<String>()
        val colunas = arrayOf("id", "email", "password")
        val cursor = this.banco.readableDatabase.query("user", colunas, null, null, null, null, null)

        cursor.moveToFirst()

        if (cursor.count > 0){
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val email = cursor.getString(cursor.getColumnIndex("email"))
                val password = cursor.getString(cursor.getColumnIndex("password"))
                val user = User(email, password, id)

                lista.add(user.toString())
            }while(cursor.moveToNext())
        }

        return lista
    }

    fun count(): Int{
        val colunas = arrayOf("id")
        val cursor = this.banco.readableDatabase.query("user", colunas, null, null, null, null, null)

        return cursor.count
    }
}