package nrru.chumsila.tippawan.servicecar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListShpo extends AppCompatActivity {

    private ListView listView;
    private String[] shopStrings, addressStrings, phoneStrings, serviceStrings, imageStrings, iconStrings, latStrings, lngStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_shpo);

        listView = (ListView) findViewById(R.id.listView);

        // อ่านข้อมูลจากฐานข้อมูลทั้งหมด ชื่อ...................
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name, MODE_PRIVATE, null);

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM shopTABLE", null);
        if (cursor != null) {

            cursor.moveToFirst();

            shopStrings = new String[cursor.getCount()];
            addressStrings = new String[cursor.getCount()];
            phoneStrings = new String[cursor.getCount()];
            serviceStrings = new String[cursor.getCount()];
            imageStrings = new String[cursor.getCount()];
            iconStrings = new String[cursor.getCount()];
            latStrings = new String[cursor.getCount()];
            lngStrings = new String[cursor.getCount()];

            for (int i = 0; i < cursor.getCount(); i += 1) {

                shopStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Shop));
                addressStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Address));
                phoneStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Phone));
                serviceStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Service));
                imageStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Image));
                iconStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Icon));
                latStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Lat));
                lngStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Lng));

                cursor.moveToNext();

            }//for

        }//if

        //สร้างวิว
        try {

            MyAdapter myAdapter =new MyAdapter(this,shopStrings,phoneStrings,serviceStrings,addressStrings,imageStrings);
            listView.setAdapter(myAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String[] strings = new String[8];
                    strings[0] = shopStrings[i];
                    strings[1] = addressStrings[i];
                    strings[2] = phoneStrings[i];
                    strings[3] = serviceStrings[i];
                    strings[4] = imageStrings[i];
                    strings[5] = iconStrings[i];
                    strings[6] = latStrings[i];
                    strings[7] = lngStrings[i];

                    Intent intent = new Intent(ListShpo.this, DetailActivity.class);
                    intent.putExtra("Shop", strings);
                    startActivity(intent);

                }   // onItemClick
            });



        }catch (Exception e){
            e.printStackTrace();
        }

    }//เม็ดตอด


} //main class
