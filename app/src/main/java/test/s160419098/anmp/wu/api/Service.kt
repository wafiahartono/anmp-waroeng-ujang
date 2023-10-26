package test.s160419098.anmp.wu.api

import android.content.Context
import androidx.annotation.RawRes
import androidx.core.content.edit
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.coroutines.delay
import test.s160419098.anmp.wu.R
import test.s160419098.anmp.wu.auth.User
import test.s160419098.anmp.wu.menu.Menu
import test.s160419098.anmp.wu.menu.MenuCategory
import test.s160419098.anmp.wu.parseAsJson
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class Service(
    private val context: Context,
) {
    companion object {
        private const val VOLLEY_TAG = "api"
        private const val SIMULATION_DELAY = 1000L

        private const val SHARED_PREF_AUTH = "auth"
        private const val SP_KEY_USER = "user"
    }

    private val requestQueue = Volley.newRequestQueue(context)

    suspend fun getRemote(uri: String): String = suspendCoroutine { continuation ->
        requestQueue.add(
            StringRequest(
                Request.Method.GET, uri,
                { response -> continuation.resume(response) },
                { e -> continuation.resumeWithException(e) },
            ).apply {
                tag = VOLLEY_TAG
            }
        )
    }

    private fun getLocal(@RawRes id: Int): String {
        return context.resources.openRawResource(id).bufferedReader().use { it.readText() }
    }

    private val authSharedPrefs
        get() = context.getSharedPreferences(SHARED_PREF_AUTH, Context.MODE_PRIVATE)

    fun getSignedInUser(): User? = authSharedPrefs.let {
        return@let it.getString(SP_KEY_USER, null)?.parseAsJson()
    }

    suspend fun signIn(username: String, password: String): User? {
        delay(SIMULATION_DELAY)

        return getLocal(R.raw.users)
            .parseAsJson<List<User>>()
            .firstOrNull { it.username == username && it.password == password }
            ?.also {
                authSharedPrefs.edit { putString(SP_KEY_USER, Gson().toJson(it)) }
            }
    }

    fun signOut() {
        authSharedPrefs.edit { remove(SP_KEY_USER) }
    }

    private var menuCategoriesCache: List<MenuCategory>? = null

    suspend fun getMenuCategories(): List<MenuCategory> {
        delay(SIMULATION_DELAY)

        return menuCategoriesCache
            ?: getLocal(R.raw.menu_category).parseAsJson<List<MenuCategory>>().also {
                menuCategoriesCache = it
            }
    }

    private var menuCache: List<Menu>? = null

    suspend fun getMenu(id: Int): Menu {
        delay(SIMULATION_DELAY)

        return (menuCache
            ?: getLocal(R.raw.menu).parseAsJson<List<Menu>>().also {
                menuCache = it
            })
            .first { it.id == id }
    }

    fun dispose() {
        requestQueue.cancelAll(VOLLEY_TAG)
    }
}