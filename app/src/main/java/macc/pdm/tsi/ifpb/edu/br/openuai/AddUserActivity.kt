package macc.pdm.tsi.ifpb.edu.br.openuai

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText

class AddUserActivity : AppCompatActivity() {

    private lateinit var etTitulo: EditText
    private lateinit var etDisciplina: EditText
    private lateinit var etDescricao: EditText
    private lateinit var btSalvar: Button
    private lateinit var btCancelar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        this.etTitulo = findViewById(R.id.etAddProjTitulo)
        this.etDisciplina = findViewById(R.id.etAddProjDisciplina)
        this.etDescricao = findViewById(R.id.etAddProjDescricao)
        this.btSalvar = findViewById(R.id.btAddProjSalvar)
        this.btCancelar = findViewById(R.id.btAddProjCancelar)

        this.btSalvar.setOnClickListener({ salvar(it) })
        this.btCancelar.setOnClickListener({ cancelar(it) })

        var projeto = intent.getSerializableExtra("PROJETO")
        if (projeto != null){
            projeto = projeto as Projeto

            this.etTitulo.setText(projeto.titulo)
            this.etDisciplina.setText(projeto.disciplina.toString())
            this.etDescricao.setText(projeto.descricao.toString())
        }
    }

    fun salvar(view: View){
        val titulo = this.etTitulo.text.toString()
        val disciplina = this.etDisciplina.text.toString()
        val descricao = this.etDescricao.text.toString()
        var projeto = intent.getSerializableExtra("PROJETO")

        if (projeto == null){
            Log.e("PROJETO", "novo projeto")
            projeto = Projeto(titulo, disciplina, descricao)
        }else{
            Log.e("PROJETO", "atualizando projeto")
            projeto = projeto as Projeto
            projeto.titulo = titulo
            projeto.disciplina = disciplina
            projeto.descricao = descricao
        }

        var it = Intent()
        it.putExtra("PROJETO", projeto)
        setResult(Activity.RESULT_OK, it)
        finish()
    }

    fun cancelar(view: View){
        finish()
    }
}
