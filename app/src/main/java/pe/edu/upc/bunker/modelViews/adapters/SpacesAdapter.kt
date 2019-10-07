package pe.edu.upc.bunker.modelViews.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.dto.SpaceDTO
import pe.edu.upc.bunker.modelViews.activities.SpaceDetailsActivity

class SpacesAdapter (var list: List<SpaceDTO>): RecyclerView.Adapter<SpacesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpacesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.spaces_recycler_view_view_holder, parent, false)
        return SpacesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SpacesViewHolder, position: Int) {
        holder.spaceTitleTextView.text = list[position].title
        holder.spaceAddressTextView.text = list[position].address
        Picasso.get().load(list[position].firstPhoto).into(holder.spacePhotoImageView)

        val spaceId = list[position].id

        holder.spaceDetailsButton.setOnClickListener {
            val context = it.context
            context.startActivity(Intent(context, SpaceDetailsActivity::class.java))
        }

        val sharedPreferences = holder.itemView.context.getSharedPreferences("bunkerPreferences",Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("Token","Test")



    }
}