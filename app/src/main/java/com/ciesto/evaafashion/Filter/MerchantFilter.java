package com.ciesto.evaafashion.Filter;

import android.widget.Filter;

import com.ciesto.evaafashion.Adapter.AtoZCategoryAdapter;
import com.ciesto.evaafashion.Adapter.FilterAdapter;
import com.ciesto.evaafashion.Adapter.ShopByProductAdapter;
import com.ciesto.evaafashion.Model.AtoZModel;
import com.ciesto.evaafashion.Model.FilterModel;
import com.ciesto.evaafashion.Model.HomeScreenModel;

import java.util.ArrayList;
import java.util.List;

public class MerchantFilter extends Filter
{
    AtoZCategoryAdapter filterAdapter;
    List<AtoZModel.Merchant> datalist;

    public MerchantFilter(AtoZCategoryAdapter filterAdapter, List<AtoZModel.Merchant> datalist) {
        this.filterAdapter=filterAdapter;
        this.datalist=datalist;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        if (constraint !=null && constraint.length() >0)
        {
            String charString = constraint.toString();
            ArrayList<AtoZModel.Merchant> filteredList = new ArrayList<>();
            for ( int i=0;i<datalist.size();i++) {

                // name match condition. this might differ depending on your requirement
                // here we are looking for name or phone number match
                if (datalist.get(i).getName().toUpperCase().contains(charString.toUpperCase())) {
                    filteredList.add(datalist.get(i));
                }
            }

            results.values = filteredList;
            results.count=filteredList.size();
            return results;

        }
        else
        {
            results.values = datalist;
            results.count=datalist.size();
            return results;
        }
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results)
    {
        filterAdapter.merchantlist= (List<AtoZModel.Merchant>) results.values;
        filterAdapter.notifyDataSetChanged();
    }
}
