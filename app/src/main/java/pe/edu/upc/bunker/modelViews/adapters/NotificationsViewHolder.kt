package pe.edu.upc.bunker.modelViews.adapters

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.repository.BookingProcessRepository
import pe.edu.upc.bunker.repository.RetrofitClientInstance

class NotificationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val authToken = "Bearer " + itemView.context.getSharedPreferences(
        "Login",
        Context.MODE_PRIVATE
    ).getString("Token", "")
    val bookingProcessRepository =
        RetrofitClientInstance().getRetrofitInstance().create(BookingProcessRepository::class.java)
    var iconImageView: ImageView = itemView.findViewById(R.id.notificationTypeIcon)
    var spaceTitleTextView: MaterialTextView =
        itemView.findViewById(R.id.spaceTitleTextViewNotification)
    var userFromTextView: MaterialTextView =
        itemView.findViewById(R.id.userFromTextViewNotification)
    var dateTextView: MaterialTextView = itemView.findViewById(R.id.dateTextViewNotification)
    var itemCardView: MaterialCardView = itemView.findViewById(R.id.notificationsItemCardView)

}