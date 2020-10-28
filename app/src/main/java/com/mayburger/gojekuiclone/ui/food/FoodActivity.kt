package com.mayburger.gojekuiclone.ui.food

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.mayburger.gojekuiclone.BR
import com.mayburger.gojekuiclone.R
import com.mayburger.gojekuiclone.databinding.ActivityFoodBinding
import com.mayburger.gojekuiclone.ui.adapters.CommonRecyclerAdapter
import com.mayburger.gojekuiclone.ui.base.BaseActivity
import com.mayburger.gojekuiclone.ui.food.order.FoodOrderFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FoodActivity : BaseActivity<ActivityFoodBinding, FoodViewModel>(), FoodNavigator, OnMapReadyCallback {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_food
    override val viewModel: FoodViewModel by viewModels()

    private lateinit var mMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // LE COMPLEX ANIMATION ALGORITHMS

        supportFragmentManager.apply {
            beginTransaction().apply {
                replace(R.id.container, FoodOrderFragment(), "")
                commit()
            }
        }


        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val sydney = LatLng(-6.540667,107.446274)
        val cameraPosition = CameraPosition.Builder()
                .target(sydney)
                .zoom(17f)
                .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

}