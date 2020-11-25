package com.ciesto.evaafashion.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CouponModel
{


    public class Coupon {

        @SerializedName("Coupon_Id")
        @Expose
        private String couponId;
        @SerializedName("Coupon_Code")
        @Expose
        private String couponCode;
        @SerializedName("Coupon_Description")
        @Expose
        private String couponDescription;
        @SerializedName("Discount_Type")
        @Expose
        private String discountType;
        @SerializedName("Coupon_Amount")
        @Expose
        private String couponAmount;
        @SerializedName("Merchant_Id")
        @Expose
        private String merchantId;

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }

        public String getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }

        public String getCouponDescription() {
            return couponDescription;
        }

        public void setCouponDescription(String couponDescription) {
            this.couponDescription = couponDescription;
        }

        public String getDiscountType() {
            return discountType;
        }

        public void setDiscountType(String discountType) {
            this.discountType = discountType;
        }

        public String getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(String couponAmount) {
            this.couponAmount = couponAmount;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

    }


        @SerializedName("Coupons")
        @Expose
        private List<Coupon> coupons = null;
        @SerializedName("Message")
        @Expose
        private String message;
        @SerializedName("Status")
        @Expose
        private String status;

        public List<Coupon> getCoupons() {
            return coupons;
        }

        public void setCoupons(List<Coupon> coupons) {
            this.coupons = coupons;
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



}
