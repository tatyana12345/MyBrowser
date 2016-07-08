package ru.startandroid.mybrowser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimsob on 17.05.16.
 */
public class WebAdapter  extends BaseAdapter implements
        View.OnClickListener {

    final String LOG_TAG = "WebAdapterLog";
    Activity activity;

    List<String> rData = new ArrayList<String>();
    List<String> rData1 = new ArrayList<String>();
    static LayoutInflater inflater = null;
    Context mContext;


    public WebAdapter(Activity a, ArrayList<String> rD,ArrayList<String> rD1,
                      Context context) {
        this.mContext = context;
        activity = a;
        rData = rD;
        rData1 = rD1;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        Log.d(LOG_TAG, "Вызов функции getCount");
        return rData.size();
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public void onClick(View v) {

    }


    /*********
     * Create a holder Class to contain inflated xml file elements
     *********/
    public static class ViewHolder {

        public TextView text;
        public TextView text2;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {
            //****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.list_view_row, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.text = (TextView) vi.findViewById(R.id.textView);
            holder.text2 = (TextView) vi.findViewById(R.id.textView1);


            /************ Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }
        String item = rData.get(position);//лежит url
        String itemData = rData1.get(position);//лежит дата

        Context context = parent.getContext();
        holder.text.setText(item);
        holder.text2.setText(itemData);

        /******** Set Item Click Listner for LayoutInflater for each row ***********/
       vi.setOnClickListener(new OnItemClickListener(position, item));
        return vi;
    }

    /*********
     * Called when Item click in ListView
     ************/

    private class OnItemClickListener implements View.OnClickListener {
        private int mPosition;
        private String item_text_url;

        OnItemClickListener(int position, String text) {
            mPosition = position;
            item_text_url = text;
        }

        @Override
        public void onClick(View arg0) {
            Intent myIntent = new Intent(mContext, ShowPageFromHistoryActivity.class);
            myIntent.putExtra("text_url", item_text_url);
            Log.d(LOG_TAG, "text_url " + item_text_url);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//добавление если активити вызывается из адаптера, а не из другой активити
            mContext.startActivity(myIntent);


        }
    }
}
