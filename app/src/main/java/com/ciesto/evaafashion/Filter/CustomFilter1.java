package com.ciesto.evaafashion.Filter;

import android.widget.Filter;

import com.ciesto.evaafashion.Adapter.FilterAdapter;
import com.ciesto.evaafashion.Model.FilterModel;

import java.util.ArrayList;
import java.util.List;

public class CustomFilter1 extends Filter
{
    FilterAdapter filterAdapter;
    List<FilterModel.Datum> datumList;

    public CustomFilter1(FilterAdapter homeAdapter, List<FilterModel.Datum> datumList)
    {
        this.filterAdapter=homeAdapter;
        this.datumList=datumList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        if (constraint !=null && constraint.length() >0)
        {
            String charString = constraint.toString();
            ArrayList<FilterModel.Datum> filteredList = new ArrayList<>();
            for ( int i=0;i<datumList.size();i++) {

                // name match condition. this might differ depending on your requirement
                // here we are looking for name or phone number match
                if (datumList.get(i).getName().toUpperCase().contains(charString.toUpperCase())) {
                    filteredList.add(datumList.get(i));
                }
            }
            results.values = filteredList;
            results.count=filteredList.size();
            return results;

        }
        else
        {
            results.values = datumList;
            results.count=datumList.size();
            return results;
        }
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results)
    {
        filterAdapter.datalist= (List<FilterModel.Datum>) results.values;
        filterAdapter.notifyDataSetChanged();
    }
}
