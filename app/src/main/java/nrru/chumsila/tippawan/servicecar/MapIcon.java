package nrru.chumsila.tippawan.servicecar;

import android.content.Context;

/**
 * Created by user on 30/9/2559.
 */
public class MapIcon {
    //explicit
    private Context context;
    private int anInt;
    private int []ints = new int[]{R.drawable.t1_60,
            R.drawable.t1_60,R.drawable.t2_60,
            R.drawable.t3_60,R.drawable.t4_60,
            R.drawable.t5_60,R.drawable.t6_60,
            R.drawable.t7_60,R.drawable.t8_60,
            R.drawable.t9_60,R.drawable.t10_60,
            R.drawable.t11_60};


    public MapIcon(Context context, int anInt) {
        this.context = context;
        this.anInt = anInt;
    }// คอนซักเตอร์

    public int showIcon() {



        return ints[anInt];
    }
}// mapicon
