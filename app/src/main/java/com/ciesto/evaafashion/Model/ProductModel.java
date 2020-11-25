package com.ciesto.evaafashion.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductModel {


    @SerializedName("Products")
    @Expose
    private List<Product> products = null;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Status")
    @Expose
    private String status;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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


    public class Price {

        @SerializedName("Price_Id")
        @Expose
        private String priceId;
        @SerializedName("MRP")
        @Expose
        private String mRP;
        @SerializedName("Sell_Price")
        @Expose
        private String sellPrice;
        @SerializedName("In_Stock")
        @Expose
        private String inStock;
        @SerializedName("Discount_Percent")
        @Expose
        private String discountPercent;
        @SerializedName("Discounted_Price")
        @Expose
        private String discountedPrice;
        @SerializedName("In_Cart")
        @Expose
        private String inCart;

        public String getPriceId() {
            return priceId;
        }

        public void setPriceId(String priceId) {
            this.priceId = priceId;
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

        public String getInStock() {
            return inStock;
        }

        public void setInStock(String inStock) {
            this.inStock = inStock;
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

    }


    public class Product {

        @SerializedName("Product_Id")
        @Expose
        private String productId;
        @SerializedName("Product_Name")
        @Expose
        private String productName;
        @SerializedName("Product_Image")
        @Expose
        private String productImage;
        @SerializedName("Wishlist")
        @Expose
        private String wishlist;
        @SerializedName("Cart")
        @Expose
        private String cart;
        @SerializedName("Cart_Price_Id")
        @Expose
        private String cartPriceId;
        @SerializedName("Product_Status")
        @Expose
        private String productStatus;
        @SerializedName("Merchant_Id")
        @Expose
        private Integer merchantId;
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

        public String getCart() {
            return cart;
        }

        public void setCart(String cart) {
            this.cart = cart;
        }

        public String getCartPriceId() {
            return cartPriceId;
        }

        public void setCartPriceId(String cartPriceId) {
            this.cartPriceId = cartPriceId;
        }

        public String getProductStatus() {
            return productStatus;
        }

        public void setProductStatus(String productStatus) {
            this.productStatus = productStatus;
        }

        public Integer getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(Integer merchantId) {
            this.merchantId = merchantId;
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
