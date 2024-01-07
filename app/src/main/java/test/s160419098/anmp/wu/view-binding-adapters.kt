package test.s160419098.anmp.wu

import android.text.Editable
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import coil.load
import test.s160419098.anmp.wu.data.Sex

@BindingAdapter("android:imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    if (url == null) return
    imageView.load(url)
}

@BindingAdapter("android:loremFlickr")
fun loadLoremFlickr(imageView: ImageView, query: String?) {
    if (query == null) return
    imageView.load("https://loremflickr.com/800/800/${query.replace(" ", "_")}")
}

@BindingAdapter("android:text")
fun bindSex(textView: TextView, sex: Sex) {
    textView.setText(
        when (sex) {
            Sex.MALE -> R.string.waiter
            Sex.FEMALE -> R.string.waitress
        }
    )
}

fun interface AfterTextChanged {
    fun afterTextChanged(s: Editable?)
}

@BindingAdapter("android:afterTextChanged")
fun setAfterTextChangedAction(editText: EditText, action: AfterTextChanged) {
    editText.doAfterTextChanged(action::afterTextChanged)
}
