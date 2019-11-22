package pe.edu.upc.bunker.modelViews.adapters

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.repository.RetrofitClientInstance
import pe.edu.upc.bunker.repository.SpacesRepository
import retrofit2.create

class SpacesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val authToken = "Bearer " + itemView.context.getSharedPreferences(
        "Login",
        Context.MODE_PRIVATE
    ).getString("Token", "")
    var spacesRepository = RetrofitClientInstance().getRetrofitInstance().create<SpacesRepository>()
    var spacePhotoImageView: ImageView = itemView.findViewById(R.id.spaces_image_view_item)
    var spaceTitleTextView: TextView = itemView.findViewById(R.id.spaces_title_item)
    var spaceAddressTextView: TextView = itemView.findViewById(R.id.spaces_address_item)
    var spaceStatusTextView: TextView = itemView.findViewById(R.id.space_status_item)
    var spaceStatusIconImageVIew: ImageView = itemView.findViewById(R.id.space_status_icon_item)
    var spaceDisableButton: MaterialButton = itemView.findViewById(R.id.disable_space_button_item)
    var spaceDetailsButton: Button = itemView.findViewById(R.id.details_space_button_item)
}