package nrru.chumsila.tippawan.servicecar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    // นี้คือการประกาศตัวแปล
    private GoogleMap mMap;
    private LocationManager locationManager; // locationManager = ชื่อตัวแปล
    private Criteria criteria;
    private double userLatADouble, userLngADouble;// ตัวแปลรัดติจูด,ลองติจูด
    private MyManage myManage;


    @Override

    protected void onCreate(Bundle savedInstanceState) { // เม็ดตอด แสดงขั้นพื้นฐาน (หลัก)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout);

        myManage = new MyManage(this);

        checkSQLite();



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // กำหนดค่าเริ่มต้นของการหาพิกัด
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); // แสดงพิกัดให้คนอื่นได้เห็น
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); //แสดงการค้นหาอย่างละเอียด 300m
        criteria.setAltitudeRequired(false); // ไม่ต้องการค้นหา ความสูงจากพื้นโลก Z
        criteria.setBearingRequired(false); // ไม่ต้องการค้นหา ความสูงจากพื้นโลก Z

    } // เม็ดตอด แสดงขั้นพื้นฐาน (หลัก)

    private void checkSQLite() {

        try {

            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE,null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM shopTEBLE",null);
            cursor.moveToFirst();

            Log.d("CarV2", "Count of Cursor ==>"+ cursor.getCount());

        } catch (Exception e) {
            Log.d("CarV2", "e ==>"+ e.toString());
        }


    }//เช็ค SQL


    // เปิดแอพจะให้ทำอะไร
    @Override
    protected void onResume() {
        super.onResume();

 // นี่คือ รัดติจูด ลองติจูด nrru
        userLatADouble = 14.984872;
        userLngADouble = 102.113251;

        Location networkLocation = myFindLocation(LocationManager.NETWORK_PROVIDER); //เวลาค้นหาให้ถามผู้บริการทางอินเตอร์เน็ต ว่าคุณอยู่ที่ไหน?
        if (networkLocation != null) {
            userLatADouble = networkLocation.getLatitude();
            userLngADouble = networkLocation.getLongitude();
        }

        Location gpsLocation = myFindLocation(LocationManager.GPS_PROVIDER);
        if (gpsLocation != null) {
            userLatADouble = gpsLocation.getLatitude();
            userLngADouble = gpsLocation.getLongitude();
        }

        //
        Log.d("CarV1","Lat ==>" + userLatADouble);
        Log.d("CarV1","Lng ==>" + userLngADouble);


    }  //

    // ถ้าปิดแอพให้ทำการปิดเซอร์วิท
    @Override
    protected void onStop() {
        super.onStop();

        locationManager.removeUpdates(locationListener);
    }

    public Location myFindLocation(String strProvider) {

        Location location = null;

        if (locationManager.isProviderEnabled(strProvider)) {

            locationManager.requestLocationUpdates(strProvider,1000,10,locationListener);
            location =locationManager.getLastKnownLocation(strProvider);
        }

        return location;
    }



 // โค้ด ถ้านำโทรสับขยับ จากเกิดผลลัพธ์ จะแสดงที่ตั้งใน ณ ตอนนั้น
    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

          userLatADouble =location.getLatitude();
            userLatADouble=location.getLatitude();


        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };



    @Override

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //ตั้งค่ากึ่งกลางของแผนที่
        LatLng nserLatLng = new LatLng(userLatADouble,userLngADouble);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nserLatLng, 16));
        mMap.addMarker(new MarkerOptions()
        .position(nserLatLng)
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.friend)));


    } // ทำกันแสดงแผนที่
} // นี้คือคลาสหลัก
