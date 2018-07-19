package macc.pdm.tsi.ifpb.edu.br.openuai.model

import java.io.Serializable

class User(var email: String, var password: String, var id: Int=-1):Serializable {
    override fun toString(): String {
        return (this.email + ":" + this.password)
    }
}