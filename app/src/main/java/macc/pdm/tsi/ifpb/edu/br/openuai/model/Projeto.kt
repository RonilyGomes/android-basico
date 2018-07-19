package macc.pdm.tsi.ifpb.edu.br.openuai.model

import java.io.Serializable

class Projeto(var titulo: String, var disciplina: String, var descricao: String, var id: Int=-1):Serializable {
    override fun toString(): String {
        return this.titulo
    }
}