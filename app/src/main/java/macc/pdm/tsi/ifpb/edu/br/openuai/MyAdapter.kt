package macc.pdm.tsi.ifpb.edu.br.openuai

import android.content.Context
import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.app.Activity

open class MyAdapter(context: Context, resource: Int, list: ArrayList<Projeto>) :
        ArrayAdapter<Projeto>(context, resource, list) {

    private var dao = ProjetoDAO(context)
    var resource: Int
    var list: ArrayList<Projeto>
    var vi: LayoutInflater
    val UPDATE = 2
    val DELETE = 3
    private lateinit var session: Session

    init {
        this.resource = resource
        this.list = list
        this.vi = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.session = Session(context)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var view = convertView
        if(convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_view_content, null)
        }
        val listItemText = view?.findViewById<TextView>(R.id.list_item_string)
        listItemText?.setText(list[position].titulo)

        val deleteBtn = view?.findViewById<FloatingActionButton>(R.id.delete_btn)
        val editBtn = view?.findViewById<FloatingActionButton>(R.id.edit_btn)

        deleteBtn?.setOnClickListener(View.OnClickListener {
            val it = Intent(context, LoginActivity::class.java)
            it.putExtra("PROJETO", list[position])
            if(session.hasLogin()) {
                (context as Activity).startActivityForResult(it, DELETE)
            }
            else {
                session.next("" + MainActivity::class.java + ":DELETE")
                (context as Activity).startActivityForResult(it, DELETE)
            }
        })
        editBtn?.setOnClickListener(View.OnClickListener {
            if(session.hasLogin()) {
                val it = Intent(context, AddProjectActivity::class.java)
                it.putExtra("PROJETO", list[position])
                (context as Activity).startActivityForResult(it, UPDATE)
            }
            else {
                session.next("" + AddProjectActivity::class.java + ":UPDATE")
                val it = Intent(context, LoginActivity::class.java)
                it.putExtra("PROJETO", list[position])
                (context as Activity).startActivityForResult(it, UPDATE)
            }
        })

        return view
    }
}