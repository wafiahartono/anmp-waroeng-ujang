package test.s160419098.anmp.wu.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import test.s160419098.anmp.wu.R
import test.s160419098.anmp.wu.auth.AuthActivity
import test.s160419098.anmp.wu.auth.AuthViewModel

class MainActivity : AppCompatActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if ((application as Application).service.getSignedInUser() == null) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment

        navController = navHostFragment.navController
        drawerLayout = findViewById(R.id.drawer_layout)

        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation_view)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(navigationView, navController)
        bottomNavigationView.setupWithNavController(navController)

        navigationView.setNavigationItemSelectedListener {
            if (it.itemId == R.id.menu_sign_out) {
                authViewModel.signOut()
            } else {
                NavigationUI.onNavDestinationSelected(it, navController)
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }

        authViewModel.user.observe(this) {
            if (it != null) return@observe
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout) || super.onSupportNavigateUp()
    }
}