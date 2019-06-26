package com.example.siqueiraneto.doacao.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.siqueiraneto.doacao.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import Config.ConfiguracaoFirebase;
import Model.Deposito;

public class MapsFragment extends  SupportMapFragment implements OnMapReadyCallback , GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    DatabaseReference firebaseref = ConfiguracaoFirebase.getFirebaseDatabase();
    DatabaseReference depositos = firebaseref.child("depositos");
    Deposito deposito = null;
    List<Deposito> depositosCadastrados;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // For showing a move to my location button
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    1);
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        LatLng ceara = new LatLng(-4.968293, -39.015717);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(ceara).zoom(16).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng (-4.971581, -39.014657))
                .title("Duarte").icon(BitmapDescriptorFactory.fromResource(R.drawable.doeracao_maps))
        );


        mMap.addMarker(new MarkerOptions()
                .position(new LatLng (-4.967386, -39.016921))
                .title("Igreja do Alto").icon(BitmapDescriptorFactory.fromResource(R.drawable.doeracao_maps))
        );

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng (-4.967963, -39.025531))
                .title("Ginasio Coberto").icon(BitmapDescriptorFactory.fromResource(R.drawable.doeracao_maps))
        );

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng (-4.978915, -39.056369))
                .title("UFC").icon(BitmapDescriptorFactory.fromResource(R.drawable.doeracao_maps))
        );


        mMap.setOnMarkerClickListener(this);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        Intent intent = new Intent(getActivity(), SetLocal.class);
        intent.putExtra("nome", marker.getTitle());
        startActivity(intent);
        return false;
    }

}