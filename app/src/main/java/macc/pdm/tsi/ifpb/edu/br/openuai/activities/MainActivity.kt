package macc.pdm.tsi.ifpb.edu.br.openuai.activities

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import macc.pdm.tsi.ifpb.edu.br.openuai.utils.MyAdapter
import macc.pdm.tsi.ifpb.edu.br.openuai.R
import macc.pdm.tsi.ifpb.edu.br.openuai.utils.Session
import macc.pdm.tsi.ifpb.edu.br.openuai.dao.ProjetoDAO
import macc.pdm.tsi.ifpb.edu.br.openuai.model.Projeto
import macc.pdm.tsi.ifpb.edu.br.openuai.utils.DisplayReceiver

class MainActivity : AppCompatActivity() {
    // Constantes de ação
    val INSERT = 1
    val UPDATE = 2
    val DELETE = 3
    val LOGIN  = 4
    val LOGIN_MENU = 5

    // Constantes de menu
    val MENU_LOGIN_LOGOUT = Menu.FIRST

    private lateinit var dao: ProjetoDAO
    private lateinit var lvProjetos: ListView
    private lateinit var session: Session
    private lateinit var displayR: DisplayReceiver
    private lateinit var itf: IntentFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.dao = ProjetoDAO(this)
        this.session = Session(this)
        this.displayR = DisplayReceiver()
        this.itf = IntentFilter()
        this.itf.addAction(Intent.ACTION_USER_PRESENT)

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

    override fun onResume() {
        super.onResume()
        registerReceiver(this.displayR, this.itf)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(this.displayR)
    }

    fun adapter(){
        this.lvProjetos.adapter = MyAdapter(this,
                android.R.layout.simple_list_item_1, this.dao.select())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.clear()
        if (session.hasLogin())
            menu.add(0, MENU_LOGIN_LOGOUT, Menu.NONE, R.string.logout)
        else{
            menu.add(0, MENU_LOGIN_LOGOUT, Menu.NONE, R.string.action_sign_in)
        }
        return super.onPrepareOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            MENU_LOGIN_LOGOUT  -> {
                if(session.hasLogin()) {
                    session.logout()
                    Toast.makeText(this, R.string.toast_logout, Toast.LENGTH_SHORT).show()
                }
                else{
                    val it = Intent(this, LoginActivity::class.java)
                    it.putExtra("MENU_LOGIN", true)
                    startActivityForResult(it, LOGIN_MENU)
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            if(requestCode == INSERT || requestCode == UPDATE || requestCode == DELETE) {
                val projeto = data?.getSerializableExtra("PROJETO") as Projeto

                if(requestCode == INSERT) {
                    this.dao.insert(projeto)
                    Toast.makeText(this, R.string.toast_adicionado, Toast.LENGTH_SHORT).show()
                }
                else if(requestCode == UPDATE) {
                    this.dao.update(projeto)
                    Toast.makeText(this, R.string.toast_alterado, Toast.LENGTH_SHORT).show()
                }
                else{
                    this.dao.delete(projeto)
                    Toast.makeText(this, R.string.toast_removido, Toast.LENGTH_SHORT).show()
                }
            }
            else if(requestCode == LOGIN) {
                val it = Intent(this, AddProjectActivity::class.java)
                startActivityForResult(it, INSERT)
            }
            else if(requestCode == LOGIN_MENU) {
                val it = Intent(this, MainActivity::class.java)
                startActivity(it)
            }
            this.adapter()
        }
    }
}