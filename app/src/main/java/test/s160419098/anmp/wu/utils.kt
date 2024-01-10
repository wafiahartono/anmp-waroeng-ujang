package test.s160419098.anmp.wu

import android.icu.text.NumberFormat
import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Locale

object Global {
    val numberFormat: NumberFormat =
        NumberFormat.getCurrencyInstance(Locale("id-ID")).apply {
            maximumFractionDigits = 0
        }
}

fun toCurrency(float: Float?): String {
    if (float == null) return ""
    return Global.numberFormat.format(float)
}

fun TextInputLayout.requireInput(trim: Boolean = true) =
    findViewWithTag<TextInputEditText>("edittext").text
        .toString().let { if (trim) it.trim() else it }
        .ifEmpty { null }
        .also { setError(if (it == null) R.string.field_required else null) }

fun TextInputLayout.setError(@StringRes resId: Int?) {
    this.error = if (resId == null) null else context.getString(resId)
}
