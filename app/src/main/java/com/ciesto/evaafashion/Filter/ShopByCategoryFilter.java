package com.ciesto.evaafashion.Filter;

import android.widget.Filter;

import com.ciesto.evaafashion.Adapter.ShopByCategoryAdapter;
import com.ciesto.evaafashion.Model.HomeScreenModel;

import java.util.ArrayList;
import java.util.List;

public class ShopByCategoryFilter extends Filter
{
    ShopByCategoryAdapter shopByCategoryAdapter;
    List<HomeScreenModel.ShopByCategory> shopbycategoryList;

    public ShopByCategoryFilter(ShopByCategoryAdapter homeAdapter, List<HomeScreenModel.ShopByCategory> shopbycategoryList)
    {
        this.shopByCategoryAdapter=homeAdapter;
        this.shopbycategoryList=shopbycategoryList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        if (constraint !=null && constraint.length() >0)
        {
            String charString = constraint.toString();
            ArrayList<HomeScreenModel.ShopByCategory> filteredList = new ArrayList<>();
            for ( int i=0;i<shopbycategoryList.size();i++) {

                // name match condition. this might differ depending on your requirement
                // here we are looking for name or phone number match
                if (shopbycategoryList.get(i).getSubCategory().toUpperCase().contains(charString.toUpperCase()) ||
                        shopbycategoryList.get(i).getCategory().toUpperCase().contains(charString.toUpperCase())) {
                    filteredList.add(shopbycategoryList.get(i));
                }
            }

            results.values = filteredList;
            results.count=filteredList.size();
            return results;

        }
        else
        {
            results.values = shopbycategoryList;
            results.count=shopbycategoryList.size();
            return results;
        }
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results)
    {
        shopByCategoryAdapter.shopbycategoryList= (List<HomeScreenModel.ShopByCategory>) results.values;
        shopByCategoryAdapter.notifyDataSetChanged();
    }
}
