package com.ciesto.evaafashion.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ciesto.evaafashion.Adapter.AttributeAdapter;
import com.ciesto.evaafashion.Adapter.BottomAdapter;
import com.ciesto.evaafashion.Adapter.ReviewAdapter;
import com.ciesto.evaafashion.Adapter.SimilrProductAdapter;
import com.ciesto.evaafashion.Model.ProductDetailsModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.ExtraPreferences;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends BaseActivity {
    protected ViewPager viewPager;
    protected TextView tvProductName;
    protected TextView tvDiscountPrice;
    protected TextView tvPrice;
    protected RecyclerView rvProductAttribute;
    protected TextView tvQty;
    protected TextView tvMinus;
    protected TextView tvPlus;
    protected LinearLayout llAddToWishList;
    protected LinearLayout llAddToCart;
    protected TextView tvAboutProduct;
    protected TextView tvDiscountPercent;
    protected RecyclerView rvBottom;
    protected LinearLayout layoutDots_slide;
    protected TextView tvWishlist;
    protected ImageView imgWishlist;
    protected ScrollView scrollView;
    protected LinearLayout linearLayout;
    protected NavigationView navView;
    protected DrawerLayout drawerLayout;
    protected TextView tvAddtocart;
    protected RecyclerView rvReview;
    protected TextView tvstock;
    protected TextView tvsku;
    protected TextView tvcategory;
    protected TextView tvdes;
    Activity activity = ProductDetailsActivity.this;
    protected RecyclerView rvViewSimilarList;
    protected TextView tvNoDataSimilarProduct;
    protected AppCompatRatingBar ratingBarAverage;
    protected TextView tvWriteReview;
    Dialog builder;
    String review = "", rating = "", title = "", wishlist;
    String Productid, MerchantId, StockID, PriceID, Productnm, ProductImage, ProductRating, Cart, productType;
    ProgressDialog progressDialog;
    LoginPreferences loginPreferences;
    int count = 1;
    List<ProductDetailsModel.GalleryImage> galleryImageList;
    private TextView[] dots;
    String UserId;
    List<ProductDetailsModel.AttributeDatum> attributeDatumList;
    List<ProductDetailsModel.PriceDatum> pricelist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_product_details);
        super.onCreateDrawer();
        initView();
        initcomponent();
        Setlistner();
    }

    private void initView() {
        tvProductName = (TextView) findViewById(R.id.tvProductName);
        tvDiscountPrice = (TextView) findViewById(R.id.tvDiscountPrice);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvDiscountPercent = (TextView) findViewById(R.id.tvDiscountPercent);

        rvProductAttribute = (RecyclerView) findViewById(R.id.rvProductAttribute);
        rvViewSimilarList = (RecyclerView) findViewById(R.id.rvViewSimilarList);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        layoutDots_slide = (LinearLayout) findViewById(R.id.layoutDots_slide);


        tvQty = (TextView) findViewById(R.id.tvQty);
        tvMinus = (TextView) findViewById(R.id.tvMinus);
        tvPlus = (TextView) findViewById(R.id.tvPlus);

        llAddToWishList = (LinearLayout) findViewById(R.id.llAddToWishList);
        llAddToCart = (LinearLayout) findViewById(R.id.llAddToCart);
        tvAboutProduct = (TextView) findViewById(R.id.tvAboutProduct);
        tvNoDataSimilarProduct = (TextView) findViewById(R.id.tvNoDataSimilarProduct);
        ratingBarAverage = (AppCompatRatingBar) findViewById(R.id.ratingBarAverage);
        tvWriteReview = (TextView) findViewById(R.id.tvWriteReview);

        rvBottom = (RecyclerView) findViewById(R.id.rv_bottom);
        tvMenu = (ImageView) findViewById(R.id.tv_menu);
        ivNotification = (ImageView) findViewById(R.id.ivNotification);
        ivCart = (ImageView) findViewById(R.id.ivCart);
        tvWishlist = (TextView) findViewById(R.id.tv_wishlist);
        imgWishlist = (ImageView) findViewById(R.id.img_wishlist);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        navView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        tvAddtocart = (TextView) findViewById(R.id.tv_addtocart);
        lnrLogo = (LinearLayout) findViewById(R.id.lnr_logo);
        rvReview = (RecyclerView) findViewById(R.id.rv_review);
        tvstock = (TextView) findViewById(R.id.tvstock);
        tvsku = (TextView) findViewById(R.id.tvsku);
        tvcategory = (TextView) findViewById(R.id.tvcategory);
        tvdes = (TextView) findViewById(R.id.tvdes);

    }

    private void initcomponent() {

        Productid = getIntent().getStringExtra("ProductId");
        MerchantId = getIntent().getStringExtra("MerchantId");

        Log.e("ProductID==", Productid);
        Log.e("MerchantId==", MerchantId);
        attributeDatumList = new ArrayList<>();
        pricelist = new ArrayList<>();
        progressDialog = new ProgressDialog(activity);
        loginPreferences = new LoginPreferences(activity);


        if (loginPreferences.isLoggIn()) {
            UserId = loginPreferences.getUserID();
            tvWriteReview.setVisibility(View.VISIBLE);
        } else {
            UserId = "0";
            tvWriteReview.setVisibility(View.GONE);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        setBottom();
        if (Constant.isNetworkAvailable(activity)) {
            GetProductDetails();
        } else {
            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
        }
    }


    private void Setlistner() {
        tvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                tvQty.setText("" + count);
            }
        });

        tvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 1) {
                    count--;
                    tvQty.setText("" + count);
                }
            }
        });

        tvWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewDialog();
            }
        });

        llAddToWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginPreferences.isLoggIn()) {
                    if (wishlist.equalsIgnoreCase("No")) {
                        if (Constant.isNetworkAvailable(activity)) {
                            AddToWishlist();
                        } else {
                            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(activity, getResources().getString(R.string.review_validation), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    startActivity(new Intent(activity, LoginActivity.class));
                }

            }
        });
        llAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (productType.equalsIgnoreCase("Variable")) {
                    String selectattribute = "";

                    for (int i = 0; i < attributeDatumList.size(); i++) {
                        List<ProductDetailsModel.Unit> unitlist = new ArrayList<>();
                        unitlist = attributeDatumList.get(i).getUnits();
                        for (int j = 0; j < unitlist.size(); j++) {
                            if (unitlist.get(j).isSelected()) {
                                selectattribute = selectattribute + unitlist.get(j).getUnitId() + ",";
                            }
                        }
                    }
                    Log.e("Select attribute==", selectattribute);

                    for (int i = 0; i < pricelist.size(); i++) {

                        if (selectattribute.equals(pricelist.get(i).getUnit() + ",")) {
                            Log.e("Condition PriceID==", pricelist.get(i).getPriceId());
                            Cart = pricelist.get(i).getInCart();
                            PriceID = pricelist.get(i).getPriceId();
                        }
                    }

                } else {
                    Cart = pricelist.get(0).getInCart();
                    PriceID = pricelist.get(0).getPriceId();
                }
                if (loginPreferences.isLoggIn()) {
                    if (Cart.equalsIgnoreCase("No")) {
                        tvAddtocart.setText(getResources().getString(R.string.add_to_cart));
                        startActivity(new Intent(activity, MerchantActivity.class)
                                .putExtra("ProductID", Productid)
                                .putExtra("PriceID", PriceID)
                                .putExtra("Productnm", Productnm)
                                .putExtra("Productimg", ProductImage)
                                .putExtra("Productrating", ProductRating)
                                .putExtra("Qty", tvQty.getText().toString()));
                        Functions.animNext(activity);

                    } else {
                        tvAddtocart.setText(getResources().getString(R.string.added_to_cart));
                        Toast.makeText(activity, getResources().getString(R.string.cart_validation), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    startActivity(new Intent(activity, LoginActivity.class));
                }

            }
        });

    }

    //========================================GetProductDetails=======================
    private void GetProductDetails() {

        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        final ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<ProductDetailsModel> call = apiService.GetProductDetails(Constant.ACCESSKEY, new ExtraPreferences(activity).getLanguage(), UserId, Productid, MerchantId);
        call.enqueue(new Callback<ProductDetailsModel>() {
            @Override
            public void onResponse(Call<ProductDetailsModel> call, Response<ProductDetailsModel> response) {
                progressDialog.dismiss();
                //Log.e("Store==", response.body().toString());
                if (response.body().getStatus().equals("1")) {

                    ProductDetailsModel.Products bean = response.body().getProducts();
                    Productnm = bean.getProductName();
                    ProductImage = bean.getProductImage();
                    ProductRating = bean.getRating();
                    wishlist = bean.getWishlist();
                    productType = bean.getProduct_Type();
                    //  Cart = bean.getCart();

                    tvProductName.setText(bean.getProductName());
                    tvAboutProduct.setText(bean.getAboutProduct());
                    tvcategory.setText(bean.getSubCategory());

                    if(bean.getShortDescription().equals(""))
                    {
                        tvdes.setText("-");
                    }
                    else
                    {
                        tvdes.setText(bean.getShortDescription());
                    }

                    ratingBarAverage.setRating(Float.parseFloat(bean.getRating()));

                    if (wishlist.equalsIgnoreCase("No")) {
                        llAddToWishList.setBackground(getResources().getDrawable(R.drawable.bg_reg_simple));
                        tvWishlist.setTextColor(getResources().getColor(R.color.colorBlack));
                        imgWishlist.setColorFilter(getResources().getColor(R.color.colorBlack));
                        tvWishlist.setText(getResources().getString(R.string.add_to_wishlist));
                    } else {
                        llAddToWishList.setBackground(getResources().getDrawable(R.drawable.green_rectangle));
                        tvWishlist.setTextColor(getResources().getColor(R.color.White));
                        imgWishlist.setColorFilter(getResources().getColor(R.color.White));
                        tvWishlist.setText(getResources().getString(R.string.added_to_wishlist));
                    }

                    //========setMinPrice===========

                    if (bean.getPriceData() != null && bean.getPriceData().size() > 0) {
                        if (pricelist.size() > 0)
                            pricelist.clear();
                        pricelist = bean.getPriceData();
                        // getMinValue(bean.getPriceData());

                        String DiscountPrice = "", DiscountPer = "0";
                        DiscountPer = pricelist.get(0).getDiscountPercent();
                        DiscountPrice = pricelist.get(0).getDiscountedPrice();

                        tvPrice.setText(getResources().getString(R.string.rupee) + " "+ pricelist.get(0).getSellPrice());
                        tvsku.setText(pricelist.get(0).getSKU());

                         if (bean.getPriceData().get(0).getStock().equals("0")|| bean.getPriceData().get(0).getStock().equals("")) {
                            tvstock.setText(bean.getProductStatus());
                        } else {
                            tvstock.setText(bean.getProductStatus() + " (" + pricelist.get(0).getStock() + ")");
                        }
                        if (Integer.parseInt(DiscountPer) > 0) {
                            tvDiscountPrice.setVisibility(View.VISIBLE);
                            tvDiscountPercent.setVisibility(View.VISIBLE);

                            tvDiscountPrice.setPaintFlags(tvDiscountPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            tvDiscountPrice.setText(getResources().getString(R.string.rupee)+ " " + DiscountPrice);
                            tvDiscountPercent.setText("( " + DiscountPer + " % off )");
                        } else {
                            tvDiscountPrice.setVisibility(View.GONE);
                            tvDiscountPercent.setVisibility(View.GONE);
                        }

                    }

                    //=================Store ProductID=============

                    String productid = "";
                    ExtraPreferences extraPreferences = new ExtraPreferences(activity);

                    if (!extraPreferences.getProductid().equals("")) {
                        if (extraPreferences.getProductid().contains(bean.getProductId() + ",")) {
                        } else {
                            productid = extraPreferences.getProductid();
                            productid = productid + bean.getProductId() + ",";
                            extraPreferences.setProductid(productid);
                        }
                    } else {
                        extraPreferences.setProductid(bean.getProductId() + ",");
                    }


                    //===============Similar Products=======================

                    if (response.body().getSimilarProduts().size() > 0 && response.body().getSimilarProduts() != null) {
                        tvNoDataSimilarProduct.setVisibility(View.GONE);
                        rvViewSimilarList.setVisibility(View.VISIBLE);
                        List<ProductDetailsModel.SimilarProdut> similarProdutList = new ArrayList<>();
                        similarProdutList = response.body().getSimilarProduts();

                        rvViewSimilarList.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
                        rvViewSimilarList.setAdapter(new SimilrProductAdapter(activity, similarProdutList));
                    } else {
                        tvNoDataSimilarProduct.setVisibility(View.VISIBLE);
                        rvViewSimilarList.setVisibility(View.GONE);
                    }

                    //==================Set Attribute====================

                    if (bean.getAttributeData().size() > 0 && bean.getAttributeData() != null) {

                        if (attributeDatumList.size() > 0)
                            attributeDatumList.clear();
                        attributeDatumList = bean.getAttributeData();
                        rvProductAttribute.setLayoutManager(new LinearLayoutManager(activity));
                        rvProductAttribute.setAdapter(new AttributeAdapter(activity, attributeDatumList));
                    }

                    //===================Set GalleryImage===========

                    if (bean.getGalleryImage().size() > 0 && bean.getGalleryImage() != null) {
                        galleryImageList = new ArrayList<>();
                        galleryImageList = bean.getGalleryImage();

                        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
                        viewPager.setAdapter(myViewPagerAdapter);
                        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
                        addBottomDots(0);
                    }


                    //==================================================Add to cart Condition==========================
                    if (productType.equalsIgnoreCase("Variable")) {
                        if (pricelist.size() != 0 && pricelist != null) {
                            Cart = pricelist.get(0).getInCart();
                        }
                    } else {
                        Cart = pricelist.get(0).getInCart();
                        PriceID = pricelist.get(0).getPriceId();
                    }
                    if (Cart.equalsIgnoreCase("No"))
                        tvAddtocart.setText(getResources().getString(R.string.add_to_cart));
                    else
                        tvAddtocart.setText(getResources().getString(R.string.added_to_cart));


                    //=====================GetReviews=====================
                    if (bean.getReviews().size() > 0 && bean.getReviews() != null) {
                        rvReview.setLayoutManager(new LinearLayoutManager(activity));
                        rvReview.setAdapter(new ReviewAdapter(activity, bean.getReviews()));
                    }


                } else {

                    // Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductDetailsModel> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
                Log.e("NETWORK ERROR --> ", t.toString());
            }
        });
    }

    public void getMinValue(List<ProductDetailsModel.PriceDatum> numbers) {
        int Price = Integer.parseInt(numbers.get(0).getSellPrice());

        String DiscountPrice = "", DiscountPer = "0";
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.size() != 1) {

                if (Integer.parseInt(numbers.get(i).getSellPrice()) < Price) {
                    Price = Integer.parseInt(numbers.get(i).getSellPrice());

                    DiscountPer = numbers.get(i).getDiscountPercent();
                    DiscountPrice = numbers.get(i).getDiscountedPrice();

                }
            } else {
                Price = Integer.parseInt(numbers.get(i).getSellPrice());
                DiscountPer = numbers.get(i).getDiscountPercent();
                DiscountPrice = numbers.get(i).getDiscountedPrice();

            }
        }

           /*  Log.e("DiscountPrice==",DiscountPrice);
            Log.e("DiscountPer==",DiscountPer);
        */

        tvPrice.setText(getResources().getString(R.string.rupee)+ " " + Price);

        if (Integer.parseInt(DiscountPer) > 0) {
            tvDiscountPrice.setVisibility(View.VISIBLE);
            tvDiscountPercent.setVisibility(View.VISIBLE);

            tvDiscountPrice.setPaintFlags(tvDiscountPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvDiscountPrice.setText(getResources().getString(R.string.rupee) + " "+ DiscountPrice);
            tvDiscountPercent.setText("( " + DiscountPer + " % off )");
        } else {
            tvDiscountPrice.setVisibility(View.GONE);
            tvDiscountPercent.setVisibility(View.GONE);
        }

    }


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            addBottomDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };


    private void addBottomDots(int currentPage) {
        dots = new TextView[galleryImageList.size()];
        int colorsActive[] = new int[dots.length];
        int colorsInactive[] = new int[dots.length];
        for (int i = 0; i < dots.length; i++) {
            colorsActive[i] = getResources().getColor(R.color.sky);
            colorsInactive[i] = getResources().getColor(R.color.colorGrey);
        }

        layoutDots_slide.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(activity);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            layoutDots_slide.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);

    }

    //================================Viewpager Adapter=====================

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.item_singleimage, container, false);
            ImageView ivImage = view.findViewById(R.id.ivImage);

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    //.placeholder(R.drawable.demo)
                    .error(R.drawable.demo)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);

            Glide.with(activity)
                    .load(Constant.Product_Image + galleryImageList.get(position).getGalleryImage())
                    .apply(options)
                    .thumbnail(Glide.with(activity).load(R.drawable.loader))
                    .into(ivImage);


            //tv_title.setText(reviewlist.get(position).getTitle());
            //  tv_des.setText(reviewlist.get(position).getDes());
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return galleryImageList.size();
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


    //=============================ReviewDialog=================================

    private void ReviewDialog() {
        builder = new Dialog(activity);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view1 = LayoutInflater.from(activity).inflate(R.layout.review_dialog, null);

        ImageView img_close = view1.findViewById(R.id.img_close);
        TextView tv_cancle = view1.findViewById(R.id.tv_cancle);
        TextView tv_add = view1.findViewById(R.id.tv_add);
        final EditText edt_review = view1.findViewById(R.id.edt_review);
        final EditText edt_title = view1.findViewById(R.id.edt_title);
        final RatingBar ratingBar_review = view1.findViewById(R.id.ratingBar_review);


        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                review = edt_review.getText().toString();
                title = edt_title.getText().toString();
                rating = String.valueOf(ratingBar_review.getRating());

                Log.e("Rating==", rating);

                if (TextUtils.isEmpty(title)) {
                    Toast.makeText(activity, getResources().getString(R.string.please_enter_title), Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(review)) {
                    Toast.makeText(activity, getResources().getString(R.string.please_enter_review), Toast.LENGTH_SHORT).show();
                } else {
                    builder.dismiss();
                    if (Constant.isNetworkAvailable(activity)) {
                        AddReviews();
                    } else {
                        Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });


        builder.setCancelable(false);
        // builder.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_round));
        builder.setContentView(view1);
        builder.show();
    }

    //=============================AddReview=================================

    private void AddReviews() {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<JsonObject> call = apiService.AddReview(Constant.ACCESSKEY, loginPreferences.getUserID(), Productid, rating, review, title);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1")) {
                        Toast.makeText(activity, object.getString("Message"), Toast.LENGTH_SHORT).show();
                        GetProductDetails();

                    } else {
                        Toast.makeText(activity, object.getString("Message").toString(), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
                Log.e("NETWORK ERROR --> ", t.toString());
            }
        });
    }

    //=============================AddToWishlist=================================

    private void AddToWishlist() {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        Log.e("Productid1==",Productid);

        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<JsonObject> call = apiService.AddToWishlist(Constant.ACCESSKEY, loginPreferences.getUserID(), Productid, "0", "0");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1")) {
                        Toast.makeText(activity, object.getString("Message"), Toast.LENGTH_SHORT).show();
                        if(new ExtraPreferences(activity).getWhishlistcount().equals(""))
                        {
                            new ExtraPreferences(activity).setWhishlistcount("1");
                        }
                        else {
                            int count = Integer.parseInt(new ExtraPreferences(activity).getWhishlistcount()) + 1;
                            new ExtraPreferences(activity).setWhishlistcount(String.valueOf(count));
                        }
                        setBottom();
                        GetProductDetails();

                    } else {
                        Toast.makeText(activity, object.getString("Message").toString(), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
                Log.e("NETWORK ERROR --> ", t.toString());
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(activity);
    }


    private void setBottom() {
        RecyclerView rvBottom = findViewById(R.id.rv_bottom);

        rvBottom.setHasFixedSize(true);
        rvBottom.setLayoutManager(new GridLayoutManager(activity, 5));
        rvBottom.setNestedScrollingEnabled(false);
        rvBottom.setAdapter(new BottomAdapter(activity, 100));
    }
}
