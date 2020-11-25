package com.ciesto.evaafashion.Filter;

import android.widget.Filter;

import com.ciesto.evaafashion.Adapter.HomeWomanAdapter;
import com.ciesto.evaafashion.Adapter.ShopByCategoryAdapter;
import com.ciesto.evaafashion.Model.HomeScreenModel;

import java.util.ArrayList;
import java.util.List;

public class HomeWomanFilter extends Filter
{
    HomeWomanAdapter homeWomanAdapter;
    List<HomeScreenModel.SubCategory1> shopbycategoryList;

    public HomeWomanFilter(HomeWomanAdapter homeAdapter, List<HomeScreenModel.SubCategory1> shopbycategoryList)
    {
        this.homeWomanAdapter=homeAdapter;
        this.shopbycategoryList=shopbycategoryList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        if (constraint !=null && constraint.length() >0)
        {
            String charString = constraint.toString();
            ArrayList<HomeScreenModel.SubCategory1> filteredList = new ArrayList<>();
            for ( int i=0;i<shopbycategoryList.size();i++) {

                // name match condition. this might differ depending on your requirement
                // here we are looking for name or phone number match
                if (shopbycategoryList.get(i).getSubCategory().toUpperCase().contains(charString.toUpperCase())) {
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
        homeWomanAdapter.WomanSubcategoryList= (List<HomeScreenModel.SubCategory1>) results.values;
        homeWomanAdapter.notifyDataSetChanged();
    }
}
