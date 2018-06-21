package macc.pdm.tsi.ifpb.edu.br.openuai

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class AddProjectActivity : AppCompatActivity() {
    private lateinit var etHeader: TextView
    private lateinit var etTitulo: EditText
    private lateinit var spnDisciplina: Spinner
    private lateinit var etDescricao: EditText
    private lateinit var btSalvar: Button
    private lateinit var btCancelar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_project)

        this.etHeader = findViewById(R.id.etHeader)
        this.etTitulo = findViewById(R.id.etAddProjTitulo)
        this.spnDisciplina = findViewById(R.id.spnAddProjDisciplina)
        this.etDescricao = findViewById(R.id.etAddProjDescricao)
        this.btSalvar = findViewById(R.id.btAddProjSalvar)
        this.btCancelar = findViewById(R.id.btAddProjCancelar)

        this.btSalvar.setOnClickListener({ salvar() })
        this.btCancelar.setOnClickListener({ cancelar() })

        var projeto = intent.getSerializableExtra("PROJETO")
        if (projeto != null){
            etHeader.text = getString(R.string.alt_proj)
            projeto = projeto as Projeto

            this.etTitulo.setText(projeto.titulo)

            val myAdap = this.spnDisciplina.getAdapter() as ArrayAdapter<String>
            val spinnerPosition = myAdap.getPosition(projeto.disciplina)
            this.spnDisciplina.setSelection(spinnerPosition)
            this.etDescricao.setText(projeto.descricao.toString())

        }
    }

    fun salvar(){
        val titulo = this.etTitulo.text.toString()
        val disciplina = this.spnDisciplina.selectedItem.toString()
        val descricao = this.etDescricao.text.toString()
        var projeto = intent.getSerializableExtra("PROJETO")

        if (projeto == null) {
            projeto = Projeto(titulo, disciplina, descricao)
        }
        else{
            projeto = projeto as Projeto
            projeto.titulo = titulo
            projeto.disciplina = disciplina
            projeto.descricao = descricao
        }

        val it = Intent()
        it.putExtra("PROJETO", projeto)
        setResult(Activity.RESULT_OK, it)
        finish()
    }

    fun cancelar(){
        finish()
    }
}
