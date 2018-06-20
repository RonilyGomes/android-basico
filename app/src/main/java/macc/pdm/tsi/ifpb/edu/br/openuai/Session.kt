package macc.pdm.tsi.ifpb.edu.br.openuai

import android.content.Context
import android.preference.PreferenceManager
import android.content.SharedPreferences

class Session(cntx: Context) {

    private val prefs: SharedPreferences

    init {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx)
    }

    fun next(next: String?) {
        prefs.edit().putString("next", next).commit()
    }

    fun nextClear() {
        next(null)
    }

    fun getNext(): String {
        return prefs.getString("next", "")
    }

    fun hasNext(): Boolean {
        return !getNext().isEmpty()
    }

    fun login(user: String?) {
        prefs.edit().putString("login", user).commit()
    }

    fun getLogin(): String {
        return prefs.getString("login", "")
    }

    fun hasLogin(): Boolean {
        return getLogin().isNotEmpty()
    }

    fun logout() {
        this.login(null)
    }
}