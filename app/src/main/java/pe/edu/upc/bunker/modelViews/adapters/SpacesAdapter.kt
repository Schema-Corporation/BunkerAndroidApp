package pe.edu.upc.bunker.modelViews.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.dto.SpaceDTO
import pe.edu.upc.bunker.modelViews.activities.SpaceDetailsActivity
import pe.edu.upc.bunker.models.Location
import pe.edu.upc.bunker.repository.LocationRepository
import pe.edu.upc.bunker.repository.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpacesAdapter (var list: List<SpaceDTO>): RecyclerView.Adapter<SpacesViewHolder>(){

    private val locationRepo = RetrofitClientInstance().getRetrofitInstance().create(LocationRepository::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpacesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.spaces_recycler_view_view_holder, parent, false)
        return SpacesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SpacesViewHolder, position: Int) {
        holder.spaceTitleTextView.text = list[position].title

        holder.spaceDetailsButton.setOnClickListener {
            val context = it.context
            context.startActivity(Intent(context, SpaceDetailsActivity::class.java))
        }

        val spaceId = list[position].id

        val sharedPreferences = holder.itemView.context.getSharedPreferences("bunkerPreferences",Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("Token","Test")



    }
}