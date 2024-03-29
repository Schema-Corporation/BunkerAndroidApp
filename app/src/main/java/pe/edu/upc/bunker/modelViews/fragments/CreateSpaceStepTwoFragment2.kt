package pe.edu.upc.bunker.modelViews.fragments

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import pe.edu.upc.bunker.R
import java.util.*

class CreateSpaceStepTwoFragment2 : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {
    //Defining companion object for the system to request permissions from user when needed
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var map: GoogleMap
    //Defining variable for managing client current position
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var nextButton: Button
    private lateinit var backButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.fragment_create_space_step_two_fragment2, container, false)
        nextButton = view.findViewById(R.id.step_2_next_button)
        backButton = view.findViewById(R.id.back_arrow_image)
        if (!Places.isInitialized()) {
            Places.initialize(
                this.requireContext(),
                getString(R.string.google_maps_key),
                Locale.getDefault()
            )
        }

        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS
            )
        )
        autocompleteFragment.setLocationBias(
            RectangularBounds.newInstance(
                LatLng(-12.247085, -76.818641),
                LatLng(-11.792347, -77.275325)
            )
        )
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {

                Log.i("Maps", "Place: " + place.name + ", " + place.id)
                placeMarkerOnMap(place.latLng!!, place.address!!)
            }

            override fun onError(status: Status) {
                Log.i("Maps", "An error occurred: $status ")
            }

        })

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this.requireActivity())

        return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        //Defining UI settings controls for user
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        if (ActivityCompat.checkSelfPermission(
                this.requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        //Allow current position button to be accessible for user
        map.isMyLocationEnabled = true
        //Instancing listener for current position from OS value, updated  when user moves
        fusedLocationClient.lastLocation.addOnSuccessListener(this.requireActivity()) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
        setUpMap()
    }

    override fun onMarkerClick(p0: Marker?) = false
    private fun setUpMap() {
        //Check if permissions for fine location where granted, if not request them
        if (ActivityCompat.checkSelfPermission(
                this.requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        //Enable current position button
        map.isMyLocationEnabled = true
        //Listener for current position from OS
        fusedLocationClient.lastLocation.addOnSuccessListener(this.requireActivity()) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }

    //Marker configuration when positioned
    private fun placeMarkerOnMap(location: LatLng, address: String) {
        map.clear()
        val markerOptions = MarkerOptions().position(location)
        //Adds address information to marker
        markerOptions.title(address)
        map.addMarker(markerOptions)
    }
}
