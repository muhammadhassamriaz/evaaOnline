package com.ciesto.evaafashion.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeScreenModel {
    public static class Category implements Parcelable {

        @SerializedName("Category_Id")
        @Expose
        private String categoryId;
        @SerializedName("Category")
        @Expose
        private String category;
        @SerializedName("Image")
        @Expose
        private String image;

        public Category(Parcel in) {
            categoryId = in.readString();
            category = in.readString();
            image = in.readString();
        }

        public static final Creator<Category> CREATOR = new Creator<Category>() {
            @Override
            public Category createFromParcel(Parcel in) {
                return new Category(in);
            }

            @Override
            public Category[] newArray(int size) {
                return new Category[size];
            }
        };


        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(categoryId);
            dest.writeString(category);
            dest.writeString(image);
        }
    }

    public class RecentlyViewed {

        @SerializedName("Product_Id")
        @Expose
        private String productId;
        @SerializedName("Product_Name")
        @Expose
        private String productName;
        @SerializedName("Product_Image")
        @Expose
        private String productImage;
        @SerializedName("Product_Price")
        @Expose
        private String productPrice;
        @SerializedName("Price")
        @Expose
        private List<Price> price = null;

        @SerializedName("Merchant_Id")
        @Expose
        private String merchantId;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public List<Price> getPrice() {
            return price;
        }

        public void setPrice(List<Price> price) {
            this.price = price;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

    }


    public class RecentlyAdded {

        @SerializedName("Product_Id")
        @Expose
        private String productId;
        @SerializedName("Product_Name")
        @Expose
        private String productName;
        @SerializedName("Product_Image")
        @Expose
        private String productImage;
        @SerializedName("Price")
        @Expose
        private List<Price_> price = null;

        public List<Price_> getPrice() {
            return price;
        }

        public void setPrice(List<Price_> price) {
            this.price = price;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

    }

    public class Price_ {

        @SerializedName("M_Price_Id")
        @Expose
        private String mPriceId;
        @SerializedName("MRP")
        @Expose
        private String mRP;
        @SerializedName("Sell_Price")
        @Expose
        private String sellPrice;
        @SerializedName("Attributes")
        @Expose
        private String attributes;
        @SerializedName("Unit")
        @Expose
        private String unit;
        @SerializedName("Stock")
        @Expose
        private String stock;
        @SerializedName("Price_Id")
        @Expose
        private String priceId;
        @SerializedName("Availability")
        @Expose
        private String availability;
        @SerializedName("In_Stock")
        @Expose
        private String inStock;
        @SerializedName("SKU")
        @Expose
        private String sKU;
        @SerializedName("Discount_Percent")
        @Expose
        private String discountPercent;
        @SerializedName("Discounted_Price")
        @Expose
        private String discountedPrice;
        @SerializedName("In_Cart")
        @Expose
        private String inCart;
        @SerializedName("Merchant_Id")
        @Expose
        private String merchantId;

        public String getMPriceId() {
            return mPriceId;
        }

        public void setMPriceId(String mPriceId) {
            this.mPriceId = mPriceId;
        }

        public String getMRP() {
            return mRP;
        }

        public void setMRP(String mRP) {
            this.mRP = mRP;
        }

        public String getSellPrice() {
            return sellPrice;
        }

        public void setSellPrice(String sellPrice) {
            this.sellPrice = sellPrice;
        }

        public String getAttributes() {
            return attributes;
        }

        public void setAttributes(String attributes) {
            this.attributes = attributes;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getPriceId() {
            return priceId;
        }

        public void setPriceId(String priceId) {
            this.priceId = priceId;
        }

        public String getAvailability() {
            return availability;
        }

        public void setAvailability(String availability) {
            this.availability = availability;
        }

        public String getInStock() {
            return inStock;
        }

        public void setInStock(String inStock) {
            this.inStock = inStock;
        }

        public String getSKU() {
            return sKU;
        }

        public void setSKU(String sKU) {
            this.sKU = sKU;
        }

        public String getDiscountPercent() {
            return discountPercent;
        }

        public void setDiscountPercent(String discountPercent) {
            this.discountPercent = discountPercent;
        }

        public String getDiscountedPrice() {
            return discountedPrice;
        }

        public void setDiscountedPrice(String discountedPrice) {
            this.discountedPrice = discountedPrice;
        }

        public String getInCart() {
            return inCart;
        }

        public void setInCart(String inCart) {
            this.inCart = inCart;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

    }

    @SerializedName("Slider")
    @Expose
    private List<Slider> slider = null;
    @SerializedName("Shop_By_Category")
    @Expose
    private List<ShopByCategory> shopByCategory = null;
    @SerializedName("Shop_By_Products")
    @Expose
    private List<ShopByProduct> shopByProducts = null;
    @SerializedName("Category")
    @Expose
    private List<Category> category = null;
    @SerializedName("Recently_Viewed")
    @Expose
    private List<RecentlyViewed> recentlyViewed = null;
    @SerializedName("Featured_Products")
    @Expose
    private List<FeaturedProduct> featuredProducts = null;
    @SerializedName("Merchant_List")
    @Expose
    private List<MerchantList> merchantList = null;
    @SerializedName("Women_Category")
    @Expose
    private WomenCategory womenCategory;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Status")
    @Expose
    private String status;

    @SerializedName("Recently_Added")
    @Expose
    private List<RecentlyAdded> recentlyAdded = null;

    public List<RecentlyAdded> getRecentlyAdded() {
        return recentlyAdded;
    }

    public void setRecentlyAdded(List<RecentlyAdded> recentlyAdded) {
        this.recentlyAdded = recentlyAdded;
    }

    public List<Slider> getSlider() {
        return slider;
    }

    public void setSlider(List<Slider> slider) {
        this.slider = slider;
    }

    public List<ShopByCategory> getShopByCategory() {
        return shopByCategory;
    }

    public void setShopByCategory(List<ShopByCategory> shopByCategory) {
        this.shopByCategory = shopByCategory;
    }

    public List<ShopByProduct> getShopByProducts() {
        return shopByProducts;
    }

    public void setShopByProducts(List<ShopByProduct> shopByProducts) {
        this.shopByProducts = shopByProducts;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<RecentlyViewed> getRecentlyViewed() {
        return recentlyViewed;
    }

    public void setRecentlyViewed(List<RecentlyViewed> recentlyViewed) {
        this.recentlyViewed = recentlyViewed;
    }

    public List<FeaturedProduct> getFeaturedProducts() {
        return featuredProducts;
    }

    public void setFeaturedProducts(List<FeaturedProduct> featuredProducts) {
        this.featuredProducts = featuredProducts;
    }

    public WomenCategory getWomenCategory() {
        return womenCategory;
    }

    public void setWomenCategory(WomenCategory womenCategory) {
        this.womenCategory = womenCategory;
    }

    public List<MerchantList> getMerchantList() {
        return merchantList;
    }

    public void setMerchantList(List<MerchantList> merchantList) {
        this.merchantList = merchantList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public class FeaturedProduct {

        @SerializedName("Product_Id")
        @Expose
        private String productId;
        @SerializedName("Product_Name")
        @Expose
        private String productName;
        @SerializedName("Product_Image")
        @Expose
        private String productImage;
        @SerializedName("Product_Price")
        @Expose
        private String productPrice;
        @SerializedName("Price")
        @Expose
        private List<Price> price = null;
        @SerializedName("Merchant_Id")
        @Expose
        private String merchantId;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public List<Price> getPrice() {
            return price;
        }

        public void setPrice(List<Price> price) {
            this.price = price;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

    }


    public class Price {

        @SerializedName("M_Price_Id")
        @Expose
        private String mPriceId;
        @SerializedName("MRP")
        @Expose
        private String mRP;
        @SerializedName("Sell_Price")
        @Expose
        private String sellPrice;
        @SerializedName("Attributes")
        @Expose
        private String attributes;
        @SerializedName("Unit")
        @Expose
        private String unit;
        @SerializedName("Stock")
        @Expose
        private String stock;
        @SerializedName("Price_Id")
        @Expose
        private String priceId;
        @SerializedName("Availability")
        @Expose
        private String availability;
        @SerializedName("In_Stock")
        @Expose
        private String inStock;
        @SerializedName("SKU")
        @Expose
        private String sKU;
        @SerializedName("Discount_Percent")
        @Expose
        private String discountPercent;
        @SerializedName("Discounted_Price")
        @Expose
        private String discountedPrice;
        @SerializedName("In_Cart")
        @Expose
        private String inCart;
        @SerializedName("Merchant_Id")
        @Expose
        private String merchantId;

        public String getMPriceId() {
            return mPriceId;
        }

        public void setMPriceId(String mPriceId) {
            this.mPriceId = mPriceId;
        }

        public String getMRP() {
            return mRP;
        }

        public void setMRP(String mRP) {
            this.mRP = mRP;
        }

        public String getSellPrice() {
            return sellPrice;
        }

        public void setSellPrice(String sellPrice) {
            this.sellPrice = sellPrice;
        }

        public String getAttributes() {
            return attributes;
        }

        public void setAttributes(String attributes) {
            this.attributes = attributes;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getPriceId() {
            return priceId;
        }

        public void setPriceId(String priceId) {
            this.priceId = priceId;
        }

        public String getAvailability() {
            return availability;
        }

        public void setAvailability(String availability) {
            this.availability = availability;
        }

        public String getInStock() {
            return inStock;
        }

        public void setInStock(String inStock) {
            this.inStock = inStock;
        }

        public String getSKU() {
            return sKU;
        }

        public void setSKU(String sKU) {
            this.sKU = sKU;
        }

        public String getDiscountPercent() {
            return discountPercent;
        }

        public void setDiscountPercent(String discountPercent) {
            this.discountPercent = discountPercent;
        }

        public String getDiscountedPrice() {
            return discountedPrice;
        }

        public void setDiscountedPrice(String discountedPrice) {
            this.discountedPrice = discountedPrice;
        }

        public String getInCart() {
            return inCart;
        }

        public void setInCart(String inCart) {
            this.inCart = inCart;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

    }


    public class ShopByCategory {

        @SerializedName("Category_Id")
        @Expose
        private String categoryId;
        @SerializedName("Category")
        @Expose
        private String category;
        @SerializedName("Sub_Category_Id")
        @Expose
        private String subCategoryId;
        @SerializedName("Sub_Category")
        @Expose
        private String subCategory;
        @SerializedName("Image")
        @Expose
        private String image;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(String subCategory) {
            this.subCategory = subCategory;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }


    public class ShopByProduct {

        @SerializedName("Category_Id")
        @Expose
        private String categoryId;
        @SerializedName("Category")
        @Expose
        private String category;
        @SerializedName("Subcategory")
        @Expose
        private List<Subcategory> subcategory = null;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public List<Subcategory> getSubcategory() {
            return subcategory;
        }

        public void setSubcategory(List<Subcategory> subcategory) {
            this.subcategory = subcategory;
        }

    }


    public class Slider {

        @SerializedName("Slider_Id")
        @Expose
        private String sliderId;
        @SerializedName("Slider_Image")
        @Expose
        private String sliderImage;

        public String getSliderId() {
            return sliderId;
        }

        public void setSliderId(String sliderId) {
            this.sliderId = sliderId;
        }

        public String getSliderImage() {
            return sliderImage;
        }

        public void setSliderImage(String sliderImage) {
            this.sliderImage = sliderImage;
        }

    }


    public class SubCategory1 {

        @SerializedName("Sub_Category_Id")
        @Expose
        private String subCategoryId;
        @SerializedName("Sub_Category")
        @Expose
        private String subCategory;
        @SerializedName("Image")
        @Expose
        private String image;

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(String subCategory) {
            this.subCategory = subCategory;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }


    public class Subcategory {

        @SerializedName("Sub_Category_Id")
        @Expose
        private String subCategoryId;
        @SerializedName("Sub_Category")
        @Expose
        private String subCategory;
        @SerializedName("Image")
        @Expose
        private String image;

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(String subCategory) {
            this.subCategory = subCategory;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }


    public class WomenCategory {

        @SerializedName("Category_Id")
        @Expose
        private String categoryId;
        @SerializedName("Category")
        @Expose
        private String category;
        @SerializedName("Image")
        @Expose
        private String image;
        @SerializedName("Sub_Category")
        @Expose
        private List<SubCategory1> subCategory = null;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public List<SubCategory1> getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(List<SubCategory1> subCategory) {
            this.subCategory = subCategory;
        }

    }

    public class MerchantList {

        @SerializedName("Merchant_Id")
        @Expose
        private String merchantId;
        @SerializedName("Merchant_Name")
        @Expose
        private String merchantName;
        @SerializedName("Mobile_No")
        @Expose
        private String mobileNo;
        @SerializedName("Profile_Image")
        @Expose
        private String profileImage;

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

    }

}
