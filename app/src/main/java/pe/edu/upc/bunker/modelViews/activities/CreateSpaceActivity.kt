package pe.edu.upc.bunker.modelViews.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.modelViews.fragments.CreateSpaceStepOneFragment
import pe.edu.upc.bunker.modelViews.fragments.HomeFragment
import pe.edu.upc.bunker.modelViews.fragments.NotificationsFragment
import pe.edu.upc.bunker.modelViews.fragments.SettingsFragment

class CreateSpaceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_space)

        setStep()
    }

    private fun setStep(): Boolean {
        return supportFragmentManager
            .beginTransaction()
            .replace(R.id.flCreateSpace, fragmentFor())
            .commit() > 0
    }

    private fun fragmentFor() : Fragment {
        return CreateSpaceStepOneFragment()
    }
}
