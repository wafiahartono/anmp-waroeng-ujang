package test.s160419098.anmp.wu.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import test.s160419098.anmp.wu.R
import test.s160419098.anmp.wu.databinding.ActivityMainBinding
import test.s160419098.anmp.wu.session.AuthActivity
import test.s160419098.anmp.wu.session.SessionViewModel

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val session: SessionViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!session.isAuthenticated) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
            return
        }

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment

        navController = navHostFragment.navController

        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.navigationView, navController)

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.navigationView.setNavigationItemSelectedListener {
            if (it.itemId == R.id.menu_sign_out) {
                session.signOut()

                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            } else {
                NavigationUI.onNavDestinationSelected(it, navController)
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)

            return@setNavigationItemSelectedListener true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.drawerLayout) ||
                super.onSupportNavigateUp()
    }
}
