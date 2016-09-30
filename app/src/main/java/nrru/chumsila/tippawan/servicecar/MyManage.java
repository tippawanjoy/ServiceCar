package nrru.chumsila.tippawan.servicecar;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by user on 9/9/2559.
 */
public class MyManage {

    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public static final String shop_table = "shopTEBLE";
    public static final String column_id = "id";
    public static final String column_Shop = "Shop";
    public static final String column_Address = "Address";
    public static final String column_Phone = "Phone";
    public static final String column_Image = "Image";
    public static final String column_Icon = "Icon";
    public static final String column_Lat = "Lat";
    public static final String column_Lng = "Lng";

    public MyManage(Context context) {


        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();

    } //


    public long addValue(String strShop,
                         String strAddress,
                         String strPhone,
                         String strImage,
                         String strIcon,
                         String strLat,
                         String strLng) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_Shop, strShop);
        contentValues.put(column_Address, strAddress);
        contentValues.put(column_Phone, strPhone);
        contentValues.put(column_Image, strImage);
        contentValues.put(column_Icon, strIcon);
        contentValues.put(column_Lat, strLat);
        contentValues.put(column_Lng, strLng);


        return sqLiteDatabase.insert(shop_table,null,contentValues);
    }

}// คลาสเม็ต
