package pe.edu.upc.bunker.modelViews.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.modelViews.CreateSpaceStepTwoActivity

class HomeFragment : Fragment() {

    private lateinit var addSpaceButton : FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view  = inflater.inflate(R.layout.fragment_home,container,false)

        addSpaceButton = view.findViewById(R.id.fab_add_space)
        goToAddSpace(view)

        return view
    }

    private fun goToAddSpace(view : View) {
        addSpaceButton.setOnClickListener {
            view.context.startActivity(Intent(view.context, CreateSpaceStepTwoActivity::class.java))
        }
    }


}
