package com.app.infideap.whatsappsearchtoolbar;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class FourFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    Location location;

    MarkerOptions marker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_four, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        marker=new MarkerOptions();
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                //googleMap.setMyLocationEnabled(true);

                LatLng sydney = new LatLng(BigDecimal.valueOf(MainActivity.lt).setScale(1, RoundingMode.HALF_UP)
                        .doubleValue(), BigDecimal.valueOf(MainActivity.ln).setScale(1, RoundingMode.HALF_UP)
                        .doubleValue());
                // For dropping a marker at a point on the MapLatLng sydney = new LatLng(MainActivity.lt, MainActivity.ln);

                googleMap.addMarker(marker.position(sydney).title(MainActivity.title).snippet("chhattishgarh"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                //googleMap.setMyLocationEnabled(true);


                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(BigDecimal.valueOf(MainActivity.lt).setScale(1, RoundingMode.HALF_UP)
                        .doubleValue(), BigDecimal.valueOf(MainActivity.ln).setScale(1, RoundingMode.HALF_UP)
                        .doubleValue());
                googleMap.clear();
                googleMap.addMarker(marker.position(sydney).title(MainActivity.title).snippet("chhattishgarh"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}