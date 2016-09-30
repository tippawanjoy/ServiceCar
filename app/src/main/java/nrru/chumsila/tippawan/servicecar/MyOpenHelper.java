package nrru.chumsila.tippawan.servicecar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 9/9/2559.
 */
public class MyOpenHelper extends SQLiteOpenHelper{// ชื่อเม็ดตอด
    // ประการตัวแปล


    public static final String database_name = "ServiceCar.db";
    private static final int database_version = 1;
    private static final String create_shop_table = "create table shopTABLE(" +
            "_id integer primary key," +
            "Shop text," +
            "Address text," +
            "Phone text," +
            "Service text," +
            "Image text," +
            "Icon text," +
            "Lat text," +
            "Lng text);";

    public MyOpenHelper(Context context) {
        super(context,database_name,null,database_version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_shop_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
} // คลาส หลัก
