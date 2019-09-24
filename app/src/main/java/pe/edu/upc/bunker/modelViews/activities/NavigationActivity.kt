package pe.edu.upc.bunker.modelViews.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.modelViews.fragments.HomeFragment
import pe.edu.upc.bunker.modelViews.fragments.NotificationsFragment
import pe.edu.upc.bunker.modelViews.fragments.SettingsFragment

class NavigationActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        bottomNavigationView = findViewById(R.id.btm_nav_main)
        bottomNavigationView.setOnNavigationItemSelectedListener(bnvFragmentsItemsSelectListener)

        bottomNavigationView.selectedItemId = R.id.home_item_nav_bar
    }

    private val bnvFragmentsItemsSelectListener : BottomNavigationView.OnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        item ->
        return@OnNavigationItemSelectedListener navigateTo(item)
    }

    private fun navigateTo(item : MenuItem): Boolean {
        item.isChecked = true
        return supportFragmentManager
            .beginTransaction()
            .replace(R.id.flBlank, fragmentFor(item))
            .commit() > 0
    }

    private fun fragmentFor(item : MenuItem) : Fragment {
        when (item.itemId) {
            R.id.home_item_nav_bar -> {
                return HomeFragment()
            }
            R.id.list_item_nav_bar -> {
                return NotificationsFragment()
            }
            R.id.configuration_item_nav_bar -> {
                return SettingsFragment()
            }
        }
        return HomeFragment()
    }
}
