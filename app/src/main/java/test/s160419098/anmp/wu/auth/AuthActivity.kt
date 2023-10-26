package test.s160419098.anmp.wu.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import test.s160419098.anmp.wu.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportActionBar?.hide()
    }
}