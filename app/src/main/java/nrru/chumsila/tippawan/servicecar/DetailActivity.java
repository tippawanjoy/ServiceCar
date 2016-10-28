package nrru.chumsila.tippawan.servicecar;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailActivity extends FragmentActivity implements OnMapReadyCallback {


    //
    private GoogleMap mMap;
    private String[] strings;
    private TextView shopTextView,addressTextView,phoneTextView,serviceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        //bind widget
        shopTextView=(TextView)findViewById(R.id.textView8);
        addressTextView=(TextView)findViewById(R.id.textView9);
        phoneTextView=(TextView)findViewById(R.id.textView10);
        serviceTextView=(TextView)findViewById(R.id.textView11);

        //get value
        strings = getIntent().getStringArrayExtra("Shop");

        //show text
        shopTextView.setText(strings[0]);
        addressTextView.setText("ที่อยู่"+ strings[1]);
        phoneTextView.setText("Phone"+ strings[2]);
        serviceTextView.setText("บริการ"+ strings[3]);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }// เม็ตตอด

    public void clickBackDetail(View view){
        finish();
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        MapIcon mapIcon = new MapIcon(this,Integer.parseInt(strings[5]));

        LatLng latLng = new LatLng(Double.parseDouble(strings[6]),
                Double.parseDouble(strings[7]));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(strings[0])
                .icon(BitmapDescriptorFactory.fromResource(mapIcon.showIcon())));
    }//onMapReady
}// เม็ดคลาส
