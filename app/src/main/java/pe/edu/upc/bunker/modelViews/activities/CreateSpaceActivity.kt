package pe.edu.upc.bunker.modelViews.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.modelViews.fragments.CreateSpaceStepTwoFragment2

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
        return CreateSpaceStepTwoFragment2()
    }
}
