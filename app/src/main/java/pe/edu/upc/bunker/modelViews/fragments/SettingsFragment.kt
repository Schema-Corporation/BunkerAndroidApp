package pe.edu.upc.bunker.modelViews.fragments


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.modelViews.activities.LoginActivity
import pe.edu.upc.bunker.repository.LoginRepository
import pe.edu.upc.bunker.repository.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SettingsFragment : Fragment() {

    private lateinit var logoutCardView: MaterialCardView
    private lateinit var termsConditionsCardView: MaterialCardView
    private lateinit var helpCardView: MaterialCardView
    private val loginRepo =
        RetrofitClientInstance().getRetrofitInstance().create(LoginRepository::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        logoutCardView = view.findViewById(R.id.logout_card_view)
        termsConditionsCardView = view.findViewById(R.id.tyc_card_view)
        helpCardView = view.findViewById(R.id.help_card_view)
        termsConditions()
        logout(view)
        helpDesk()
        return view
    }

    private fun logout(view: View) {
        logoutCardView.setOnClickListener {

            val sharedPreferences =
                this.activity?.getSharedPreferences("Login", Context.MODE_PRIVATE)
            val token = sharedPreferences?.getString("Token", "")
            loginRepo.logout(authorization = token!!).enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(activity, "Failed to Log Out", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Snackbar.make(logoutCardView, "Logged Out", Snackbar.LENGTH_SHORT).show()
                    sharedPreferences.edit().clear().apply()
                    view.context.startActivity(Intent(view.context, LoginActivity::class.java))
                }
            })
        }
    }

    private fun termsConditions() {
        termsConditionsCardView.setOnClickListener {
            val termsCon = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://firebasestorage.googleapis.com/v0/b/bunker-258012.appspot.com/o/tyc%2FBUNKER%20-%20T%C3%89RMINOS%20Y%20CONDICIONES.pdf?alt=media&token=5b7882bb-1778-4006-9b5f-4b27a1d43225")
            )
            startActivity(termsCon)
        }
    }

    private fun helpDesk() {
        helpCardView.setOnClickListener {
            val destination = "Schema@upc.edu.pe"
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "plain/text"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(destination))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Solicitud de ayuda")
            emailIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Le pedimos ser lo más detallado posible en la solicitud que nos envié para que podamos atender su caso lo más rápida e eficientemente posible"
            )
            context?.startActivity(Intent.createChooser(emailIntent, "Send mail..."))

        }
    }

}
