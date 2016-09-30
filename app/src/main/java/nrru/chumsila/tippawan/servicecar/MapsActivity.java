package nrru.chumsila.tippawan.servicecar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

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

        // ทดสอบแวลู้
        //myManage.addValue("shop","add","phone","image","icon","lat","lng");

        deleteAllSQLite();



        //ดึงฐานมูลมาเป็นSQL
        SynData synData = new SynData(this);
        synData.execute();






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

    public  void clickListShpo(View view){
        startActivity(new Intent(MapsActivity.this, ListShpo.class));
    }
    public void clickSearchShop(View view){
        startActivity(new Intent(MapsActivity.this,SearchShop.class));
    }



    private class SynData extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
//ป้องกันการErroe
            try {

                OkHttpClient okHttpClient=new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request =builder.url(urILSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                Log.d("CarV3","e doInBack ==>" + e.toString());
                return  null;
            }


        }//เม็ดตอดเบื้องหลัง

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("CarV3","JSON ==>"+s);

            try {

                JSONArray jsonArray = new JSONArray(s);

                for (int i=0;i<jsonArray.length();i+=1) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String strShop = jsonObject.getString(MyManage.column_Shop);
                    String strAddress =jsonObject.getString(MyManage.column_Address);
                    String strPhone =jsonObject.getString(MyManage.column_Phone);
                    String strService =jsonObject.getString(MyManage.column_Service);
                    String strImage =jsonObject.getString(MyManage.column_Image);
                    String strIcon =jsonObject.getString(MyManage.column_Icon);
                    String strLat =jsonObject.getString(MyManage.column_Lat);
                    String strLng =jsonObject.getString(MyManage.column_Lng);

                    MyManage myManage = new MyManage(context);
                    myManage.addValue(strShop,strAddress,strPhone,strService,strImage,strIcon,strLat,strLng);

                    // มาคส์จุดตำแหน่งของร้าน , มี ชื่อร้าน,เบอร์โทรศัพท์
                    mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(strLat),Double.parseDouble(strLng)))
                    .title(strShop)
                    .snippet(strPhone));








                }   // for ตัวที่ใช้ในการเปลี่ยน String ให้เป็นประโยคสั่นๆ

                checkSQLite();

            } catch (Exception e) {
                Log.d("CarV3", "e onPost ==> " + e.toString());
            }



        }//ออนโพส

        //ประกาศตัวแปล
        private Context context;
        private  static  final  String urILSON ="http://swiftcodingthai.com/joy1/get_data.php";
        public SynData(Context context) {
            this.context = context;
        }
    }


    private void deleteAllSQLite() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(MyManage.shop_table, null, null);


    }

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
