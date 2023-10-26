package test.s160419098.anmp.wu

import android.icu.text.NumberFormat
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Locale

object Global {
    val numberFormat: NumberFormat =
        NumberFormat.getCurrencyInstance(Locale("id-ID")).apply {
            maximumFractionDigits = 0
        }
}

data class TextInput(
    val layout: TextInputLayout, val editText: TextInputEditText
) {
    constructor(root: View, @IdRes layoutId: Int, @IdRes editTextId: Int) :
            this(root[layoutId], root[editTextId])
}

fun Float.toCurrency(): String {
    return Global.numberFormat.format(this)
}

fun Int.toCurrency(): String {
    return Global.numberFormat.format(this)
}

fun String.loremFlickr(size: Int): String {
    return "https://loremflickr.com/$size/$size/${this.replace(" ", "_")}"
}

inline fun <reified T> String.parseAsJson(): T {
    return Gson().fromJson(this, object : TypeToken<T>() {}.type)
}

fun TextInput.requireInput(): String? {
    return editText.text.toString().trim()
        .ifEmpty { null }
        .also { layout.setError(if (it == null) R.string.field_required else null) }
}

fun TextInputLayout.setError(@StringRes resId: Int?) {
    this.error = if (resId == null) null else context.getString(resId)
}

operator fun <T : View> View.get(@IdRes id: Int): T = this.findViewById(id)
