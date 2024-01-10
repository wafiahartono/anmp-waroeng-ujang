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

    private val navHostFragment
        get() = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment

    private val navController: NavController
        get() = navHostFragment.navController

    private val session: SessionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var initialized = false

        session.user.observe(this) { user ->
            if (initialized) return@observe

            if (user == null) {
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            } else {
                initializeUI()
            }

            initialized = true
        }
    }

    override fun onSupportNavigateUp() =
        NavigationUI.navigateUp(navController, binding.drawerLayout) || super.onSupportNavigateUp()

    private fun initializeUI() {
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}
