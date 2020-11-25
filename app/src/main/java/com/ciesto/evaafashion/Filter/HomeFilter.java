package com.ciesto.evaafashion.Filter;

import android.widget.Filter;


import com.ciesto.evaafashion.Adapter.FilterAdapter;
import com.ciesto.evaafashion.Adapter.ShopByProductAdapter;
import com.ciesto.evaafashion.Model.FilterModel;
import com.ciesto.evaafashion.Model.HomeScreenModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFilter extends Filter
{
    ShopByProductAdapter shopByProductAdapter;
    List<HomeScreenModel.ShopByProduct> shopByProducts;

    public HomeFilter(ShopByProductAdapter homeAdapter, List<HomeScreenModel.ShopByProduct> shopByProductslist)
    {
        this.shopByProductAdapter=homeAdapter;
        this.shopByProducts=shopByProductslist;
    }

    public HomeFilter(FilterAdapter filterAdapter, List<FilterModel.Datum> datalist) {
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        if (constraint !=null && constraint.length() >0)
        {
            String charString = constraint.toString();
            ArrayList<HomeScreenModel.ShopByProduct> filteredList = new ArrayList<>();
            for ( int i=0;i<shopByProducts.size();i++) {

                // name match condition. this might differ depending on your requirement
                // here we are looking for name or phone number match
                if (shopByProducts.get(i).getCategory().toUpperCase().contains(charString.toUpperCase())) {
                    filteredList.add(shopByProducts.get(i));
                }
            }

            results.values = filteredList;
            results.count=filteredList.size();
            return results;

        }
        else
        {
            results.values = shopByProducts;
            results.count=shopByProducts.size();
            return results;
        }
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results)
    {
        shopByProductAdapter.shopByProductsList= (List<HomeScreenModel.ShopByProduct>) results.values;
        shopByProductAdapter.notifyDataSetChanged();
    }
}
