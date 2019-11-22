package pe.edu.upc.bunker.modelViews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.models.BookingProcess

class NotificationsAdapter(var list: List<BookingProcess>) :
    RecyclerView.Adapter<NotificationsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notifications_recycler_view_view_holder, parent, false)
        return NotificationsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        val bookingProcess = list[position]
        val step = bookingProcess.step
        val lessee = bookingProcess.lessee
        val name = "${lessee.firstName} ${lessee.lastName}"
        holder.dateTextView.text = bookingProcess.createdAt.split("T")[0]
        holder.spaceTitleTextView.text = bookingProcess.space.title
        holder.userFromTextView.text = name
        when (step) {
            0 -> holder.iconImageView.setImageResource(R.drawable.ic_book_black_24dp)
        }
    }

}