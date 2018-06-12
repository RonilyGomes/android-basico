package macc.pdm.tsi.ifpb.edu.br.openuai

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class AddProjectActivity : AppCompatActivity() {

    private lateinit var etTitulo: EditText
    private lateinit var etDisciplina: EditText
    private lateinit var etDescricao: EditText
    private lateinit var btSalvar: Button
    private lateinit var btCancelar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_project)


        this.etTitulo = findViewById(R.id.etAddProjTitulo)
        this.etDisciplina = findViewById(R.id.etAddProjDisciplina)
        this.etDescricao = findViewById(R.id.etAddProjDescricao)
        this.btSalvar = findViewById(R.id.btAddProjSalvar)
        this.btCancelar = findViewById(R.id.btAddProjCancelar)

        this.btSalvar.setOnClickListener({ salvar(it) })
        this.btCancelar.setOnClickListener({ cancelar(it) })
    }

    fun salvar(view: View){
        val titulo = this.etTitulo.text.toString()
        val disciplina = this.etDisciplina.text.toString()
        val descricao = this.etDescricao.text.toString()
        val projeto = Projeto(titulo, disciplina, descricao)

        var it = Intent()
        it.putExtra("PROJETO", projeto)
        setResult(Activity.RESULT_OK, it)
        finish()
    }

    fun cancelar(view: View){
        finish()
    }
}
