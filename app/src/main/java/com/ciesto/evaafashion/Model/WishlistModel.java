package com.ciesto.evaafashion.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WishlistModel
{


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
        @SerializedName("Product_Type")
        @Expose
        private String productType;
        @SerializedName("Price_Id")
        @Expose
        private String priceId;
        @SerializedName("MRP")
        @Expose
        private String mRP;
        @SerializedName("Sell_Price")
        @Expose
        private String sellPrice;
        @SerializedName("Attribute_Id")
        @Expose
        private String attributeId;
        @SerializedName("Attributes")
        @Expose
        private String attributes;
        @SerializedName("Unit_Ids")
        @Expose
        private String unitIds;
        @SerializedName("Units")
        @Expose
        private String units;
        @SerializedName("Wishlist")
        @Expose
        private String wishlist;
        @SerializedName("Cart")
        @Expose
        private String cart;
        @SerializedName("Cart_Price_Id")
        @Expose
        private String cartPriceId;
        @SerializedName("Shopping_List")
        @Expose
        private String shoppingList;
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

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

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

        public String getAttributeId() {
            return attributeId;
        }

        public void setAttributeId(String attributeId) {
            this.attributeId = attributeId;
        }

        public String getAttributes() {
            return attributes;
        }

        public void setAttributes(String attributes) {
            this.attributes = attributes;
        }

        public String getUnitIds() {
            return unitIds;
        }

        public void setUnitIds(String unitIds) {
            this.unitIds = unitIds;
        }

        public String getUnits() {
            return units;
        }

        public void setUnits(String units) {
            this.units = units;
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

        public String getShoppingList() {
            return shoppingList;
        }

        public void setShoppingList(String shoppingList) {
            this.shoppingList = shoppingList;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }
    }
}
