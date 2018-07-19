package macc.pdm.tsi.ifpb.edu.br.openuai.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class DisplayReceiver : BroadcastReceiver{

    constructor() {}

    override fun onReceive(p0: Context?, p1: Intent?) {
        Toast.makeText(p0, "Esse projeto vale 100!", Toast.LENGTH_SHORT).show()
    }
}