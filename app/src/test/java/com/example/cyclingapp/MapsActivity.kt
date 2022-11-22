package com.example.cyclingapp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.models.RouteOptions
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.navigation.core.MapboxNavigationProvider
import com.mapbox.navigation.dropin.NavigationView
import com.mapbox.navigation.ui.maps.route.line.model.MapboxRouteLineOptions
import java.lang.ref.WeakReference


class MapsActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.maps_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.itemManagement -> {
                showDefaultDialog(this)
                true
            }
            R.id.menuMaps -> {
                showLogout(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //add annotationshit
    private lateinit var mapView: MapView
    private lateinit var mapboxMap: MapboxMap

    private val routeLineOption: MapboxRouteLineOptions by lazy {
        MapboxRouteLineOptions.Builder(this)
            .withRouteLineBelowLayerId("road-label") // for Style.LIGHT and Style.DARK
            .withVanishingRouteLineEnabled(true)
            .displaySoftGradientForTraffic(true)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        findViewById<NavigationView>(R.id.navigationView).customizeViewOptions {
            mapStyleUriDay = "mapbox://styles/arden-guinto/cla6gbdy6005p15o46dklmzvn/draft"
            mapStyleUriNight = "mapbox://styles/arden-guinto/cla6gbdy6005p15o46dklmzvn/draft"
            routeLineOptions = this@MapsActivity.routeLineOption
            showSpeedLimit = false

        }
        mapView = findViewById(R.id.mapView)
        mapView.getMapboxMap().loadStyleUri("mapbox://styles/arden-guinto/cla6gbdy6005p15o46dklmzvn/draft",
            object : Style.OnStyleLoaded {
                override fun onStyleLoaded(style: Style) {
                }
            }
        )

        mapboxMap = mapView.getMapboxMap()
        mapboxMap.setBounds(cameraBoundsOptions)
    }

    private val cameraBoundsOptions = CameraBoundsOptions.Builder()
        .bounds(
            CoordinateBounds(
                Point.fromLngLat(120.533185, 15.406246),
                Point.fromLngLat(120.690083, 15.541244),
                false
            )
        )
        .minZoom(10.0)
        .build()

    private fun onMapReady() {
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(14.0)
                .build()
        )
    }



    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    fun showLogout(context: Context){
        val alertDialog = AlertDialog.Builder(context)

        alertDialog.apply {
            //setIcon(R.drawable.ic_hello)
            setTitle("Confirm Logout")
            setMessage("Are you sure you want to Logout?")
            setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                //Toast.makeText(context, "EquipmentYes", Toast.LENGTH_SHORT).show()
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this@MapsActivity, MainActivity::class.java))
                finish()
            }
            setNegativeButton("No") { _, _ ->
                //Toast.makeText(context, "Equipment No", Toast.LENGTH_SHORT).show()
            }

            setOnDismissListener {
                //Toast.makeText(context, "Equipment Check", Toast.LENGTH_SHORT).show()
            }

        }.create().show()
    }

}

