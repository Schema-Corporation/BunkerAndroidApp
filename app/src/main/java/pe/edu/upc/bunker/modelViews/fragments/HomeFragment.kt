package pe.edu.upc.bunker.modelViews.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.dto.SpaceDTO
import pe.edu.upc.bunker.modelViews.activities.CreateSpaceStepOneActivity
import pe.edu.upc.bunker.modelViews.adapters.SpacesAdapter
import pe.edu.upc.bunker.modelViews.adapters.SpacesViewHolder
import pe.edu.upc.bunker.models.Location
import pe.edu.upc.bunker.repository.LocationRepository
import pe.edu.upc.bunker.repository.RetrofitClientInstance
import pe.edu.upc.bunker.repository.SpacesRepository
import retrofit2.Callback

class HomeFragment : Fragment() {

    private lateinit var addSpaceButton : FloatingActionButton

    private lateinit var spacesRecyclerView: RecyclerView
    private lateinit var spacesAdapter: RecyclerView.Adapter<*>
    private lateinit var spacesViewHolder: RecyclerView.ViewHolder

    val spacesRepo = RetrofitClientInstance().getRetrofitInstance().create(SpacesRepository::class.java)
    val locationRepo = RetrofitClientInstance().getRetrofitInstance().create(LocationRepository::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view  = inflater.inflate(R.layout.fragment_home,container,false)

        addSpaceButton = view.findViewById(R.id.fab_add_space)

        spacesRecyclerView = view.findViewById(R.id.spaces_recycler_view)

        //spacesRepo.getSpacesByLessorId()

        //spacesAdapter = SpacesAdapter<SpaceDTO>()

        /*locationRepo.getLocationBySpaceId(spaceId, token).enqueue(object : Callback<Location> {
            override fun onFailure(call: Call<Location>, t: Throwable) {
                Toast.makeText(holder.itemView.context,"Get Failed",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Location>, response: Response<Location>) {
                holder.spaceAddressTextView.text = response.body()?.address
            }
        })
        */


        goToAddSpace(view)

        return view
    }

    private fun goToAddSpace(view : View) {
        addSpaceButton.setOnClickListener {
            view.context.startActivity(Intent(view.context, CreateSpaceStepOneActivity::class.java))
        }
    }

    /*

     */


}
