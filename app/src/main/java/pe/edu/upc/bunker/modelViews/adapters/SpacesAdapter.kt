package pe.edu.upc.bunker.modelViews.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.dto.SpaceInfoDTO
import pe.edu.upc.bunker.modelViews.activities.SpaceDetailsActivity
import pe.edu.upc.bunker.models.Space
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpacesAdapter(var list: List<SpaceInfoDTO>) : RecyclerView.Adapter<SpacesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpacesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.spaces_recycler_view_view_holder, parent, false)
        return SpacesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SpacesViewHolder, position: Int) {
        holder.spaceTitleTextView.text = list[position].title
        holder.spaceAddressTextView.text = list[position].address
        Picasso.get()
            .load(list[position].firstPhoto)
            .error(R.drawable.ic_error_outline_black_24dp)
            .placeholder(R.drawable.progress_animation)
            .fit()
            .into(holder.spacePhotoImageView)
        if (list[position].status == 1) {
            holder.spaceDisableButton.text =
                holder.itemView.resources.getString(R.string.enable_label_button)
            holder.spaceDisableButton.setBackgroundColor(
                holder.itemView.resources.getColor(
                    R.color.quantum_googgreen,
                    null
                )
            )
            holder.spaceDisableButton.icon =
                holder.itemView.resources.getDrawable(R.drawable.ic_check_white_16dp, null)
        }
        holder.spaceDetailsButton.setOnClickListener {
            val context = it.context
            val intent = Intent(context, SpaceDetailsActivity::class.java)
            intent.putExtra("space", list[position])
            context.startActivity(intent)
        }
        holder.spaceDisableButton.setOnClickListener {
            if (list[position].status == 0) {
                holder.spacesRepository.blockSpace(holder.authToken, list[position].id)
                    .enqueue(object : Callback<Space> {
                        override fun onFailure(call: Call<Space>, t: Throwable) {
                            Log.e("NetworkingDebug", "Could not block space", t)
                            Snackbar.make(
                                holder.spaceDisableButton,
                                "No se pudo bloquear el espacio, por favor intente nuevamente",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }

                        override fun onResponse(call: Call<Space>, response: Response<Space>) {
                            if (response.code() == 200) {
                                holder.spaceDisableButton.text =
                                    holder.itemView.resources.getString(R.string.enable_label_button)
                                holder.spaceDisableButton.setBackgroundColor(
                                    holder.itemView.resources.getColor(
                                        R.color.quantum_googgreen,
                                        null
                                    )
                                )
                                holder.spaceDisableButton.icon =
                                    holder.itemView.resources.getDrawable(
                                        R.drawable.ic_check_white_16dp,
                                        null
                                    )
                                list[position].status = 1
                            }
                        }
                    })
            } else {
                holder.spacesRepository.unblockSpace(holder.authToken, list[position].id)
                    .enqueue(object : Callback<Space> {
                        override fun onFailure(call: Call<Space>, t: Throwable) {
                            Log.e("NetworkingDebug", "Could not unblock space", t)
                            Snackbar.make(
                                holder.spaceDisableButton,
                                "No se pudo desbloquear el espacio, por favor intente nuevamente",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }

                        override fun onResponse(call: Call<Space>, response: Response<Space>) {
                            if (response.code() == 200) {
                                holder.spaceDisableButton.text =
                                    holder.itemView.resources.getString(R.string.disable_label_button)
                                holder.spaceDisableButton.setBackgroundColor(
                                    holder.itemView.resources.getColor(
                                        R.color.quantum_googred,
                                        null
                                    )
                                )
                                holder.spaceDisableButton.icon =
                                    holder.itemView.resources.getDrawable(
                                        R.drawable.ic_cancel_button_16dp,
                                        null
                                    )
                                list[position].status = 0
                            }
                        }
                    })
            }
        }
    }
}