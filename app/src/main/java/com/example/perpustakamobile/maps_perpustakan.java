package com.example.perpustakamobile;

import androidx.fragment.app.FragmentActivity;


import android.os.Bundle;




public class maps_perpustakan extends FragmentActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_perpustakan);

        getSupportFragmentManager().beginTransaction().
                add(R.id.ContinerMaps,new maps_fragment()).commit();


    }




}