package com.ciesto.evaafashion.Reset;


import com.ciesto.evaafashion.Model.AddressModel;
import com.ciesto.evaafashion.Model.AtoZModel;
import com.ciesto.evaafashion.Model.CartModel;
import com.ciesto.evaafashion.Model.CategoryModel;
import com.ciesto.evaafashion.Model.CouponModel;
import com.ciesto.evaafashion.Model.FilterModel;
import com.ciesto.evaafashion.Model.HomeScreenModel;
import com.ciesto.evaafashion.Model.MerchantDetailsModel;
import com.ciesto.evaafashion.Model.MerchantFilterModel;
import com.ciesto.evaafashion.Model.MerchantModel;
import com.ciesto.evaafashion.Model.PandingOrderModel;
import com.ciesto.evaafashion.Model.OrderDetails;
import com.ciesto.evaafashion.Model.ProductDetailsModel;
import com.ciesto.evaafashion.Model.ProductModel;
import com.ciesto.evaafashion.Model.SubCategoryModel;
import com.ciesto.evaafashion.Model.WardrobModel;
import com.ciesto.evaafashion.Model.WishlistModel;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("log_out")
    Call<JsonObject> Logout(@Field("AccessKey") String key,
                            @Field("Customer_Id") String Customer_Id);


    @FormUrlEncoded
    @POST("customer_signup_new")
    Call<JsonObject> SignupMobile(@Field("AccessKey") String key,
                                  @Field("Status") String Status,
                                  @Field("Mobile_No") String Mobile_No);


    @FormUrlEncoded
    @POST("customer_signup_new")
    Call<JsonObject> Signup(@Field("AccessKey") String key,
                            @Field("Status") String Status,
                            @Field("Name") String Name,
                            @Field("Mobile_No") String Mobile_No,
                            @Field("Email_Id") String Email_Id,
                            @Field("Password") String Password,
                            @Field("Gender") String Gender,
                            @Field("Device_Key") String Device_Key);

    @Multipart
    @POST("customer_profile")
    Call<JsonObject> EditProfile(@Part("AccessKey") RequestBody key,
                                 @Part("Status") RequestBody Status,
                                 @Part("Customer_Id") RequestBody Customer_Id,
                                 @Part("First_Name") RequestBody First_Name,
                                 @Part("Email_Id") RequestBody Email_Id,
                                 @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("customer_profile")
    Call<JsonObject> GetProfile(@Field("AccessKey") String key,
                                @Field("Status") String Status,
                                @Field("Customer_Id") String Customer_Id);


    @FormUrlEncoded
    @POST("customerlogin")
    Call<JsonObject> Login(@Field("AccessKey") String key,
                           @Field("Mobile_No") String Mobile_No,
                           @Field("Password") String Password,
                           @Field("Device_Key") String DeviceKey);

    @FormUrlEncoded
    @POST("change_device_key")
    Call<JsonObject> UdpateKey(@Field("AccessKey") String key,
                               @Field("type") String type,
                               @Field("id") String id,
                               @Field("device_key") String device_key);


    @FormUrlEncoded
    @POST("customer_profile")
    Call<JsonObject> ChangePassword(@Field("AccessKey") String key,
                                    @Field("Customer_Id") String Customer_Id,
                                    @Field("Password") String Password,
                                    @Field("Old_Password") String Old_Password,
                                    @Field("Status") String Status);

    @FormUrlEncoded
    @POST("forgotpassword")
    Call<JsonObject> Forget1(@Field("AccessKey") String key,
                             @Field("Mobile_No") String Mobile_No,
                             @Field("Type") String Type);

    @FormUrlEncoded
    @POST("update_forgotpassword")
    Call<JsonObject> Forget2(@Field("AccessKey") String key,
                             @Field("User_Id") String User_Id,
                             @Field("Type") String Type,
                             @Field("Password") String Password);

    @FormUrlEncoded
    @POST("customer_profile")
    Call<JsonObject> AddNewAddress(@Field("AccessKey") String key,
                                   @Field("Customer_Id") String Customer_Id,
                                   @Field("Status") String Status,
                                   @Field("Building_Name") String Building_Name,
                                   @Field("Area_Name") String Area_Name,
                                   @Field("Landmark") String Landmark,
                                   @Field("Country") String Country,
                                   @Field("State") String State,
                                   @Field("City") String City,
                                   @Field("Pincode") String Pincode,
                                   @Field("IsActive") String IsActive,
                                   @Field("IsPrimary") String IsPrimary,
                                   @Field("Shipping_Address") String Shipping_Address,
                                   @Field("Latitude") String Latitude,
                                   @Field("Longitude") String Longitude,
                                   @Field("Address_Id") String Address_Id);


    @FormUrlEncoded
    @POST("customer_profile")
    Call<AddressModel> ViewAddress(@Field("AccessKey") String key,
                                   @Field("Status") String Status,
                                   @Field("Customer_Id") String Customer_Id);

    @FormUrlEncoded
    @POST("customer_profile")
    Call<JsonObject> SetPrimaryAddress(@Field("AccessKey") String key,
                                       @Field("Status") String Status,
                                       @Field("Customer_Id") String Customer_Id,
                                       @Field("Address_Id") String Address_Id);


    @FormUrlEncoded
    @POST("homescreen")
    Call<HomeScreenModel> GetHomeScreen(@Field("AccessKey") String key,
                                        @Field("Language") String Language,
                                        @Field("Customer_Id") String Customer_Id,
                                        @Field("Product_Ids") String Product_Ids);


    @FormUrlEncoded
    @POST("category_product")
    Call<CategoryModel> GetCategory(@Field("AccessKey") String key,
                                    @Field("Language") String Language);

    @FormUrlEncoded
    @POST("scategory_list")
    Call<SubCategoryModel> GetSubCategory(@Field("AccessKey") String key,
                                          @Field("Language") String Language,
                                          @Field("Category_Id") String Category_Id);

    @FormUrlEncoded
    @POST("scategory_product")
    Call<ProductModel> GetProduct(@Field("AccessKey") String key,
                                  @Field("Language") String Language,
                                  @Field("Sub_Category_Id") String Sub_Category_Id,
                                  @Field("Featured") String Featured);

    @FormUrlEncoded
    @POST("productdetail")
    Call<ProductDetailsModel> GetProductDetails(@Field("AccessKey") String key,
                                                @Field("Language") String Language,
                                                @Field("Customer_Id") String Customer_Id,
                                                @Field("Product_Id") String Product_Id,
                                                @Field("Merchant_Id") String Merchant_Id);

    @FormUrlEncoded
    @POST("add_productreview")
    Call<JsonObject> AddReview(@Field("AccessKey") String key,
                               @Field("Customer_Id") String Customer_Id,
                               @Field("Product_Id") String Product_Id,
                               @Field("Rating") String Rating,
                               @Field("Review") String Review,
                               @Field("Title") String Title);

    @FormUrlEncoded
    @POST("addtowishlist")
    Call<JsonObject> AddToWishlist(@Field("AccessKey") String key,
                                   @Field("Customer_Id") String Customer_Id,
                                   @Field("Product_Id") String Product_Id,
                                   @Field("Stock_Id") String Stock_Id,
                                   @Field("Merchant_Id") String Merchant_Id);

    @FormUrlEncoded
    @POST("getwishlist")
    Call<WishlistModel> GetWishlist(@Field("AccessKey") String key,
                                    @Field("Language") String Language,
                                    @Field("Customer_Id") String Customer_Id);

    @FormUrlEncoded
    @POST("getmerchants_byproduct")
    Call<MerchantModel> GetMerchantByProduct(@Field("AccessKey") String key,
                                             @Field("Language") String Language,
                                             @Field("Product_Id") String Product_Id,
                                             @Field("Price_Id") String Price_Id);

    @FormUrlEncoded
    @POST("addtocart")
    Call<JsonObject> AddToCart(@Field("AccessKey") String key,
                               @Field("Customer_Id") String Customer_Id,
                               @Field("Merchant_Id") String Merchant_Id,
                               @Field("Product_Id") String Product_Id,
                               @Field("Stock_Id") String Stock_Id,
                               @Field("Qty") String Qty,
                               @Field("Price_Id") String Price_Id,
                               @Field("M_Price_Id") String M_Price_Id);


    @FormUrlEncoded
    @POST("shoppingcart")
    Call<CartModel> GetShoppingCart(@Field("AccessKey") String key,
                                    @Field("Language") String Language,
                                    @Field("Customer_Id") String Customer_Id);

    @FormUrlEncoded
    @POST("removecart")
    Call<JsonObject> RemoveCart(@Field("AccessKey") String key,
                                @Field("Cart_Id") String Cart_Id);


    @FormUrlEncoded
    @POST("updatecart")
    Call<JsonObject> UpdateCart(@Field("AccessKey") String key,
                                @Field("Cart_Data") String Cart_Data);

    @FormUrlEncoded
    @POST("getcoupon")
    Call<CouponModel> GetCoupon(@Field("AccessKey") String key,
                                @Field("Merchant_Ids") String Merchant_Ids);

    @FormUrlEncoded
    @POST("checkcoupon")
    Call<JsonObject> Checkcoupon(@Field("AccessKey") String key,
                                 @Field("Coupon_Code") String Coupon_Code,
                                 @Field("Customer_Id") String Customer_Id,
                                 @Field("Grand_Total") String Grand_Total,
                                 @Field("Total") String Total);

    @FormUrlEncoded
    @POST("placeorder")
    Call<JsonObject> PlaceOrder(@Field("AccessKey") String key,
                                @Field("Customer_Id") String Customer_Id,
                                @Field("Shipping_Address") String Shipping_Address,
                                @Field("Total_Amount") String Total_Amount,
                                @Field("Sub_Total") String Sub_Total,
                                @Field("Coupon_Id") String Coupon_Id,
                                @Field("Coupon_Amount") String Coupon_Amount,
                                @Field("Delivery_Charge") String Delivery_Charge);

    @FormUrlEncoded
    @POST("myorders")
    Call<PandingOrderModel> GetPandingOrder(@Field("AccessKey") String key,
                                            @Field("Customer_Id") String Customer_Id,
                                            @Field("Status") String Status);//0->panding,1->confirm,2->cancle

    @FormUrlEncoded
    @POST("get_updatedorder")
    Call<OrderDetails> GetOrderDetails(@Field("AccessKey") String key,
                                       @Field("Language") String Language,
                                       @Field("Customer_Id") String Customer_Id,
                                       @Field("Order_Id") String Order_Id);

    @FormUrlEncoded
    @POST("cancelorder")
    Call<JsonObject> CancleOrder(@Field("AccessKey") String key,
                                 @Field("Order_Id") String Order_Id);

    @FormUrlEncoded
    @POST("app_setting")
    Call<JsonObject> GetCount(@Field("AccessKey") String key,
                              @Field("Type") String Type,
                              @Field("User_Id") String User_Id);

    @FormUrlEncoded
    @POST("get_product_by_filter")
    Call<ProductModel> GetFilter(@Field("AccessKey") String key,
                                 @Field("Language") String Language,
                                 @Field("Customer_Id") String Customer_Id,
                                 @Field("Filter") String Filter,
                                 @Field("Filter_Val") String Filter_Val,
                                 @Field("Sort") String Sort,
                                 @Field("Type") String Type,
                                 @Field("Subcategory_Id") String Subcategory_Id);

    @FormUrlEncoded

    @POST("filters")
    Call<FilterModel> GetFilterData(@Field("AccessKey") String key,
                                    @Field("Language") String Language);


    @FormUrlEncoded
    @POST("merchant_list_by_type")
    Call<AtoZModel> GetMerchantList(@Field("AccessKey") String key,
                                    @Field("Merchant_Type") String Merchant_Type);


    @FormUrlEncoded
    @POST("merchant_details")
    Call<MerchantDetailsModel> GetMerchantDetails(@Field("AccessKey") String key,
                                                  @Field("Merchant_Id") String Merchant_Id,
                                                  @Field("Language") String Language,
                                                  @Field("Customer_Id") String Customer_Id);

    @FormUrlEncoded
    @POST("get_product_by_filter")
    Call<MerchantFilterModel> GetMerchantFilter(@Field("AccessKey") String key,
                                                @Field("Language") String Language,
                                                @Field("Customer_Id") String Customer_Id,
                                                @Field("Filter") String Filter,
                                                @Field("Filter_Val") String Filter_Val,
                                                @Field("Sort") String Sort,
                                                @Field("Type") String Type,
                                                @Field("Merchant_Id") String Merchant_Id,
                                                @Field("Subcategory_Id") String Subcategory_Id);


    @FormUrlEncoded
    @POST("search_merchant")
    Call<AtoZModel> GetMerchantSearch(@Field("AccessKey") String key,
                                      @Field("Merchant_Type") String Merchant_Type,
                                      @Field("Keyword") String Keyword);

    @FormUrlEncoded
    @POST("merchant_list_by_type")
    Call<WardrobModel> GetMerchantListWithDesigner(@Field("AccessKey") String key,
                                                   @Field("Merchant_Type") String Merchant_Type);

    @FormUrlEncoded
    @POST("search_merchant")
    Call<WardrobModel> GetMerchantFilter(@Field("AccessKey") String key,
                                      @Field("Merchant_Type") String Merchant_Type,
                                      @Field("Keyword") String Keyword);
}



