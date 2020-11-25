package com.ciesto.evaafashion.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MerchantDetailsModel
{


        @SerializedName("Merchant")
        @Expose
        private Merchant merchant;
        @SerializedName("Message")
        @Expose
        private String message;
        @SerializedName("Status")
        @Expose
        private String status;

        public Merchant getMerchant() {
            return merchant;
        }

        public void setMerchant(Merchant merchant) {
            this.merchant = merchant;
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




    public class Merchant {

        @SerializedName("Merchant_Id")
        @Expose
        private String merchantId;
        @SerializedName("Merchant_Name")
        @Expose
        private String merchantName;
        @SerializedName("Profile_Image")
        @Expose
        private String profileImage;
        @SerializedName("Cover_Image")
        @Expose
        private String coverImage;
        @SerializedName("Bio")
        @Expose
        private String bio;
        @SerializedName("Products")
        @Expose
        private List<Product> products = null;

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

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }



        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
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

    }


    public class Product {

        @SerializedName("Product_Id")
        @Expose
        private String productId;
        @SerializedName("Product_Name")
        @Expose
        private String productName;
        @SerializedName("About_Product")
        @Expose
        private String aboutProduct;
        @SerializedName("Product_Image")
        @Expose
        private String productImage;

        @SerializedName("Wishlist")
        @Expose
        private String wishlist;
        @SerializedName("Product_Price")
        @Expose
        private String productPrice;
        @SerializedName("Price")
        @Expose
        private List<Price> price = null;

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

        public String getAboutProduct() {
            return aboutProduct;
        }

        public void setAboutProduct(String aboutProduct) {
            this.aboutProduct = aboutProduct;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public String getWishlist() {
            return wishlist;
        }

        public void setWishlist(String wishlist) {
            this.wishlist = wishlist;
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


    }
}
