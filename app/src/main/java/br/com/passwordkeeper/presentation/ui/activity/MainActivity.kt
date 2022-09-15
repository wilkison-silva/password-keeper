package br.com.passwordkeeper.presentation.ui.activity

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.ActivityMainBinding
import br.com.passwordkeeper.domain.result.viewmodelstate.BottomNavigationState
import br.com.passwordkeeper.presentation.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        findNavController(R.id.main_activity_host)
    }

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeBottomNavigationState()
        setupNavControllerListerner()
        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigation.setOnItemReselectedListener {
            return@setOnItemReselectedListener
        }
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> navController.popBackStack(R.id.homeFragment, false)
                R.id.fragmentCreateNewCard -> navController.navigate(R.id.fragmentCreateNewCard)
            }
            true
        }
    }

    private fun setupNavControllerListerner() {
        navController.addOnDestinationChangedListener { navController: NavController, navDestination: NavDestination, bundle: Bundle? ->
            when (navDestination.id) {
                R.id.homeFragment -> {
                    binding.bottomNavigation.menu.findItem(R.id.homeFragment).isChecked = true
                }
                R.id.fragmentCreateNewCard -> {
                    binding.bottomNavigation.menu.findItem(R.id.fragmentCreateNewCard).isChecked =
                        true
                }
            }
        }
    }

    private fun observeBottomNavigationState() {
        mainViewModel.bottomNavigationState.observe(this) {
            when (it) {
                is BottomNavigationState.Hide -> {
                    binding.bottomNavigation.visibility = GONE
                }
                is BottomNavigationState.Show -> {
                    binding.bottomNavigation.visibility = VISIBLE
                }
            }
        }
    }
}