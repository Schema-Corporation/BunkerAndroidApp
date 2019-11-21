package pe.edu.upc.bunker.modelViews.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_notifications.*
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.modelViews.activities.LoginActivity
import pe.edu.upc.bunker.modelViews.adapters.NotificationsAdapter
import pe.edu.upc.bunker.models.BookingProcess
import pe.edu.upc.bunker.repository.BookingProcessRepository
import pe.edu.upc.bunker.repository.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsFragment : Fragment() {
    private lateinit var bookingProcessList: List<BookingProcess>
    private lateinit var bookingProcessRecyclerView: RecyclerView
    private lateinit var noNotificationsTextView: TextView
    private lateinit var bookingProcessAdapter: RecyclerView.Adapter<*>
    private lateinit var bookingProcessLayoutManager: RecyclerView.LayoutManager
    private val bookingProcessRepository =
        RetrofitClientInstance().getRetrofitInstance().create(BookingProcessRepository::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)
        noNotificationsTextView = view.findViewById(R.id.noNotificationsFoundTextView)
        bookingProcessRecyclerView = view.findViewById(R.id.notificationsRecyclerView)
        bookingProcessLayoutManager = LinearLayoutManager(this.context)
        getNotificationList()
        return view
    }

    private fun getNotificationList() {
        val sharedPreferences = this.activity?.getSharedPreferences("Login", Context.MODE_PRIVATE)
        val token = "Bearer " + sharedPreferences?.getString("Token", "") as String
        val lessorId = sharedPreferences.getInt("LessorId", 0)
        bookingProcessRepository.getSpacesNotification(lessorId, token)
            .enqueue(object : Callback<List<BookingProcess>> {
                override fun onFailure(call: Call<List<BookingProcess>>, t: Throwable) {
                    Log.e("NetworkingDebug", "Could not communicate with back-end", t)
                }

                override fun onResponse(
                    call: Call<List<BookingProcess>>,
                    response: Response<List<BookingProcess>>
                ) {
                    when (response.code()) {
                        200 -> {
                            val body = response.body()
                            if (body != null) {
                                if (body.isNotEmpty()) {
                                    noNotificationsTextView.visibility = View.GONE
                                    notificationsRecyclerView.visibility = View.VISIBLE
                                    bookingProcessAdapter = NotificationsAdapter(body)
                                    bookingProcessRecyclerView.adapter = bookingProcessAdapter
                                    bookingProcessRecyclerView.layoutManager =
                                        bookingProcessLayoutManager
                                } else {
                                    noNotificationsTextView.visibility = View.VISIBLE
                                    notificationsRecyclerView.visibility = View.GONE
                                }
                            } else {
                                Log.d("Networking", call.request().url().toString())
                            }
                        }
                        401 -> {
                            startActivity(
                                Intent(
                                    this@NotificationsFragment.context,
                                    LoginActivity::class.java
                                )
                            )
                        }
                    }
                }
            })
    }
}
