package com.ciesto.evaafashion.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartModel {



    public static class CartDatum
    {

        @SerializedName("Cart_Id")
        @Expose
        private String cartId;
        @SerializedName("Qty")
        @Expose
        private String qty;
        @SerializedName("Product_Id")
        @Expose
        private String productId;
        @SerializedName("Stock_Id")
        @Expose
        private String stockId;
        @SerializedName("Product_Name")
        @Expose
        private String productName;
        @SerializedName("Product_Image")
        @Expose
        private String productImage;
        @SerializedName("Product_Type")
        @Expose
        private String productType;
        @SerializedName("MRP")
        @Expose
        private String mRP;
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
        @SerializedName("Product_Price")
        @Expose
        private String productPrice;
        @SerializedName("Discount_Percent")
        @Expose
        private String discountPercent;
        @SerializedName("Discounted_Price")
        @Expose
        private String discountedPrice;
        @SerializedName("Cart_Price_Id")
        @Expose
        private String cartPriceId;
        @SerializedName("Merchant_Id")
        @Expose
        private String merchantId;

        public String getCartId() {
            return cartId;
        }

        public void setCartId(String cartId) {
            this.cartId = cartId;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getStockId() {
            return stockId;
        }

        public void setStockId(String stockId) {
            this.stockId = stockId;
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

        public String getMRP() {
            return mRP;
        }

        public void setMRP(String mRP) {
            this.mRP = mRP;
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

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
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

        public String getCartPriceId() {
            return cartPriceId;
        }

        public void setCartPriceId(String cartPriceId) {
            this.cartPriceId = cartPriceId;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

    }


        @SerializedName("Products")
        @Expose
        private List<Product> products = null;
        @SerializedName("Cart_Total")
        @Expose
        private String cartTotal;
        @SerializedName("Grand_Total")
        @Expose
        private String grandTotal;
        @SerializedName("Delivery_Charge")
        @Expose
        private String deliveryCharge;
        @SerializedName("Merchant_Ids")
        @Expose
        private String merchantIds;
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

        public String getCartTotal() {
            return cartTotal;
        }

        public void setCartTotal(String cartTotal) {
            this.cartTotal = cartTotal;
        }

        public String getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(String grandTotal) {
            this.grandTotal = grandTotal;
        }

        public String getDeliveryCharge() {
            return deliveryCharge;
        }

        public void setDeliveryCharge(String deliveryCharge) {
            this.deliveryCharge = deliveryCharge;
        }

        public String getMerchantIds() {
            return merchantIds;
        }

        public void setMerchantIds(String merchantIds) {
            this.merchantIds = merchantIds;
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




    public class Product
    {

        @SerializedName("Merchant_Name")
        @Expose
        private String merchantName;


        @SerializedName("Merchant_Id")
        @Expose
        private String merchantId;

        @SerializedName("Mobile_No")
        @Expose
        private String mobileNo;
        @SerializedName("Email_Id")
        @Expose
        private String emailId;
        @SerializedName("Profile_Image")
        @Expose
        private String profileImage;
        @SerializedName("Address")
        @Expose
        private String address;
        @SerializedName("Total")
        @Expose
        private String total;
        @SerializedName("Sub_Total")
        @Expose
        private String subTotal;
        @SerializedName("Delivery_Charge")
        @Expose
        private String deliveryCharge;
        @SerializedName("Cart_Data")
        @Expose
        private List<CartDatum> cartData = null;

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

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getSubTotal() {
            return subTotal;
        }

        public void setSubTotal(String subTotal) {
            this.subTotal = subTotal;
        }

        public String getDeliveryCharge() {
            return deliveryCharge;
        }

        public void setDeliveryCharge(String deliveryCharge) {
            this.deliveryCharge = deliveryCharge;
        }

        public List<CartDatum> getCartData() {
            return cartData;
        }

        public void setCartData(List<CartDatum> cartData) {
            this.cartData = cartData;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }
    }

}
