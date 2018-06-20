package macc.pdm.tsi.ifpb.edu.br.openuai

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val INSERT = 1
    val UPDATE = 2
    val LOGIN  = 3
    val DELETE = 4

    private lateinit var dao: ProjetoDAO
    private lateinit var lvProjetos: ListView
    private lateinit var session: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.dao = ProjetoDAO(this)
        this.session = Session(this)
        session.logout()

        fab.setOnClickListener { view ->
            if(session.hasLogin()) {
                val it = Intent(this, AddProjectActivity::class.java)
                startActivityForResult(it, INSERT)
            }
            else {
                val it = Intent(this, LoginActivity::class.java)
                startActivityForResult(it, LOGIN)
            }
        }

        this.lvProjetos = findViewById(R.id.lvMainProjetos)
        this.adapter()
    }

    fun adapter(){
        this.lvProjetos.adapter = MyAdapter(this,
                android.R.layout.simple_list_item_1, this.dao.select())
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
            if(requestCode == INSERT || requestCode == UPDATE || requestCode == DELETE) {
                val projeto = data?.getSerializableExtra("PROJETO") as Projeto

                if(requestCode == INSERT) {
                    this.dao.insert(projeto)
                }
                else if(requestCode == UPDATE) {
                    this.dao.update(projeto)
                }
                else {
                    this.dao.delete(projeto)
                }
            }
            else if(requestCode == LOGIN) {
                val it = Intent(this, AddProjectActivity::class.java)
                startActivityForResult(it, INSERT)
            }
            this.adapter()
        }
    }
}
