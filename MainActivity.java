package com.example.capstoneprototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneprototype.Activities.ChatActivity;
import com.example.capstoneprototype.DataModels.shopLocate;

import com.example.capstoneprototype.fragments.CartFragment;
import com.example.capstoneprototype.fragments.fragHome;
import com.example.capstoneprototype.fragments.profileFrag;
import com.example.capstoneprototype.fragments.searchFrag;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    DatabaseReference mref;
    GoogleMap gmap;
    public SlidingUpPanelLayout slidelayout;
    FloatingActionButton fab;
    Location Lastlocation;
    FusedLocationProviderClient mclient;

        FrameLayout frameLayout;
        ScrollView scroll_v;
    //Botton nav

    BottomNavigationView bottomNavigationView;
    RelativeLayout relative_drag;
    TextView tv;

    Toolbar toolbar;

    double longt,lat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing map
        final SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mref= FirebaseDatabase.getInstance().getReference();

        TextView home_logo=(TextView) findViewById(R.id.home_logo);
        frameLayout=(FrameLayout)findViewById(R.id.main_frame) ;
        scroll_v=(ScrollView)findViewById(R.id.scroll_v);
        slidelayout=(SlidingUpPanelLayout)findViewById(R.id.slideuppanel);

        slidelayout.setTouchEnabled(false);



        tv=(TextView)findViewById(R.id.home_logo);
        tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(slidelayout.getPanelState()==SlidingUpPanelLayout.PanelState.COLLAPSED){
                            slidelayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentById(R.id.map)).commit();



                        }
                        else{
                            getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentById(R.id.map)).commit();

                            slidelayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

                        }

                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;

                }
                return true;


            }
        });
        populateMapss();



        fab=(FloatingActionButton)findViewById(R.id.float_search) ;

        //fragment transaction for home frag because without it there will be ambigities
        final FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_frame,new fragHome());
        fragmentTransaction.commit();



        mclient= LocationServices.getFusedLocationProviderClient(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this, ChatActivity.class);
                startActivity(i);

                Toast.makeText(MainActivity.this, "fab clicked "+slidelayout.getPanelState(), Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                final FragmentTransaction fragmentTransaction1=getSupportFragmentManager().beginTransaction();


                switch (menuItem.getItemId()){
                    case R.id.main_home:
                        fragmentTransaction1.replace(R.id.main_frame,new fragHome() );
                        fragmentTransaction1.commit();
                        return true;



                    case R.id.main_search:
                        fragmentTransaction1.replace(R.id.main_frame,new searchFrag() );
                        fragmentTransaction1.commit();
                        return true;



                    case R.id.main_cart:
                        fragmentTransaction1.replace(R.id.main_frame,new CartFragment());
                        fragmentTransaction1.commit();
                        return true;

                     default:
                         return false;






                }

            }
        });




        setLoc();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap=googleMap;
        LatLng sydney = new LatLng(-34, 151);
        gmap.addMarker(new MarkerOptions().position(sydney).title("Sydney"));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,new String []{ Manifest.permission.ACCESS_FINE_LOCATION},1);
//
//
//        }else {
//            mclient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//
//                       Lastlocation = location;
//                       longt = Lastlocation.getLongitude();
//                       lat = Lastlocation.getLatitude();
//                       LatLng myLoc = new LatLng(lat, longt);
//                       gmap.addMarker(new MarkerOptions().position(myLoc).title("your location"));
//                       gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 15.0f));
//
//
//
//
//                }
//
//            });
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               setLoc();
                Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();


            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String []{ Manifest.permission.ACCESS_FINE_LOCATION},1);


        }else {



            setLoc();
        }

    }


    public void populateMapss(){

        mref.child("Businesses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        String shop_name=dataSnapshot1.child("shopname").getValue().toString();
                        String lat=dataSnapshot1.child("lat").getValue().toString();
                        String longt=dataSnapshot1.child("longt").getValue().toString();
                        LatLng shop=new LatLng(Double.parseDouble(lat),Double.parseDouble(longt));
                        gmap.addMarker(new MarkerOptions().position(shop).title(shop_name).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.house))));
                        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(shop,10.0f));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public void setLoc() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


        } else {
            mclient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Lastlocation = location;
                        longt = Lastlocation.getLongitude();
                        lat = Lastlocation.getLatitude();
                        LatLng myLoc = new LatLng(lat, longt);
                        gmap.addMarker(new MarkerOptions().position(myLoc).title("your location"));
                        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 15.0f));

                    }


                }

            });

        }
    }
}
