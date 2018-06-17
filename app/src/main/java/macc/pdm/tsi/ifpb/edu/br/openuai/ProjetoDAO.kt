package macc.pdm.tsi.ifpb.edu.br.openuai

import android.content.ContentValues
import android.content.Context

/**
 * Created by ifpb on 18/05/18.
 */

class ProjetoDAO (var context: Context){
    private lateinit var banco: BancoHelper

    init {
        this.banco = BancoHelper(context)
    }

    fun insert(projeto: Projeto){
        val cv = ContentValues()
        cv.put("titulo", projeto.titulo)
        cv.put("disciplina", projeto.disciplina)
        cv.put("descricao", projeto.descricao)
        this.banco.writableDatabase.insert("projeto", null, cv)
    }

    fun select() : ArrayList<Projeto>{
        val lista = ArrayList<Projeto>()
        val colunas = arrayOf("id", "titulo", "disciplina", "descricao")
        val cursor = this.banco.readableDatabase.query("projeto", colunas, null, null, null, null, null)

        cursor.moveToFirst()

        if (cursor.count > 0){
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val titulo = cursor.getString(cursor.getColumnIndex("titulo"))
                val disciplina = cursor.getString(cursor.getColumnIndex("disciplina"))
                val descricao = cursor.getString(cursor.getColumnIndex("descricao"))

                lista.add(Projeto(titulo, disciplina, descricao, id))
            }while(cursor.moveToNext())
        }

        return lista
    }

    fun count(): Int{
        val colunas = arrayOf("id")
        val cursor = this.banco.readableDatabase.query("projeto", colunas, null, null, null, null, null)

        return cursor.count
    }

    fun delete(p: Projeto){
        this.banco.writableDatabase.delete("projeto", "id = ?", arrayOf(p.id.toString()))
    }

    fun update(p: Projeto){
        val cv = ContentValues()
        cv.put("titulo", p.titulo)
        cv.put("disciplina", p.disciplina)
        cv.put("descricao", p.descricao)

        this.banco.writableDatabase.update("projeto", cv, "id = ?", arrayOf(p.id.toString()))
    }
}