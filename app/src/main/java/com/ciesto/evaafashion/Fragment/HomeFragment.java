package com.ciesto.evaafashion.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ciesto.evaafashion.Activity.ProductActivity;
import com.ciesto.evaafashion.Activity.SubCategoryActivity1;
import com.ciesto.evaafashion.Adapter.FeaturedProductAapter;
import com.ciesto.evaafashion.Adapter.HomeMerchantAdapter;
import com.ciesto.evaafashion.Adapter.HomeWomanAdapter;
import com.ciesto.evaafashion.Adapter.RecentlyViewAapter;
import com.ciesto.evaafashion.Adapter.ShopByCategoryAdapter;
import com.ciesto.evaafashion.Adapter.ShopByProductAdapter;
import com.ciesto.evaafashion.Model.HomeScreenModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.ExtraPreferences;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;
import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;
import com.jama.carouselview.enums.IndicatorAnimationType;
import com.jama.carouselview.enums.OffsetType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    protected View rootView;
    protected ViewPager viewPager;
    protected ViewPager viewSlide;
    protected LinearLayout layoutDotsSlide;
    protected RecyclerView rvShopByCategory;
    protected RecyclerView rvProduct;
    protected RecyclerView rvcategory;
    protected RecyclerView rvRecentlyView;
    protected EditText etSearch;
    protected ImageView ivClose;
    protected ImageView ivTwitter;
    protected ImageView ivFacebook;
    protected ImageView ivInstagram;
    protected ImageView ivYoutube;
    protected NestedScrollView scrollView;
    protected RecyclerView rvFeaturedproduct;
    protected TextView tvfeaturedNoData;
    protected TextView tvFeaturedseeall;
    protected RecyclerView rvWomancategory;
    protected TextView tvLike;
    protected TextView tvFollow;
    protected CarouselView carouselRecentlyview;
    protected TextView tvLatestProduct;
    protected RecyclerView rvMerchant;
    CarouselView carousellist;


    private TextView[] dots;
    private int[] layouts;
    MyViewPagerAdapter myViewPagerAdapter;
    Activity activity;
    View view;
    ProgressDialog progressDialog;
    LoginPreferences loginPreferences;
    List<HomeScreenModel.Slider> sliderList;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 1000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    private int NUM_PAGES;
    Handler handler;
    Runnable Update;
    TextView tvNoData;
    LinearLayout lnrmain;
    String UserId, ProductID = "";
    ExtraPreferences extraPreferences;
    TextView tvrecentlyNoData;
    ShopByProductAdapter shopByProductAdapter;
    ShopByCategoryAdapter shopByCategoryAdapter;
    HomeWomanAdapter homeWomanAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        activity = getActivity();
        initView(view);
        initComponent();
        return view;

    }

    private void initView(View rootView) {
        viewSlide = (ViewPager) rootView.findViewById(R.id.view_slide);
        layoutDotsSlide = (LinearLayout) rootView.findViewById(R.id.layoutDots_slide);
        rvShopByCategory = (RecyclerView) rootView.findViewById(R.id.rv_category);
        rvProduct = (RecyclerView) rootView.findViewById(R.id.rv_product);
        rvcategory = (RecyclerView) rootView.findViewById(R.id.rv_subcategory);
        rvRecentlyView = (RecyclerView) rootView.findViewById(R.id.rv_recentlyView);
        tvNoData = (TextView) rootView.findViewById(R.id.tvNoData);
        lnrmain = (LinearLayout) rootView.findViewById(R.id.lnrmain);
        tvrecentlyNoData = (TextView) rootView.findViewById(R.id.tvrecentlyNoData);
        etSearch = (EditText) rootView.findViewById(R.id.et_search);
        ivClose = (ImageView) rootView.findViewById(R.id.iv_close);
        ivTwitter = (ImageView) rootView.findViewById(R.id.ivTwitter);
        ivFacebook = (ImageView) rootView.findViewById(R.id.ivFacebook);
        ivInstagram = (ImageView) rootView.findViewById(R.id.ivInstagram);
        ivYoutube = (ImageView) rootView.findViewById(R.id.ivYoutube);
        rvFeaturedproduct = (RecyclerView) rootView.findViewById(R.id.rv_featuredproduct);
        tvfeaturedNoData = (TextView) rootView.findViewById(R.id.tvfeaturedNoData);
        scrollView = (NestedScrollView) rootView.findViewById(R.id.scrollView);
        tvFeaturedseeall = (TextView) rootView.findViewById(R.id.tv_featuredseeall);
        rvWomancategory = (RecyclerView) rootView.findViewById(R.id.rv_womancategory);
        tvLike = (TextView) rootView.findViewById(R.id.tvLike);
        tvFollow = (TextView) rootView.findViewById(R.id.tvFollow);
//        carousellist = (CarouselView) rootView.findViewById(R.id.carousellist);
//        carouselRecentlyview = (CarouselView) rootView.findViewById(R.id.carousel_recentlyview);
        tvLatestProduct = (TextView) rootView.findViewById(R.id.tv_latestProduct);
        rvMerchant = (RecyclerView) rootView.findViewById(R.id.rv_Merchant);


    }

    private void initComponent() {
        progressDialog = new ProgressDialog(getActivity());
        loginPreferences = new LoginPreferences(getActivity());
        extraPreferences = new ExtraPreferences(getActivity());
        sliderList = new ArrayList<>();

      /*  layouts = new int[]{
                R.drawable.ic_cart,
                R.drawable.ic_cart,
                R.drawable.ic_cart,
                R.drawable.ic_cart};

*/

        if (!extraPreferences.getProductid().equals("")) {
            ProductID = extraPreferences.getProductid();
            Log.e("ProductID==", ProductID);
        }
        if (new ExtraPreferences(activity).getLanguage().equalsIgnoreCase("Arabic")) {
            ivYoutube.setRotation(0);
            ivInstagram.setRotation(0);
            ivFacebook.setRotation(0);
            ivTwitter.setRotation(0);
        }

        if (Constant.isNetworkAvailable(activity)) {
            GetHomeScreen();
        } else {
            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
        }

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                shopByProductAdapter.getFilter().filter(s);
                shopByCategoryAdapter.getFilter().filter(s);
                homeWomanAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
            }
        });

        tvFeaturedseeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, ProductActivity.class)
                        .putExtra("Action", "1"));
                Functions.animNext(activity);

            }
        });
        tvLatestProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, SubCategoryActivity1.class);
                startActivity(i);
            }
        });

    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            addBottomDots(position);


            // changing the next button text 'NEXT' / 'GOT IT'
           /* if (position == 3) {
                // last page. make button text to GOT IT
                lnr_skip.setVisibility(View.GONE);
                card_start.setVisibility(View.VISIBLE);
            } else {
                // still pages are left
                lnr_skip.setVisibility(View.VISIBLE);
                card_start.setVisibility(View.GONE);
            }*/
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    private void addBottomDots(int currentPage) {
        dots = new TextView[sliderList.size()];
        int colorsActive[] = new int[dots.length];
        int colorsInactive[] = new int[dots.length];
        for (int i = 0; i < dots.length; i++) {
            colorsActive[i] = getResources().getColor(R.color.sky);
            colorsInactive[i] = getResources().getColor(R.color.colorGrey);
        }
        layoutDotsSlide.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(activity);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            layoutDotsSlide.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);

    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.item_homeslider, container, false);
            ImageView ivImage = view.findViewById(R.id.ivImage);

            Picasso.get()
                    .load(Constant.Slider_Image + sliderList.get(position).getSliderImage())
                    .error(R.drawable.demo)
                    .into(ivImage);

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return sliderList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    private void GetHomeScreen() {

        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        Log.e("language==", new ExtraPreferences(activity).getLanguage());
        if (loginPreferences.isLoggIn()) {
            UserId = loginPreferences.getUserID();

            Log.e("UserID==", loginPreferences.getUserID());
        } else {
            UserId = "0";
        }


        ApiInterface apiService = ApiClient.getClient(getActivity()).create(ApiInterface.class);
        Call<HomeScreenModel> call = apiService.GetHomeScreen(Constant.ACCESSKEY, new ExtraPreferences(activity).getLanguage(), UserId, ProductID);
        call.enqueue(new Callback<HomeScreenModel>() {
            @Override
            public void onResponse(Call<HomeScreenModel> call, Response<HomeScreenModel> response) {
                progressDialog.dismiss();
                //Log.e("Store==", response.body().toString());
                if (response.body().getStatus().equals("1")) {
                    tvNoData.setVisibility(View.GONE);
                    lnrmain.setVisibility(View.VISIBLE);

                    //=============================Slider================================
                    sliderList = response.body().getSlider();
                    myViewPagerAdapter = new MyViewPagerAdapter();
                    viewSlide.setAdapter(myViewPagerAdapter);
                    viewSlide.addOnPageChangeListener(viewPagerPageChangeListener);
                    addBottomDots(0);

                    NUM_PAGES = sliderList.size() + 1;

                    if (sliderList.size() > 0) {
                        handler = new Handler();
                        Update = new Runnable() {
                            public void run() {
                                if (currentPage == NUM_PAGES - 1) {
                                    currentPage = 0;
                                }
                                viewSlide.setCurrentItem(currentPage++, true);
                            }
                        };

                        timer = new Timer();
                        timer.schedule(new TimerTask() { // task to be scheduled
                            @Override
                            public void run() {
                                handler.post(Update);
                            }
                        }, DELAY_MS, PERIOD_MS);
                    }


                    //=====================================Shop_By_Category============================

                    if (response.body().getShopByCategory().size() > 0 && response.body().getShopByCategory() != null) {
                        List<HomeScreenModel.ShopByCategory> shopbycategoryList = new ArrayList<>();
                        shopbycategoryList = response.body().getShopByCategory();
                        rvShopByCategory.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
                        shopByCategoryAdapter = new ShopByCategoryAdapter(activity, shopbycategoryList);
                        rvShopByCategory.setAdapter(shopByCategoryAdapter);
                    }

                    //===================================Shop_By_Product===============================
                    if (response.body().getShopByProducts().size() > 0 && response.body().getShopByProducts() != null) {
                        List<HomeScreenModel.ShopByProduct> shopByProductsList = new ArrayList<>();
                        shopByProductsList = response.body().getShopByProducts();
                        rvProduct.setLayoutManager(new LinearLayoutManager(activity));
                        shopByProductAdapter = new ShopByProductAdapter(activity, shopByProductsList);
                        rvProduct.setAdapter(shopByProductAdapter);
                    }

                    //=================================Category==========================================
                   /* if (response.body().getCategory().size() > 0 && response.body().getCategory() != null) {
                        List<HomeScreenModel.Category> categorylist = new ArrayList<>();
                        categorylist = response.body().getCategory();
                        Constant.categorylist = response.body().getCategory();
                        rvcategory.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
                        rvcategory.setAdapter(new CategoryAdapter(activity, categorylist, -1));
                    }*/

                    //=================================Merchant List==========================================
                    if (response.body().getMerchantList().size() > 0 && response.body().getMerchantList() != null) {
                        rvMerchant.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
                        rvMerchant.setAdapter(new HomeMerchantAdapter(activity,response.body().getMerchantList()));
                    }

                    //=================================Featured Product==========================================
//                    if (response.body().getFeaturedProducts().size() > 0 && response.body().getFeaturedProducts() != null) {
//                        rvFeaturedproduct.setVisibility(View.GONE);
//                        tvfeaturedNoData.setVisibility(View.GONE);
//                        List<HomeScreenModel.FeaturedProduct> featuredproductlist = new ArrayList<>();
//                        featuredproductlist = response.body().getFeaturedProducts();
//                        rvFeaturedproduct.setLayoutManager(new GridLayoutManager(activity, 2));
//                        rvFeaturedproduct.setAdapter(new FeaturedProductAapter(activity, featuredproductlist));
//
//                        carousellist.setVisibility(View.VISIBLE);
//                        carousellist.setSize(response.body().getFeaturedProducts().size());
//                        carousellist.setResource(R.layout.item_recentlyview);
//                        carousellist.setAutoPlay(false);
//                        carousellist.setIndicatorAnimationType(IndicatorAnimationType.THIN_WORM);
//                        carousellist.setCarouselOffset(OffsetType.START);
//                        final List<HomeScreenModel.FeaturedProduct> finalFeaturedproductlist = featuredproductlist;
//                        carousellist.setCarouselViewListener(new CarouselViewListener() {
//                            @Override
//                            public void onBindView(View view, int position) {
//                                // Example here is setting up a full image carousel
//                                ImageView imageView = view.findViewById(R.id.ivProductImage);
//                                TextView tvProductName = view.findViewById(R.id.tvProductName);
//                                TextView tvPrice = view.findViewById(R.id.tvPrice);
//
//                                RequestOptions options = new RequestOptions()
//                                        .centerCrop()
//                                        .error(R.drawable.demo)
//                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                        .priority(Priority.HIGH);
//
//                                Glide.with(activity)
//                                        .load(Constant.Product_Image + finalFeaturedproductlist.get(position).getProductImage())
//                                        .apply(options)
//                                        .thumbnail(Glide.with(activity).load(R.drawable.loader))
//                                        .into(imageView);
//
//                                tvProductName.setText(finalFeaturedproductlist.get(position).getProductName());
//                                tvPrice.setText(getResources().getString(R.string.rupee)+ " " + finalFeaturedproductlist.get(position).getProductPrice());
//                            }
//                        });
//                        // After you finish setting up, show the CarouselView
//                        carousellist.show();

                    } else {
                        rvFeaturedproduct.setVisibility(View.GONE);
                        carousellist.setVisibility(View.GONE);
//                        tvfeaturedNoData.setVisibility(View.VISIBLE);
                    }

                    //=================================Recently View==========================================
                    if (response.body().getRecentlyViewed().size() > 0 && response.body().getRecentlyViewed() != null) {
                        rvRecentlyView.setVisibility(View.VISIBLE);
                        tvrecentlyNoData.setVisibility(View.GONE);
                        List<HomeScreenModel.RecentlyViewed> recentlyViewedList = new ArrayList<>();
                        recentlyViewedList = response.body().getRecentlyViewed();
                        rvRecentlyView.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
                        rvRecentlyView.setAdapter(new RecentlyViewAapter(activity, recentlyViewedList));
                    } else {
                        rvRecentlyView.setVisibility(View.GONE);
//                        tvrecentlyNoData.setVisibility(View.VISIBLE);
                    }

                    //=================================Woman view==========================================

                    if (response.body().getWomenCategory().getSubCategory().size() > 0) {
                        rvWomancategory.setVisibility(View.VISIBLE);
                        rvWomancategory.setLayoutManager(new LinearLayoutManager(activity));
                        homeWomanAdapter = new HomeWomanAdapter(activity, response.body().getWomenCategory().getSubCategory(), response.body().getWomenCategory().getImage());
                        rvWomancategory.setAdapter(homeWomanAdapter);

                    } else {
                        rvWomancategory.setVisibility(View.GONE);
//                    }
//                } else {
//                    tvNoData.setVisibility(View.VISIBLE);
//                    lnrmain.setVisibility(View.GONE);
//                    //Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                }

                //=================================Recently Add==========================================
//                if (response.body().getRecentlyAdded().size() > 0 && response.body().getRecentlyAdded() != null) {
//                    carouselRecentlyview.setVisibility(View.VISIBLE);
//
//                    carouselRecentlyview.setSize(response.body().getRecentlyAdded().size());
//                    carouselRecentlyview.setResource(R.layout.item_recentlyview);
//                    carouselRecentlyview.setAutoPlay(false);
//                    carouselRecentlyview.setIndicatorAnimationType(IndicatorAnimationType.THIN_WORM);
//                    carouselRecentlyview.setCarouselOffset(OffsetType.START);
//                    final List<HomeScreenModel.RecentlyAdded> recentlyAddedList = response.body().getRecentlyAdded();
//                    carouselRecentlyview.setCarouselViewListener(new CarouselViewListener() {
//                        @Override
//                        public void onBindView(View view, int position) {
//                            // Example here is setting up a full image carousel
//                            ImageView imageView = view.findViewById(R.id.ivProductImage);
//                            TextView tvProductName = view.findViewById(R.id.tvProductName);
//                            TextView tvPrice = view.findViewById(R.id.tvPrice);
//
//                            RequestOptions options = new RequestOptions()
//                                    .centerCrop()
//                                    .error(R.drawable.demo)
//                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                    .priority(Priority.HIGH);
//
//                            Glide.with(activity)
//                                    .load(Constant.Product_Image + recentlyAddedList.get(position).getProductImage())
//                                    .apply(options)
//                                    .thumbnail(Glide.with(activity).load(R.drawable.loader))
//                                    .into(imageView);
//
//                            tvProductName.setText(recentlyAddedList.get(position).getProductName());
//                            tvPrice.setText(getResources().getString(R.string.rupee)+ " " + recentlyAddedList.get(position).getPrice().get(0).getSellPrice());
//                        }
//                    });
//                    // After you finish setting up, show the CarouselView
//                    carouselRecentlyview.show();
//
//                } else {
//                    carouselRecentlyview.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onFailure(Call<HomeScreenModel> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
                Log.e("NETWORK ERROR --> ", t.toString());
            }
        });
    }

    @Override
    public void onDetach() {
        handler.removeCallbacks(Update);
        timer.cancel();
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        if (handler != null) {
            handler.removeCallbacks(Update);
            timer.cancel();
        }
        super.onDestroy();
    }
}
