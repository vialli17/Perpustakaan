package com.example.perpustakamobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class maps_fragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_maps_fragment, container, false);
        SupportMapFragment maps = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps);

        maps.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                LatLng perpustakaan_location = new LatLng(-6.1813161,106.8269285);

                googleMap.addMarker(new MarkerOptions()
                .position(perpustakaan_location)
                .title("Perpustakaan Nasional Republik Indonesia"));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(perpustakaan_location,20f));

            }
        });

        return view;

    }
}


