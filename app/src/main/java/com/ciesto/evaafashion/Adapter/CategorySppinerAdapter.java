package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.ciesto.evaafashion.Model.FilterModel;
import com.ciesto.evaafashion.R;

import java.util.List;

public class CategorySppinerAdapter  extends ArrayAdapter<FilterModel.Datum>
{

    private Activity activity;
    private List<FilterModel.Datum> data;
    LayoutInflater inflater;

    public CategorySppinerAdapter(Activity activitySpinner, int textViewResourceId, List objects)
    {
        super(activitySpinner, textViewResourceId, objects);
        activity = activitySpinner;
        data = objects;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.item_sppiner1, parent, false);


        TextView label = (TextView) row.findViewById(R.id.tvQty);
        label.setText(data.get(position).getName());

        return row;
    }


}
