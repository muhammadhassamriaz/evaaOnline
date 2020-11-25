package com.ciesto.evaafashion.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PandingOrderModel {

    @SerializedName("Orders")
    @Expose
    private List<Order> orders = null;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Status")
    @Expose
    private String status;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
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


    public class Order {

        @SerializedName("Order_Id")
        @Expose
        private String orderId;
        @SerializedName("Order_No")
        @Expose
        private String orderNo;
        @SerializedName("_Merchant_Id")
        @Expose
        private String merchantId;
        @SerializedName("Shipping_Address")
        @Expose
        private String shippingAddress;
        @SerializedName("Order_Status")
        @Expose
        private String orderStatus;
        @SerializedName("Order_Status_Text")
        @Expose
        private String orderStatusText;
        @SerializedName("Remarks")
        @Expose
        private String remarks;
        @SerializedName("Total_Amount")
        @Expose
        private String totalAmount;
        @SerializedName("Sub_Total")
        @Expose
        private String subTotal;
        @SerializedName("Delivery_Charge")
        @Expose
        private String deliveryCharge;
        @SerializedName("Coupon_Amount")
        @Expose
        private String couponAmount;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getShippingAddress() {
            return shippingAddress;
        }

        public void setShippingAddress(String shippingAddress) {
            this.shippingAddress = shippingAddress;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderStatusText() {
            return orderStatusText;
        }

        public void setOrderStatusText(String orderStatusText) {
            this.orderStatusText = orderStatusText;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
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

        public String getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(String couponAmount) {
            this.couponAmount = couponAmount;
        }

    }
}
