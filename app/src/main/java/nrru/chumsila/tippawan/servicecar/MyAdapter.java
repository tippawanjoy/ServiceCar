package nrru.chumsila.tippawan.servicecar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by user on 30/9/2559.
 */
public class MyAdapter extends BaseAdapter{

    //
   private Context context;
        private String[]shopStrings,phoneStrings,serviceStrings,addressStrings,iconStrings;
    private ImageView imageView;
    private TextView shopTextView,phoneTextView,serviceTextView,addressTextView;

    public MyAdapter(Context context,
                     String[] shopStrings,
                     String[] phoneStrings,
                     String[] serviceStrings,
                     String[] addressStrings,
                     String[] iconStrings) {
        this.context = context;
        this.shopStrings = shopStrings;
        this.phoneStrings = phoneStrings;
        this.serviceStrings = serviceStrings;
        this.addressStrings = addressStrings;
        this.iconStrings = iconStrings;
    }

    @Override
    public int getCount() {
        return shopStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.my_listview,viewGroup,false);

        //การผูกตัวแปล
        imageView = (ImageView) view1.findViewById(R.id.imageView);
        shopTextView =(TextView) view1.findViewById(R.id.textView2);
        phoneTextView=(TextView)view1.findViewById(R.id.textView3);
        serviceTextView=(TextView)view1.findViewById(R.id.textView5);
        addressTextView=(TextView)view1.findViewById(R.id.textView7);

        // โชร์วิว
        Picasso.with(context).load(iconStrings[i])
        .resize(120,120).into(imageView);
        shopTextView.setText(shopStrings[i]);
        phoneTextView.setText(phoneStrings[i]);
        serviceTextView.setText(serviceStrings[i]);
        addressTextView.setText(addressStrings[i]);

        return view1;
    }
}// เม็ดคอต

