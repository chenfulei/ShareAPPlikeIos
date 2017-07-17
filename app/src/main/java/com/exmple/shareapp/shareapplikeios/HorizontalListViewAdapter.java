

package com.exmple.shareapp.shareapplikeios;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HorizontalListViewAdapter extends ArrayAdapter<AppInfo> {

    private Context mContext;
    private ArrayList<AppInfo> appList;
    private int mResource;

    public HorizontalListViewAdapter(Context context, int resource, ArrayList<AppInfo> objects){
        super(context, resource, objects);
        this.mResource = resource;
        this.mContext = context;
        this.appList = objects;
    }

    @Override
    public int getCount() {
        return appList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final AppInfo appInfo = getItem(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(mResource, null);

        ImageView img = (ImageView) convertView.findViewById(R.id.img_list_item);
        TextView txt =(TextView) convertView.findViewById(R.id.text_list_item);

        txt.setText(appInfo.getAppName());
        img.setBackground(appInfo.getAppIcon());

        return convertView;
    }
}
