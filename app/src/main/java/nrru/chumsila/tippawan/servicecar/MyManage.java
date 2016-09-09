package nrru.chumsila.tippawan.servicecar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by user on 9/9/2559.
 */
public class MyManage {

    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public MyManage(Context context) {


        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();

    } //
}// คลาสเม็ต
