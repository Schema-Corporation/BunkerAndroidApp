package pe.edu.upc.bunker.modelViews.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.modelViews.activities.SpaceDetailsActivity
import pe.edu.upc.bunker.models.Space

class SpacesAdapter (var list: List<Space>): RecyclerView.Adapter<SpacesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpacesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.spaces_recycler_view_view_holder, parent, false)
        return SpacesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SpacesViewHolder, position: Int) {
        holder.spaceTitleTextView.text = list[position].title

        holder.spaceDetailsButton.setOnClickListener{
            val context = it.context
            context.startActivity(Intent(context, SpaceDetailsActivity::class.java))
        }
    }

}