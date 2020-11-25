package com.ciesto.evaafashion.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MerchantModel {

    @SerializedName("Merchant")
    @Expose
    private List<Merchant> merchant = null;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Status")
    @Expose
    private String status;

    public List<Merchant> getMerchant() {
        return merchant;
    }

    public void setMerchant(List<Merchant> merchant) {
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
        @SerializedName("Delivery_Charge")
        @Expose
        private String deliveryCharge;
        @SerializedName("Price")
        @Expose
        private String price;
        @SerializedName("M_Price_Id")
        @Expose
        private String mPriceId;
        @SerializedName("Store_Name")
        @Expose
        private String storeName;
        @SerializedName("Stock_Id")
        @Expose
        private String stockId;
        @SerializedName("Product_Id")
        @Expose
        private String productId;
        @SerializedName("Price_Id")
        @Expose
        private String priceId;
        @SerializedName("Address")
        @Expose
        private String address;

        @SerializedName("IsSelected")
        @Expose
        private boolean IsSelected;

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

        public String getDeliveryCharge() {
            return deliveryCharge;
        }

        public void setDeliveryCharge(String deliveryCharge) {
            this.deliveryCharge = deliveryCharge;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getMPriceId() {
            return mPriceId;
        }

        public void setMPriceId(String mPriceId) {
            this.mPriceId = mPriceId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStockId() {
            return stockId;
        }

        public void setStockId(String stockId) {
            this.stockId = stockId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getPriceId() {
            return priceId;
        }

        public void setPriceId(String priceId) {
            this.priceId = priceId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public boolean isSelected() {
            return IsSelected;
        }

        public void setSelected(boolean selected) {
            IsSelected = selected;
        }
    }

}
