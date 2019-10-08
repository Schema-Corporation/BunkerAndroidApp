package pe.edu.upc.bunker.modelViews.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.card.MaterialCardView
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.modelViews.activities.LoginActivity
import pe.edu.upc.bunker.repository.LoginRepository
import pe.edu.upc.bunker.repository.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsFragment : Fragment() {

    private lateinit var logoutCardView: MaterialCardView

    private val loginRepo =
        RetrofitClientInstance().getRetrofitInstance().create(LoginRepository::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        logoutCardView=view.findViewById(R.id.logout_card_view)

        logout(view)
        return view
    }

    private fun logout(view : View) {
        logoutCardView.setOnClickListener {

            val sharedPreferences = this.activity?.getSharedPreferences("Login",Context.MODE_PRIVATE)
            val token = sharedPreferences?.getString("Token", "")
            loginRepo.logout(authorization = token!!).enqueue(object : Callback<Void>{
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(activity, "Log FAILED", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Toast.makeText(activity, "Logged Out", Toast.LENGTH_SHORT).show()
                }

            })
            view.context.startActivity(Intent(view.context, LoginActivity::class.java))
        }
    }

}
