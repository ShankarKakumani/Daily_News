package pro.crazydude.scoopwhoop.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import pro.crazydude.scoopwhoop.R
import pro.crazydude.scoopwhoop.databinding.ActivityMainBinding
import pro.crazydude.scoopwhoop.fragment.HomeFragment
import pro.crazydude.scoopwhoop.fragment.MenuFragment
import pro.crazydude.scoopwhoop.fragment.ProfileFragment
import pro.crazydude.scoopwhoop.viewmodel.MainActivityViewModel


class MainActivity : AppCompatActivity(){


    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNav: BottomNavigationView


    private val homeFragment = HomeFragment()
    private val profileFragment = ProfileFragment()
    private val menuFragment = MenuFragment()

    private val fragmentManager = supportFragmentManager
    private var active: Fragment = homeFragment




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)


        bottomNavigation()

    }

    private fun bottomNavigation() {
        bottomNav = binding.navigation

        addFragmentsToFragmentManager()

        bottomNav.apply {
            selectedItemId = R.id.menu_home
            setOnNavigationItemSelectedListener {
                when (it.itemId) {

                    R.id.menu_home -> {
                        setBottomNavFragment(homeFragment)
                        true

                    }
                    R.id.menu_profile -> {
                        setBottomNavFragment(profileFragment)
                        true
                    }
                    R.id.menu_menu -> {
                        setBottomNavFragment(menuFragment)
                        true
                    }

                    else -> {
                        setBottomNavFragment(homeFragment)
                        true
                    }
                }
            }
        }

    }

    private fun addFragmentsToFragmentManager() {
        fragmentManager.beginTransaction()
            .add(R.id.frame_layout, homeFragment, "1").commit()

        fragmentManager.beginTransaction()
            .add(R.id.frame_layout, profileFragment, "2")
            .hide(profileFragment).commit()

        fragmentManager.beginTransaction()
            .add(R.id.frame_layout, menuFragment, "3")
            .hide(menuFragment).commit()

    }

    private fun setBottomNavFragment(fragment: Fragment) {
        if (active != fragment) {
            fragmentManager.beginTransaction().hide(active).show(fragment).commit()
            active = fragment
        }
    }


}