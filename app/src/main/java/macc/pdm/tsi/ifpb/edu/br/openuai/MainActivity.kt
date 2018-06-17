package macc.pdm.tsi.ifpb.edu.br.openuai

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val INSERT = 1
    val UPDATE = 2

    private lateinit var dao: ProjetoDAO
    private lateinit var lvProjetos: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(toolbar)

        this.dao = ProjetoDAO(this)

        fab.setOnClickListener { view ->
            val it = Intent(this, AddProjectActivity::class.java)
            startActivityForResult(it, INSERT)
        }

        this.lvProjetos = findViewById(R.id.lvMainProjetos)
        this.adapter()

        this.lvProjetos.setOnItemClickListener(OnClick()) // UPDATE
        this.lvProjetos.setOnItemLongClickListener(OnLongClick()) // REMOVE
    }

    fun adapter(){
        this.lvProjetos.adapter = ArrayAdapter<Projeto>(this,
                android.R.layout.simple_list_item_1,
                this.dao.select())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            val projeto = data?.getSerializableExtra("PROJETO") as Projeto
            if (requestCode == INSERT){
                this.dao.insert(projeto)
            }
            else if (requestCode == UPDATE){
                this.dao.update(projeto)
            }
            Log.i("PESSOA", "${projeto.id} - ${projeto.titulo} - " +
                    "${projeto.disciplina} - ${projeto.descricao}")
            this.adapter()
        }
    }

    inner class OnClick: AdapterView.OnItemClickListener{
        override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            if (p0 != null) {
                var p = p0.adapter.getItem(p2) as Projeto
                var it = Intent(this@MainActivity, AddProjectActivity::class.java)
                it.putExtra("PROJETO", p)
                startActivityForResult(it, UPDATE)
            }
        }
    }

    inner class OnLongClick : AdapterView.OnItemLongClickListener{
        override fun onItemLongClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long): Boolean {
            if (p0 != null) {
                var p = p0.adapter.getItem(p2) as Projeto
                dao.delete(p)
                adapter()
            }
            return true
        }
    }
}
