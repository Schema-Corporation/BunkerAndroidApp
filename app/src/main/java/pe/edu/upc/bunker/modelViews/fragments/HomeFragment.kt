package pe.edu.upc.bunker.modelViews.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.dto.SpaceInfoDTO
import pe.edu.upc.bunker.modelViews.activities.CreateSpaceStepOneActivity
import pe.edu.upc.bunker.modelViews.adapters.SpacesAdapter
import pe.edu.upc.bunker.repository.RetrofitClientInstance
import pe.edu.upc.bunker.repository.SpacesRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var addSpaceButton : FloatingActionButton

    private lateinit var listSpacesInfo : List<SpaceInfoDTO>

    private lateinit var spacesRecyclerView: RecyclerView

    private lateinit var spacesAdapter: RecyclerView.Adapter<*>
    private lateinit var spacesLayoutManager: RecyclerView.LayoutManager

    private val spacesRepo = RetrofitClientInstance().getRetrofitInstance().create(SpacesRepository::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view  = inflater.inflate(R.layout.fragment_home,container,false)

        addSpaceButton = view.findViewById(R.id.fab_add_space)

        spacesRecyclerView = view.findViewById(R.id.spaces_recycler_view)

        spacesLayoutManager = LinearLayoutManager(this.context)

        getSpacesInfo()

        goToAddSpace(view)

        return view
    }

    private fun goToAddSpace(view : View) {
        addSpaceButton.setOnClickListener {
            view.context.startActivity(Intent(view.context, CreateSpaceStepOneActivity::class.java))
        }
    }

    private fun getSpacesInfo() {
        val sharedPreferences = this.activity?.getSharedPreferences("Login", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("Token", "")
        val lessorId = 1

        val bearerToken = "Bearer $token"

        spacesRepo.getSpacesByLessorId(lessorId, bearerToken)
            .enqueue(object : Callback<List<SpaceInfoDTO>> {
            override fun onFailure(call: Call<List<SpaceInfoDTO>>, t: Throwable) {
                Toast.makeText(activity, "Log FAILED", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<SpaceInfoDTO>>, response: Response<List<SpaceInfoDTO>>) {
                val body = response.body()
                if(body != null) {
                    if (body.isNotEmpty()) {
                        listSpacesInfo = body

                        spacesAdapter = SpacesAdapter(listSpacesInfo)

                        spacesRecyclerView.adapter = spacesAdapter
                        spacesRecyclerView.layoutManager = spacesLayoutManager
                    }
                }else{
                    Log.d("Networking",call.request().url().toString())
                }
            }

        })
    }


}
