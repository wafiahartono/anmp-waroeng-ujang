package test.s160419098.anmp.wu

import android.os.SystemClock
import android.text.Editable
import android.widget.Chronometer
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import coil.load
import test.s160419098.anmp.wu.data.Waiter.Sex
import java.util.Date

@BindingAdapter("android:base")
fun bindChronometerBase(chronometer: Chronometer, date: Date?) {
    if (date == null) {
        chronometer.stop()
    } else {
        chronometer.base = date.time - (System.currentTimeMillis() - SystemClock.elapsedRealtime())
        chronometer.start()
    }
}

fun interface AfterTextChanged {
    fun afterTextChanged(s: Editable?)
}

@BindingAdapter("android:afterTextChanged")
fun setAfterTextChangedAction(editText: EditText, action: AfterTextChanged) {
    editText.doAfterTextChanged(action::afterTextChanged)
}

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
fun bindSex(textView: TextView, sex: Sex?) {
    if (sex == null) return
    textView.setText(
        when (sex) {
            Sex.MALE -> R.string.waiter
            Sex.FEMALE -> R.string.waitress
        }
    )
}
